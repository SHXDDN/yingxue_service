package com.baizhi.service;

import com.baizhi.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

public interface UserService {

    HashMap<String,Object> queryAllPage(Integer page,Integer pageSize);

    void update(User user);

    void add(User user);

    String uploadHeadImgAliyun(MultipartFile headImg);

    User queryById(String id);

    void delete(User user);

}
