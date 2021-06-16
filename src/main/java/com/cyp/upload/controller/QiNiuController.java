package com.cyp.upload.controller;

import com.alibaba.fastjson.JSON;
import com.cyp.upload.service.QiNiuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class QiNiuController {

    @Autowired
    QiNiuService qiNiuService;

    @ResponseBody
    @RequestMapping(value = "/uploadQiniuFile", method = RequestMethod.POST)
    private String postUserInforUpDate(HttpServletRequest request, @RequestParam("file") MultipartFile multipartFile) throws IOException {

        // 用来获取其他参数
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        if (!multipartFile.isEmpty()) {
            FileInputStream inputStream = (FileInputStream) multipartFile.getInputStream();
            //获取文件名称
            String filename = multipartFile.getOriginalFilename();
            //1.在文件名称中添加随机唯一的值
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            filename = uuid+filename;
            String path = qiNiuService.uploadQNImg(inputStream,filename); // KeyUtil.genUniqueKey()生成图片的随机名
            System.out.print("七牛云返回的图片链接:" + "http://"+path);
            Map map = new HashMap();
            map.put("code","200");
            map.put("url","http://"+path);
            return JSON.toJSONString(map);
        }
        return "上传失败";
    }
}
