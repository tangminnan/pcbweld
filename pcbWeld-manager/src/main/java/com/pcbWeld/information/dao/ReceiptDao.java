package com.pcbWeld.information.dao;

import com.pcbWeld.information.domain.ReceiptDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 发票表
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-05-13 10:02:23
 */
@Mapper
public interface ReceiptDao {

	ReceiptDO get(Long id);
	
	List<ReceiptDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ReceiptDO receipt);
	
	int update(ReceiptDO receipt);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
