package com.baizhi.controller;

import com.baizhi.dto.CategoryPageDTO;
import com.baizhi.dto.PageDTO;
import com.baizhi.entity.Category;
import com.baizhi.service.CategoryService;
import com.baizhi.vo.CommonQueryPageVO;
import com.baizhi.vo.CommonVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
public class CategoryController {

    @Resource
    CategoryService categoryService;

    @PostMapping("category/queryOnePage")
    public CommonQueryPageVO queryOnePage(@RequestBody PageDTO pageDTO){

        CommonQueryPageVO commonQueryPageVO = categoryService.queryOnePage(pageDTO);

        return commonQueryPageVO;
    }

    @PostMapping("category/add")
    public CommonVO add(@RequestBody Category category){

        return categoryService.add(category);
    }

    @PostMapping("category/delete")
    public CommonVO delete(@RequestBody Category category){

        return categoryService.delete(category);
    }

    @GetMapping("category/queryById")
    public Category queryById(String id){

        return categoryService.queryById(id);
    }

    @PostMapping("category/update")
    public CommonVO update(@RequestBody Category category){

        return categoryService.update(category);
    }

    @PostMapping("category/queryTwoPage")
    public CommonQueryPageVO  queryTwoPage(@RequestBody CategoryPageDTO categoryPageDTO){

        return categoryService.queryTwoPage(categoryPageDTO);
    }

    @PostMapping("category/queryByLevelsCategory")
    public List<Category> queryByLevelsCategory(Integer levels){

        return categoryService.queryByLevelsCategory(levels);
    }

}
