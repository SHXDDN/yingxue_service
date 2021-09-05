package com.baizhi.serviceimpl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.annotation.AddLog;
import com.baizhi.dao.UserMapper;
import com.baizhi.entity.Feedback;
import com.baizhi.entity.FeedbackExample;
import com.baizhi.entity.User;
import com.baizhi.entity.Video;
import com.baizhi.service.UserService;
import com.baizhi.util.AliyunOSSUtil;
import com.baizhi.util.UUIDUtil;
import com.baizhi.vo.CommonVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;
    @Resource
    HttpServletRequest request;


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

    }
    @AddLog("添加用户")
    @Override
    public void add(User user) {
        user.setId(UUIDUtil.getUUID());
        userMapper.insertSelective(user);

    }

    @Override
    public String uploadHeadImgAliyun(MultipartFile headImg) {
        //获取文件名
        String filename = headImg.getOriginalFilename();

        //拼接时间戳
        String newName = new Date().getTime()+"-"+filename;

        //配置存储空间
        String bucketName = "yingx-liuwq";

        //配置文件名
        String objectName = "userImg/"+newName;

        //拼接图片网络地址
        String netPath = "http://yingx-liuwq.oss-cn-beijing.aliyuncs.com/"+objectName;

        //文件上传
        String message = null;
        try {
            AliyunOSSUtil.uploadfileBytes(bucketName,objectName,headImg);
            //返回文件名
            message = netPath;
        } catch (Exception e) {
            e.printStackTrace();
            message = netPath;
        }

        return message;
    }

    @Override
    public User queryById(String id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @AddLog("删除用户")
    @Override
    public void delete(User user) {

        //根据文件信息
        User users = userMapper.selectOne(user);
        //获取图片网络路径
        String headImg = users.getHeadImg();
        //字符串处理，将路径替换成空
        String imgPath = headImg.replace("http://yingx-liuwq.oss-cn-beijing.aliyuncs.com/", "");

        AliyunOSSUtil.deleteFile("yingx-liuwq",imgPath);

        //删除数据
        userMapper.delete(user);
    }

    @AddLog("导出用户信息")
    @Override
    public void SearchVideo() {

        User user = new User();

        //查询用户数据
        List<User> users = userMapper.selectAll();

        //创建导出参数对象  参数:大标题,子标题,工作表名
        ExportParams exportParams = new ExportParams("用户信息表","用户表");

        //设置导出Excel的参数  参数:导出参数对象,要导出的类对象,要导出的数据
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, user.getClass(), users);

        //到处excel
        try {
            workbook.write(new FileOutputStream(new File("D://framework//11 后期项目//Day9 Poi,EasyPOI//user.xls")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
