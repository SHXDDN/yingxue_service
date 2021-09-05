package com.baizhi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.dao.UserMapper;
import com.baizhi.entity.User;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@SpringBootTest
class YingxueLiuwqApplicationTests {

    @Resource
    UserMapper userMapper;

    @Test
    void contextLoads() {
        User user = new User();

        //查询用户数据
        List<User> users = userMapper.selectAll();

        //创建导出参数对象  参数:大标题,子标题,工作表名
        ExportParams exportParams = new ExportParams("用户信息表","用户表");

        //设置导出Excel的参数  参数:导出参数对象,要导出的类对象,要导出的数据
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, user.getClass(), users);

        //到处excel
        try {
            workbook.write(new FileOutputStream(new File("D://framework//11 后期项目//Day9 Poi,EasyPOI//user.xls")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
