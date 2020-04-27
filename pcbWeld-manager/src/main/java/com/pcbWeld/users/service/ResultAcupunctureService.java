package com.pcbWeld.users.service;

import java.util.List;
import java.util.Map;

import com.pcbWeld.users.domain.ResultAcupunctureDo;

public interface ResultAcupunctureService {
	
	ResultAcupunctureDo get(Integer tAcupunctureId);

	List<ResultAcupunctureDo> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(ResultAcupunctureDo ResultAcupunctureDo);
	
	int update(ResultAcupunctureDo ResultAcupunctureDo);
	
	List<Map<String, Object>> exeList(Map<String, Object> map);

}
