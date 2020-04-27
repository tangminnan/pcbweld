package com.pcbWeld.users.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcbWeld.users.dao.ResultAcupunctureDao;
import com.pcbWeld.users.domain.ResultAcupunctureDo;
import com.pcbWeld.users.service.ResultAcupunctureService;

@Service
public class ResultAcupunctureServiceImpl implements ResultAcupunctureService {

	@Autowired
	private ResultAcupunctureDao rsMapper;
	
	@Override
	public ResultAcupunctureDo get(Integer tAcupunctureId) {
		
		return rsMapper.get(tAcupunctureId);
	}

	@Override
	public List<ResultAcupunctureDo> list(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return rsMapper.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return rsMapper.count(map);
	}

	@Override
	public int save(ResultAcupunctureDo ResultAcupunctureDo) {
		// TODO Auto-generated method stub
		return rsMapper.save(ResultAcupunctureDo);
	}

	@Override
	public int update(ResultAcupunctureDo ResultAcupunctureDo) {
		// TODO Auto-generated method stub
		return rsMapper.update(ResultAcupunctureDo);
	}

	@Override
	public List<Map<String, Object>> exeList(Map<String, Object> map) {
		
		return rsMapper.exeList(map);
	}

}
