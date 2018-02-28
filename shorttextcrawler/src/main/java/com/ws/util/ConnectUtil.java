package com.ws.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class ConnectUtil {
    private static int maxConnectTimes=3;
    private static int timeOut=50000;

    //使用HttpClient建立连接对象，获取网页内容实体，再由Jsoup进行页面元素内容的处理
    public static String getPageSourceCode(String url){
        try {
            int n=maxConnectTimes;
            HttpClient httpClient = new DefaultHttpClient();
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,timeOut);
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse=null;
            while(n>0) {
                httpResponse = httpClient.execute(httpGet);
                StatusLine statusLine=httpResponse.getStatusLine();
                if(statusLine.getStatusCode()!= HttpStatus.SC_OK)
                    n--;
                else
                    break;
            }
            if(n!=0){
                HttpEntity entity=httpResponse.getEntity();
                InputStream pageContent = entity.getContent();
                BufferedReader reader=new BufferedReader(new InputStreamReader(pageContent));
                StringBuilder pageSourceCode=new StringBuilder();
                String line;
                while((line=reader.readLine())!=null){
                    pageSourceCode.append(line);
                    pageSourceCode.append("\n");
                }
                reader.close();
                pageContent.close();
                return pageSourceCode.toString();
            }else{
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
