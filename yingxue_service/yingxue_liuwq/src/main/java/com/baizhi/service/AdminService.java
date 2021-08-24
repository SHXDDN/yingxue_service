package com.baizhi.service;

import com.baizhi.entity.Admin;

import java.util.HashMap;
import java.util.Map;

public interface AdminService {
    HashMap<String,Object> login(Admin admin, String enCode);
}
