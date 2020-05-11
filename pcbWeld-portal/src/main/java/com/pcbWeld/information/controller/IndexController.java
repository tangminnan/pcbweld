package com.pcbWeld.information.controller;

import com.pcbWeld.common.annotation.Log;
import com.pcbWeld.information.domain.MaterialItemDO;
import com.pcbWeld.information.service.MaterialItemService;
import com.pcbWeld.information.service.MaterialParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    MaterialItemService materialItemService;
    @Autowired
    MaterialParameterService materialParameterService;


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
    @GetMapping("/wode")
    String wode(Model model) {

        return "wode";
    }
}
