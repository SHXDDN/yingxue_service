package com.baizhi.service;

import com.baizhi.dto.PageDTO;
import com.baizhi.entity.Category;
import com.baizhi.vo.CommonQueryPageVO;
import com.baizhi.vo.CommonVO;

public interface CategoryService {

    CommonQueryPageVO queryOnePage(PageDTO pageDTO);

    CommonVO add(Category category);

    CommonVO delete(Category category);

    Category queryById(String id);

    CommonVO update(Category category);
}
