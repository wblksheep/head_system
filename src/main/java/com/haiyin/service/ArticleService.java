package com.haiyin.service;

import com.haiyin.pojo.Article;
import com.haiyin.pojo.PageBean;

public interface ArticleService {
    //新增
    void add(Article article);

    //条件分页列表查询
    PageBean list(Integer pageNum, Integer pageSize, Integer categoryId, String state);
}
