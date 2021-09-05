package com.baizhi.serviceimpl;

import com.baizhi.annotation.AddLog;
import com.baizhi.dao.LikeMapper;
import com.baizhi.dao.VideoMapper;
import com.baizhi.entity.LikeExample;
import com.baizhi.entity.Video;
import com.baizhi.entity.VideoExample;
import com.baizhi.po.VideoPO;
import com.baizhi.repository.VideoRepository;
import com.baizhi.service.VideoService;
import com.baizhi.util.AliyunOSSUtil;
import com.baizhi.util.UUIDUtil;
import org.apache.ibatis.session.RowBounds;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@Transactional
public class VideoServiceImpl implements VideoService {

    @Resource
    VideoMapper videoMapper;
    @Resource
    HttpServletRequest request;
    @Resource
    LikeMapper likeMapper;
    @Resource
    RestHighLevelClient restHighLevelClient;
    @Resource
    VideoRepository videoRepository;



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

    @Override
    public List<Video> searchVideo(String content) {
        //创建处理高亮的对象
        HighlightBuilder highlightBuilder = new HighlightBuilder()
                .preTags("<span style='color:red'>")  //高亮前缀
                .postTags("</span>") //高亮的后缀
                .requireFieldMatch(false) //开启多行高亮
                .field("*");

        //创建搜索条件对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .query(QueryBuilders
                        .queryStringQuery(content)  //搜索条件
                        .field("title")  //匹配的字段
                        .field("description")   //匹配的字段
                        .analyzer("ik_max_word"))  //指定搜索的分词器
                .highlighter(highlightBuilder);    //设置高亮查询

        //创建搜索请求对象
        SearchRequest searchRequest = new SearchRequest()
                .indices("yingx")  //设置索引
                .types("video")  //设置类型
                .source(searchSourceBuilder);  //设置搜索条件 搜索的方式

        //搜索索引
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**--------------------数据处理------------------------*/

        ArrayList<Video> videoList = new ArrayList<>();

        //获取命中的数据
        SearchHits hits = searchResponse.getHits();

        SearchHit[] hitss = hits.getHits();

        //获取搜索的数据数组
        for (SearchHit doc : hitss) {

            //获取map数据
            Map<String, Object> sourceAsMap = doc.getSourceAsMap();

            //获取数据
            String id = sourceAsMap.get("id").toString();
            String title = sourceAsMap.get("title").toString();
            String description = sourceAsMap.get("description").toString();
            String videoPath = sourceAsMap.get("videoPath").toString();
            String coverPath = sourceAsMap.get("coverPath").toString();
            String status = sourceAsMap.get("status").toString();
            String categoryId = sourceAsMap.get("categoryId").toString();
            String userId = sourceAsMap.get("userId").toString();

            //判断该数据是否为空
            //boolean b = sourceAsMap.containsKey("groupId");

            /*String groupId =null;
            if(sourceAsMap.get("groupId")!=null){
                groupId = sourceAsMap.get("groupId").toString();
            }*/

            String groupId =sourceAsMap.get("groupId")!=null?sourceAsMap.get("groupId").toString():null;

            String createTimes = sourceAsMap.get("createTime").toString();
            Date createTime =null;
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                createTime = simpleDateFormat.parse(createTimes);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //将数据封装为对象
            Video video = new Video(id, title, description, videoPath, coverPath, status, createTime, categoryId, userId, groupId);


            /**处理高亮的数据*/
            Map<String, HighlightField> highlightFields = doc.getHighlightFields();

            if(highlightFields.get("title")!=null){
                String titles = highlightFields.get("title").fragments()[0].toString();
                video.setTitle(titles);
            }

            if(highlightFields.get("description")!=null){
                String descriptions = highlightFields.get("description").fragments()[0].toString();
                video.setDescription(descriptions);
            }

            //将封装的对象放入集合
            videoList.add(video);
        }
        return videoList;
    }
}
