package com.baizhi.serviceimpl;

import com.baizhi.annotation.AddLog;
import com.baizhi.dao.UserMapper;
import com.baizhi.entity.Feedback;
import com.baizhi.entity.FeedbackExample;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import com.baizhi.vo.CommonVO;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;
    @Override
    public HashMap<String, Object> queryAllPage(Integer page, Integer pageSize) {

        HashMap<String, Object> map = new HashMap<>();

        map.put("page",page);

        //设置总页数
        FeedbackExample example = new FeedbackExample();
        int count = userMapper.selectCountByExample(example);
        map.put("total",count);

        //设置分页对象 参数：起始条数，显示条数
        RowBounds rowBounds = new RowBounds((page-1)*pageSize,pageSize);
        //设置分页查数据
        List<User> users = userMapper.selectByExampleAndRowBounds(example, rowBounds);
        map.put("rows",users);
        return map;
    }

    @AddLog("修改用户")
    @Override
    public void update(User user){

        userMapper.updateByPrimaryKeySelective(user);
        int q = 1/0;

    }
}
