package com.ws.test;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;


import java.io.File;
import java.io.InputStream;

public class GeneralTest {

    @Test
    public void httpClientTest()throws Exception{
        HttpClient httpClient=new DefaultHttpClient();
        HttpGet get=new HttpGet("http://www.baidu.com/");
        HttpResponse response=httpClient.execute(get);
        HttpEntity entity=response.getEntity();
        Header contentType=entity.getContentType();
    }

    @Test
    public void fileTest() throws Exception{
        File file=new File("E://mycrawler"+File.separator+"data");
        if(!file.exists()){
            file.mkdir();
        }
        System.out.println("success");

    }

    @Test
    public void htmlTest() throws  Exception{
        HttpClient httpClient=new DefaultHttpClient();
        HttpGet get=new HttpGet("http://news.163.com");
        HttpResponse response=httpClient.execute(get);
        HttpEntity entity=response.getEntity();
        String pagCode= EntityUtils.toString(entity);
        Document document2=Jsoup.parse(pagCode);
        Document document= Jsoup.connect("http://news.163.com").get();

        Elements elements2=document2.select("a[href]");
        System.out.println(elements2.size());
        Elements elements=document.select("a[href]");
        System.out.println(elements.size());

        String charset=EntityUtils.getContentCharSet(entity);

        System.out.println(charset);

    }
}
