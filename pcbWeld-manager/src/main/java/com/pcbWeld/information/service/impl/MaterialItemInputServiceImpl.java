package com.pcbWeld.information.service.impl;

import com.pcbWeld.information.dao.MaterialItemInputDao;
import com.pcbWeld.information.domain.MaterialItemInputDO;
import com.pcbWeld.information.service.MaterialItemInputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class MaterialItemInputServiceImpl implements MaterialItemInputService {
    @Autowired
    private MaterialItemInputDao materialItemInputDao;

    @Override
    public MaterialItemInputDO get(Long id) {
        return materialItemInputDao.get(id);
    }

    @Override
    public List<MaterialItemInputDO> list(Map<String, Object> map) {
        return materialItemInputDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return materialItemInputDao.count(map);
    }

    @Override
    public int save(MaterialItemInputDO materialItemInput) {
        return materialItemInputDao.save(materialItemInput);
    }

    @Override
    public int update(MaterialItemInputDO materialItemInput) {
        return materialItemInputDao.update(materialItemInput);
    }

    @Override
    public int remove(Long id) {
        return materialItemInputDao.remove(id);
    }

    @Override
    public int batchRemove(Long[] ids) {
        return materialItemInputDao.batchRemove(ids);
    }

}
