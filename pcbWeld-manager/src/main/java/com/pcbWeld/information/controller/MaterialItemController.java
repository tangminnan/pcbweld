package com.pcbWeld.information.controller;

import com.pcbWeld.common.utils.PageUtils;
import com.pcbWeld.common.utils.Query;
import com.pcbWeld.common.utils.R;
import com.pcbWeld.information.domain.MaterialItemDO;
import com.pcbWeld.information.domain.MaterialItemInputDO;
import com.pcbWeld.information.domain.MaterialParameterDO;
import com.pcbWeld.information.service.MaterialItemInputService;
import com.pcbWeld.information.service.MaterialItemService;
import com.pcbWeld.information.service.MaterialParameterService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * pcb类目表
 *
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-04-28 10:26:39
 */

@Controller
@RequestMapping("/information/materialItem")
public class MaterialItemController {
    @Autowired
    private MaterialItemService materialItemService;
    @Autowired
    private MaterialItemInputService materialItemInputService;
    @Autowired
    private MaterialParameterService materialParameterService;

    @GetMapping()
    @RequiresPermissions("information:materialItem:materialItem")
    String MaterialItem() {
        return "information/materialItem/materialItem";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("information:materialItem:materialItem")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<MaterialItemDO> materialItemList = materialItemService.list(query);
        int total = materialItemService.count(query);
        PageUtils pageUtils = new PageUtils(materialItemList, total);
        return pageUtils;
    }

    @GetMapping("/add")
    @RequiresPermissions("information:materialItem:add")
    String add() {
        return "information/materialItem/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("information:materialItem:edit")
    String edit(@PathVariable("id") Long id, Model model) {
        MaterialItemDO materialItem = materialItemService.get(id);
        model.addAttribute("materialItem", materialItem);
        return "information/materialItem/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("information:materialItem:add")
    public R save(MaterialItemDO materialItem) {
        MaterialItemInputDO inputDO = new MaterialItemInputDO();
        inputDO.setPrice(materialItem.getPrice());
        inputDO.setCountWay(materialItem.getCountWay());
        inputDO.setScope(materialItem.getScope());


        materialItem.setCreateTime(new Date());
        materialItem.setStatus(2);
        if (materialItemService.save(materialItem) > 0) {
            inputDO.setItemId(materialItem.getId());
            inputDO.setCreateTime(new Date());

            materialItemInputService.save(inputDO);
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("information:materialItem:edit")
    public R update(MaterialItemDO materialItem) {
        materialItem.setUpdateTime(new Date());
        materialItemService.update(materialItem);
        return R.ok();
    }

    /**
     * 修改状态
     */
    @ResponseBody
    @RequestMapping(value = "/updateEnable")
    public R updateEnable(Long id, Integer enable) {
        MaterialItemDO materialItemDO = new MaterialItemDO();
        materialItemDO.setId(id);
        materialItemDO.setStatus(enable);
        materialItemService.update(materialItemDO);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("information:materialItem:remove")
    public R remove(Long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("itemId", id);
        List<MaterialParameterDO> materialParameterDOS = materialParameterService.list(map);

        for (MaterialParameterDO materialParameterDO : materialParameterDOS) {
            if (materialParameterDOS.size() > 0) {
                materialParameterService.remove(materialParameterDO.getId());
            }
        }
        if (materialItemService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("information:materialItem:batchRemove")
    public R remove(@RequestParam("ids[]") Long[] ids) {
        materialItemService.batchRemove(ids);
        return R.ok();
    }

}
