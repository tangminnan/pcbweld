package com.pcbWeld.information.controller;

import com.pcbWeld.common.utils.PageUtils;
import com.pcbWeld.common.utils.Query;
import com.pcbWeld.common.utils.R;
import com.pcbWeld.information.domain.OrderDO;
import com.pcbWeld.information.service.OrderService;
import com.pcbWeld.users.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 订单表
 *
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-04-28 09:17:26
 */

@Controller
@RequestMapping("/information/invoice")
public class InvoiceController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @GetMapping()
    @RequiresPermissions("information:invoice:order")
    String Order() {
        return "information/invoice/order";
    }


    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("information:invoice:order")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        Map<String, Object> params1 = new HashMap<>();

        //查询列表数据
        params.put("invoiceType", 2);
        Query query = new Query(params);
        List<OrderDO> orderList = orderService.list(query);

        params.remove("invoiceType");
        params.put("invoiceType", 3);
        Query query1 = new Query(params);
        orderList.addAll(orderService.list(query1));

        for (OrderDO orderDO : orderList) {
            if (orderDO.getUserId() != null) {
                orderDO.setUsername(userService.get(orderDO.getUserId().intValue()).getName());
            }
        }
        int total = orderService.count(query);
        int total1 = orderService.count(query1);

        PageUtils pageUtils = new PageUtils(orderList, total + total1);
        return pageUtils;
    }


    @GetMapping("/edit/{id}")
    @RequiresPermissions("information:invoice:edit")
    String edit(@PathVariable("id") Long id, Model model) {
        OrderDO order = orderService.get(id);
        model.addAttribute("order", order);
        return "information/invoice/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("information:invoice:add")
    public R save(OrderDO order) {
        if (orderService.save(order) > 0) {
            return R.ok();
        }
        return R.error();
    }


    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("information:invoice:remove")
    public R remove(Long id) {
        if (orderService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("information:invoice:batchRemove")
    public R remove(@RequestParam("ids[]") Long[] ids) {
        orderService.batchRemove(ids);
        return R.ok();
    }

}
