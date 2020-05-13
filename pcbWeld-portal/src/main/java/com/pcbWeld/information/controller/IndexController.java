package com.pcbWeld.information.controller;

import com.pcbWeld.common.annotation.Log;
import com.pcbWeld.information.domain.MaterialItemDO;
import com.pcbWeld.information.domain.MsgDO;
import com.pcbWeld.information.service.MaterialItemService;
import com.pcbWeld.information.service.MaterialParameterService;
import com.pcbWeld.information.service.MsgService;
import com.pcbWeld.information.service.OrderService;
import com.pcbWeld.owneruser.domain.OwnerUserDO;
import com.pcbWeld.owneruser.service.OwnerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @GetMapping("/wode/{id}")
    String wode(@PathVariable("id") Long id, Model model) {

        OwnerUserDO user = userService.get(id);

        List<MsgDO> msgDOS = msgService.userMsgList(id);

        //未读消息数量
        model.addAttribute("msgCount", msgDOS.size());
        //用户信息
        model.addAttribute("user", user);
        return "wode";
    }
}
