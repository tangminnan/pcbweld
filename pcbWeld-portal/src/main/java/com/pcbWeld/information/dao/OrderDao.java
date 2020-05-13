package com.pcbWeld.information.dao;

import com.pcbWeld.information.domain.OrderDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
/**
 * 订单表
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-04-28 09:17:26
 */
@Mapper
public interface OrderDao {

	OrderDO get(Long id);
	
	List<OrderDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(OrderDO order);
	
	int update(OrderDO order);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
