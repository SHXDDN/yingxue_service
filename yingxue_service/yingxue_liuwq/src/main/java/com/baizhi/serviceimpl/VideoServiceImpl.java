package com.baizhi.serviceimpl;

import com.baizhi.annotation.AddLog;
import com.baizhi.dao.VideoMapper;
import com.baizhi.entity.User;
import com.baizhi.entity.Video;
import com.baizhi.entity.VideoExample;
import com.baizhi.service.VideoService;
import com.baizhi.util.AliyunOSSUtil;
import com.baizhi.util.UUIDUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class VideoServiceImpl implements VideoService {

    @Resource
    VideoMapper videoMapper;
    @Resource
    HttpServletRequest request;



    @Override
    public HashMap<String, Object> queryAllPage(Integer page, Integer pageSize) {
        HashMap<String, Object> map = new HashMap<>();

        map.put("page",page);

        //设置总页数
        VideoExample example = new VideoExample();
        int count = videoMapper.selectCountByExample(example);
        map.put("total",count);

        RowBounds rowBounds = new RowBounds((page-1)*pageSize,pageSize);
        List<Video> videoList = videoMapper.selectByExampleAndRowBounds(example, rowBounds);
        map.put("rows",videoList);

        return map;
    }

    @Override
    public void update(Video video) {
        videoMapper.updateByPrimaryKeySelective(video);
    }

    @AddLog("添加视频")
    @Override
    public void add(Video video) {
        video.setId(UUIDUtil.getUUID());
        video.setCreateTime(new Date());
        video.setCoverPath(video.getVideoPath());
        videoMapper.insertSelective(video);
    }

    @Override
    public String uploadHeadImgAliyun(MultipartFile videoFile) {

        String type = videoFile.getContentType();
        String[] split = type.split("/");

        String message = null;

        //判断类型是不是video

            //获取文件名
            String filename = videoFile.getOriginalFilename();

            //拼接时间戳
            String newName = new Date().getTime()+"-"+filename;

            //配置存储空间
            String bucketName = "yingx-liuwq";

            //配置文件名
            String objectName = "video/"+newName;

            //拼接图片网络地址
            String netPath = "http://yingx-liuwq.oss-cn-beijing.aliyuncs.com/"+objectName;

            //文件上传
            try {
                AliyunOSSUtil.uploadfileBytes(bucketName,objectName,videoFile);
                //返回文件名
                message = netPath;
            } catch (Exception e) {
                e.printStackTrace();
                message = netPath;
            }

        return message;
    }

    @Override
    public Video queryById(String id) {
        return videoMapper.selectByPrimaryKey(id);
    }

    @AddLog("删除视频")
    @Override
    public void delete(Video video) {
        //根据文件信息
        Video videos = videoMapper.selectOne(video);
        //获取图片网络路径
        String path = videos.getVideoPath();
        //字符串处理，将路径替换成空
        String videoPath = path.replace("http://yingx-liuwq.oss-cn-beijing.aliyuncs.com/", "");

        AliyunOSSUtil.deleteBucket("yingx-liuwq",videoPath);

        videoMapper.delete(video);

    }
}
