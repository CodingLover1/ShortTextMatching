package com.ws.lucene.myquery;

import com.ws.bean.News;
import com.ws.util.TimeUtil;
import com.ws.util.WordCount;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
@Repository
public class MyQuery {
    public MyQuery(){

    }

    public static List<News> search(String indexDir, String q, int size)throws Exception{

        ArrayList<News> newsList=new ArrayList<>();
        //得到读取索引文件的路径
        Directory dir= FSDirectory.open(Paths.get(indexDir));

        //通过dir得到的路径下的所有的文件
        IndexReader reader= DirectoryReader.open(dir);

        //建立索引查询器
        IndexSearcher is=new IndexSearcher(reader);

        //实例化分析器
        Analyzer analyzer = new SmartChineseAnalyzer();

        //建立查询解析器
        /**
         * 第一个参数是要查询的字段；
         * 第二个参数是分析器Analyzer
         * */
        QueryParser parser=new QueryParser("newsContent", analyzer);

        //根据传进来的p查找
        Query query=parser.parse(q);

        //计算索引开始时间
        long start=System.currentTimeMillis();

        //开始查询
        /**
         * 第一个参数是通过传过来的参数来查找得到的query；
         * 第二个参数是要出查询的行数
         * */
        TopDocs hits=is.search(query, size);

        //计算索引结束时间
        long end=System.currentTimeMillis();

        System.out.println("匹配 "+q+" ，总共花费"+(end-start)+"毫秒"+"查询到"+hits.totalHits+"个记录");

        //遍历hits.scoreDocs，得到scoreDoc
        /**
         * ScoreDoc:得分文档,即得到文档
         * scoreDocs:代表的是topDocs这个文档数组
         * @throws Exception
         * */

        SimpleHTMLFormatter htmlFormatter = new SimpleHTMLFormatter( "<span style=\"color:red\">", "</span>");
        Highlighter highlighter = new Highlighter(htmlFormatter, new QueryScorer(query));
        /*for (int i = 0; i < 10; i++) {
            int id = hits.scoreDocs[i].doc;
            Document doc = is.doc(id);
            String text = doc.get("newsContent");
            TokenStream tokenStream = TokenSources.getAnyTokenStream(is.getIndexReader(), id, "newsContent", analyzer);
            TextFragment[] frag = highlighter.getBestTextFragments(tokenStream, text, false, 10);//highlighter.getBestFragments(tokenStream, text, 3, "...");
            for (int j = 0; j < frag.length; j++) {
                if ((frag[j] != null) && (frag[j].getScore() > 0)) {
                    sb.append(frag[j]);
                }
            }
        }*/

        long newsId=0;

        for(ScoreDoc scoreDoc:hits.scoreDocs){
            StringBuilder sb=new StringBuilder();
            int docId=scoreDoc.doc;
            Document doc=is.doc(scoreDoc.doc);
            String newsContent=doc.get("newsContent");
            TokenStream tokenStream = TokenSources.getAnyTokenStream(is.getIndexReader(), docId, "newsContent", analyzer);
            TextFragment[] frag = highlighter.getBestTextFragments(tokenStream, newsContent, false, 10);//highlighter.getBestFragments(tokenStream, text, 3, "...");
            for (int j = 0; j < frag.length; j++) {
                if ((frag[j] != null) && (frag[j].getScore() > 0)) {
                    sb.append(frag[j]);
                }
            }
            java.sql.Date date= TimeUtil.toSqlDate(doc.get("newsDate"));
            News news=new News(newsId++,doc.get("newsTitle"),doc.get("newsHref"),newsContent,date,doc.get("className"));
            String newsAbstract=sb.toString();
            String[] subStr=newsAbstract.split("</span>");
            StringBuilder n=new StringBuilder();
            for(int i=0;i<subStr.length;i++){
                if(n.length()>300){
                    break;
                }
                n.append(subStr[i]);
                n.append("</span>");
            }
            news.setNewsAbstract(n.toString());
          //  System.out.println(sb.toString());
            newsList.add(news);
        }

        //关闭reader
        reader.close();
        return newsList;
    }
}
