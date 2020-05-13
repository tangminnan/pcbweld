package com.pcbWeld.information.service;

import com.pcbWeld.information.domain.ReceiptDO;
import com.pcbWeld.owneruser.domain.OwnerUserDO; /**
 * 发票  开票
 */
public interface ReceiptService {
    int saveReceipt(OwnerUserDO ownerUserDO);

    OwnerUserDO getReceipt(long id);
    int save(ReceiptDO receipt);
}
