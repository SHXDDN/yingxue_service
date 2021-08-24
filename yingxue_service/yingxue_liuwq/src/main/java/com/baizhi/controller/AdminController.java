package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import com.baizhi.util.ImageCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
