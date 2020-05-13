package com.pcbWeld.information.dao;

import com.pcbWeld.information.domain.UserAddressDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 用户地址
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-04-29 16:41:14
 */
@Mapper
public interface UserAddressDao {

	UserAddressDO get(Integer id);
	
	List<UserAddressDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(UserAddressDO userAddress);
	
	int update(UserAddressDO userAddress);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);

    int updateAll(long userId);

	int updateDefault(UserAddressDO userAddressDO);
}
