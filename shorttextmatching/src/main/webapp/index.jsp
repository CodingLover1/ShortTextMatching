<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="WEB-INF/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head lang="zh">
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
        z-index: 1002;

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
    /******************登录弹框****************************/
   .box_lg{
       display: none;
       position:absolute;
       top:20%;
       left:35%;
       width:350px;
       height:300px;
       background-color:white;
       z-index: 1005;
   }
    
    .box_bg{
    position:absolute;
    display:none;
    top:0%;
    left:0%;
    width:100%;
    height:100%;
    background-color:rgba(0,0,0,.7);
        z-index: 1001;

    }

   .box_cont{
        margin-top:20px;
       text-align: center;
   }

    .inputText{
        width:280px;
        height:27px;
        margin-top:20px;
    }

    .btn-login{
        width:284px;
        height:31px;
        margin-top:20px;
        background-color:deepskyblue;
        border:0px;
    }

   .box_ft{
       margin-top:20px;
       margin-left:30px;
       font-size:14px;
   }

</style>

</head>
<body>
<div id="wrapper">
    <div id="head-wrap">
        <div id="head-right">
            <a id="alogin" href="javascript:void(0)" onclick="showLogin()">
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
            <a href="#" style="margin-left:110px;">立即注册</a>
        </div>
    </div>
    <div class="box_bg"></div>
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

    function showLogin(){
        <c:choose>
        <c:when test="${sessionScope.loginflag==null}">
        var loginflag=false;
        </c:when>
        <c:otherwise>
        var loginflag=true;
        </c:otherwise>
        </c:choose>
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
         }
         $.ajax({
             url:"${basePath}/user/userlogin.action",
             type:"POST",
             data:{"userName":$("#userName").val().trim(),"userPassword":$("#userPassword").val().trim()},
             dataType:"json",
             scriptCharset:"utf-8",
             success:function(data){
                 document.getElementById("alogin").innerText=data.userName;
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