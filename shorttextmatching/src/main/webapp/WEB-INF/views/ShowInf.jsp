<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 像这些文档类型声明文件和数据定义文件不能缺少，否则jstl表达式等就不能使用了 -->
<%@ include file="/WEB-INF/include/taglib.jsp" %>
<html>
<head>
    <title>检索</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <style type="text/css">

        #keyword {
            width:500px;
            height:22px;
        }

        .submit{
            height:28px;
            width:100px;
            color: #fff;
            letter-spacing: 1px;
            background: #3385ff;
            border-bottom: 1px solid #2d78f4;
            outline: medium;
            -webkit-appearance: none;
            -webkit-border-radius: 0;
            font-size: 14px;
            padding: 0;
            border: 0;
            cursor: pointer;
        }

        #head_img{
            display:inline-block;
            width:105px;
            height:33px;
            margin:0px 0px 0px 0px;
        }

        #head_img img{
            width:100%;
            height:100%;
        }

        .text{
            width:610px;
            margin:10px 0px 0px 110px;
            font-size:14px;
        }

        .text_href{
            font-size:12px;
        }

        .text_href a{
            color:green;
        }
    </style>
</head>
<body>
<div id="head">
    <div id="left_box">
        <div id="head_img"><img src="images/baidu_logo.gif" /></div>
        <div style="display: inline-block;">
            <form action="" method="post" >
                <input type="text" name="keyword" maxlength="255" id="keyword"/>
                <input type="submit" value="检索" class="submit" id="su"/>
            </form>
        </div>
    </div>
    <div id="right_box">
    </div>
</div>

<div id="content">
    <div class="text">
        <div class="text_title">
            <a href="www.baidu.com">这是一条测试消息</a>
        </div>
        <div class="text_content">
                这是一条测试消息这是一条测试消息这是一条测试消息这是一条测试消息这是一条测试消息这是一条测试消息这是
                一条测试消息这是一条测试消息这是一条测试消息这是一条测试消息这是一条测试
                消息这是一条测试消息这是一条测试消息这是一条测试消息这是一条测试消息这是一条测试消息这是一条测试消息
                这是一条测试消息这是一条测试消息这是一条测试消息这是一条测试消息这是一条测试消息这是一条测试消息
        </div>
        <div class="text_href">
            <a href="http://www.baidu.com/">http://www.baidu.com/</a>
        </div>
    </div>

    <c:forEach var="news" items="${newsList}">
        <div class="text">
            <div class="text_title">
                <a href="showDetail.action?newsId=${news.newsId}">${news.newsTitle}</a>
            </div>
            <div class="text_content">
               ${news.newsAbstract}...
            </div>
            <div class="text_href">
                <a href="${news.newsHref}">${news.newsHref}</a>
            </div>
        </div>
    </c:forEach>
</div>

</body>
</html>
