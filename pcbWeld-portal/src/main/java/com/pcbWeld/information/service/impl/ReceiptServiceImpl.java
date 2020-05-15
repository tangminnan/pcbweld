package com.pcbWeld.information.service.impl;

import com.pcbWeld.information.dao.ReceiptDao;
import com.pcbWeld.information.domain.ReceiptDO;
import com.pcbWeld.information.service.ReceiptService;
import com.pcbWeld.owneruser.domain.OwnerUserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 发票 开票
 */
@Service
public class ReceiptServiceImpl implements ReceiptService {
    @Autowired
    private ReceiptDao receiptDao;
    @Override
    public int saveReceipt(OwnerUserDO ownerUserDO) {
       return receiptDao.saveReceipt(ownerUserDO);
    }

    @Override
    public OwnerUserDO getReceipt(long id) {
       return receiptDao.getReceipt(id);
    }

    @Override
    public int save(ReceiptDO receipt) {
      return  receiptDao.save(receipt);
    }
    @Override
    public List<ReceiptDO> list(Map<String, Object> map){
        return receiptDao.list(map);
    }
}
