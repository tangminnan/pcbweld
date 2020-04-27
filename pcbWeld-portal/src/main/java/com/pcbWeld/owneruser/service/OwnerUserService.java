package com.pcbWeld.owneruser.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pcbWeld.common.domain.Tree;
import com.pcbWeld.owneruser.domain.OwnerUserDO;
import com.pcbWeld.owneruser.domain.SetDO;


public interface OwnerUserService {
	OwnerUserDO get(Long id);
	
	OwnerUserDO getbyname(String username);

	List<OwnerUserDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);
	
	int save(OwnerUserDO user);

	int update(OwnerUserDO user);

	int remove(Long userId);

	boolean exit(Map<String, Object> params);

	OwnerUserDO getbyunionid(String unionid);

	OwnerUserDO getbyopenid(String openId);

	boolean getUnionid(Map<String, Object> params) ;

	boolean getopenId(Map<String, Object> params);

	OwnerUserDO getUserByphone(String phone);

	int updateUser(OwnerUserDO ownerUserDO);

}
