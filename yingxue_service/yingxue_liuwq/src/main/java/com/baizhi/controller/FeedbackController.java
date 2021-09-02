package com.baizhi.controller;

import com.baizhi.annotation.AddCache;
import com.baizhi.annotation.DelCache;
import com.baizhi.entity.Feedback;
import com.baizhi.service.FeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("feedback")
public class FeedbackController {

    @Resource
    FeedbackService feedbackService;


    @AddCache
    @RequestMapping("queryAllPage")
    public HashMap<String,Object> queryAllPage(Integer page,Integer pageSize){
        log.info("page{}",page);
        log.info("pageSize{}",pageSize);
        System.out.println(1);
        HashMap<String, Object> map = feedbackService.queryAllPage(page, pageSize);

        return map;
    }

    @DelCache
    @RequestMapping("delete")
    public HashMap<String,Object> delete(@RequestBody Feedback feedback){
        log.info("接收的对象{}",feedback);

        HashMap<String, Object> delete = feedbackService.delete(feedback);

        return delete;
    }
}
