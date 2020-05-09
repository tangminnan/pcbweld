package com.pcbWeld.news.service.impl;

import com.pcbWeld.news.dao.NewsDao;
import com.pcbWeld.news.domain.NewsDO;
import com.pcbWeld.news.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsDao newsDao;

    @Override
    public NewsDO get(Integer id) {
        return newsDao.get(id);
    }

    @Override
    public List<NewsDO> list(Map<String, Object> map) {
        return newsDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return newsDao.count(map);
    }

    @Override
    public int save(NewsDO news) {
        return newsDao.save(news);
    }

    @Override
    public int update(NewsDO news) {
        return newsDao.update(news);
    }

    @Override
    public int remove(Integer id) {
        return newsDao.remove(id);
    }

    @Override
    public int batchRemove(Integer[] ids) {
        return newsDao.batchRemove(ids);
    }


}
