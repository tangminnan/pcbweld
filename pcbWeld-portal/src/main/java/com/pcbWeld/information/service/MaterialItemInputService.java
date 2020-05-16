package com.pcbWeld.information.service;

import com.pcbWeld.information.domain.MaterialItemInputDO;

import java.util.List;
import java.util.Map;

/**
 * 输入框属性对应价格表
 *
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-05-13 15:18:09
 */
public interface MaterialItemInputService {

    MaterialItemInputDO get(Long id);

    List<MaterialItemInputDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(MaterialItemInputDO materialItemInput);

    int update(MaterialItemInputDO materialItemInput);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
