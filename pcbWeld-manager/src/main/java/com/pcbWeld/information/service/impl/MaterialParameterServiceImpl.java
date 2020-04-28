package com.pcbWeld.information.service.impl;

import com.pcbWeld.information.dao.MaterialParameterDao;
import com.pcbWeld.information.domain.MaterialParameterDO;
import com.pcbWeld.information.service.MaterialParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class MaterialParameterServiceImpl implements MaterialParameterService {
	@Autowired
	private MaterialParameterDao materialParameterDao;
	
	@Override
	public MaterialParameterDO get(Long id){
		return materialParameterDao.get(id);
	}
	
	@Override
	public List<MaterialParameterDO> list(Map<String, Object> map){
		return materialParameterDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return materialParameterDao.count(map);
	}
	
	@Override
	public int save(MaterialParameterDO materialParameter){
		return materialParameterDao.save(materialParameter);
	}
	
	@Override
	public int update(MaterialParameterDO materialParameter){
		return materialParameterDao.update(materialParameter);
	}
	
	@Override
	public int remove(Long id){
		return materialParameterDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return materialParameterDao.batchRemove(ids);
	}
	
}
