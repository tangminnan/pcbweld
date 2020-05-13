package com.pcbWeld.information.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pcbWeld.common.annotation.Log;
import com.pcbWeld.common.utils.R;
import com.pcbWeld.common.utils.ShiroUtils;
import com.pcbWeld.information.domain.OrderDO;
import com.pcbWeld.information.domain.ReceiptDO;
import com.pcbWeld.information.domain.UserAddressDO;
import com.pcbWeld.information.service.ReceiptService;
import com.pcbWeld.information.service.UserAddressService;
import com.pcbWeld.owneruser.domain.OwnerUserDO;
import com.pcbWeld.owneruser.service.OwnerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 发票 开票
 */
@RestController
@RequestMapping("/information/receipt")
public class ReceiptController {
    @Autowired
    private ReceiptService receiptService;
    @Autowired
    private UserAddressService userAddressService;
    @Autowired
    private OwnerUserService ownerUserService;

    @Log("保存发票开票信息")
    @PostMapping("/saveReceipt")
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
    Map<String,Object> getReceipt(){
        long id = ShiroUtils.getUserId();
        OwnerUserDO ownerUserDO = receiptService.getReceipt(id);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("data",ownerUserDO);
        return map;
    }

    @Log("开发票详情")
    @GetMapping("/createReceipt")
    String createReceipt(@RequestBody JSONArray orderNo, BigDecimal Amount,Model model){
        List<OrderDO> list = new ArrayList<>();
        for(int i=0;i<orderNo.size();i++){
           JSONObject jsonObject = orderNo.getJSONObject(i);
           OrderDO orderDO = new OrderDO();
           orderDO.setOrderNo(jsonObject.getString("orderNo"));
           orderDO.setPayAmount(jsonObject.getBigDecimal("payAmount"));
           list.add(orderDO);
       }
        model.addAttribute("list",list);
        model.addAttribute("Amount",Amount);
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
    public R confirmReceipt(@RequestBody JSONObject obj){
        String address = obj.getString("address");
        BigDecimal payAmount = obj.getBigDecimal("payAmount");
        String username = obj.getString("username");
        String mobile=obj.getString("mobile");
        String[] orderNos =new String[]{};
        obj.getJSONArray("orderNos").toArray(orderNos);
        ReceiptDO receiptDO  = new ReceiptDO();
        receiptDO.setAddress(address);
        receiptDO.setMobile(mobile);
        receiptDO.setOrderNos(orderNos.toString());
        receiptDO.setUsername(username);
        receiptDO.setPayAmount(payAmount);
        receiptDO.setUserId(ShiroUtils.getUserId());
        OwnerUserDO ownerUserDO= ownerUserService.get(ShiroUtils.getUserId());
        receiptDO.setReceiptAccount(ownerUserDO.getReceiptAccount());
        receiptDO.setReceiptAddress(ownerUserDO.getReceiptAddress());
        receiptDO.setReceiptBank(ownerUserDO.getReceiptBank());
        receiptDO.setReceiptCompany(ownerUserDO.getCompany());
        receiptDO.setReceiptNumber(ownerUserDO.getReceiptNumber());
        receiptDO.setReceiptPhone(ownerUserDO.getReceiptPhone());
       if(receiptService.save(receiptDO) >0)
           return R.ok();
       return  R.error();
    }
}

