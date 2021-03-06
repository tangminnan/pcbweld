package com.pcbWeld.information.service;

import com.pcbWeld.information.domain.MaterialPamSelectDO;

import java.util.List;
import java.util.Map;

/**
 * 多选框属性对应参数表
 *
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-05-13 15:18:09
 */
public interface MaterialPamSelectService {

    MaterialPamSelectDO get(Long id);

    List<MaterialPamSelectDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(MaterialPamSelectDO materialPamSelect);

    int update(MaterialPamSelectDO materialPamSelect);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
