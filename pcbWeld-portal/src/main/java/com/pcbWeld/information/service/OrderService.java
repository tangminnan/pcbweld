package com.pcbWeld.information.service;

import com.pcbWeld.information.domain.OrderDO;

import java.util.List;
import java.util.Map;


public interface OrderService {
    List<OrderDO> list(Map<String, Object> paramsMap);
}
