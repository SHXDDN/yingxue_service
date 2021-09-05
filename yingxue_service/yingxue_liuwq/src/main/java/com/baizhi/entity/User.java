package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
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

    @Excel(name = "ID",width = 20)
    @Id
    private String id;

    @Excel(name = "用户名")
    private String username;

    @Excel(name = "密码", isColumnHidden = true)
    private String password;

    @Excel(name = "签名")
    private String sign;

    @Excel(name = "头像", type = 2)
    @Column(name = "head_img")
    private String headImg;

    @Excel(name = "手机号")
    private String phone;

    @Excel(name = "微信号")
    private String wechat;

    @Excel(name = "状态")
    private String status;

    @Excel(name = "创建日期",format = "yyyy年MM月dd日",width = 15)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "create_time")
    private Date createTime;

    @Excel(name = "性别")
    private String sex;

    @Excel(name = "城市")
    private String city;

}