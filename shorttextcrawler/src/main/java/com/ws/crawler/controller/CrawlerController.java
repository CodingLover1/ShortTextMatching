package com.ws.crawler.controller;

import com.ws.crawler.config.CrawlerConfig;
import com.ws.lucene.Indexer;
import com.ws.mybatis.bean.News;
import com.ws.util.ConnectUtil;
import com.ws.util.NewsServer;
import com.ws.util.TimeUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CrawlerController {
    private static Logger log= LoggerFactory.getLogger(CrawlerController.class);
    private HashMap<String,String> subDomainMap;
    private CrawlerConfig config;
    //private HashSet<String> processedUrlSet=new HashSet<>();
    //private LinkedList<String> processUrlList=new LinkedList<>();


    /**
     * subDomainMap 中存放了新闻分类的标题以及所对应的URL
     * 在每一个类别的URL中获取该类别下可下载的新闻页面
     */
    public void process(){
            Iterator<Map.Entry<String, String>> iterator = subDomainMap.entrySet().iterator();
            Indexer indexer=null;
            try{
                indexer=new Indexer(config.getIndexDir());
            }catch (Exception e){
                e.printStackTrace();
            }

            //类别循环处理
            HashSet<String> processedUrlSet=new HashSet<>();
            while (iterator.hasNext()) {
                try {
                    LinkedList<String> processUrlList=new LinkedList<>();
                    Map.Entry<String, String> entry = iterator.next();
                    String className = entry.getKey();
                    String preDownloadUrl = entry.getValue();
                    String storePath = config.getStorePath() + className;
                    log.debug("正在处理 " + className + " : " + preDownloadUrl);
                    Document document = Jsoup.connect(preDownloadUrl).timeout(50000).get();
                   /* String pageSourceCode = ConnectUtil.getPageSourceCode(preDownloadUrl);
                    Document document = Jsoup.parse(pageSourceCode);*/
                    Elements elements = document.select("a[href]");
                    //提取该类别下可下载的页面
                    for (Element element : elements) {
                        String url = element.attr("abs:href");
                        log.debug(url);
                        if (url.matches(preDownloadUrl + config.getDownloadRegex())) {
                            //判断该url是否被处理过
                            if (!processedUrlSet.contains(url)) {
                                 processUrlList.add(url);
                                 processedUrlSet.add(url);
                            }
                        }
                    }


                    //下载该类别下所有的页面
                    download(processUrlList, storePath,config,indexer,className);
                   /* Worker worker=new Worker(processUrlList,storePath,config,className,indexer);
                    Thread thread=new Thread(worker,className);
                    thread.start();*/
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            try {
               //Thread.currentThread().join();
                indexer.close();
            }catch (Exception e){
                e.printStackTrace();
            }
           /* try {
                Thread.currentThread().join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }*/


    }

    private void download(LinkedList<String> processUrlList, String storePath, CrawlerConfig config,Indexer indexer ,String className){
        System.out.println("线程:"+Thread.currentThread().getName()+"存储路径:"+storePath+" 文件个数:"+processUrlList.size());
        int count=0;
        File directory=new File(storePath);
        if(!directory.exists()){
            directory.mkdir();
        }
        //统计该目录下已有的文件数
        if(directory.list()==null){
            count=0;
        }else{
            count=directory.list().length;
        }

        ArrayList<String> divIdList=config.getDivIdList();
        while(!processUrlList.isEmpty()){
            try {
                String url = processUrlList.pop();
                Document document = Jsoup.connect(url).timeout(50000).get();
               /* String pageSourceCode=ConnectUtil.getPageSourceCode(url);
                Document document=Jsoup.parse(pageSourceCode);*/
                String title=document.title();
                File file = new File(storePath + "\\" + count++ + ".txt");
                file.createNewFile();
                log.info("创建"+file.getName()+"文件");
                for(int i=0;i<divIdList.size();++i){
                    Elements elements=document.select("div[id="+divIdList.get(i)+"]");
                    if(elements!=null) {
                        for (Element element : elements) {
                            String text = element.text();
                            News news=new News(title,url,text, TimeUtil.getToday(),className);
                            log.info("正在将网页内容写入数据库...");
                            NewsServer.insert(news);
                            log.info("网页内容写入数据库完成");
                            log.info("正在将网页内容写入文件"+file.getName()+"...");
                            char[] textArray=text.toCharArray();
                            FileWriter writer = new FileWriter(file);
                            writer.write("title:"+title + "\r\n");
                            writer.write("url:"+url + "\r\n");
                            int index=0;
                            for(int j=0;j<(textArray.length/100);j++){
                                writer.write(textArray,index,100);
                                writer.write("\r\n");
                                index+=100;
                            }
                            writer.write(textArray,index,textArray.length-index);
                            writer.close();
                            log.info("网页内容写入文件"+file.getName()+"完成");
                            //这里的时候文件已经写入完成，可以顺便创建索引
                            log.info("开始为文件"+file.getName()+"创建索引...");
                            indexer.indexFile(file,news);
                            log.info("文件"+file.getName()+"创建索引完成");
                        }

                    }


                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
   /* private void download(LinkedList<String> processUrlList,String storePath){
        System.out.println("存储路径:"+storePath+" 文件个数:"+processUrlList.size());
        int count=0;
        File directory=new File(storePath);
        if(!directory.exists()){
            directory.mkdir();
        }
        //统计该目录下已有的文件数
        if(directory.list()==null){
            count=0;
        }else{
            count=directory.list().length;
        }

        ArrayList<String> divIdList=config.getDivIdList();
        while(!processUrlList.isEmpty()){
            try {
                String url = processUrlList.pop();
                //Document document = Jsoup.connect(url).timeout(50000).get();
                String pageSourceCode=ConnectUtil.getPageSourceCode(url);
                Document document=Jsoup.parse(pageSourceCode);
                String title=document.title();
                File file = new File(storePath + "\\" + count++ + ".txt");
                file.createNewFile();
                log.info("创建"+file.getName()+"文件");
                for(int i=0;i<divIdList.size();++i){
                    Elements elements=document.select("div[id="+divIdList.get(i)+"]");
                    if(elements!=null) {
                        for (Element element : elements) {
                            String text = element.text();
                            char[] textArray=text.toCharArray();
                            FileWriter writer = new FileWriter(file);
                            writer.write("title:"+title + "\r\n");
                            writer.write("url:"+url + "\r\n");
                            int index=0;
                            for(int j=0;j<(textArray.length/100);j++){
                                writer.write(textArray,index,100);
                                writer.write("\r\n");
                                index+=100;
                            }
                            writer.write(textArray,index,textArray.length-index);
                            writer.close();
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }*/

    public CrawlerConfig getConfig() {
        return config;
    }

    public void setConfig(CrawlerConfig config) {
        this.config = config;
    }

    public HashMap<String, String> getSubDomainMap() {
        return subDomainMap;
    }

    public void setSubDomainMap(HashMap<String, String> subDomainMap) {
        this.subDomainMap = subDomainMap;
    }
}


class Worker implements Runnable{
    private  LinkedList<String> processUrlList;
    private  CrawlerConfig config;
    private String storePath;
    private Logger log=LoggerFactory.getLogger(Worker.class);
    private String className;
    private volatile Indexer indexer=null;
    @Override
    public  void run(){
        download(processUrlList,storePath,config);
    }

   public  Worker(LinkedList<String> processUrlList,String storePath,CrawlerConfig config,String className,Indexer indexer){
        this.processUrlList=processUrlList;
        this.storePath=storePath;
        this.config=config;
        this.className=className;
        this.indexer=indexer;
   }

   public Worker(){

   }

    public LinkedList<String> getProcessUrlList() {
        return processUrlList;
    }

    public void setProcessUrlList(LinkedList<String> processUrlList) {
        this.processUrlList = processUrlList;
    }

    public String getStorePath() {
        return storePath;
    }

    public void setStorePath(String storePath) {
        this.storePath = storePath;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    private void download(LinkedList<String> processUrlList, String storePath, CrawlerConfig config){
        System.out.println("线程:"+Thread.currentThread().getName()+"存储路径:"+storePath+" 文件个数:"+processUrlList.size());
        int count=0;
        File directory=new File(storePath);
        if(!directory.exists()){
            directory.mkdir();
        }
        //统计该目录下已有的文件数
        if(directory.list()==null){
            count=0;
        }else{
            count=directory.list().length;
        }

        ArrayList<String> divIdList=config.getDivIdList();
        while(!processUrlList.isEmpty()){
            try {
                String url = processUrlList.pop();
                Document document = Jsoup.connect(url).timeout(50000).get();
               /* String pageSourceCode=ConnectUtil.getPageSourceCode(url);
                Document document=Jsoup.parse(pageSourceCode);*/
                String title=document.title();
                File file = new File(storePath + "\\" + count++ + ".txt");
                file.createNewFile();
                log.info("创建"+file.getName()+"文件");
                for(int i=0;i<divIdList.size();++i){
                    Elements elements=document.select("div[id="+divIdList.get(i)+"]");
                    if(elements!=null) {
                        for (Element element : elements) {
                            String text = element.text();
                            News news=new News(title,url,text, TimeUtil.getToday(),className);
                            log.info("正在将网页内容写入数据库...");
                            NewsServer.insert(news);
                            log.info("网页内容写入数据库完成");
                            log.info("正在将网页内容写入文件"+file.getName()+"...");
                            char[] textArray=text.toCharArray();
                            FileWriter writer = new FileWriter(file);
                            writer.write("title:"+title + "\r\n");
                            writer.write("url:"+url + "\r\n");
                            int index=0;
                            for(int j=0;j<(textArray.length/100);j++){
                                writer.write(textArray,index,100);
                                writer.write("\r\n");
                                index+=100;
                            }
                            writer.write(textArray,index,textArray.length-index);
                            writer.close();
                            log.info("网页内容写入文件"+file.getName()+"完成");
                            //这里的时候文件已经写入完成，可以顺便创建索引
                            log.info("开始为文件"+file.getName()+"创建索引...");
                            indexer.indexFile(file,news);
                            log.info("文件"+file.getName()+"创建索引完成");
                        }

                    }


                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    public Indexer getIndexer() {
        return indexer;
    }

    public void setIndexer(Indexer indexer) {
        this.indexer = indexer;
    }
}

