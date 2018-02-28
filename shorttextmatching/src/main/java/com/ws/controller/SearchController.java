package com.ws.controller;

import com.ws.bean.News;
import com.ws.lucene.myquery.MyQuery;
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

import java.util.List;

@Controller
public class SearchController {
    private static final Logger log= LoggerFactory.getLogger(SearchController.class);
    private static final String indexDir="E:\\mycrawler\\index";
    private List<News> newsList=null;

    @Autowired
    private MyQuery query;

    @RequestMapping(value="search",method= RequestMethod.POST)
    public String search(@RequestParam(name="keyword") String keyword,Model model){
        try {
            newsList=query.search(indexDir, keyword, 20);
        }catch (Exception e){
            e.printStackTrace();
        }
        model.addAttribute("newsList",newsList);
        return "ShowInf";
    }

    @RequestMapping(value="showDetail")
    public String showDeatil(@RequestParam(name="newsId") int newsId,Model model){
        News news=newsList.get(newsId);
        model.addAttribute("news",news);
        return "showNewsDetail";
    }
}
