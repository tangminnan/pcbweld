package com.pcbWeld.information.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pcbWeld.common.annotation.Log;
import com.pcbWeld.common.utils.ShiroUtils;
import com.pcbWeld.information.domain.UserAddressDO;
import com.pcbWeld.information.service.UserAddressService;

@Controller
@RequestMapping("/information/address")
public class UserAdderssController {
    @Autowired
    UserAddressService userAddressService;
    
    
    @Log("用户地址")
    @GetMapping("/useraddress")
    Map<String, Object> useraddress() {
    	Map<String, Object> mapP = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        map.put("userId", ShiroUtils.getUserId());
        List<UserAddressDO> address = userAddressService.list(map);
        mapP.put("code", 0);
        mapP.put("msg", "获取成功");
        mapP.put("data", address);
        return map;
    }
    
    
    @Log("保存地址")
    @ResponseBody
    @PostMapping("/saveAddress")
    Map<String, Object> saveAddress(@RequestBody UserAddressDO address) {
    	Map<String, Object> map = new HashMap<>();
    	address.setUserId(ShiroUtils.getUserId());
    	address.setDefaultFlag("1");
    	address.setCreateTime(new Date());
    	userAddressService.save(address);
    	map.put("code",0);
        map.put("data","");
        map.put("msg", "保存成功");
		return map;
    	
    }
    
    @Log("编辑地址")
    @ResponseBody
    @PostMapping("/editAddress")
    Map<String, Object> editAddress(@RequestBody UserAddressDO address) {
    	Map<String, Object> map = new HashMap<>();
    	userAddressService.update(address);
    	map.put("code",0);
        map.put("data","");
        map.put("msg", "编辑成功");
		return map;
    	
    }
    
    @Log("删除地址")
    @ResponseBody
    @PostMapping("/removeAddress")
    Map<String, Object> removeAddress(Integer id) {
    	Map<String, Object> map = new HashMap<>();
    	userAddressService.remove(id);
    	map.put("code",0);
        map.put("data","");
        map.put("msg", "删除成功");
		return map;
    	
    }
}
