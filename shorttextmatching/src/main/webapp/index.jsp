<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head lang="en">
<meta charset="UTF-8">
<title>短文本匹配系统</title>

<script type="text/javascript" src="comonjs/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="comonjs/modernizr-2.6.2.min.js"></script>
<script type="text/javascript" src="js/starbg.js"></script>

<link type="text/css" rel="stylesheet" href="css/styles.css" />
<style>
	*{ margin:0; padding:0;}
	canvas{ display: block; margin: 0; width:100%; height:100%; position: fixed; left: 0; right: 0; top:0; bottom:0;
		z-index: -1; }
	.content{ width: 1144px; margin: 0 auto; padding-top:200px;}
	.rabitBg{ position:fixed; left:0; top:50%;}
	.content-title{position:fixed;top:180px;left:583px}
	
</style>

</head>
<body>
<canvas id="fullstarbg">你的浏览器不支持canvas标签</canvas>
<div class="content-title">
 <span style="font-size:20px">短文本匹配系统</span>
 </div>
<div class="content">
 <img src="images/rabit.png" class="rabitBg" />
    <form  method="post" id="searchForm">
        <div class="input_wrap_box box_S">
            <div class="input_cover">
                <span class="num">关键字</span>
                <span class="icons">搜索</span>
            </div>
            <input type="text" id="sercHead" class="box_S" name="keyword">
        </div>
    </form>
</div>
<script>
   $(".input_cover").mousedown(function(){
	   $(this).hide();
	   $(this).siblings("input").addClass("cur");
	   $("#sercHead").focus();
	   return false;

   })
   $("#sercHead").blur(function(){
	   $(this).removeClass("cur");
	   if($(this).val()==''){
		   $(this).siblings(".input_cover").show();
	   }
   })
   
   $("#sercHead").keydown(function(){
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
</script>

<div style="text-align:center;margin:50px 0; font:normal 14px/24px 'MicroSoft YaHei';">
</div>
</body>
</html>