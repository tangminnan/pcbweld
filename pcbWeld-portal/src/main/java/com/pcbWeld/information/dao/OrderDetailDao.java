package com.pcbWeld.information.dao;

import java.util.List;
import java.util.Map;

import com.pcbWeld.information.domain.OrderDetailDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 物料表
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-05-16 13:38:45
 */
@Mapper
public interface OrderDetailDao {

	OrderDetailDO get(Long id);
	
	List<OrderDetailDO> list(Map<String, Object> map);

	int save(OrderDetailDO orderDetail);
	
	int remove(Long id);
	

}
