package com.baizhi.serviceimpl;

import com.baizhi.annotation.AddLog;
import com.baizhi.dao.LogMapper;
import com.baizhi.entity.Log;
import com.baizhi.entity.LogExample;
import com.baizhi.service.LogService;
import com.baizhi.util.UUIDUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class LogServiceImpl implements LogService {

    @Resource
    LogMapper logMapper;

    @Override
    public HashMap<String, Object> queryPage(Integer page, Integer pageSize) {

        HashMap<String,Object> map = new HashMap<>();

        //设置当前页
        map.put("page",page);

        //设置查询条件对象
        LogExample example = new LogExample();
        example.setOrderByClause("option_time desc");

        //查询总条数
        int count = logMapper.selectCountByExample(example);
        map.put("total",count);

        //创建分页对象
        RowBounds rowBounds = new RowBounds((page - 1) * pageSize, pageSize);

        List<Log> logs = logMapper.selectByExampleAndRowBounds(example, rowBounds);

        map.put("rows",logs);

        return map;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public void add(Log log) {

        log.setId(UUIDUtil.getUUID());
        logMapper.insertSelective(log);

    }
}
