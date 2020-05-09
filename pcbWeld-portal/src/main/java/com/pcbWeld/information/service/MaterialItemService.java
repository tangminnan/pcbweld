package com.pcbWeld.information.service;

import com.pcbWeld.information.domain.MaterialItemDO;

import java.util.List;
import java.util.Map;

/**
 * pcb类目表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-04-28 10:26:39
 */
public interface MaterialItemService {
	
	MaterialItemDO get(Long id);
	
	List<MaterialItemDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(MaterialItemDO materialItem);
	
	int update(MaterialItemDO materialItem);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
