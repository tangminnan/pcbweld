package com.pcbWeld.information.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.pcbWeld.information.dao.ReceiptDao;
import com.pcbWeld.information.domain.ReceiptDO;
import com.pcbWeld.information.service.ReceiptService;



@Service
public class ReceiptServiceImpl implements ReceiptService {
	@Autowired
	private ReceiptDao receiptDao;
	
	@Override
	public ReceiptDO get(Long id){
		return receiptDao.get(id);
	}
	
	@Override
	public List<ReceiptDO> list(Map<String, Object> map){
		return receiptDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return receiptDao.count(map);
	}
	
	@Override
	public int save(ReceiptDO receipt){
		return receiptDao.save(receipt);
	}
	
	@Override
	public int update(ReceiptDO receipt){
		return receiptDao.update(receipt);
	}
	
	@Override
	public int remove(Long id){
		return receiptDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return receiptDao.batchRemove(ids);
	}
	
}
