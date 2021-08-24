package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "yx_feedback")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Feedback {

    @Id
    private String id;

    private String title;

    private String content;

    @Column(name = "user_id")
    private Integer userId;

    @JsonFormat(locale = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime;


}