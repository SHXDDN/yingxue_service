package com.baizhi.controller;

import com.baizhi.annotation.AddCache;
import com.baizhi.annotation.DelCache;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import com.baizhi.vo.CommonVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;

@RestController
@RequestMapping("user")
@CrossOrigin
@Slf4j
public class UserController {

    @Resource
    UserService userService;

    @AddCache
    @RequestMapping("queryAllPage")
    public HashMap<String,Object> queryAllPage(Integer page,Integer pageSize){
        log.info("page{}",page);
        log.info("pageSize{}",pageSize);

        HashMap<String, Object> map = userService.queryAllPage(page, pageSize);

        return map;
    }

    @DelCache
    @RequestMapping("update")
    public CommonVO update(@RequestBody User user,MultipartFile headImg){

        try {
            userService.delete(user);
            userService.uploadHeadImgAliyun(headImg);
           userService.update(user);
            return CommonVO.success("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return CommonVO.faild("操作失败");
        }
    }

    @DelCache
    @RequestMapping("add")
    public CommonVO add(@RequestBody User user){

        try {
            userService.add(user);
            return CommonVO.success("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return CommonVO.faild("操作失败");
        }
    }


    @RequestMapping("uploadHeadImg")
    public HashMap<String,String> uploadHeadImg(MultipartFile headImg){

        log.info("文件名：{}",headImg.getOriginalFilename());
        log.info("文件大小：{}",headImg.getSize());
        log.info("文件类型：{}",headImg.getContentType());

        String msg = userService.uploadHeadImgAliyun(headImg);

        HashMap<String, String> map = new HashMap<>();

        map.put("fileName",msg);
        return map;
    }

    @RequestMapping("queryById")
    public User queryById(String id){
        return userService.queryById(id);
    }


    @DelCache
    @RequestMapping("delete")
    public CommonVO delete(@RequestBody User user){

        try {
            userService.delete(user);
            return CommonVO.success("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return CommonVO.faild("操作失败");
        }
    }


}
