package com.pcbWeld.information.service.impl;

import com.pcbWeld.information.dao.MaterialPamSelectDao;
import com.pcbWeld.information.domain.MaterialPamSelectDO;
import com.pcbWeld.information.service.MaterialPamSelectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class MaterialPamSelectServiceImpl implements MaterialPamSelectService {
    @Autowired
    private MaterialPamSelectDao materialPamSelectDao;

    @Override
    public MaterialPamSelectDO get(Long id) {
        return materialPamSelectDao.get(id);
    }

    @Override
    public List<MaterialPamSelectDO> list(Map<String, Object> map) {
        return materialPamSelectDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return materialPamSelectDao.count(map);
    }

    @Override
    public int save(MaterialPamSelectDO materialPamSelect) {
        return materialPamSelectDao.save(materialPamSelect);
    }

    @Override
    public int update(MaterialPamSelectDO materialPamSelect) {
        return materialPamSelectDao.update(materialPamSelect);
    }

    @Override
    public int remove(Long id) {
        return materialPamSelectDao.remove(id);
    }

    @Override
    public int batchRemove(Long[] ids) {
        return materialPamSelectDao.batchRemove(ids);
    }

}
