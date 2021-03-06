package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.entity.User;
import com.baizhi.service.AdminService;
import com.baizhi.util.ImageCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("admin")
public class AdminController {

    @Resource
    HttpServletRequest request;
    @Autowired
    AdminService adminService;

    @RequestMapping("getImageCodes")
    public String getImageCodes(){
        String code = ImageCodeUtil.getSecurityCode();
        request.getServletContext().setAttribute("code",code);
        log.info("验证码  ："+code);
        String imgBase64 = null;
        try {
            imgBase64 = ImageCodeUtil.careateImgBase64(code);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imgBase64;
    }

    @RequestMapping("login")
    public HashMap<String,Object> login(@RequestBody Admin admin, String enCode){
        HashMap<String, Object> login = adminService.login(admin, enCode);
        return login;
    }

    @RequestMapping("logout")
    public void logout(){
        request.removeAttribute("admin");
    }

    @RequestMapping("queryAllPage")
    public HashMap<String,Object> queryAllPage(Integer page,Integer pageSize){
        log.info("page{}",page);
        log.info("pageSize{}",pageSize);

        HashMap<String, Object> map = adminService.queryAllPage(page, pageSize);

        return map;
    }

    @RequestMapping("update")
    public HashMap<String,Object> update(@RequestBody Admin admin){

        HashMap<String, Object> map = adminService.update(admin);

        return map;
    }

    @RequestMapping("delete")
    public HashMap<String,Object> delete(@RequestBody Admin admin){

        HashMap<String, Object> map = adminService.delete(admin);

        return map;
    }

    @RequestMapping("add")
    public HashMap<String,Object> add(@RequestBody Admin admin){

        HashMap<String, Object> map = adminService.add(admin);

        return map;
    }

    @GetMapping("queryById")
    public Admin queryById(String id){

        Admin admin1 = adminService.queryById(id);

        log.info("admin{}",admin1);

        return admin1;
    }
}
