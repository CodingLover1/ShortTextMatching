package com.ws.test;

import com.ws.mybatis.bean.News;
import com.ws.mybatis.mapper.NewsMapper;
import com.ws.util.NewsServer;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.jdbc.Sql;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SpringMybatis {

    @Test
    public void springMybatis()throws Exception{
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date today=simpleDateFormat.parse("2018-01-01");
        java.sql.Date stoday=new java.sql.Date(today.getTime());
       /*ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring-mybatis.xml");
       SqlSessionFactory sqlSessionFactory=(SqlSessionFactory)applicationContext.getBean("sqlSessionFactory");
       SqlSession  sqlSession=sqlSessionFactory.openSession();
       NewsMapper newsMapper=sqlSession.getMapper(NewsMapper.class);*/
       News news=new News();
       news.setNewsTitle("测试");
       news.setNewsHref("http://www.ws.com/");
       news.setNewsContent("这是一条测试新闻");
       news.setNewsDate(stoday);
       news.setClassName("其他");
       System.out.print(NewsServer.insert(news));
    }
}
