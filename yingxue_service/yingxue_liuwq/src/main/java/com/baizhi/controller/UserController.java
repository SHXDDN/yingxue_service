package com.baizhi.controller;

import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

@RestController
@RequestMapping("user")
@CrossOrigin
@Slf4j
public class UserController {

    @Resource
    UserService userService;

    @RequestMapping("queryAllPage")
    public HashMap<String,Object> queryAllPage(Integer page,Integer pageSize){
        log.info("page{}",page);
        log.info("pageSize{}",pageSize);

        HashMap<String, Object> map = userService.queryAllPage(page, pageSize);

        return map;
    }

    @RequestMapping("update")
    public HashMap<String,Object> update(@RequestBody User user){

        HashMap<String, Object> map = userService.update(user);

        return map;
    }

}
