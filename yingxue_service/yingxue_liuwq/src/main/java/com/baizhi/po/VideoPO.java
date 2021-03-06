package com.baizhi.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoPO {

    private String id;
    private String videoTitle;
    private String cover;
    private String path;
    private Date uploadTime;
    private String description;
    private Integer likeCount;

    private String cateName;
    private String userPhoto;

    private String uid;
    private String cid;

}
