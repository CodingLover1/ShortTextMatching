package com.ws.lucene;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;

public class Indexer {

    //写索引的实例到指定目录下
    private IndexWriter writer;

    /**
     * 构造方法：为了实例化IndexWriter
     */
    public Indexer(String indexDir) throws Exception{

        //得到索引所在目录的路径
        Directory dir = FSDirectory.open(Paths.get(indexDir));

        //实例化分析器
        Analyzer analyzer = new SmartChineseAnalyzer();

        //实例化IndexWriterConfig
        IndexWriterConfig con = new IndexWriterConfig(analyzer);

        //实例化IndexWriter
        writer = new IndexWriter(dir, con);

    }

    /**
     * 关闭写索引
     * @throws Exception
     */
    public void close()throws Exception{

        writer.close();
    }


    /**
     * 索引指定目录的所有文件
     * @throws Exception
     */
    public int index(String dataDir) throws Exception{

        //定义文件数组，循环得出要加索引的文件
        File[] file = new File(dataDir).listFiles();

        for (File files : file) {

            //从这开始，对每个文件加索引
            indexFile(files);
        }

        //返回索引了多少个文件，有几个文件返回几个
        return writer.numDocs();

    }

    /**
     * 索引指定文件
     * @throws Exception
     */
    private void indexFile(File files) throws Exception {

        System.out.println("索引文件："+files.getCanonicalPath());

        //索引要一行一行的找，，在数据中为文档，所以要得到所有行，即文档
        Document document = getDocument(files);

        //开始写入,就把文档写进了索引文件里去了；
        writer.addDocument(document);

    }

    /**
     * 获得文档，在文档里在设置三个字段
     *
     * 获得文档，相当于数据库里的一行
     * @throws Exception
     * */
    private Document getDocument(File files) throws Exception {

        //实例化Document
        Document doc = new Document();

        //add():把设置好的索引加到Document里，以便在确定被索引文档。
        doc.add(new TextField("contents",new FileReader(files)));

        //Field.Store.YES：把文件名存索引文件里，为NO就说明不需要加到索引文件里去
        doc.add(new TextField("FileName", files.getName(), Field.Store.YES));

        //把完整路径存在索引文件里
        doc.add(new TextField("fullPath", files.getCanonicalPath(),Field.Store.YES));

        //返回document
        return doc;
    }


    //开始测试写入索引
    public static void main(String[] args){

        //索引指定的文档路径
        String indexDir = "E:\\luceneDemo";

        //被索引数据的路径
        String dataDir = "E:\\mycrawler\\data\\公益";

        //写索引
        Indexer indexer = null;
        int numIndex = 0;

        //索引开始时间
        long start = System.currentTimeMillis();

        try {
            //通过索引指定的路径，得到indexer
            indexer = new  Indexer(indexDir);

            //将要索引的数据路径(int:因为这是要索引的数据，有多少就返回多少数量的索引文件)
            numIndex = indexer.index(dataDir);

        } catch (Exception e) {

            e.printStackTrace();
        }finally{
            try {
                indexer.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //索引结束时间
        long end = System.currentTimeMillis();

        //显示结果
        System.out.println("索引了  "+numIndex+"  个文件，花费了  "+(end-start)+"  毫秒");

    }

}
