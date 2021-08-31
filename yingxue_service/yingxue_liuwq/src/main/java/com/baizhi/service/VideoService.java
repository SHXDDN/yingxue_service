package com.baizhi.service;

import com.baizhi.entity.User;
import com.baizhi.entity.Video;
import com.baizhi.po.VideoPO;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

public interface VideoService {

    HashMap<String,Object> queryAllPage(Integer page,Integer pageSize);

    void update(Video video);

    void add(Video video);

    HashMap<String,String> uploadHeadImgAliyun(MultipartFile headImg);

    Video queryById(String id);

    void delete(Video video);

    List<VideoPO> queryByReleaseTime();



}
