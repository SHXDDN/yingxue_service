package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "yx_category")
public class Category {

    @Id
    private String id;

    @Column(name = "cate_name")
    private String cateName;

    private Integer levels;

    @Column(name = "parent_id")
    private String parentId;

}