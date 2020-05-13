package com.pcbWeld.information.service.impl;

import com.pcbWeld.information.dao.OrderDao;
import com.pcbWeld.information.domain.OrderDO;
import com.pcbWeld.information.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderDao orderDao;
    @Override
    public List<OrderDO> list(Map<String, Object> paramsMap) {
       return orderDao.list(paramsMap);
    }
}
