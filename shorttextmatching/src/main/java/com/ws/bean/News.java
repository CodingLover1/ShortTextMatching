package com.ws.bean;

import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public class News {
    private long newsId;
    private String newsTitle;
    private String newsHref;
    private String newsContent;
    private Date newsDate;
    private String className;
    private String newsAbstract;

    public News(){

    }

    public News(String newsTitle, String newsHref, String newsContent, Date newsDate, String className){
        this.newsTitle=newsTitle;
        this.newsHref=newsHref;
        this.newsContent=newsContent;
        this.newsDate=newsDate;
        this.className=className;
    }
    public News(long id,String newsTitle, String newsHref, String newsContent, Date newsDate, String className){
        this.newsId=id;
        this.newsTitle=newsTitle;
        this.newsHref=newsHref;
        this.newsContent=newsContent;
        this.newsDate=newsDate;
        this.className=className;
    }
    public long getNewsId() {
        return newsId;
    }

    public void setNewsId(long newsId) {
        this.newsId = newsId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsHref() {
        return newsHref;
    }

    public void setNewsHref(String newsHref) {
        this.newsHref = newsHref;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public Date getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(Date newsDate) {
        this.newsDate = newsDate;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getNewsAbstract() {
        return newsAbstract;
    }

    public void setNewsAbstract(String newsAbstract) {
        this.newsAbstract = newsAbstract;
    }
}
