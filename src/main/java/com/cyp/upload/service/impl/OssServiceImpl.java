package com.cyp.upload.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.cyp.upload.service.OssService;
import com.cyp.upload.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {

    @Autowired(required = false)
    DateUtils dateUtils;

    public String uploadFileAvatar(MultipartFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "oss-cn-beijing.aliyuncs.com";
        String accessKeyId = "";
        String accessKeySecret = "";
        String bucketName = "cypde";
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        InputStream inputStream = null;
        try {
            //获取文件流
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取文件名称
        String filename = file.getOriginalFilename();
        //1.在文件名称中添加随机唯一的值
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        filename = uuid+filename;

        //2.把文件按日期分类
        Date date  = new Date();
        String datePath = dateUtils.dateformat(date);
        filename = datePath +"/"+filename;

        //调用OSS方法实现上传
        ossClient.putObject(bucketName, filename, inputStream);
        ossClient.shutdown();
        //把上传之后的文件路径返回
        //需要把上传到阿里云oss路径手动拼接出来
        //路径规则：https://edu-cgq.oss-cn-chengdu.aliyuncs.com/QQ%E5%9B%BE%E7%89%8720200502111905.jpg
        String url = "https://"+bucketName+"."+endpoint+"/"+filename;
        return url;
    }
}
