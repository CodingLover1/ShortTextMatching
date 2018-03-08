<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
    <title>个人信息</title>
    <meta charse="utf-8" />
    <script type="text/javascript" src="${basePath}/comonjs/jquery-1.11.2.min.js"></script>
    <style>
        .right{
            text-align: right;
        }
        .inputText{
            width:200px;
            height:20px;
        }
        .save-btn{
            width:200px;
            height:20px;
            border:0px;
            margin-left:84px;
        }

        #wrapper{
            width:400px;
            margin:20px auto;
        }
    </style>
</head>
<body>
<div id="wrapper">
    <div class="title">
        <h4 style="display: inline-block;">个人信息</h4>
        <a href="${basePath}/" style="float:right;margin-top:20px">返回主页</a>
        <hr />
    </div>
    <div class="content">
        <form id="userform" action="#" method="post">
        <table >
            <input type="text" name="userId" value="${sessionScope.user.userId}"  style="display: none;"/>
            <tr><td class="right">用户名：</td><td><input type="text" id="userName" name="userName" value="${sessionScope.user.userName}" class="inputText" /></td></tr>
            <tr><td class="right">密码：</td><td><input type="text" id="userPassword" name="userPassword" value="${sessionScope.user.userPassword}" class="inputText" /></td></tr>
            <tr><td class="right">性别：</td><td><input type="radio"  name="userSex" value="男"/>男<input type="radio" name="userSex" value="女" />女</td></tr>
            <tr><td class="right">手机号：</td><td><input type="text" id="userPhone" name="userPhone" value="${sessionScope.user.userPhone}" class="inputText" /></td></tr>
            <tr><td class="right">创建时间：</td><td><input type="text" id="createTime" name="createTime" readonly="readonly" class="inputText" value="<fmt:formatDate value="${sessionScope.user.createTime}" pattern="yyyy-MM-dd : hh:mm:ss"/>"/></td></tr>
        </table>
        </form>
        <button class="save-btn" onclick="save()">保存</button>
    </div>
</div>
<script>
    Date.prototype.toLocaleString = function() {
        return this.getFullYear() + "/" + (this.getMonth() + 1) + "/" + this.getDate() + "/ " + this.getHours() + ":" + this.getMinutes() + ":" + this.getSeconds();
    };
    $(function(){
       if("男"=="${sessionScope.user.userSex}"){
           $(":radio[value='男']").attr("checked","true");
       }
       else{
           $(":radio[value='女']").attr("checked","true");
       }
    });

    function save(){
        console.log("${sessionScope.user.userId}");
        console.log($('input:radio[name="userSex"]:checked').val());
        console.log("${sessionScope.user.createTime}");
        $.ajax({
            url:"${basePath}/user/updateuser.action",
            type:"POST",
            data:{"userId":"${sessionScope.user.userId}","userName":$("#userName").val(),"userPassword":$("#userPassword").val(),"userSex":$('input:radio[name="userSex"]:checked').val(),"userPhone":$("#userPhone").val()},
            dataType:"json",
            scriptCharset:"utf-8",
            success:function(data){
                console.log(data);
                if(data.msg=="success")
                    alert("保存成功");
                else{
                    alert("保存失败")
                }
            },
            error:function(data){
                console.log(data);
                alert("阿偶，出错了，请联系管理员");
            }
        })
    }
</script>
</body>
</html>
