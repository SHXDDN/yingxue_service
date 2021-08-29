package com.baizhi.controller;

import com.baizhi.service.LogService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

@RestController
@CrossOrigin
@RequestMapping("log")
public class LogController {

    @Resource
    LogService logService;

    @PostMapping("queryAllPage")
    public HashMap<String, Object> queryAllPage(Integer page, Integer pageSize){


        HashMap<String, Object> map = logService.queryPage(page,pageSize);

        return map;
    }
}
