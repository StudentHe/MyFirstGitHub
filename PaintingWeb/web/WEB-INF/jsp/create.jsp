<%@page pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<!-- 新增油画页面 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>新增油画</title>
<link rel="stylesheet" type="text/css" href="css\create.css">
<script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
	<script src="js/validation.js" type="text/javascript"></script>
	<script type="text/javascript">
		function checkSubmit()
		{//js方式进行提交校验
        var r1=checkEmpty("#pname","#errPname");
        var r2=checkCategory("#category","#errCategory");
        var r3=checkPrice("#price","#errPrice");
        var r4=checkFile("#painting","#errPainting");
        var r5=checkEmpty("#description","#errDescription");
        if(r1&&r2&&r3&&r4&&r5)//只有当所有扁担数据都校验成功才能提交，否则就会提交失败
		{
			return true;
		}
        return false;
		}
		// $("#submit").submit(function()
		// {//也可以jQuery方式进行提交校验
		// 	var r1=checkEmpty("#pname","#errPname");
		// 	var r2=checkCategory("#category","#errCategory");
		// 	var r3=checkPrice("#price","#errPrice");
		// 	var r4=checkFile("#painting","#errPainting");
		// 	var r5=checkEmpty("#description","#errDescription");
		// 	if(r1&&r2&&r3&&r4&&r5)//只有当所有扁担数据都校验成功才能提交，否则就会提交失败
		// 	{
		// 		return true;//返回true会提交
		// 	}
		// 	return false;//返回false不会提交
		// }
		// );
	</script>
</head>
<body>
	<div class="container">
		<fieldset>
			<legend>新增油画</legend>
			<%--当提交的表单中有文件内容时得设置enctype="multipart/form-data"，并且method必须是post--%>
			<%--form标签可以设置onsubmit事件，提交表单前会触发，当返回true才能提交，返回false时不会提交--%>
			<form action="/management?method=create" onsubmit="return checkSubmit()" id="submit" method="post" enctype="multipart/form-data" autocomplete="off">
				<ul class="ulform">
					<li>
						<span>油画名称</span>
						<span id="errPname"></span>
						<input id="pname" name="pname" onblur="checkEmpty('#pname','#errPname')"/>
					</li>
					<li>
						<span>油画类型</span>
						<span id="errCategory"></span>
						<select id="category" name="category" onchange="checkCategory('#category','#errCategory')">
							<option value="-1">请选择油画类型</option>
							<option value="1">现实主义</option>
							<option value="2">抽象主义</option>
						</select>
					</li>
					<li>
						<span>油画价格</span>
						<span id="errPrice"></span>
						<input id="price" name="price" onblur="checkPrice('#price','#errPrice')"/>
					</li>
					<li>
						<span>作品预览</span>
						<span id="errPainting"></span>
						<input id="painting" name="painting" type="file" 
							style="padding-left: 0px;" accept="image/*" onchange="checkFile('#painting','#errPainting')"/>
					</li>

					<li>
						<span>详细描述</span>
						<span id="errDescription"></span>
						<textarea
							id="description" name="description"  onblur="checkEmpty('#description','#errDescription')"></textarea>
					</li>
					<li style="text-align: center;">
						<button type="submit" class="btn-button">提交表单</button>
					</li>
				</ul>
			</form>
		</fieldset>
	</div>

</body>
</html>
