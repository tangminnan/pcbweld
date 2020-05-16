package com.pcbWeld.information.service.impl;

import com.pcbWeld.information.dao.OrderDetailDao;
import com.pcbWeld.information.domain.OrderDetailDO;
import com.pcbWeld.information.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class OrderDetailServiceImpl implements OrderDetailService {
	@Autowired
	private OrderDetailDao orderDetailDao;
	
	@Override
	public OrderDetailDO get(Long id){
		return orderDetailDao.get(id);
	}
	
	@Override
	public List<OrderDetailDO> list(Map<String, Object> map){
		return orderDetailDao.list(map);
	}

	
	@Override
	public int save(OrderDetailDO orderDetail){
		return orderDetailDao.save(orderDetail);
	}
	

	
	@Override
	public int remove(Long id){
		return orderDetailDao.remove(id);
	}
	

}
