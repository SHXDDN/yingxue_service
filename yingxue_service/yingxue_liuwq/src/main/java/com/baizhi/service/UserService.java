package com.baizhi.service;

import com.baizhi.entity.User;

import java.util.HashMap;

public interface UserService {

    HashMap<String,Object> queryAllPage(Integer page,Integer pageSize);

    HashMap<String,Object> update(User user);

}
