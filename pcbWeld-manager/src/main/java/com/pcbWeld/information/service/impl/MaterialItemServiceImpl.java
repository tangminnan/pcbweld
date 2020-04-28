package com.pcbWeld.information.service.impl;

import com.pcbWeld.information.dao.MaterialItemDao;
import com.pcbWeld.information.domain.MaterialItemDO;
import com.pcbWeld.information.service.MaterialItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class MaterialItemServiceImpl implements MaterialItemService {
	@Autowired
	private MaterialItemDao materialItemDao;
	
	@Override
	public MaterialItemDO get(Long id){
		return materialItemDao.get(id);
	}
	
	@Override
	public List<MaterialItemDO> list(Map<String, Object> map){
		return materialItemDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return materialItemDao.count(map);
	}
	
	@Override
	public int save(MaterialItemDO materialItem){
		return materialItemDao.save(materialItem);
	}
	
	@Override
	public int update(MaterialItemDO materialItem){
		return materialItemDao.update(materialItem);
	}
	
	@Override
	public int remove(Long id){
		return materialItemDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return materialItemDao.batchRemove(ids);
	}
	
}
