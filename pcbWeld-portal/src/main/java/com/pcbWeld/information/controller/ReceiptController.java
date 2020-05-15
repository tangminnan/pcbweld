package com.pcbWeld.information.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pcbWeld.common.annotation.Log;
import com.pcbWeld.common.utils.R;
import com.pcbWeld.common.utils.ShiroUtils;
import com.pcbWeld.information.domain.OrderDO;
import com.pcbWeld.information.domain.ReceiptDO;
import com.pcbWeld.information.domain.UserAddressDO;
import com.pcbWeld.information.service.OrderService;
import com.pcbWeld.information.service.ReceiptService;
import com.pcbWeld.information.service.UserAddressService;
import com.pcbWeld.owneruser.domain.OwnerUserDO;
import com.pcbWeld.owneruser.service.OwnerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * 发票 开票
 */
@Controller
@RequestMapping("/information/receipt")
public class ReceiptController {
    @Autowired
    private ReceiptService receiptService;
    @Autowired
    private UserAddressService userAddressService;
    @Autowired
    private OwnerUserService ownerUserService;
    @Autowired
    private OrderService orderService;



    @Log("保存发票开票信息")
    @PostMapping("/saveReceipt")
    @ResponseBody
    R saveReceipt(OwnerUserDO ownerUserDO) {
        ownerUserDO.setId(ShiroUtils.getUserId());
        Map<String, Object> map = new HashMap<>();
        if(receiptService.saveReceipt(ownerUserDO)>0) {
           return R.ok();
        }
        return R.error();
    }

    @Log("获取发票开票信息")
    @GetMapping("/getReceipt")
    @ResponseBody
    Map<String,Object> getReceipt(){
        long id = ShiroUtils.getUserId();
        OwnerUserDO ownerUserDO = receiptService.getReceipt(id);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("data",ownerUserDO);
        return map;
    }

    @Log("开发票详情")
    @GetMapping("/createReceipt")
    String createReceipt(String[] orderNos,Model model){

        List<OrderDO> list = orderService.listAllSelectedOrder(orderNos);
        BigDecimal bigDecimal = new BigDecimal(0);
        for(OrderDO orderDO:list){
            bigDecimal=bigDecimal.add(orderDO.getPayAmount());
        }
        model.addAttribute("list",list);
        model.addAttribute("Amount",bigDecimal);
        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("userId",ShiroUtils.getUserId());
        paramsMap.put("defaultFlag",0);
        List<UserAddressDO> addressDOList = userAddressService.list(paramsMap);
        UserAddressDO userAddressDO=null;
        if(list.size()>0)
            userAddressDO=addressDOList.get(0);
        model.addAttribute("userAddressDO",userAddressDO);
        model.addAttribute("addressDOList",addressDOList);
        return "/kaipiaoxiangqing";
    }

    @Log("确定开票")
    @PostMapping("/confirmReceipt")
    @ResponseBody
    public R confirmReceipt(String str){
        JSONObject obj =   JSON.parseObject(str);
        String address = obj.getString("address");
        BigDecimal payAmount = obj.getBigDecimal("payAmount");
        String consignee = obj.getString("consignee");
        String mobile=obj.getString("mobile");
        Object[] orderNos=obj.getJSONArray("orderNos").toArray();
        StringBuffer sbf  =new StringBuffer();
        for(Object s :orderNos){
           sbf.append(s).append(" ");
        }
        ReceiptDO receiptDO  = new ReceiptDO();
        receiptDO.setAddress(address);
        receiptDO.setMobile(mobile);
        receiptDO.setOrderNos(sbf.toString());
        receiptDO.setConsignee(consignee);
        receiptDO.setPayAmount(payAmount);
        receiptDO.setUserId(ShiroUtils.getUserId());
        receiptDO.setCreateTime(new Date());
        OwnerUserDO ownerUserDO= ownerUserService.get(ShiroUtils.getUserId());
        receiptDO.setReceiptAccount(ownerUserDO.getReceiptAccount());
        receiptDO.setReceiptAddress(ownerUserDO.getReceiptAddress());
        receiptDO.setReceiptStatus(0);
        receiptDO.setReceiptBank(ownerUserDO.getReceiptBank());
        receiptDO.setReceiptCompany(ownerUserDO.getCompany());
        receiptDO.setReceiptNumber(ownerUserDO.getReceiptNumber());
        receiptDO.setReceiptPhone(ownerUserDO.getReceiptPhone());
       if(receiptService.save(receiptDO) >0) {
          orderService.updateOrderReceiptStatus(orderNos);
           return R.ok();
       }
       return  R.error();
    }
}

