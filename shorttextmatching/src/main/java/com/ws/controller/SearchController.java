package com.ws.controller;

import com.ws.bean.News;
import com.ws.bean.User;
import com.ws.lucene.myquery.MyQuery;
import com.ws.mapper.UserMapper;
import com.ws.util.WordCount;
import org.apache.lucene.document.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.jnlp.UnavailableServiceException;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class SearchController {
    private static final Logger log= LoggerFactory.getLogger(SearchController.class);
    private static final String indexDir="E:\\mycrawler\\index";
    private List<News> newsList=null;

    @Autowired
    private MyQuery query;

    @Autowired
    UserMapper userMapper;

    @RequestMapping(value="search",method= RequestMethod.POST)
    public String search(@RequestParam(name="keyword") String keyword, Model model, HttpSession httpSession){
        try {
            newsList=query.search(indexDir, keyword, 100);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(newsList==null||newsList.size()==0)
            return "ShowNotFound";
//        model.addAttribute("newsList",newsList);
//        model.addAttribute("pageCount",newsList.size()/20);
        httpSession.setAttribute("newsList",newsList);
        httpSession.setAttribute("currentPage",1);

        return "ShowInf";
    }

    @RequestMapping(value="showDetail")
    public String showDeatil(@RequestParam(name="newsId") int newsId,Model model){
        News news=newsList.get(newsId);
        model.addAttribute("news",news);
        return "showNewsDetail";
    }

    @RequestMapping(value="getPageContent",method=RequestMethod.GET)
    public String getPageContent(@RequestParam("index") int index,HttpSession httpSession){
        System.out.println(index);
        httpSession.setAttribute("currentPage",index);
        return "ShowInf";
    }
}
