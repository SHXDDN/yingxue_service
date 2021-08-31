package com.baizhi.po;

import com.baizhi.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryPO {

    private String id;
    private String cateName;
    private String levels;
    private String parentId;

    private List<Category> categoryList;


}
