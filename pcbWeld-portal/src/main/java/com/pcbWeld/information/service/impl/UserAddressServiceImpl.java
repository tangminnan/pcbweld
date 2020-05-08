package com.pcbWeld.information.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.pcbWeld.information.dao.UserAddressDao;
import com.pcbWeld.information.domain.UserAddressDO;
import com.pcbWeld.information.service.UserAddressService;



@Service
public class UserAddressServiceImpl implements UserAddressService {
	@Autowired
	private UserAddressDao userAddressDao;
	
	@Override
	public UserAddressDO get(Integer id){
		return userAddressDao.get(id);
	}
	
	@Override
	public List<UserAddressDO> list(Map<String, Object> map){
		return userAddressDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return userAddressDao.count(map);
	}
	
	@Override
	public int save(UserAddressDO userAddress){
		return userAddressDao.save(userAddress);
	}
	
	@Override
	public int update(UserAddressDO userAddress){
		return userAddressDao.update(userAddress);
	}
	
	@Override
	public int remove(Integer id){
		return userAddressDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return userAddressDao.batchRemove(ids);
	}
	
}
