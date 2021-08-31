package com.baizhi.serviceimpl;

import com.baizhi.annotation.AddLog;
import com.baizhi.dao.CategoryMapper;
import com.baizhi.dto.CategoryPageDTO;
import com.baizhi.dto.PageDTO;
import com.baizhi.entity.Category;
import com.baizhi.entity.CategoryExample;
import com.baizhi.entity.LikeExample;
import com.baizhi.po.CategoryPO;
import com.baizhi.po.VideoPO;
import com.baizhi.service.CategoryService;
import com.baizhi.util.UUIDUtil;
import com.baizhi.vo.CommonQueryPageVO;
import com.baizhi.vo.CommonVO;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Resource
    CategoryMapper categoryMapper;

    @Override
    public CommonQueryPageVO queryOnePage(PageDTO pageDTO) {

        CategoryExample example = new CategoryExample();
        example.createCriteria().andLevelsEqualTo(1);
        int total = categoryMapper.selectCountByExample(example);

        RowBounds rowBounds = new RowBounds((pageDTO.getPage() - 1) * pageDTO.getPageSize(), pageDTO.getPageSize());

        List<Category> categoryList = categoryMapper.selectByExampleAndRowBounds(example, rowBounds);

        CommonQueryPageVO commonQueryPageVO = new CommonQueryPageVO(pageDTO.getPage(), total, categoryList);

        return commonQueryPageVO;
    }

    @AddLog("添加类别")
    @Override
    public CommonVO add(Category category) {

        try {
            if (category.getParentId() == null) {
                category.setLevels(1);
            } else {
                category.setLevels(2);
            }
            category.setId(UUIDUtil.getUUID());
            categoryMapper.insertSelective(category);
            return CommonVO.success("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return CommonVO.success("添加失败");
        }
    }

    @AddLog("删除类别")
    @Override
    public String delete(Category category) {

        String message = null;
        //判断是一级还是二级
        if (category.getParentId() == null) {
            //判断一级下面是否有二级
            CategoryExample example = new CategoryExample();
            //查询parentid是否包含一级id
            example.createCriteria().andParentIdEqualTo(category.getId());
            int count = categoryMapper.selectCountByExample(example);
            if (count == 0) {
                categoryMapper.delete(category);
                message = "一级类别删除成功";
                return message;
            } else {
                message = "一级类别下有二级类别，请先删除二级类别";
                throw new RuntimeException(message);

            }
        } else {
            categoryMapper.delete(category);
            message = "二级类别删除失败";
        }
        return message;
    }

    @Override
    public Category queryById(String id) {
        Category category = categoryMapper.selectByPrimaryKey(id);
        return category;
    }

    @AddLog("修改类别")
    @Override
    public CommonVO update(Category category) {
        try {
            categoryMapper.updateByPrimaryKeySelective(category);
            return CommonVO.success("修改成功！！！");
        } catch (Exception e) {
            e.printStackTrace();
            return CommonVO.faild("修改失败！！！");
        }
    }

    @Override
    public CommonQueryPageVO queryTwoPage(CategoryPageDTO categoryPageDTO) {
        CategoryExample example = new CategoryExample();
        example.createCriteria().andParentIdEqualTo(categoryPageDTO.getCategoryId());

        //根据条件查询一级类别数量
        int count = categoryMapper.selectCountByExample(example);
        RowBounds rowBounds = new RowBounds((categoryPageDTO.getPage() - 1) * categoryPageDTO.getPageSize(), categoryPageDTO.getPageSize());
        List<Category> categoryList = categoryMapper.selectByExampleAndRowBounds(example, rowBounds);

        CommonQueryPageVO commonQueryPageVO = new CommonQueryPageVO(categoryPageDTO.getPage(), count, categoryList);
        return commonQueryPageVO;
    }

    @Override
    public List<Category> queryByLevelsCategory(Integer levels) {
        CategoryExample example = new CategoryExample();
        example.createCriteria().andLevelsEqualTo(levels);
        List<Category> categories = categoryMapper.selectByExample(example);

        return categories;
    }

    @Override
    public List<CategoryPO> queryAllCategory() {
        //1.获取数据
        List<CategoryPO> categoryPO = categoryMapper.queryAllCategory();

        //2.遍历数据
        for (CategoryPO po : categoryPO) {

            CategoryExample example = new CategoryExample();
            example.createCriteria().andParentIdEqualTo("1");
            //example.createCriteria().andLevelsEqualTo(1);
            List<Category> categoryList = categoryMapper.selectByExample(example);
            po.setCategoryList(categoryList);
        }

        return categoryPO;
    }
}

