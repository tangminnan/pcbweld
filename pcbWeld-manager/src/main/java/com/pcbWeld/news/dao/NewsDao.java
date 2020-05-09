package com.pcbWeld.news.dao;


import com.pcbWeld.news.domain.NewsDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 咨讯表
 *
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-02-15 16:39:38
 */
@Mapper
public interface NewsDao {

    NewsDO get(Integer id);

    List<NewsDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(NewsDO news);

    int update(NewsDO news);

    int remove(Integer id);

    int batchRemove(Integer[] ids);
}
