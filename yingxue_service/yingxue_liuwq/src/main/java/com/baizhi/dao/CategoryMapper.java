package com.baizhi.dao;

import com.baizhi.entity.Category;
import com.baizhi.entity.CategoryExample;
import java.util.List;

import com.baizhi.po.CategoryPO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface CategoryMapper extends Mapper<Category> {

    List<CategoryPO> queryAllCategory();

}