package com.baizhi.serviceimpl;

import com.baizhi.dao.FeedbackMapper;
import com.baizhi.entity.Feedback;
import com.baizhi.entity.FeedbackExample;
import com.baizhi.service.FeedbackService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class FeedbackServiceImpl implements FeedbackService {

    @Resource
    FeedbackMapper feedbackMapper;

    @Override
    public HashMap<String, Object> queryAllPage(Integer page, Integer pageSize) {
        HashMap<String, Object> map = new HashMap<>();

        //设置当前页
        map.put("page",page);

        //设置总页数
        FeedbackExample example = new FeedbackExample();
        int count = feedbackMapper.selectCountByExample(example);
        map.put("total",count);

        //设置分页对象 参数：起始条数，显示条数
        RowBounds rowBounds = new RowBounds((page-1)*pageSize,pageSize);
        //设置分页查数据
        List<Feedback> feedbacks = feedbackMapper.selectByExampleAndRowBounds(example, rowBounds);
        map.put("rows",feedbacks);

        return map;
    }

    @Override
    public HashMap<String, Object> delete(Feedback feedback) {

        HashMap<String, Object> map = new HashMap<>();


        try {
            feedbackMapper.delete(feedback);
            map.put("message","数据删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("message","数据删除失败");
        }
        return map;
    }


}
