package com.pcbWeld.information.controller;

import com.pcbWeld.common.utils.PageUtils;
import com.pcbWeld.common.utils.Query;
import com.pcbWeld.common.utils.R;
import com.pcbWeld.common.utils.ShiroUtils;
import com.pcbWeld.information.domain.MaterialItemDO;
import com.pcbWeld.information.domain.MaterialParameterDO;
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
 * pcb参数表
 *
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-04-28 10:26:39
 */

@Controller
@RequestMapping("/information/materialParameter")
public class MaterialParameterController {
    @Autowired
    private MaterialParameterService materialParameterService;
    @Autowired
    private MaterialItemService materialItemService;

    @GetMapping()
    @RequiresPermissions("information:materialParameter:materialParameter")
    String MaterialParameter() {
        return "information/materialParameter/materialParameter";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("information:materialParameter:materialParameter")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<MaterialParameterDO> materialParameterList = materialParameterService.list(query);
        for (MaterialParameterDO materialParameterDO : materialParameterList) {
            if (materialParameterDO != null && materialParameterDO.getItemId() != null) {
                MaterialItemDO materialItemDO = materialItemService.get(materialParameterDO.getItemId());

                materialParameterDO.setItemName(materialItemDO.getType());
            }

        }
        int total = materialParameterService.count(query);
        PageUtils pageUtils = new PageUtils(materialParameterList, total);
        return pageUtils;
    }

    @GetMapping("/add")
    @RequiresPermissions("information:materialParameter:add")
    String add(Model model) {
        Map<String, Object> params = new HashMap<>();
        params.put("status", 2);
        List<MaterialItemDO> materialItemDOList = materialItemService.list(params);


        model.addAttribute("materialItemDOList", materialItemDOList);
        return "information/materialParameter/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("information:materialParameter:edit")
    String edit(@PathVariable("id") Long id, Model model) {
        MaterialParameterDO materialParameter = materialParameterService.get(id);
        if (ShiroUtils.getUser() != null) {
            materialParameter.setCreateBy(ShiroUtils.getUser().getName());
        }

        Map<String, Object> params = new HashMap<>();
        params.put("status", 2);
        List<MaterialItemDO> materialItemDOList = materialItemService.list(params);
        model.addAttribute("materialItemDOList", materialItemDOList);

        System.out.println("=======materialItemDOList============================" + materialItemDOList);
        model.addAttribute("materialParameter", materialParameter);

        if (materialParameter.getItemId() != null) {
            materialParameter.setItemName(materialItemService.get(materialParameter.getItemId()).getType());
        }
        System.out.println("=======materialParameter============================" + materialParameter);
        return "information/materialParameter/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("information:materialParameter:add")
    public R save(@RequestBody MaterialParameterDO materialParameter) {

        if(materialParameter.getIsJunior() == 0){
            List<MaterialParameterDO> np = materialParameter.getNamePrice();
            if(np.size() >0){
            	for (MaterialParameterDO materialParameterDO : np) {
                    MaterialParameterDO  mp = new MaterialParameterDO();
                    mp.setCreateTime(new Date());
                    mp.setStatus(2);
                    if (ShiroUtils.getUser() != null) {
                        mp.setCreateBy(ShiroUtils.getUser().getName());
                    }
                    mp.setItemId(materialParameter.getItemId());
                    mp.setIsJunior(materialParameter.getIsJunior());
                    mp.setParameterName(materialParameterDO.getParameterName());
                    mp.setPrice(materialParameterDO.getPrice());
                    materialParameterService.save(mp);
                }
                return R.ok();
            }
        }
        if(materialParameter.getIsJunior() == 1){
            MaterialParameterDO mp = new MaterialParameterDO();
            mp.setCreateTime(new Date());
            mp.setStatus(2);
            mp.setItemId(materialParameter.getItemId());
            mp.setIsJunior(materialParameter.getIsJunior());
            if (ShiroUtils.getUser() != null) {
                mp.setCreateBy(ShiroUtils.getUser().getName());
            }
            if (materialParameterService.save(mp) > 0) {
                return R.ok();
            }
        }

        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("information:materialParameter:edit")
    public R update(MaterialParameterDO materialParameter) {
        materialParameter.setUpdateTime(new Date());
        materialParameterService.update(materialParameter);
        return R.ok();
    }


    /**
     * 修改状态
     */
    @ResponseBody
    @RequestMapping(value = "/updateEnable")
    public R updateEnable(Long id, Integer enable) {
        MaterialParameterDO materialParameter = new MaterialParameterDO();
        materialParameter.setId(id);
        materialParameter.setStatus(enable);

        materialParameterService.update(materialParameter);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("information:materialParameter:remove")
    public R remove(Long id) {
        if (materialParameterService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("information:materialParameter:batchRemove")
    public R remove(@RequestParam("ids[]") Long[] ids) {
        materialParameterService.batchRemove(ids);
        return R.ok();
    }



}
