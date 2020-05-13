package com.pcbWeld.information.controller;

import com.pcbWeld.common.utils.ShiroUtils;
import com.pcbWeld.information.domain.OrderDO;
import com.pcbWeld.information.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 订单
 */
@RequestMapping("/information/order")
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 查询已完成的所有订单
     */
    @GetMapping("/getAllOrders")
    List<OrderDO> getAllOrders(){
       long id =  ShiroUtils.getUserId();
       Map<String,Object> paramsMap = new HashMap<String,Object>();
       paramsMap.put("id",id) ;
       List<OrderDO> orderList = orderService.list(paramsMap);
       orderList = orderList.stream().filter(a ->a.getOrderStatus()==10).collect(Collectors.toList());
       return orderList;
    }
}
