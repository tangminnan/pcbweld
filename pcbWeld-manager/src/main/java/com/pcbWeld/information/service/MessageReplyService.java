package com.pcbWeld.information.service;

import com.pcbWeld.information.domain.MessageReplyDO;

import java.util.List;
import java.util.Map;

/**
 * 留言回复表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-04-27 18:26:02
 */
public interface MessageReplyService {
	
	MessageReplyDO get(Integer id);
	
	List<MessageReplyDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(MessageReplyDO messageReply);
	
	int update(MessageReplyDO messageReply);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
