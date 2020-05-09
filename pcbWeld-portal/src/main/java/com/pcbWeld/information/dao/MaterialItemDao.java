package com.pcbWeld.information.dao;

import com.pcbWeld.information.domain.MaterialItemDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * pcb类目表
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-04-28 10:26:39
 */
@Mapper
public interface MaterialItemDao {

	MaterialItemDO get(Long id);
	
	List<MaterialItemDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(MaterialItemDO materialItem);
	
	int update(MaterialItemDO materialItem);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
