<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.ws.bean.News" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 像这些文档类型声明文件和数据定义文件不能缺少，否则jstl表达式等就不能使用了 -->
<%@ include file="/WEB-INF/include/taglib.jsp" %>
<html>
<head>
    <title>检索</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <script type="text/javascript" src="comonjs/jquery-1.11.2.min.js"></script>
    <style type="text/css">

        #head{
            width:100%;

        }
        #content{
        }

        #head #left_box{
            display: inline-block;
        }

        #head #right_box{
            float:right;
            font-size:14px;
            margin-top: 20px;
        }
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

        #pageList{
            margin-left:108px;
            margin-top: 20px;
        }
        .page-box{
            width:22px;
            height:22px;
            font-size:12px;
            border:1px solid;
            margin:2px;
            line-height: 22px;
            text-align: center;
            display: inline-block;
            color:dodgerblue;
        }

        .page-box:hover{
            color:red;
        }

        .curret-page{
            color:green;
        }
    </style>
</head>
<body>
<div id="head">
    <div id="left_box">
        <div id="head_img"><img src="images/baidu_logo.gif" /></div>
        <div style="display: inline-block;">
            <form id="searchForm" action="#" method="post" >
                <input type="text" name="keyword" maxlength="255" id="keyword"/>
                <input type="button" value="检索" class="submit" onclick="mysubmit()" id="su"/>
            </form>
        </div>
    </div>
    <div id="right_box">
        <a href="${basePath}">主页</a>
        <a href="${basePath}/user/showuser.action">
            <c:choose>
                <c:when test="${sessionScope.user==null}">
                    登录
                </c:when>
                <c:otherwise>
                    ${sessionScope.user.userName}
                </c:otherwise>
            </c:choose>
         </a>
    </div>
    <hr/>
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

    <%
        List<News> newsList=(List<News>) session.getAttribute("newsList");
        int currentPage=(int)session.getAttribute("currentPage");
        for(int i=currentPage;i<currentPage+10;i++){
    %>
    <div class="text">
        <div class="text_title">
            <a href="showDetail.action?newsId=<%= newsList.get(i).getNewsId()%>"><%=newsList.get(i).getNewsTitle()%></a>
        </div>
        <div class="text_content">
            <%=newsList.get(i).getNewsAbstract()%>...
        </div>
        <div class="text_href">
            <a href="<%=newsList.get(i).getNewsHref()%>"><%=newsList.get(i).getNewsHref()%></a>
        </div>
    </div>
    <%
        }
    %>
    <div id="pageList">
        <%
            int pageCount=newsList.size()/10;
            for(int i=0;i<pageCount;i++){
                if(i==(currentPage-1)){
        %>
            <div class="page-box curret-page">
                <%=i+1%>
            </div>
        <%
            }
            else{
        %>
        <div class="page-box">
            <%=i+1%>
        </div>
        <%
        }
        %>
        <%
        }
        %>
    </div>
    <%--<c:forEach var="news" items="${newsList}">
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
    </c:forEach>--%>


</div>
<script>
    $(".page-box").each(function(){
        $(this).bind("click",function(){
            console.log($(this).text());
            window.location.href="${basePath}/getPageContent.action?index="+$(this).text().trim();
        })
    })

    function mysubmit(){
        if($("#keyword").val().trim().length==0) {
            alert("请输入短文本信息");
        }
        else{
            var formId=document.getElementById("searchForm");
            formId.action="search.action";
            formId.submit();
        }
    }
</script>
</body>
</html>
