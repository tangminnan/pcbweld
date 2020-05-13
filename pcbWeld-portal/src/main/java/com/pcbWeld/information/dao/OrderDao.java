package com.pcbWeld.information.dao;

import com.pcbWeld.information.domain.OrderDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderDao {
    List<OrderDO> list(Map<String, Object> paramsMap);
}
