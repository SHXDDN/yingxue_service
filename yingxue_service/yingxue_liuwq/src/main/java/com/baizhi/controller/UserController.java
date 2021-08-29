package com.baizhi.controller;

import com.baizhi.annotation.AddLog;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import com.baizhi.vo.CommonVO;
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

    @AddLog("修改用户")
    @RequestMapping("update")
    public CommonVO update(@RequestBody User user){

        try {
           userService.update(user);
            return CommonVO.success("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return CommonVO.faild("操作失败");
        }

    }

}
