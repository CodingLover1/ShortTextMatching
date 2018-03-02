<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="WEB-INF/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head lang="en">
<meta charset="UTF-8">
<title>短文本匹配系统</title>
<script type="text/javascript" src="comonjs/jquery-1.11.2.min.js"></script>
<style>

    body {
        width:100%;
        height:100%;
        background-image:url("${basePath}/images/wrapper.jpg");
        background-repeat:no-repeat;
        background-size:100%;
    }

    #head-wrap{
        margin:0px;
        padding:0px;
        width:100%;
        height:32px;
        background-color:rgba(0,0,0,.4);

        position: fixed;
        left:0px;
        top:0px;
    }

    #head-right{
        float:right;
        color:red;
        margin-right:20px;
        line-height: 32px;
    }

    #head-right a{
        color:#fff;
        font-size:13px;

    }

    #content{

    }

    #logo{
        width:200px;
        height:100px;
        margin:50px auto 30px;
    }

    #search{
        margin:0px auto;
        text-align:center;
    }

    #keyword{
        width:537px;
        height:34px;
        padding:0px;
        border-width:0px;
    }

    .jiansuo{
        width:104px;
        height:32px;
        margin:0px;
        border:0px;
        padding:0px;
    }

</style>

</head>
<body>
<div id="wrapper">
    <div id="head-wrap">
        <div id="head-right">
            <a id="alogin" href="javascript:void(0)">登录</a>
        </div>
    </div>
    <div id="content">
        <div id="logo">
            <img src="${basePath}/images/baidulogo.png" width="100%" height="100%"/>
        </div>
        <div id="search">
            <form id="searchForm" action="#" method="post" style="display:inline-block;">
                <input id="keyword" name="keyword" />
                <input type="button" class="jiansuo" value="检索" onclick="mysubmit()"></input>
            </form>
        </div>
    </div>

    <div style="color:#fff;width:100% ;height:23px;line-height: 23px; text-align: center;font-size:14px;margin:300px auto 30px;">
        版权所有&copy;962199594@qq.com
    </div>
</div>
<script>
    $("#keyword").keydown(function(){
        if(event.keyCode==13){
            if($(this).val().trim().length==0) {
                alert("请输入短文本信息");
            }
            else{
                var formId=document.getElementById("searchForm");
                formId.action="search.action";
                formId.submit();
            }
        }
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