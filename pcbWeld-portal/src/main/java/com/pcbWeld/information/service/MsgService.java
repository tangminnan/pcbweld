package com.pcbWeld.information.service;

import com.pcbWeld.information.domain.MsgDO;

import java.util.List;
import java.util.Map;

/**
 * 消息表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-04-27 15:31:12
 */
public interface MsgService {
	
	List<MsgDO> userMsgList(Long userId);
	
	MsgDO get(Integer id);
	
	List<MsgDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(MsgDO msg);
	
	int update(MsgDO msg);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
