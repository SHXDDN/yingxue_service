package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "yx_video")
@Document(indexName = "yingx",type="video")
public class Video {

    @Id
    private String id;

    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String title;

    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String description;

    @Field(type = FieldType.Keyword)
    @Column(name = "video_path")
    private String videoPath;

    @Field(type = FieldType.Keyword)
    @Column(name = "cover_path")
    private String coverPath;

    @Field(type = FieldType.Keyword)
    private String status;

    @Field(type = FieldType.Keyword)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "create_time")
    private Date createTime;

    @Field(type = FieldType.Keyword)
    @Column(name = "category_id")
    private String categoryId;

    @Field(type = FieldType.Keyword)
    @Column(name = "user_id")
    private String userId;

    @Field(type = FieldType.Keyword)
    @Column(name = "group_id")
    private String groupId;



    @Transient  //该字段数据库没有
    private Integer playCount; //播放次数
    @Transient
    private Integer likeCount; //点赞次数

    public Video(String id, String title, String description, String videoPath, String coverPath, String status, Date createTime, String categoryId, String userId, String groupId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.videoPath = videoPath;
        this.coverPath = coverPath;
        this.status = status;
        this.createTime = createTime;
        this.categoryId = categoryId;
        this.userId = userId;
        this.groupId = groupId;
    }
}