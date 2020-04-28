package com.pcbWeld.information.dao;

import com.pcbWeld.information.domain.MaterialParameterDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * pcb参数表
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-04-28 10:26:39
 */
@Mapper
public interface MaterialParameterDao {

	MaterialParameterDO get(Long id);
	
	List<MaterialParameterDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(MaterialParameterDO materialParameter);
	
	int update(MaterialParameterDO materialParameter);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
