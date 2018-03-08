<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
    <title>注册</title>
    <meta charse="utf-8" />
    <script type="text/javascript" src="${basePath}/comonjs/jquery-1.11.2.min.js"></script>
    <link  href="${basePath}/css/regist.css" type="text/css" rel="stylesheet"/>
</head>
<body>
<div id="wrapper">
    <div class="title">
        <h4 style="display: inline-block;">个人信息</h4>
        <a href="${basePath}/" style="float:right; margin-top:20px">返回主页</a>
        <hr />
    </div>
    <div class="content">
        <form id="userform" action="#" method="post">
            <table >
                <tr><td class="right">用户名：</td><td><input type="text" id="userName" name="userName"  class="inputText" /></td></tr>
                <tr><td class="right">密码：</td><td><input type="text" id="userPassword" name="userPassword"  class="inputText" /></td></tr>
                <tr><td class="right">性别：</td><td><input type="radio"  name="userSex" value="男" checked="checked"/>男<input type="radio" name="userSex" value="女" />女</td></tr>
                <tr><td class="right">手机号：</td><td><input type="text" id="userPhone" name="userPhone" class="inputText" /></td></tr>
            </table>
        </form>
        <button class="save-btn" onclick="save()">提交</button>
    </div>
</div>
<script>
    Date.prototype.toLocaleString = function() {
        return this.getFullYear() + "/" + (this.getMonth() + 1) + "/" + this.getDate() + "/ " + this.getHours() + ":" + this.getMinutes() + ":" + this.getSeconds();
    };

    function save(){
        if($("#userName").val().trim()==""||
            $("#userPassword").val().trim()==""||
            $("#userPhone").val().trim()==""){
            alert("请填写完整信息");
            return ;
        }
        $.ajax({
            url:"${basePath}/user/adduser.action",
            type:"POST",
            data:{"userName":$("#userName").val(),"userPassword":$("#userPassword").val(),"userSex":$('input:radio[name="userSex"]:checked').val(),"userPhone":$("#userPhone").val()},
            dataType:"json",
            scriptCharset:"utf-8",
            success:function(data){
                console.log(data);
                if(data.msg=="success")
                    alert("注册成功");
                else{
                    alert("注册失败")
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
