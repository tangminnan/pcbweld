package com.pcbWeld.information.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.lowagie.text.pdf.codec.Base64;
import com.pcbWeld.common.FileUtil;
import com.pcbWeld.common.config.BootdoConfig;
import com.pcbWeld.common.utils.*;
import com.pcbWeld.information.domain.OrderDO;
import com.pcbWeld.information.domain.OrderDetailDO;
import com.pcbWeld.information.domain.UserAddressDO;
import com.pcbWeld.information.service.OrderDetailService;
import com.pcbWeld.information.service.OrderService;
import com.pcbWeld.information.service.UserAddressService;
import com.pcbWeld.owneruser.comment.GenerateCode;
import com.pcbWeld.system.config.MessageHandler;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.TextMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 订单
 */
@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    UserAddressService userAddressService;
    @Autowired
    private BootdoConfig bootdoConfig;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private MessageHandler messageHandler;

    /**
     * 确定 生成订单
     */
    @GetMapping("/information/order/createOrder")
    String createOrder(Model model) {
        //物料数据及价格
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("userId", ShiroUtils.getUserId());
        List<UserAddressDO> addressList = userAddressService.list(paramsMap);
        model.addAttribute("addresslist", addressList);
        UserAddressDO userAddressDO = new UserAddressDO();
        List<UserAddressDO> adlist = addressList.stream().filter(a -> "0".equals(a.getDefaultFlag())).collect(Collectors.toList());
        if (adlist.size() > 0)
            userAddressDO = adlist.get(0);
        model.addAttribute("address", userAddressDO);
        model.addAttribute("addressDOList", addressList);
        List<OrderDetailDO> list = ShiroUtils.getUser().getList();
        System.out.println("===============得到物料======================");
        System.out.println(list);
        System.out.println("===============得到物料=====================");
        model.addAttribute("orderDetailList", list);
        model.addAttribute("zj", new BigDecimal(20).add(ShiroUtils.getUser().getZj()));
        return "/tijiaodingdan";
    }

    /**
     * 提交订单
     */
    @PostMapping("/information/order/confirmOrder")
    @ResponseBody
    public R confirmOrder(OrderDO orderDO) {
        //    JSONObject jsonObject =  JSONArray.parseObject(orderDO);
        //    OrderDO order =    JSONObject.toJavaObject(jsonObject,OrderDO.class);
        String orderNo = CommonUtils.generateUUID();
        orderDO.setOrderNo(orderNo);
        orderDO.setUserId(ShiroUtils.getUserId());
        orderDO.setOrderStatus(1);
        orderDO.setInvoiceStatus(1);
        orderDO.setCreateTime(new Date());
        orderDO.setShipmentAmount(new BigDecimal(20));
        orderDO.setOrderAmount(ShiroUtils.getUser().getZj());
        BigDecimal bigDecimal = ShiroUtils.getUser().getZj().add(new BigDecimal(20));
        orderDO.setPayAmount(bigDecimal);
        orderDO.setDeleteFlag(0);
        try {
            if (orderDO.getPcbFile() != null && orderDO.getPcbFile().getSize() > 0) {
                String pcbStr = orderDO.getPcbFile().getOriginalFilename();
                orderDO.setOriginalFilename(pcbStr);
                pcbStr = FileUtil.renameToUUID(pcbStr);
                FileUtil.uploadFile(orderDO.getPcbFile().getBytes(), bootdoConfig.getUploadPath(), pcbStr);
                orderDO.setPcbStr(pcbStr);
                orderDO.setOrderStatus(2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        orderService.save(orderDO);
        List<OrderDetailDO> list = ShiroUtils.getUser().getList();
        Date date = new Date();
        for (OrderDetailDO o : list) {
            o.setCreateTime(date);
            o.setOrderId(orderDO.getId());
            o.setUserId(ShiroUtils.getUserId());
            orderDetailService.save(o);
        }

        return R.ok(orderNo);
    }

    /**
     * 提交订单成功
     */
    @GetMapping("/information/order/confirmSuccess/{orderNo}")
    public String confirmSuccess(@PathVariable("orderNo") String orderNo, Model model) {
        model.addAttribute("orderNo", orderNo);
        return "/tijiaochenggong";
    }

    /**
     * 查看订单详情
     */
    @GetMapping("/information/order/orderDetail/{orderNo}")
    public String orderDetail(@PathVariable("orderNo") String orderNo, Model model) {
        model.addAttribute("orderNo", orderNo);
        OrderDO orderDO = orderService.getOrderDOByOrderNo(orderNo);
        model.addAttribute("orderDO", orderDO);
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("userId", ShiroUtils.getUserId());
        paramsMap.put("orderId", orderDO.getId());
        List<OrderDetailDO> list = orderDetailService.list(paramsMap);
        model.addAttribute("dlist", list);
        return "/dingdanxiangqing";
    }

    /**
     * 文件重新上传
     */
    @PostMapping("/information/order/reUpload")
    @ResponseBody
    public R reUpload(OrderDO orderDO) {
        String pcbStr = "";
        try {
            pcbStr = orderDO.getPcbFile().getOriginalFilename();
            orderDO.setOriginalFilename(pcbStr);
            pcbStr = FileUtil.renameToUUID(pcbStr);
            FileUtil.uploadFile(orderDO.getPcbFile().getBytes(), bootdoConfig.getUploadPath(), pcbStr);
            orderDO.setPcbStr(pcbStr);
            orderDO.setOrderStatus(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        orderService.updateByOrderNo(orderDO);
        return R.ok(pcbStr);
    }

    /**
     * 物料文件下载
     */
    @GetMapping("/information/order/downloadFile/{orderNo}")
    public void downloadFile(@PathVariable("orderNo") String orderNo, HttpServletResponse response) throws FileNotFoundException, UnsupportedEncodingException {
        OrderDO orderDO = orderService.getOrderDOByOrderNo(orderNo);
        File file = new File(bootdoConfig.getUploadPath() + orderDO.getPcbStr());
        if (!file.exists()) {
            return;
        }
        // 设置输出的格式
        response.reset();
        System.out.println("===================================================");
        System.out.println(orderDO.getOriginalFilename());
        System.out.println("=================================================ss");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(orderDO.getOriginalFilename().getBytes(), "ISO8859-1") + "\"");
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
     * 更新运单号
     */
    @PostMapping("/information/order/updatePostid")
    @ResponseBody
    public R updatePostid(OrderDO orderDO) {
        if (orderService.updateByOrderNo(orderDO) > 0)
            return R.ok();
        else
            return R.error();
    }

    @ResponseBody
    @PostMapping("/information/order/saveOrderDetail")
    public R saveOrderDetail(String str, BigDecimal zj) {
        JSONArray jsonArray = JSONObject.parseArray(str);
        List<OrderDetailDO> list = new ArrayList<OrderDetailDO>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            OrderDetailDO orderDetailDO = jsonObject.toJavaObject(OrderDetailDO.class);
            list.add(orderDetailDO);
        }
        ShiroUtils.getUser().setList(list);
        ShiroUtils.getUser().setZj(zj);
        return R.ok();
    }

    /**
     * 在线支付
     */
    @GetMapping("/information/order/zaixianzhifu")
    public String zaixianzhifu(String orderNo, BigDecimal payMount, Model model) {
        model.addAttribute("orderNo", orderNo);
        model.addAttribute("payMount", payMount);
        return "/zhifu";
    }


    /**
     * 发起微信支付
     */

    @GetMapping("/information/order/sendWxPay")
    public String sendWxPay(String orderNo, HttpServletRequest request, Model model, HttpServletResponse response) throws Exception {
        OrderDO orderDO = orderService.getOrderDOByOrderNo(orderNo);
        String codeUrl = unifiedOrder(request,orderDO);
        if(codeUrl!=null) {
            //生成二维码
            Map<EncodeHintType, Object> hints = new HashMap<>();
            //设置纠错等级
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(codeUrl, BarcodeFormat.QR_CODE, 400, 400, hints);
            File outputFile = new File(bootdoConfig.getUploadPath(), orderNo + ".png");
            MatrixToImageWriter.writeToFile(bitMatrix, "png", outputFile);
        }
        model.addAttribute("orderNo",orderNo);
        model.addAttribute("payAmount",orderDO.getPayAmount());
        model.addAttribute("qrCode","/files/"+orderNo+".png");
        model.addAttribute("payType","WEIPAY");

        return "/zhifu2";
    }

    /**
     *  微信支付  统一下单方法
     */
    private String unifiedOrder(HttpServletRequest request, OrderDO orderDO) throws Exception {
            SortedMap<String, String> params = new TreeMap<String,String>();
            params.put("appid", WXPayUtils.APPID);
            params.put("mch_id", WXPayUtils.MERID);
            params.put("nonce_str", CommonUtils.generateUUID());
            params.put("body", "PCB");
            params.put("out_trade_no", orderDO.getOrderNo());
            int total = orderDO.getPayAmount().multiply(new BigDecimal(100)).intValue();
       //     params.put("total_fee",String.valueOf(total));
             params.put("total_fee",String.valueOf(1));
            params.put("spbill_create_ip",CommonUtils.getIpAddr(request));
            params.put("notify_url", WXPayUtils.CALLBACK);
            params.put("trade_type", "NATIVE");
            //签名
            String sign = WXPayUtils.createSign(params, WXPayUtils.APPKEY);
            params.put("sign",sign);
            String payXml = WXPayUtils.mapToXml(params);
            System.out.println(payXml);
            //统一下单
            String orderStr =  HttpUtils.doPost(WXPayUtils.OPENURL,payXml,4);
            if(null==orderStr){
                return null;
            }
            Map<String,String> unifiedOrderMap =  WXPayUtils.xmlToMap(orderStr);
            System.out.println("=================统一下单结果返回==============");
            System.out.println(unifiedOrderMap);
            System.out.println("=================统一下单结果返回==============");
            if(unifiedOrderMap!=null){
                if("SUCCESS".equals(unifiedOrderMap.get("return_code"))
                        &&"SUCCESS".equals(unifiedOrderMap.get("result_code")))
                return unifiedOrderMap.get("code_url");
            }

        return null;
    }

    /**
     * 微信扫码支付回调
     */
    @PostMapping("/payCallback/")
    public void orderCallback(HttpServletRequest request,HttpServletResponse response) throws Exception {
        InputStream inputStream = request.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
        StringBuffer sb = new StringBuffer();
        String line;
        while((line = in.readLine()) !=null){
                sb.append(line);
        }
        in.close();
        inputStream.close();
        Map<String,String> callBackMap = WXPayUtils.xmlToMap(sb.toString());
        System.out.println("=================微信扫码支付回调返回===========================");
        System.out.println(callBackMap);
        System.out.println("=================微信扫码支付回调返回===========================");
        SortedMap<String,String> sortedMap = WXPayUtils.getSortedMap(callBackMap);
        //验证签名是否正确
        if(WXPayUtils.isCorrectSign(sortedMap,WXPayUtils.APPKEY)){
            System.out.println("==============签名正确===================");
            if("SUCCESS".equals(sortedMap.get("result_code"))){
                String outTradeNo = sortedMap.get("out_trade_no");//订单号
                OrderDO orderDO = orderService.getOrderDOByOrderNo(outTradeNo);
                if( orderDO!=null && orderDO.getOrderStatus()<4){//订单未支付，更改订单状态 支付时间
                    System.out.println("统一下单修改订单状态");
                    orderDO.setOrderStatus(5);
                    orderDO.setUpdateTime(new Date());
                    orderDO.setPayType("WEIPAY");
                    int i= orderService.updateByOrderNo(orderDO);
                    if(i==1){
                        TextMessage textMessage = new TextMessage("SUCCESS");//WebSocket消息推送，通知关闭二维码链接
                        messageHandler.sendMessage(outTradeNo, textMessage);
                        System.out.println("===========通知微信支付成功==================");
                        response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>");
                    }
                }
            }
        }
        //支付失败
        response.getWriter().write("<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[报文为空]]></return_msg></xml>");
    }
}