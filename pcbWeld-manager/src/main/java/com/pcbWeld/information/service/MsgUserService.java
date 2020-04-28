package com.pcbWeld.information.service;

import com.pcbWeld.information.domain.MsgUserDO;

import java.util.List;
import java.util.Map;

/**
 * 用户消息中间表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-04-27 15:31:12
 */
public interface MsgUserService {
	
	MsgUserDO get(Long id);
	
	List<MsgUserDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(MsgUserDO msgUser);
	
	int update(MsgUserDO msgUser);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
