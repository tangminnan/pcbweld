package com.pcbWeld.users.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import com.pcbWeld.users.domain.ResultAcupunctureDo;


@Mapper
public interface ResultAcupunctureDao {
	
	ResultAcupunctureDo get(Integer tAcupunctureId);

	List<ResultAcupunctureDo> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(ResultAcupunctureDo ResultAcupunctureDo);
	
	int update(ResultAcupunctureDo ResultAcupunctureDo);
	
	List<Map<String, Object>> exeList(Map<String, Object> map);
	
}
