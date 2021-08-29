package com.baizhi.serviceimpl;

import com.baizhi.annotation.AddLog;
import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import com.baizhi.entity.FeedbackExample;
import com.baizhi.entity.User;
import com.baizhi.service.AdminService;
import com.baizhi.util.Md5Utils;
import com.baizhi.util.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;


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

    @Override
    public HashMap<String, Object> queryAllPage(Integer page, Integer pageSize) {
        HashMap<String, Object> map = new HashMap<>();

        map.put("page",page);

        //设置总页数
        FeedbackExample example = new FeedbackExample();
        int count = adminDao.selectCountByExample(example);
        map.put("total",count);

        //设置分页对象 参数：起始条数，显示条数
        RowBounds rowBounds = new RowBounds((page-1)*pageSize,pageSize);
        //设置分页查数据
        List<Admin> admins = adminDao.selectByExampleAndRowBounds(example, rowBounds);
        map.put("rows",admins);
        return map;
    }

    @AddLog("修改管理员")
    @Override
    public HashMap<String, Object> update(Admin admin) {

        HashMap<String, Object> map = new HashMap<>();

        try {
            adminDao.updateByPrimaryKeySelective(admin);
            map.put("message","操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("message","操作失败");
        }
        return map;
    }

    @AddLog("删除管理员")
    @Override
    public HashMap<String, Object> delete(Admin admin) {

        HashMap<String, Object> map = new HashMap<>();

        try {
            adminDao.delete(admin);
            map.put("message","操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("message","操作失败");
        }
        return map;
    }

    @AddLog("添加管理员")
    @Override
    public HashMap<String, Object> add(Admin admin) {

        HashMap<String, Object> map = new HashMap<>();

        try {
            admin.setId(UUIDUtil.getUUID());
            admin.setSalt(Md5Utils.getSalt(4));
            admin.setStatus("1");
            adminDao.insert(admin);
            map.put("message","添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("message","添加失败");
        }
        return map;
    }

    @Override
    public Admin queryById(String id) {

        return adminDao.selectByPrimaryKey(id);
    }
}
