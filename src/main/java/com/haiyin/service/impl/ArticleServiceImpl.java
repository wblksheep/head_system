package com.haiyin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.haiyin.mapper.ArticleMapper;
import com.haiyin.pojo.Article;
import com.haiyin.pojo.PageBean;
import com.haiyin.service.ArticleService;
import com.haiyin.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void add(Article article) {
        //补充属性值
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        article.setCreateUser(userId);
        articleMapper.add(article);
    }

    @Override
    public PageBean list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        //创建pageBean对象
        PageBean<Article> pb = new PageBean<>();

        //开启分页查询 PageHelper
        PageHelper.startPage(pageNum, pageSize);

        //调用mapper
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<Article> as = articleMapper.list(userId, categoryId, state);
        Page<Article> p = (Page<Article>) as;

        //把数据填充到PageBean对象中
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }
}
