package com.cyp.upload.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    /**
     * 时间转换成固定字符串函数
     * @param date 待转换时间
     * @return 转换后的字符串
     */
    public static String dateformat(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");//制定字符串格式
        String str=sdf.format(date);//将时间转换成指定格式
        return str;
    }


}
