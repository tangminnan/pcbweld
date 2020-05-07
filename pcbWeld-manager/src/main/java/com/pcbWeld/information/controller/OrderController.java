package com.pcbWeld.information.controller;

import com.alibaba.fastjson.JSONObject;
import com.pcbWeld.common.utils.PageUtils;
import com.pcbWeld.common.utils.Query;
import com.pcbWeld.common.utils.R;
import com.pcbWeld.common.utils.SynQueryCofig;
import com.pcbWeld.information.domain.MaterialExamineDO;
import com.pcbWeld.information.domain.OrderDO;
import com.pcbWeld.information.service.MaterialExamineService;
import com.pcbWeld.information.service.OrderService;
import com.pcbWeld.users.service.UserService;
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
 * 订单表
 *
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-04-28 09:17:26
 */

@Controller
@RequestMapping("/information/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private MaterialExamineService materialExamineService;

    @GetMapping()
    @RequiresPermissions("information:order:order")
    String Order() {
        return "information/order/order";
    }


    //资料审核列表
    @GetMapping("/dataCheckOrder")
    @RequiresPermissions("information:order:dataCheckOrder")
    String dataCheckOrder(Model model) {
        model.addAttribute("orderStatus", 2);
        return "information/order/order";
    }

    //资料审核未通过列表
    @GetMapping("/dataCheckFailedOrder")
    @RequiresPermissions("information:order:dataCheckFailedOrder")
    String dataCheckFailedOrder(Model model) {
        model.addAttribute("orderStatus", 3);
        return "information/order/order";
    }

    //待支付列表
    @GetMapping("/unpaidOrder")
    @RequiresPermissions("information:order:unpaidOrder")
    String unpaidOrder(Model model) {
        model.addAttribute("orderStatus", 4);
        return "information/order/order";
    }

    //物料寄送列表
    @GetMapping("/materialDelivery")
    @RequiresPermissions("information:order:materialDelivery")
    String materialDelivery(Model model) {
        model.addAttribute("orderStatus", 5);
        return "information/order/order";
    }

    //物料待审核列表
    @GetMapping("/materialCheck")
    @RequiresPermissions("information:order:materialCheck")
    String materialCheck(Model model) {
        model.addAttribute("orderStatus", 6);
        return "information/order/order";
    }

    //物料审核失败
    @GetMapping("/materialCheckFailed")
    @RequiresPermissions("information:order:materialCheckFailed")
    String materialCheckFailed(Model model) {
        model.addAttribute("orderStatus", 7);
        return "information/order/order";
    }

    //待发货
    @GetMapping("/waitFaHuo")
    @RequiresPermissions("information:order:waitFaHuo")
    String waitFaHuo(Model model) {
        model.addAttribute("orderStatus", 8);
        return "information/order/order";
    }

    //待收货
    @GetMapping("/waitShouHuo")
    @RequiresPermissions("information:order:waitShouHuo")
    String waitShouHuo(Model model) {
        model.addAttribute("orderStatus", 9);
        return "information/order/order";
    }

    //已完成
    @GetMapping("/achieveOrder")
    @RequiresPermissions("information:order:achieveOrder")
    String achieveOrder(Model model) {
        model.addAttribute("orderStatus", 10);
        return "information/order/order";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("information:order:order")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        Map<String, Object> params1 = new HashMap<>();

        //查询列表数据
        Query query = new Query(params);
        List<OrderDO> orderList = orderService.list(query);
        for (OrderDO orderDO : orderList) {
            if (orderDO.getUserId() != null) {
                orderDO.setUsername(userService.get(orderDO.getUserId().intValue()).getName());
            }

            params1.put("orderId", orderDO.getId());
            params1.put("examineFlag", 3);
            //资料审核
            params1.put("type", 1);
            //资料审核未通过原因
            List<MaterialExamineDO> data = materialExamineService.list(params1);
            if (data.size() > 0) {
                orderDO.setUnDataResult(data.get(0).getExamineIdea());
                orderDO.setFiles(data.get(0).getFiles());
            } else {
                orderDO.setUnDataResult("");
            }

            params1.remove("type");
            params1.put("type", 2);
            //物料审核未通过原因
            List<MaterialExamineDO> material = materialExamineService.list(params1);
            if (material.size() > 0) {
                orderDO.setUnMaterialResult(material.get(0).getExamineIdea());
            } else {
                orderDO.setUnMaterialResult("");
            }

        }
        int total = orderService.count(query);
        PageUtils pageUtils = new PageUtils(orderList, total);
        return pageUtils;
    }

    @GetMapping("/add")
    @RequiresPermissions("information:order:add")
    String add() {
        return "information/order/add";
    }

    @GetMapping("/dataCheck")
    @RequiresPermissions("information:order:dataCheck")
    String dataCheck() {
        return "information/order/dataCheck";
    }

    @GetMapping("/writeNum/{id}")
    @RequiresPermissions("information:order:edit")
    String writeNum(@PathVariable("id") Long id, Model model) {
        model.addAttribute("id", id);
        return "information/order/writeNum";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("information:order:edit")
    String edit(@PathVariable("id") Long id, Model model) {
        OrderDO order = orderService.get(id);
        model.addAttribute("order", order);
        return "information/order/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("information:order:add")
    public R save(OrderDO order) {
        if (orderService.save(order) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("information:order:edit")
    public R update(OrderDO order) {
        OrderDO orderDO = orderService.get(order.getId());

        if (order.getOrderStatus() != null && order.getOrderStatus() == 6) {
            MaterialExamineDO materialExamineDO = new MaterialExamineDO();

            materialExamineDO.setUserId(orderDO.getUserId());
            materialExamineDO.setOrderId(orderDO.getId());
            materialExamineDO.setExamineFlag(1);
            materialExamineDO.setAddTime(new Date());
            materialExamineDO.setType(2);

            materialExamineService.save(materialExamineDO);
        }

        orderService.update(order);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("information:order:remove")
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
    @RequiresPermissions("information:order:batchRemove")
    public R remove(@RequestParam("ids[]") Long[] ids) {
        orderService.batchRemove(ids);
        return R.ok();
    }


    /**
     * 查询物流接口
     */
    @GetMapping("/getWuliu")
    String getWuliu(String num, String comCode, Model model) {
        String key = "rMUPmWRC4136";                //贵司的授权key
        String customer = "C0EF557D424A643F874550C9C465C832";            //贵司的查询公司编号
        String com = comCode;            //快递公司编码
        String num1 = num;    //快递单号
        String phone = "";                //手机号码后四位
        String from = "";                //出发地
        String to = "";                    //目的地
        int resultv2 = 0;                //开启行政规划解析

        //快递100实时查询接口
        SynQueryCofig sqc = new SynQueryCofig(key, customer);
        //返回结果
        String result = sqc.synQueryData(com, num1, phone, from, to, resultv2);
        //将返回的String结果转换成json格式
        JSONObject postJson = (JSONObject) JSONObject.parse(result);
        model.addAttribute("postJson", postJson.getJSONArray("data"));

        return "information/order/wuliuxiangqing";
    }
}
