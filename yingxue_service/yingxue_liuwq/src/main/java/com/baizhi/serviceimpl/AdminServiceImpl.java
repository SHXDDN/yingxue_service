package com.baizhi.serviceimpl;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;


@Slf4j
@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Resource
    AdminDao adminDao;
    @Resource
    HttpServletRequest request;

    @Override
    public HashMap<String, Object> login(Admin admin, String enCode) {
        //获取验证码
        String code = (String) request.getServletContext().getAttribute("code");
        log.info("用户输入的验证码："+enCode);
        log.info("获取的验证码："+code);

        HashMap<String, Object> map = new HashMap<>();

        //判断验证码
        if(code.equals(enCode)){
            Admin admins = adminDao.queryByUsername(admin.getUsername());
            log.info("用户： "+admins);
            //判断用户
            if (admins != null){
                //判断状态
                if (admins.getStatus().equals("1")){
                    //判断密码
                    if (admin.getPassword().equals(admins.getPassword())){
                        //存储登陆标记
                        request.getServletContext().setAttribute("admin",admins);
                        map.put("message",admins);
                        map.put("status","200");
                    }else {
                        map.put("message","密码错误");
                        map.put("status","404");
                    }
                }else {
                    map.put("message","该账号已被冻结，请联系超级管理员");
                    map.put("status","404");
                }
            }else {
                map.put("message","用户不存在");
                map.put("status","404");
            }
        }else {
            map.put("message","验证码输入错误");
            map.put("status","404");
        }
        return map;
    }
}
