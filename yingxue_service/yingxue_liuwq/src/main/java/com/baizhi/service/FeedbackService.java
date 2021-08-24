package com.baizhi.service;

import com.baizhi.entity.Feedback;

import java.util.HashMap;

public interface FeedbackService {

    HashMap<String,Object> queryAllPage(Integer page,Integer pageSize);

    HashMap<String,Object> delete(Feedback feedback);

}
