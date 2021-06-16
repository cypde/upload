package com.cyp.upload.controller;

import com.alibaba.fastjson.JSON;
import com.cyp.upload.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
public class OssController {

    @Autowired
    OssService ossService ;
    //上传头像的方法
    @PostMapping("/uploadOssFile")
    public String uploadOssFile(MultipartFile file){
        //获取上传文件MultipartFile
        //返回上传到oss的路径
        String url = ossService.uploadFileAvatar(file);
        Map map = new HashMap();
        map.put("code","200");
        map.put("url",url);
        return JSON.toJSONString(map);
    }
}
