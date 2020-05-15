package com.pcbWeld.information.service;

import com.pcbWeld.information.domain.ReceiptDO;

import java.util.List;
import java.util.Map;

/**
 * 发票表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-05-13 10:02:23
 */
public interface ReceiptService {
	
	ReceiptDO get(Long id);
	
	List<ReceiptDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ReceiptDO receipt);
	
	int update(ReceiptDO receipt);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
