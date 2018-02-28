package com.ws.util;

import java.text.SimpleDateFormat;

public class TimeUtil {

    public static java.sql.Date getToday(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date utoday=new java.util.Date();
        String ustr=simpleDateFormat.format(utoday);
        java.util.Date utoday2;
        try {
            utoday2 = simpleDateFormat.parse(ustr);
        }catch (Exception e){
            e.printStackTrace();
            utoday2=new java.util.Date();
        }
        java.sql.Date today=new java.sql.Date(utoday2.getTime());
        return today;
    }
}
