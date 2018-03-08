package com.ws.crawler.main;

import com.ws.crawler.config.CrawlerConfig;
import com.ws.crawler.start.CrawlerStart;
import org.omg.CORBA.CODESET_INCOMPATIBLE;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 网页爬虫的启动类
 *  "http://news.baidu.com/"
 * "http://news.sina.com.cn/"
 * "http://news.sohu.com/"
 * "http://news.163.com/"
 */
public class CrawlerMain {
    public static void main(String[] args){
        CrawlerStart crawlerStart =new CrawlerStart();

        //设置请求方式及起始URL
        crawlerStart.setRequestMethod("get","http://news.sina.com.cn");

        //设置请求头部参数信息
        HashMap<String,String> headParams=new HashMap<>();
      //  headParams.put("Referer","https://www.baidu.com/link?url=ejsK-B_2Q4oKQheOpV5Aoag1TprWZ9xne6EtoUOa1jWRVqMO-tZZao9ATmzWHCY3&wd=&eqid=bc34862e000022f4000000055a654203");
        headParams.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
        crawlerStart.setRequestHead(headParams);

        //设置配置信息，包括要处理的url的正则表达式，下载的存储地址
        CrawlerConfig config=new CrawlerConfig();
        config.setSubDomainRegex("http://[a-z]+.sina.com.cn/");
        config.setDownloadRegex("[a-z]+/[\\d]{4}-[\\d]{2}-[\\d]{2}/doc-[a-z]+[\\d]+.shtml");

        //设置存储路径
        config.setStorePath("E:\\mycrawler\\data1\\");
        ArrayList<String> divIdList=new ArrayList<>();
        divIdList.add("artibody");
        divIdList.add("article");
        config.setDivIdList(divIdList);
        config.setIndexDir("E:\\mycrawler\\index1");
        crawlerStart.setConfig(config);
        crawlerStart.start();
    }
}

/**
 * sina:
 * subDomianRegex:"http://[a-z]+.sina.com.cn/"
 * downloadRegex:"[a-z]+/[\\d]{4}-[\\d]{2}-[\\d]{2}/doc-[a-z]+[\\d]+.shtml"
 * divId: artibody article
 *
 * neteasy:
 * subDomainRegex:"http://[a-z]+.163.com/"
 * downloadRegex:"[\\d]{2}/[\\d]{4}/[\\d]{2}/[a-zA-z\\d]+.html"
 * divId:endText
 */
