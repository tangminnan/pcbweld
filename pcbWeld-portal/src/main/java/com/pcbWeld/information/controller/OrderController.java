package com.pcbWeld.information.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pcbWeld.common.FileUtil;
import com.pcbWeld.common.config.BootdoConfig;
import com.pcbWeld.common.utils.R;
import com.pcbWeld.common.utils.ShiroUtils;
import com.pcbWeld.information.domain.OrderDO;
import com.pcbWeld.information.domain.UserAddressDO;
import com.pcbWeld.information.service.OrderService;
import com.pcbWeld.information.service.UserAddressService;
import com.pcbWeld.owneruser.comment.GenerateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 订单
 */
@RequestMapping("/information/order")
@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    UserAddressService userAddressService;
    @Autowired
    private BootdoConfig bootdoConfig;
    /**
     *确定 生成订单
     */
    @GetMapping("/createOrder")
    String createOrder(Model model){
        //物料数据及价格
        Map<String,Object> paramsMap = new HashMap<String,Object>();
        paramsMap.put("userId", ShiroUtils.getUserId());
        List<UserAddressDO> addressList = userAddressService.list(paramsMap);
        model.addAttribute("addresslist",addressList);
        UserAddressDO userAddressDO=new UserAddressDO();
        List<UserAddressDO> adlist = addressList.stream().filter(a ->"0".equals(a.getDefaultFlag())).collect(Collectors.toList());
        if(addressList.size()>0)
            userAddressDO=adlist.get(0);
        model.addAttribute("address",userAddressDO);
        return "/tijiaodingdan";
    }

    /**
     * 提交订单
     */
    @PostMapping("/confirmOrder")
    @ResponseBody
    public R confirmOrder(OrderDO orderDO){
  //    JSONObject jsonObject =  JSONArray.parseObject(orderDO);
    //    OrderDO order =    JSONObject.toJavaObject(jsonObject,OrderDO.class);
        String orderNo = GenerateCode.getUUID();
        orderDO.setOrderNo(orderNo);
        orderDO.setUserId(ShiroUtils.getUserId());
        orderDO.setOrderStatus(1);
        orderDO.setInvoiceStatus(1);
        orderDO.setCreateTime(new Date());
        orderDO.setPayAmount(new BigDecimal(0));
        try {
            String pcbStr = orderDO.getPcbFile().getOriginalFilename();
            orderDO.setOriginalFilename(pcbStr);
            pcbStr = FileUtil.renameToUUID(pcbStr);
            FileUtil.uploadFile(orderDO.getPcbFile().getBytes(), bootdoConfig.getUploadPath(), pcbStr);
            orderDO.setPcbStr(pcbStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        orderService.save(orderDO);
        return R.ok(orderNo);
    }

    /**
     * 提交订单成功
     */
    @GetMapping("/confirmSuccess/{orderNo}")
    public String confirmSuccess(@PathVariable("orderNo")String orderNo,  Model model){
        model.addAttribute("orderNo",orderNo);
        return "/tijiaochenggong";
    }

    /**
     *查看订单详情
     */
    @GetMapping("/orderDetail/{orderNo}")
    public String orderDetail(@PathVariable("orderNo")String orderNo,  Model model){
        model.addAttribute("orderNo",orderNo);
        OrderDO orderDO=orderService.getOrderDOByOrderNo(orderNo);
        model.addAttribute("orderDO",orderDO);
        return "/dingdanxiangqing";
    }

    /**
     *文件重新上传
     */
    @PostMapping("/reUpload")
    @ResponseBody
    public R reUpload(OrderDO orderDO){
        String pcbStr="";
        try {
            pcbStr = orderDO.getPcbFile().getOriginalFilename();
            orderDO.setOriginalFilename(pcbStr);
            pcbStr = FileUtil.renameToUUID(pcbStr);
            FileUtil.uploadFile(orderDO.getPcbFile().getBytes(), bootdoConfig.getUploadPath(), pcbStr);
            orderDO.setPcbStr(pcbStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        orderService.updateByOrderNo(orderDO);
        return R.ok(pcbStr);
    }

    /**
     *物料文件下载
     */
    @GetMapping("/downloadFile/{orderNo}")
    public void downloadFile(@PathVariable("orderNo") String orderNo, HttpServletResponse response) throws FileNotFoundException, UnsupportedEncodingException {
        OrderDO orderDO=orderService.getOrderDOByOrderNo(orderNo);
        File file = new File(bootdoConfig.getUploadPath()+orderDO.getPcbStr());
        if(!file.exists()){
            return ;
        }
        // 设置输出的格式
        response.reset();
        System.out.println("===================================================");
        System.out.println(orderDO.getOriginalFilename());
        System.out.println("=================================================ss");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(orderDO.getOriginalFilename().getBytes(),"ISO8859-1") + "\"");
        InputStream inStream = new FileInputStream(file);
        // 循环取出流中的数据
        byte[] b = new byte[100];
        int len;
        try {
            while ((len = inStream.read(b)) > 0)
                response.getOutputStream().write(b, 0, len);
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *更新运单号
     */
    @PostMapping("/updatePostid")
    @ResponseBody
    public R updatePostid(OrderDO orderDO){
        if(orderService.updateByOrderNo(orderDO)>0)
            return R.ok();
        else
            return R.error();
    }
}
