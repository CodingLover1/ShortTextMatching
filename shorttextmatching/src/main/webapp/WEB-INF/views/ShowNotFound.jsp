<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/include/taglib.jsp" %>
<html>
<head>
    <title>未检测到匹配关键字</title>
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
            height:28px;
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
