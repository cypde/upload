package com.cyp.upload.service;

import org.springframework.stereotype.Service;

import java.io.FileInputStream;


public interface QiNiuService {

    public String uploadQNImg(FileInputStream file, String key) ;
}
