package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "yx_video")
public class Video {

    @Id
    private String id;

    private String title;

    private String description;

    @Column(name = "video_path")
    private String videoPath;

    @Column(name = "cover_path")
    private String coverPath;

    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "category_id")
    private String categoryId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "group_id")
    private String groupId;

}