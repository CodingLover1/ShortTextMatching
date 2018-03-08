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
    <link href="${basePath}/css/showinf.css" rel="stylesheet" type="text/css" />
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
        <a href="javascript:void(0)" onclick="showLogin()" id="#alogin">
            <c:choose>
                <c:when test="${sessionScope.user==null}">
                    登录
                </c:when>
                <c:otherwise>
                    ${sessionScope.user.userName}
                </c:otherwise>
            </c:choose>
         </a>
        <a id="alogout" href="${basePath}/user/userlogout.action"  >退出</a>
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

    <div class="box_lg">
        <div class="box_tit">
            <a class="close" style="float:right " href="javascript:void(0)" onclick="hiddenDialog()">X</a>
            <span style="background-color:deepskyblue;color:white;display: block;">登录账号</span>
        </div>

        <div class="box_cont">
            <form class="box_frm" action="#">
                <input type="text" placeholder="用户名" name="userName" id="userName" class="inputText" /><br />
                <input type="password" placeholder="密码" name="userPassword" id="userPassword"class="inputText" /><br/>
                <input type="button"  value="登录" class="btn-login" onclick="login()"/>
            </form>
        </div>
        <div class="box_ft">
            <input type="checkbox" style="margin-right: 8px"><span >下次自动登录</span>
            <a href="${basePath}/user/userregist.action" style="margin-left:110px;">立即注册</a>
        </div>
    </div>
    <div class="box_bg"></div>
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
    //判断用户是否登录
    <c:choose>
    <c:when test="${sessionScope.loginflag==null}">
    var loginflag=false;
    </c:when>
    <c:otherwise>
    var loginflag=true;
    </c:otherwise>
    </c:choose>

    $(function(){
        if(loginflag){
            $("#alogout").css("display","inline-block");
        }
    });

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

    function showLogin(){
        if(!loginflag) {
            $(".box_bg").css("display", "block");
            $(".box_lg").css("display", "block");
        }
        else{
            window.open("${basePath}/user/showuser.action");
        }
    }

    function hiddenDialog(){
        $(".box_bg").css("display","none");
        $(".box_lg").css("display","none");
    }

    function login(){
        if($("#userName").val().trim().length==0||$("#userPassword").val().trim().length==0){
            alert("用户名或密码不能为空");
            return ;
        }
        $.ajax({
            url:"${basePath}/user/userlogin.action",
            type:"POST",
            data:{"userName":$("#userName").val().trim(),"userPassword":$("#userPassword").val().trim()},
            dataType:"json",
            scriptCharset:"utf-8",
            success:function(data){
                console.log(data);
                hiddenDialog();
                location.reload();
            },
            error:function(){
                alert("你输入的用户名或密码不对");
            }
        })
    }
</script>
</body>
</html>
