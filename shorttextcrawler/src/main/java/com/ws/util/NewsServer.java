package com.ws.util;

import com.ws.mybatis.bean.News;
import com.ws.mybatis.mapper.NewsMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class NewsServer {

    private static SqlSessionFactory sqlSessionFactory=null;
    private static SqlSession sqlSession=null;
    private static NewsMapper newsMapper=null;

    static{
        ApplicationContext app=new ClassPathXmlApplicationContext("classpath:spring-mybatis.xml");
        sqlSessionFactory=(SqlSessionFactory)app.getBean("sqlSessionFactory");
        sqlSession=sqlSessionFactory.openSession();
        newsMapper=sqlSession.getMapper(NewsMapper.class);
    }

    public static int insert(News news){
        return newsMapper.insertANews(news);
    }

}
