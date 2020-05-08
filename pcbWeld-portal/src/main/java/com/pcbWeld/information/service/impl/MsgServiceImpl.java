package com.pcbWeld.information.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.pcbWeld.information.dao.MsgDao;
import com.pcbWeld.information.domain.MsgDO;
import com.pcbWeld.information.service.MsgService;



@Service
public class MsgServiceImpl implements MsgService {
	@Autowired
	private MsgDao msgDao;
	
	@Override
	public MsgDO get(Integer id){
		return msgDao.get(id);
	}
	
	@Override
	public List<MsgDO> list(Map<String, Object> map){
		return msgDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return msgDao.count(map);
	}
	
	@Override
	public int save(MsgDO msg){
		return msgDao.save(msg);
	}
	
	@Override
	public int update(MsgDO msg){
		return msgDao.update(msg);
	}
	
	@Override
	public int remove(Integer id){
		return msgDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return msgDao.batchRemove(ids);
	}

	@Override
	public List<MsgDO> userMsgList(Long userId) {
		return msgDao.userMsgList(userId);
	}
	
}
