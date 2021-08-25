package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Cleanup;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "yx_user")
public class User {

    @Id
    private String id;

    private String username;

    private String password;

    private String sign;

    @Column(name = "head_img")
    private String headImg;

    private String phone;

    private String wechat;

    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "create_time")
    private Date createTime;

    private String sex;

    private String city;

}