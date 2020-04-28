package com.pcbWeld.information.service;

import com.pcbWeld.information.domain.MaterialExamineDO;

import java.util.List;
import java.util.Map;

/**
 * 资料审核
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-04-28 11:28:36
 */
public interface MaterialExamineService {
	
	MaterialExamineDO get(Integer id);
	
	List<MaterialExamineDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(MaterialExamineDO materialExamine);
	
	int update(MaterialExamineDO materialExamine);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
