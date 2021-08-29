package com.baizhi.util;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class AliyunOSSUtil {

    // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
    private static String endpoint = "https://oss-cn-beijing.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
    private static String accessKeyId = "LTAI5t7X3ts4mMtin1gDY5Un";
    private static String accessKeySecret = "blyatYafFonJe72crr8ylHhvYMhSBK";

    /**
     * 将文件以字节数组的形式上传至阿里云
     *  参数：
     * @param bucketName（String）  存储空间名
     * @param fileName（String）    文件名   目录名/文件名
     * @param headImg（MultipartFile） MultipartFile类型的文件
     * */
    public static void uploadfileBytes(String bucketName, String fileName, MultipartFile headImg){

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId,accessKeySecret);

        // 填写Byte数组。

        byte[] bytes = new byte[0];
        try {
            bytes = headImg.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 依次填写Bucket名称（例如examplebucket）和Object完整路径（例如exampledir/exampleobject.txt）。Object完整路径中不能包含Bucket名称。
        ossClient.putObject(bucketName, fileName, new ByteArrayInputStream(bytes));

        // 关闭OSSClient。
        ossClient.shutdown();
    }


    /**
     * 将阿里云文件删除
     *  参数：
     * @param bucketName（String）  存储空间名  yingx-2103
     * @param fileName（String）    文件名   目录名/文件名  lalala.png
     * */
    public static void deleteBucket(String bucketName, String fileName){

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除文件或目录。如果要删除目录，目录必须为空。
        ossClient.deleteObject(bucketName, fileName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
