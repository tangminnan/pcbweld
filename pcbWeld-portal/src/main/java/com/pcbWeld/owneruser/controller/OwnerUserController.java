package com.pcbWeld.owneruser.controller;

import com.pcbWeld.common.annotation.Log;
import com.pcbWeld.common.config.BootdoConfig;
import com.pcbWeld.common.controller.BaseController;
import com.pcbWeld.common.utils.OBSUtils;
import com.pcbWeld.common.utils.R;
import com.pcbWeld.common.utils.ShiroUtils;
import com.pcbWeld.information.domain.MsgDO;
import com.pcbWeld.information.domain.MsgUserDO;
import com.pcbWeld.information.domain.UserAddressDO;
import com.pcbWeld.information.service.MsgService;
import com.pcbWeld.information.service.MsgUserService;
import com.pcbWeld.information.service.UserAddressService;
import com.pcbWeld.owneruser.domain.OwnerUserDO;
import com.pcbWeld.owneruser.service.OwnerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/user")
@Controller
public class OwnerUserController extends BaseController {
    @Autowired
    OwnerUserService userService;
    @Autowired
    private BootdoConfig bootdoConfig;
    @Autowired
    MsgService msgService;
    @Autowired
    MsgUserService msgUserService;

    /**
     * 个人中心
     *
     * @return
     */
    @Log("获取用户信息")
    @GetMapping("/userInfo")
    @ResponseBody
    Map<String, Object> user() {
        Map<String, Object> map = new HashMap<>();
        OwnerUserDO udo = userService.get(ShiroUtils.getUserId());
        map.put("code", 0);
        map.put("msg", "获取成功");
        map.put("data", udo);
        return map;
    }
    
   
    @Log("用户消息")
    @GetMapping("/msgList")
    @ResponseBody
    Map<String, Object> msgList() {
        Map<String, Object> map = new HashMap<>();
        List<MsgDO> msg = msgService.userMsgList(ShiroUtils.getUserId());
        map.put("code", 0);
        map.put("msg", "获取成功");
        map.put("data", msg);
        return map;
    }
    
    @Log("消息详情")
    @GetMapping("/msgListInfo")
    String msgListInfo(Integer msgId,Model model) {
        MsgUserDO mu = new MsgUserDO();
        mu.setId(ShiroUtils.getUserId());
        mu.setStatue(0);
        mu.setUpdateTime(new Date());
        msgUserService.update(mu);
        MsgDO msg = msgService.get(msgId);
        model.addAttribute("msg",msg);
        return "/xiaoxixiangqing";
    }

   
    
    /**
     * 编辑用户信息
     *
     * @return
     */
    @Log("编辑用户信息")
    @ResponseBody
    @PostMapping("/editInfo")
    R editInfo(OwnerUserDO user) {
        user.setId(ShiroUtils.getUserId());
    /*    Map<String, Object> map = new HashMap<>();
        OwnerUserDO userd = userService.get(getUserId());      */
       /*
        if (user.getHeardUrl() != null) {
            userd.setHeardUrl(user.getHeardUrl());
        }
        if (user.getNickname() != null) {
            userd.setNickname(user.getNickname());
        }
        if (user.getName() != null) {
            userd.setName(user.getName());
        }
        if(user.getPhone() != null){
        	userd.setPhone(user.getPhone());
        }
		if(user.getCompany() != null){
			userd.setCompany(user.getCompany());
		}
		if(user.getQqNumber() != null){
			userd.setQqNumber(user.getQqNumber());
		}
        if (user.getSex() != null) {
            userd.setSex(user.getSex());
        }*/

        if(user.getFileImg() != null && user.getFileImg().getSize() > 0){
            String fileName = OBSUtils.uploadFile(user.getFileImg(),"pcbWeld/headUrl/");
            user.setHeardUrl(fileName);
        }

        if (userService.update(user) > 0) {
            System.out.println("================================="+user);
          return R.ok();
        } else {
          return R.error();
        }

    }



}
