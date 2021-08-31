package com.baizhi.controller;

import com.baizhi.annotation.AddLog;
import com.baizhi.dto.PageDTO;
import com.baizhi.entity.User;
import com.baizhi.entity.Video;
import com.baizhi.service.VideoService;
import com.baizhi.vo.CommonVO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;

@RestController
@RequestMapping("video")
@CrossOrigin
public class VideoController {

    @Resource
    VideoService videoService;

    @RequestMapping("queryAllPage")
    public HashMap<String,Object> queryAllPage(@RequestBody PageDTO pageDTO){

        HashMap<String, Object> map = videoService.queryAllPage(pageDTO.getPage(), pageDTO.getPageSize());

        return map;
    }

    @RequestMapping("queryById")
    public Video queryAById(String id){
        return videoService.queryById(id);
    }

    @RequestMapping("add")
    public CommonVO add(@RequestBody Video video){
        try {
            videoService.add(video);
            return CommonVO.success("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return CommonVO.success("操作失败");
        }
    }

    @RequestMapping("update")
    public CommonVO update(@RequestBody Video video){
        try {
            videoService.update(video);
            return CommonVO.success("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return CommonVO.success("操作失败");
        }
    }

    @RequestMapping("uploadHeadImg")
    public HashMap<String,String> uploadHeadImg(MultipartFile videoFile){
        return videoService.uploadHeadImgAliyun(videoFile);
    }

    @PostMapping("delete")
    public CommonVO delete(@RequestBody Video video){
        try {
            videoService.delete(video);
            return CommonVO.success("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return CommonVO.faild("操作失败");
        }
    }

}
