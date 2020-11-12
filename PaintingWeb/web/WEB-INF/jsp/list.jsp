<%@page pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>油画列表</title>
    <script src="js\jquery-3.4.1.min.js"  type="text/javascript"></script	>
    <!--引入SweetAlert-->
    <script src="js\sweetalert2.js"  type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="css\list.css">


    <script type="text/javascript">
        function showPreview(previewObj)
        {
            var preview=$(previewObj).attr("data-preview");
            var pname=$(previewObj).attr("data-pname");
            Swal.fire(//使用了SweetAlert实现预览弹出框
                {
                    title:pname,//弹出框的标题名
                    //设置弹出框的html内容，实现预览的功能
                    html:"<img src='"+preview+"'style='width:361px;height=240px'>",
                    showCloseButton: true,//是否显示关闭按钮
                    showConfirmButton: false//是否显示确认按钮
                }
            )

        }
    </script>
</head>
<body>
	<div class="container">
		<fieldset>
			<legend>油画列表</legend>
			<div style="height: 40px">
				<a href="/management?method=showCreate" class="btn-button">新增</a>
			</div>
			<!-- 油画列表 -->
			<table cellspacing="0px">
				<thead>
					<tr style="width: 150px;">
						<th style="width: 100px">分类</th>
						<th style="width: 150px;">名称</th>
						<th style="width: 100px;">价格</th>
						<th style="width: 400px">描述</th>
						<th style="width: 100px">操作</th>
					</tr>
				</thead>
				<c:forEach items="${requestScope.PageModel.pageData}" var="painting">
				<tr>
<!--在JSTL中使用html注释标签会不识别，得使用Jsp专属注释-->
             <c:choose> <%--通过核心库choos标签实现风格判断--%>
						<c:when test="${painting.category==1}">
							<td>现实主义</td>
						</c:when>
    <c:when test="${painting.category==2}"><td>抽象主义</td></c:when>
						<c:otherwise>
							<td>未知类型</td>
						</c:otherwise>
					</c:choose>
<%--					<td>${painting.category==1?"现实主义":"抽象主义"}</td><!--el表达式三目运算符实现风格判断-->--%>
<%--		核心库if标签实现风格判断<c:if test="${painting.category==1}"><td>现实主义</td></c:if>--%>
<%--					<c:if test="${painting.category==2}"><td>抽象主义</td></c:if>--%>
						<td>${painting.pname}</td>
						<td><fmt:formatNumber pattern="￥0.00" value="${painting.price}"></fmt:formatNumber></td>
						<td>${painting.description}</td>
						<td>
                            <!-- data-pname和data-preview是自定义属性 -->
                            <!--href="javascript:void(0)"代表超链接不做任何反应-->
							<a class="oplink" data-pname="${painting.pname}" data-preview="${painting.preview}" onclick="showPreview(this)" href="javascript:void(0)">预览</a>
							<a class="oplink" data-pname="" data-preview=""  href="#">修改</a>
							<a class="oplink" data-pname="" data-preview=""  href="#">删除</a>
						</td>
					</tr>
				</c:forEach>
			</table>
			<!-- 分页组件 -->
			<ul class="page">
				<li><a href="/management?method=list&p=1">首页</a></li>
				<li><a href="/management?method=list&p=${requestScope.PageModel.isHasPreviousPage()?PageModel.page-1:PageModel.page}">上页</a></li>
				<c:forEach begin="1" end="${requestScope.PageModel.totalPages}" var="page">
				<li class="${PageModel.page==page?'active':''}"><a href="/management?method=list&p=${page}">${page}</a></li>
				</c:forEach>
				<li><a href="/management?method=list&p=${requestScope.PageModel.isHasNextPage()?PageModel.page+1:PageModel.page}">下页</a></li>
				<li><a href="/management?method=list&p=${PageModel.totalPages}">尾页</a></li>
			</ul>
		</fieldset>
	</div>

</body>
</html>
