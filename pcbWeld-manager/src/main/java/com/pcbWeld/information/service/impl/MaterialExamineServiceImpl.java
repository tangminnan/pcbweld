package com.pcbWeld.information.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.pcbWeld.information.dao.MaterialExamineDao;
import com.pcbWeld.information.domain.MaterialExamineDO;
import com.pcbWeld.information.service.MaterialExamineService;



@Service
public class MaterialExamineServiceImpl implements MaterialExamineService {
	@Autowired
	private MaterialExamineDao materialExamineDao;
	
	@Override
	public MaterialExamineDO get(Integer id){
		return materialExamineDao.get(id);
	}
	
	@Override
	public List<MaterialExamineDO> list(Map<String, Object> map){
		return materialExamineDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return materialExamineDao.count(map);
	}
	
	@Override
	public int save(MaterialExamineDO materialExamine){
		return materialExamineDao.save(materialExamine);
	}
	
	@Override
	public int update(MaterialExamineDO materialExamine){
		return materialExamineDao.update(materialExamine);
	}
	
	@Override
	public int remove(Integer id){
		return materialExamineDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return materialExamineDao.batchRemove(ids);
	}
	
}
