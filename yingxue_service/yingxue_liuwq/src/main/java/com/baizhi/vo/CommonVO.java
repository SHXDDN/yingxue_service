package com.baizhi.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonVO {
    private String message;
    private Object status;

    public static CommonVO success(String message,Object status){
        CommonVO commonVO = new CommonVO();
        commonVO.setMessage(message);
        commonVO.setStatus(status);
        return commonVO;
    }

    public static CommonVO success(String message){
        CommonVO commonVO = new CommonVO();
        commonVO.setMessage(message);
        commonVO.setStatus(200);
        return commonVO;
    }
    public static CommonVO success(){
        CommonVO commonVO = new CommonVO();
        commonVO.setMessage("操作成功！！！");
        commonVO.setStatus(200);
        return commonVO;
    }


    public static CommonVO faild(String message,Object status){
        CommonVO commonVO = new CommonVO();
        commonVO.setMessage(message);
        commonVO.setStatus(status);
        return commonVO;
    }

    public static CommonVO faild(String message){
        CommonVO commonVO = new CommonVO();
        commonVO.setMessage(message);
        commonVO.setStatus(400);
        return commonVO;
    }


    public static CommonVO faild(){
        CommonVO commonVO = new CommonVO();
        commonVO.setMessage("操作失败！！！！");
        commonVO.setStatus(400);
        return commonVO;
    }
}
