<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 像这些文档类型声明文件和数据定义文件不能缺少，否则jstl表达式等就不能使用了 -->
<%@ include file="/WEB-INF/include/taglib.jsp" %>
<html>
<head>
    <title>${news.newsTitle}</title>
    <style type="text/css">
        .text{
            width:610px;
            margin:70px 0px 0px 110px;
            font-size:14px;
            display: inline-block;
        }

        .text_title {
            text-align:center;
        }

        .text_content {
            margin:20px 0px ;
        }

        #wordClound {
            width:400px;
            height:500px;
            /*background-color:blue;*/
            float:right;
            margin:100px 100px 0px 0px;
        }
    </style>
</head>
<body>
<div class="text">
    <div class="text_title">
        <a href="showDetail.action?newsId=${news.newsId}">${news.newsTitle}</a>
    </div>
    <div class="text_content">
        ${news.newsContent}
    </div>
</div>
<div id="wordClound">

</div>
</body>
</html>
