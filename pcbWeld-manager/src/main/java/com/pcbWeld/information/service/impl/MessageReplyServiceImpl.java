package com.pcbWeld.information.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.pcbWeld.information.dao.MessageReplyDao;
import com.pcbWeld.information.domain.MessageReplyDO;
import com.pcbWeld.information.service.MessageReplyService;



@Service
public class MessageReplyServiceImpl implements MessageReplyService {
	@Autowired
	private MessageReplyDao messageReplyDao;
	
	@Override
	public MessageReplyDO get(Integer id){
		return messageReplyDao.get(id);
	}
	
	@Override
	public List<MessageReplyDO> list(Map<String, Object> map){
		return messageReplyDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return messageReplyDao.count(map);
	}
	
	@Override
	public int save(MessageReplyDO messageReply){
		return messageReplyDao.save(messageReply);
	}
	
	@Override
	public int update(MessageReplyDO messageReply){
		return messageReplyDao.update(messageReply);
	}
	
	@Override
	public int remove(Integer id){
		return messageReplyDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return messageReplyDao.batchRemove(ids);
	}
	
}
