<%@page pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Document</title>
	<link rel="stylesheet" type="text/css" href="css\common.css">
	<script type="text/javascript" src="js\js1.js"></script>
</head>
<body>
<!--param是获得请求中的所有参数(param.c是活的参数名为c的参数的值),当请求转发到此jsp文件时，如果url中的参数c不是空的，会执行if标签里的内容-->
<c:if test="${param.c!=null}">
<c:set var="categoryParam" value="&c=${param.c}"></c:set><!--在当前页面创建一个变量保存一个数据,var是变量名，value是变量值-->
</c:if>
<c:if test="${param.c==null}"><!--如果参数c为空返回一个空的变量-->
	<c:set var="categoryParam" value=""></c:set>
</c:if>
	<div class="header">
		<div class="logo">
			<img src="image\logo.png">
		</div>
		<div class="menu"   onclick="show_menu()" onmouseleave="show_menu1()">
			 <div class="menu_title" ><a href="###">内容分类</a></div>
			 <ul id="title">
				<li><a href="/servlet?c=1">现实主义</a></li>
				<li><a href="/servlet?c=2">抽象主义</a></li>
			 </ul>
		</div>
		<div class="auth">
			<ul>
				<li><a href="#">登录</a></li>
				<li><a href="#">注册</a></li>
			</ul>
		</div>
	</div>
	<div class="content">
	  <div class="banner">
  		<img src="../../image/welcome.png" class="banner-img">
	  </div>
	  <div class="img-content">
		<ul>
			<c:forEach items="${requestScope.PageModel.pageData}" var="painting" varStatus="idx">
			<li>
				<img src="${painting.preview}" class="img-li">
				<div class="info">
					<h3>${painting.pname}</h3>
					<p>
					${painting.description}
					</p>
					<div class="img-btn">
						<div class="price"><fmt:formatNumber value="${painting.price}" pattern="￥0.00"></fmt:formatNumber></div>
							<a href="#" class="cart">
						       <div class="btn">
							      <img src="../../image/cart.svg">
						       </div>
						    </a>
					</div>
				</div>
			</li>
			</c:forEach>
		</ul>
	  </div>
	  <div class="page-nav">
		<ul>
			<li><a href="/servlet?p=1${categoryParam}">首页</a></li>
			<!--通过el表达式三目运算符还有PageModel的isHasPreviousPage方法判断是否有上一页，然后对href进行设置-->
			<li><a href="/servlet?p=${PageModel.isHasPreviousPage()?PageModel.page-1:1}${categoryParam}">上一页</a></li>
			<!--通过core库的forEach循环 以begin，end形式进行循环，使用超链接得到切换页面的效果-->
			<c:forEach begin="1" end="${requestScope.PageModel.totalPages}" var="pno">
				<!--同过el表达式三目运算符进行判断是span标签数字是否是当前页数字pno，如果不是span标签就没有class属性，如果是那么
				span会有class属性 ,在css中对sapan的类选择器"first-page"对应的设置就不会生效 ,在css中对sapan的类选择
				器"first-page"对应的设置就会生效-->
			<li><span ${pno==requestScope.PageModel.page?"class='first-page'":""}><a href="/servlet?p=${pno}${categoryParam}">${pno}</a></span></li>
			</c:forEach>
			<li><a href="/servlet?p=${PageModel.isHasNextPage()?PageModel.page+1:PageModel.totalPages}${categoryParam}">下一页</a></li>
			<li><a href="/servlet?p=${PageModel.totalPages}${categoryParam}">尾页</a></li>
		</ul>
	  </div>
	</div>
	<div class="footer">
		<p><span>M-GALLARY</span>©2020 POWERED BY IMOOC.INC</p>
	</div>
</body>
</html>