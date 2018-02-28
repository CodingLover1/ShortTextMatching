package com.ws.crawler.config;

import java.util.ArrayList;

public class CrawlerConfig {
   private String subDomainRegex;
   private ArrayList<String> divIdList;
   private String storePath;
   private String downloadRegex;
   private String classRegex="[\u4e00-\u9fa5]{2,3}";
   private String indexDir;

    public String getSubDomainRegex() {
        return subDomainRegex;
    }

    public void setSubDomainRegex(String subDomainRegex) {
        this.subDomainRegex = subDomainRegex;
    }

    public String getStorePath() {
        return storePath;
    }

    public void setStorePath(String storePath) {
        this.storePath = storePath;
    }

    public String getDownloadRegex() {
        return downloadRegex;
    }

    public void setDownloadRegex(String downloadRegex) {
        this.downloadRegex = downloadRegex;
    }

    public String getClassRegex() {
        return classRegex;
    }

    public void setClassRegex(String classRegex) {
        this.classRegex = classRegex;
    }

    public ArrayList<String> getDivIdList() {
        return divIdList;
    }

    public void setDivIdList(ArrayList<String> divIdList) {
        this.divIdList = divIdList;
    }

    public String getIndexDir() {
        return indexDir;
    }

    public void setIndexDir(String indexDir) {
        this.indexDir = indexDir;
    }
}
