package com.baizhi.serviceimpl;

import com.baizhi.annotation.AddLog;
import com.baizhi.dao.LikeMapper;
import com.baizhi.dao.VideoMapper;
import com.baizhi.entity.LikeExample;
import com.baizhi.entity.Video;
import com.baizhi.entity.VideoExample;
import com.baizhi.po.VideoPO;
import com.baizhi.service.VideoService;
import com.baizhi.util.AliyunOSSUtil;
import com.baizhi.util.UUIDUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URL;
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
    @Resource
    LikeMapper likeMapper;



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
        //video.setCoverPath(video.getVideoPath());
        videoMapper.insertSelective(video);
    }

    @Override
    public HashMap<String,String> uploadHeadImgAliyun(MultipartFile videoFile) {


        //获取文件名
        String filename = videoFile.getOriginalFilename();

        //拼接时间戳
        String newName = new Date().getTime()+"-"+filename;

        //配置存储空间
        String bucketName = "yingx-liuwq";

        //配置文件名
        String objectName = "video/"+newName;

        HashMap<String, String> map = new HashMap<>();

        //将文件以字节数组的形式上传至阿里云
        AliyunOSSUtil.uploadfileBytes(bucketName,objectName,videoFile);
        //获取阿里云视频文件截取视频封面
        //@param bucketName（String）  存储空间名  yingx-2103
        //@param fileName（String）    要截取封面的视频名   目录名/文件名
        URL url = AliyunOSSUtil.videoInterceptCover(bucketName, objectName);
        //通过视频名获取图片名
        String coverName="videoCover/"+newName.split("\\.")[0]+".jpg";
        try {
            /**
             * 将视频封面上传至阿里云
             *  参数：
             * @param bucketName（String）  存储空间名  yingx-2103
             * @param fileName（String）    要上传的图片名   目录名/文件名
             * @param netFilePath（String）   要上传文件的网络路径
             * */
            AliyunOSSUtil.uploadNetIO(bucketName,coverName,url.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        //拼接图片网络地址
        String netPath = "http://yingx-liuwq.oss-cn-beijing.aliyuncs.com/"+objectName;
        String coverNames = "http://yingx-liuwq.oss-cn-beijing.aliyuncs.com/"+coverName;

        //返回文件名
        map.put("fileName",netPath);
        map.put("coverName",coverNames);

        return map;
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
        String ImgPath = videos.getVideoPath();
        String coverPath = videos.getCoverPath();
        //字符串处理，将路径替换成空
        String videoPath = ImgPath.replace("http://yingx-liuwq.oss-cn-beijing.aliyuncs.com/", "");
        String coverPathName = coverPath.replace("https://yingx-liuwq.oss-cn-beijing.aliyuncs.com/", "");

        AliyunOSSUtil.deleteFile("yingx-liuwq",videoPath);
        AliyunOSSUtil.deleteFile("yingx-liuwq",coverPathName);


        videoMapper.delete(video);

    }

    @Override
    public List<VideoPO> queryByReleaseTime() {

        //1.获取数据
        List<VideoPO> videoPOS = videoMapper.queryByReleaseTime();

        //2.遍历视频数据
        for (VideoPO videoPO : videoPOS) {

            //3.获取视频id
            String videoId = videoPO.getId();

            //设置查询条件
            LikeExample example = new LikeExample();
            example.createCriteria().andVideoIdEqualTo(videoId);

            //4.根据视频id查询视频点赞数
            Integer count  = likeMapper.selectCountByExample(example);

            //5.将点赞数放入对象
            videoPO.setLikeCount(count);

        }

        return videoPOS;
    }
}
