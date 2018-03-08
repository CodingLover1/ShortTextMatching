<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/include/taglib.jsp" %>
<html>
<head>
    <title>未检测到匹配关键字</title>
    <script type="text/javascript" src="comonjs/jquery-1.11.2.min.js"></script>
    <link href="${basePath}/css/shownotfound.css" rel="stylesheet" type="text/css" />
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
        <a href="">个人信息</a>
    </div>
    <hr/>
</div>
<div id="content">
    <div class="text">
        抱歉，你要找的关键词未检索到！
    </div>

</div>
<script>
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
