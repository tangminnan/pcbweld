package com.pcbWeld.owneruser.controller;

import com.pcbWeld.common.annotation.Log;
import com.pcbWeld.common.config.BootdoConfig;
import com.pcbWeld.common.controller.BaseController;
import com.pcbWeld.common.utils.OBSUtils;
import com.pcbWeld.common.utils.ShiroUtils;
import com.pcbWeld.owneruser.domain.OwnerUserDO;
import com.pcbWeld.owneruser.service.OwnerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/user")
@RestController
public class OwnerUserController extends BaseController {
    @Autowired
    OwnerUserService userService;
    @Autowired
    private BootdoConfig bootdoConfig;

    /**
     * 个人中心
     *
     * @return
     */
    @Log("获取用户信息")
    @GetMapping("/userInfo")
    Map<String, Object> user() {
        Map<String, Object> map = new HashMap<>();
        OwnerUserDO udo = userService.get(ShiroUtils.getUserId());
        map.put("code", 0);
        map.put("msg", "获取成功");
        map.put("data", udo);
        return map;
    }


    /**
     * 编辑用户信息
     *
     * @return
     */
    @Log("编辑用户信息")
    @ResponseBody
    @PostMapping("/editInfo")
    Map<String, Object> editInfo(OwnerUserDO user) {
        Map<String, Object> map = new HashMap<>();
        OwnerUserDO userd = userService.get(getUserId());
        if(user.getHeight() !=null){
            userd.setHeight(user.getHeight());
        }
        if(user.getWeight()!=null){
            userd.setWeight(user.getWeight());
        }
        if(user.getIdentityCard()!=null){
            userd.setIdentityCard(user.getIdentityCard());
        }
        if(user.getSchool()!=null){
            userd.setSchool(user.getSchool());
        }
        if(user.getGrade()!=null){
            userd.setGrade(user.getGrade());
        }
        if (user.getHeardUrl() != null) {
            userd.setHeardUrl(user.getHeardUrl());
        }
        if (user.getNickname() != null) {
            userd.setNickname(user.getNickname());
        }
        if (user.getName() != null) {
            userd.setName(user.getName());
        }
        if (user.getUserId() != null) {
            userd.setUserId(user.getUserId());
        }
        if (user.getSex() != null) {
            userd.setSex(user.getSex());
        }

        if(user.getFileImg() != null && user.getFileImg().getSize() > 0){
            String fileName = OBSUtils.uploadFile(user.getFileImg(),"pcbWeld/headUrl/");
            userd.setHeardUrl(fileName);
        }

        if (userService.update(userd) > 0) {
            map.put("code",0);
            map.put("data",userd);
            map.put("msg", "保存成功");
        } else {
            map.put("code",-1);
            map.put("data",null);
            map.put("msg", "保存失败");
        }
        return map;
    }



}
