package com.cyp.upload.service.impl;

import com.aliyun.oss.common.utils.DateUtil;
import com.cyp.upload.service.QiNiuService;
import com.cyp.upload.utils.DateUtils;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.util.Date;

@Service
public class QiNiuServiceImpl implements QiNiuService {

    @Autowired(required = false)
    DateUtils dateUtils;

    // 设置需要操作的账号的AK和SK
    private static final String ACCESS_KEY = "";
    private static final String SECRET_KEY = "";

    // 要上传的空间名称
    private static final String BUCKETNAME = "cypde";

    // 密钥
    private static final Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

    // 外链默认域名
    private static final String DOMAIN = "quryzmbu7.hb-bkt.clouddn.com";

    /**
     * 将图片上传到七牛云
     */
    public  String uploadQNImg(FileInputStream file, String key) {
        // 构造一个带指定Zone对象的配置类, 注意这里的Zone.zone0需要根据主机选择
        Configuration cfg = new Configuration(Zone.zone1());
        // 其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        // 生成上传凭证，然后准备上传

        try {
            //    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
            String upToken = auth.uploadToken(BUCKETNAME);
            try {
                //2.把文件按日期分类
//                Date date  = new Date();
//                String datePath = dateUtils.dateformat(date);
//                String filename = datePath +"/"+key;
                Response response = uploadManager.put(file, key, upToken, null, null);
                // 解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

                String returnPath = DOMAIN + "/" + putRet.key;
                // 这个returnPath是获得到的外链地址,通过这个地址可以直接打开图片
                return returnPath;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
