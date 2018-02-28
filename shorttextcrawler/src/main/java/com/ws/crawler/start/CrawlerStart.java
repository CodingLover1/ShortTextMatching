package com.ws.crawler.start;

import com.ws.crawler.config.CrawlerConfig;
import com.ws.crawler.controller.CrawlerController;
import com.ws.util.RegexMatch;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class CrawlerStart {
    private static Logger log= LoggerFactory.getLogger(CrawlerStart.class);
    private static int maxConnectTimes=3;
    private static int timeOut=50000;
    private CrawlerConfig config=null;
    private HttpClient  httpClient=null;
    private HttpGet httpGet=null;
    private HttpResponse httpResponse=null;
    private HashMap<String,String> headParams=null;


    public void setRequestMethod(String method,String url){
        httpGet=new HttpGet(url);
    }

    public void setRequestHead(HashMap<String,String> headParams){
        this.headParams=headParams;
        Iterator<Map.Entry<String,String>> iterator=headParams.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String,String> entry=iterator.next();
            httpGet.setHeader(entry.getKey(),entry.getValue());
        }
    }

    public void setConfig(CrawlerConfig config){
        this.config=config;
    }

    /**
     *
     * 建立主页面的连接
     */
    public void start(){
        try {
            httpClient = new DefaultHttpClient();
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,timeOut);
            int n=maxConnectTimes;
            while(n>0) {
                httpResponse = httpClient.execute(httpGet);
                StatusLine statusLine=httpResponse.getStatusLine();
                if(statusLine.getStatusCode()!= HttpStatus.SC_OK)
                    n--;
                else
                    break;
            }
            if(n==0) {
                log.info("连接URL异常！");
                System.exit(0);
            }
            else{
                processResponse();
            }
        }catch (Exception e){
            //e.printStackTrace();
            log.info("连接URL异常！");
            System.exit(0);
        }
    }

    /**
     *
     * 处理主页面的响应
     */
    private void processResponse(){
        log.info("StatusLine:"+httpResponse.getStatusLine());
        Header[] headers=httpResponse.getAllHeaders();
        for(int i=0;i<headers.length;++i){
            System.out.println(headers[i].getName()+" : "+headers[i].getValue());
        }
        HttpEntity httpEntity=httpResponse.getEntity();
        try {
            InputStream pageContent = httpEntity.getContent();
            BufferedReader reader=new BufferedReader(new InputStreamReader(pageContent));
            StringBuilder pageSourceCode=new StringBuilder();
            String line;
            while((line=reader.readLine())!=null){
                pageSourceCode.append(line);
                pageSourceCode.append("\n");
            }
            reader.close();
            pageContent.close();
            log.debug(pageSourceCode.toString());
            processPageSourceCode(pageSourceCode.toString());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 从主页面中提取新闻分类的URL；
     * @param pageSourceCode
     */
    private void processPageSourceCode(String pageSourceCode){
        HashMap<String,String> subDomainMap=new HashMap<>();
        Document document= Jsoup.parse(pageSourceCode);
        Elements elements=document.select("a[href]");

        for(Element e:elements){
            String href=e.attr("abs:href");
            String text=e.text();
            if(href.matches(config.getSubDomainRegex())){
                if(text.matches(config.getClassRegex())){
                    subDomainMap.put(text,href);
                    log.debug(text+":"+href);
                }
            }
        }

        CrawlerController controller=new CrawlerController();
        controller.setSubDomainMap(subDomainMap);
        controller.setConfig(config);
        controller.process();
    }
}
