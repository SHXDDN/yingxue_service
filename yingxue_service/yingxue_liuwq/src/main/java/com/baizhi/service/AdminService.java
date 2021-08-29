package com.baizhi.service;

import com.baizhi.entity.Admin;

import java.util.HashMap;
import java.util.Map;

public interface AdminService {

    HashMap<String,Object> login(Admin admin, String enCode);

    HashMap<String,Object> queryAllPage(Integer page,Integer pageSize);

    HashMap<String,Object> update(Admin admin);

    HashMap<String,Object> delete(Admin admin);

    HashMap<String,Object> add(Admin admin);

    Admin queryById(String id);

}
