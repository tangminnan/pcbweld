package com.pcbWeld.information.controller;

import com.pcbWeld.common.annotation.Log;
import com.pcbWeld.common.utils.R;
import com.pcbWeld.common.utils.ShiroUtils;
import com.pcbWeld.information.domain.*;
import com.pcbWeld.information.service.*;
import com.pcbWeld.owneruser.domain.OwnerUserDO;
import com.pcbWeld.owneruser.service.OwnerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class IndexController {
    @Autowired
    MaterialItemService materialItemService;
    @Autowired
    MaterialParameterService materialParameterService;
    @Autowired
    OwnerUserService userService;
    @Autowired
    MsgService msgService;
    @Autowired
    OrderService orderService;
    @Autowired
    private ReceiptService receiptService;
    @Autowired
    UserAddressService userAddressService;
    @Autowired
    private MaterialItemInputService materialItemInputService;
    @Autowired
    private MaterialPamSelectService materialPamSelectService;


    @Log("首页")
    @GetMapping("/")
    String index(Model model) {
        Map<String, Object> params = new HashMap<>();
        params.put("status", 2);

        List<MaterialItemDO> materialItemDOS = materialItemService.list(params);
        for (MaterialItemDO materialItemDO : materialItemDOS) {
            params.put("itemId", materialItemDO.getId());

            materialItemDO.setMaterialParameterDO(materialParameterService.list(params));
        }
        System.out.println("===========materialItemDOS====================" + materialItemDOS);

        model.addAttribute("materialItemDOS", materialItemDOS);
        return "jijia";
    }

    @Log("首页")
    @GetMapping("/wode/{text}")
    String wode(@PathVariable("text") String text,Model model) {
        long  id= ShiroUtils.getUserId();
        Map<String, Object> params = new HashMap<>();

        OwnerUserDO user = userService.get(id);


        //资料审核数量
        params.put("orderStatus", 2);
        Integer dataCheckOrder = orderService.list(params).size();

        //资料审核未通过列表数量
        params.remove("orderStatus");
        params.put("orderStatus", 3);
        Integer dataCheckFailedOrder = orderService.list(params).size();

        //待支付列表数量
        params.remove("orderStatus");
        params.put("orderStatus", 4);
        Integer unpaidOrder = orderService.list(params).size();

        //物料寄送列表数量
        params.remove("orderStatus");
        params.put("orderStatus", 5);
        Integer materialDelivery = orderService.list(params).size();

        //物料待审核列表数量
        params.remove("orderStatus");
        params.put("orderStatus", 6);
        Integer materialCheck = orderService.list(params).size();

        //未读消息数量
        List<MsgDO> msgDOS = msgService.userMsgList(id);
        long size = msgDOS.stream().filter(a ->a.getStatue()==1).count();
        model.addAttribute("msgCount",size);
        //用户信息
        model.addAttribute("user", user);
        model.addAttribute("page", "../templates/wode");
        model.addAttribute("context","wode");
        model.addAttribute("text",text);
        if("开票信息".equals(text)) {
            model.addAttribute("page", "../templates/kaipiaoxinxi");
            model.addAttribute("context", "kpxx");
            OwnerUserDO ownerUserDO = receiptService.getReceipt(id);
            model.addAttribute("data",ownerUserDO);
        }else if("收货地址".equals(text)){
            model.addAttribute("page", "../templates/shouhuodizhi");
            model.addAttribute("context", "shdz");
            Map<String,Object> paramsMap = new HashMap<String,Object>();
            paramsMap.put("userId", ShiroUtils.getUserId());
            List<UserAddressDO> address = userAddressService.list(paramsMap);
            model.addAttribute("address",address);
        }else if("待开票金额".equals(text)){
            Map<String,Object> paramsMap = new HashMap<String,Object>();
            paramsMap.put("userId",id) ;
            List<OrderDO> orderList = orderService.list(paramsMap);
            System.out.println("------------------------"+orderList);
            orderList = orderList.stream().filter(a ->a.getOrderStatus()==10).filter(b->b.getInvoiceStatus()!=2).collect(Collectors.toList());
            System.out.println("------------------------"+orderList);
            model.addAttribute("orderList",orderList);
            model.addAttribute("page", "../templates/daikaijine");
            model.addAttribute("context", "dkje");
        }else if("已开发票记录".equals(text)){
            Map<String,Object> paramsMap = new HashMap<String,Object>();
            paramsMap.put("userId",id) ;
            List<ReceiptDO> recepitList = receiptService.list(paramsMap);
            model.addAttribute("recepitList",recepitList);
            model.addAttribute("page", "../templates/yikaifapiao");
            model.addAttribute("context", "ykjl");
        }else if("资料待审核".equals(text)){
            Map<String,Object> paramsMap = new HashMap<String,Object>();
            paramsMap.put("userId",id) ;
            paramsMap.put("orderStatus",2);
            List<OrderDO> orderDOList = orderService.list(paramsMap);
            model.addAttribute("orderDOList",orderDOList);
            model.addAttribute("page", "../templates/wodedingdan");
            model.addAttribute("context", "wddd");
        }else if("资料审核未通过".equals(text)){
            Map<String,Object> paramsMap = new HashMap<String,Object>();
            paramsMap.put("userId",id) ;
            paramsMap.put("orderStatus",3);
            List<OrderDO> orderDOList = orderService.list(paramsMap);
            model.addAttribute("orderDOList",orderDOList);
            model.addAttribute("page", "../templates/wodedingdan");
            model.addAttribute("context", "wddd");
        }else if("待支付".equals(text)){
            Map<String,Object> paramsMap = new HashMap<String,Object>();
            paramsMap.put("userId",id) ;
            paramsMap.put("orderStatus",4);
            List<OrderDO> orderDOList = orderService.list(paramsMap);
            model.addAttribute("orderDOList",orderDOList);
            model.addAttribute("page", "../templates/wodedingdan");
            model.addAttribute("context", "wddd");
        }else if("物料寄送".equals(text)){
            Map<String,Object> paramsMap = new HashMap<String,Object>();
            paramsMap.put("userId",id) ;
            paramsMap.put("orderStatus",5);
            List<OrderDO> orderDOList = orderService.list(paramsMap);
            model.addAttribute("orderDOList",orderDOList);
            model.addAttribute("page", "../templates/wodedingdan");
            model.addAttribute("context", "wddd");
        }else if("物料待审核".equals(text)){
            Map<String,Object> paramsMap = new HashMap<String,Object>();
            paramsMap.put("userId",id) ;
            paramsMap.put("orderStatus",6);
            List<OrderDO> orderDOList = orderService.list(paramsMap);
            model.addAttribute("orderDOList",orderDOList);
            model.addAttribute("page", "../templates/wodedingdan");
            model.addAttribute("context", "wddd");
        }else if("物料审核未通过".equals(text)){
            Map<String,Object> paramsMap = new HashMap<String,Object>();
            paramsMap.put("userId",id) ;
            paramsMap.put("orderStatus",7);
            List<OrderDO> orderDOList = orderService.list(paramsMap);
            model.addAttribute("orderDOList",orderDOList);
            model.addAttribute("page", "../templates/wodedingdan");
            model.addAttribute("context", "wddd");
        }else if("待发货".equals(text)){
            Map<String,Object> paramsMap = new HashMap<String,Object>();
            paramsMap.put("userId",id) ;
            paramsMap.put("orderStatus",8);
            List<OrderDO> orderDOList = orderService.list(paramsMap);
            model.addAttribute("orderDOList",orderDOList);
            model.addAttribute("page", "../templates/wodedingdan");
            model.addAttribute("context", "wddd");
        }else if("待收货".equals(text)){
            Map<String,Object> paramsMap = new HashMap<String,Object>();
            paramsMap.put("userId",id) ;
            paramsMap.put("orderStatus",9);
            List<OrderDO> orderDOList = orderService.list(paramsMap);
            model.addAttribute("orderDOList",orderDOList);
            model.addAttribute("page", "../templates/wodedingdan");
            model.addAttribute("context", "wddd");
        } else if("全部订单".equals(text)){
            Map<String,Object> paramsMap = new HashMap<String,Object>();
            paramsMap.put("userId",id) ;
            List<OrderDO> orderDOList = orderService.list(paramsMap);
            model.addAttribute("orderDOList",orderDOList);
            model.addAttribute("page", "../templates/wodedingdan");
            model.addAttribute("context", "wddd");
        } else if("已完成".equals(text)){
            Map<String,Object> paramsMap = new HashMap<String,Object>();
            paramsMap.put("userId",id) ;
            paramsMap.put("orderStatus",10);
            List<OrderDO> orderDOList = orderService.list(paramsMap);
            model.addAttribute("orderDOList",orderDOList);
            model.addAttribute("page", "../templates/wodedingdan");
            model.addAttribute("context", "wddd");
        }else if("消息中心".equals(text)){
            model.addAttribute("msglist",msgDOS);
            model.addAttribute("page", "../templates/xiaoxizhongxin");
            model.addAttribute("context", "xxzx");
        }else if("个人资料".equals(text)){
            OwnerUserDO ownerUserDO = userService.get(id);
            model.addAttribute("user",ownerUserDO);
            model.addAttribute("page", "../templates/xiugaiziliao");
            model.addAttribute("context", "xgzl");
        }else if("个人中心".equals(text)){
            model.addAttribute("page", "../templates/wode");
            model.addAttribute("context", "wode");
        }


        return "main";
    }

    /**
     *  时间查询订单
     */
    @GetMapping("/wodeorder")
    public String wodeorder(@RequestParam("startdate") Date startdate,
                            @RequestParam("enddate") Date enddate,
                            Model model){
        System.out.println("=================================");
        System.out.println(startdate);
        System.out.println(enddate);
        System.out.println("=================================");
        Map<String,Object> paramsMap = new HashMap<String,Object>();
        if(startdate!=null && enddate!=null){
            paramsMap.put("startdate",startdate);
            paramsMap.put("enddate",enddate);
        }else if(startdate==null){
            paramsMap.put("createTime",enddate);
        }
        paramsMap.put("userId",ShiroUtils.getUserId());
        List<OrderDO> list = orderService.list(paramsMap);
        model.addAttribute("orderDOList",list);
        model.addAttribute("page", "../templates/wodedingdan");
        model.addAttribute("context", "wddd");
        model.addAttribute("text","全部订单");
        return "main";
    }

    /**
     *  订单删除
     */

    @PostMapping("/deleteOrderDO")
    @ResponseBody
    public R deleteOrderDO(String orderNo){
        OrderDO orderDO = new OrderDO();
        orderDO.setOrderNo(orderNo);
        orderDO.setDeleteFlag(1);
        if(orderService.updateByOrderNo(orderDO)>0)
            return R.ok();
        return R.error();
    }



    @GetMapping("/itemIdByInput")
    @ResponseBody
    public List<MaterialItemInputDO> itemIdByInput(Long itemId){
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("itemId", itemId);
		return materialItemInputService.list(map);
    	
    }
    
    @GetMapping("/paramsIdBySelect")
    @ResponseBody
    public List<MaterialPamSelectDO> paramsIdBySelect(Integer paramsId){
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("paramsId", paramsId);
		return materialPamSelectService.list(map);
    	
    }
    
}
