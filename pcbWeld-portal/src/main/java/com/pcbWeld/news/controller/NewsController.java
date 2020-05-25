package com.pcbWeld.news.controller;

import com.pcbWeld.common.utils.ShiroUtils;
import com.pcbWeld.news.domain.NewsDO;
import com.pcbWeld.news.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 咨讯表
 *
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-02-15 16:39:38
 */

@Controller
@RequestMapping("/information/news")
public class NewsController {
    @Autowired
    private NewsService newsService;


    @GetMapping("/list")
    public String list(Model model) {
        String isLogin = ShiroUtils.getUser()==null?"ERROR":"RIGHT";
        model.addAttribute("isLogin",isLogin);
        //查询列表数据
        Map<String, Object> params = new HashMap<>();

        params.put("deleted", "0");
        List<NewsDO> newsList = newsService.list(params);

        model.addAttribute("newsList", newsList);
        return "shuoming";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {
        System.out.println("========id===========" + id);
        NewsDO news = newsService.get(id);

        System.out.println("===========news=================" + news);
        model.addAttribute("news", news);
        return "xiangqing";
    }

}
