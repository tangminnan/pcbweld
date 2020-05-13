package com.pcbWeld.information.dao;

import com.pcbWeld.information.domain.ReceiptDO;
import com.pcbWeld.owneruser.domain.OwnerUserDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 发票 开票
 */
@Mapper
public interface ReceiptDao {

    int saveReceipt(OwnerUserDO ownerUserDO);

    OwnerUserDO getReceipt(long id);

    int save(ReceiptDO receipt);
}
