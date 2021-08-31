package com.baizhi.app;

import com.baizhi.entity.Category;
import com.baizhi.po.CategoryPO;
import com.baizhi.po.VideoPO;
import com.baizhi.service.CategoryService;
import com.baizhi.service.VideoService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = "http://192.168.9.91:1210/")
@RestController
@RequestMapping("app")
public class AppController {

    @Resource
    VideoService videoService;
    @Resource
    CategoryService categoryService;

    @RequestMapping("queryByReleaseTime")
    public HashMap<String, Object> queryByReleaseTime(){

        List<VideoPO> videoPOList = videoService.queryByReleaseTime();

        HashMap<String, Object> map = new HashMap<>();

        map.put("message","查询成功");
        map.put("status",100);
        map.put("data",videoPOList);

        return map;
    }

    @RequestMapping("queryAllCategory")
    public HashMap<String, Object> queryAllCategory(){

        //List<Category> categoryPOS = categoryService.queryByLevelsCategory(1);
        //List<Category> categoryList = categoryService.queryByParentIdCategory("1");

        List<CategoryPO> categoryPOList = categoryService.queryAllCategory();

        HashMap<String, Object> map = new HashMap<>();

        map.put("message","查询成功");
        map.put("status",100);
        map.put("data",categoryPOList);

        return map;
    }

}
