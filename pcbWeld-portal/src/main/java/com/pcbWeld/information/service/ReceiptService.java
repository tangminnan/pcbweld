package com.pcbWeld.information.service;

import com.pcbWeld.information.domain.ReceiptDO;
import com.pcbWeld.owneruser.domain.OwnerUserDO;

import java.util.List;
import java.util.Map;

/**
 * 发票  开票
 */
public interface ReceiptService {
    int saveReceipt(OwnerUserDO ownerUserDO);

    OwnerUserDO getReceipt(long id);
    int save(ReceiptDO receipt);
    List<ReceiptDO> list(Map<String, Object> map);
}
