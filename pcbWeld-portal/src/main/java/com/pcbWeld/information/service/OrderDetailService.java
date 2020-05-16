package com.pcbWeld.information.service;


import com.pcbWeld.information.domain.OrderDetailDO;

import java.util.List;
import java.util.Map;

/**
 * 物料表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-05-16 13:38:45
 */
public interface OrderDetailService {
	
	OrderDetailDO get(Long id);
	
	List<OrderDetailDO> list(Map<String, Object> map);
	

	
	int save(OrderDetailDO orderDetail);
	

	
	int remove(Long id);
	

}
