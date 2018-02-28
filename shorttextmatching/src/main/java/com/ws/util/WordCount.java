package com.ws.util;

public class WordCount {

    public static int wordCount(String src,String target){
        String tmp=" "+src+" ";
        return tmp.split(target).length-1;
    }
}
