<%@page import="com.neuedu.shop.entity.Admin"%>
<%@page contentType="text/html; charset=gbk" pageEncoding="gbk" %>
<%
	//从session对象中获取登陆后的admin对象
	//如果返回值为null,重新定向到登录界面继续登陆
//	Admin admin = (Admin) session.getAttribute("admin");
//	if (admin == null) {
//		response.sendRedirect("login.jsp");
//		return;
//	}
	String ctxPath = request.getContextPath();
%>
<div id="in-nav">
	<div class="container">
		<div class="row">
			<div class="span12">
				<ul class="pull-right">
					<li><a href="<%=ctxPath %>/pre/index.pre">前台首页</a></li>

					<li><a href="login.jsp">登录</a></li>
					<li><a href="javascript:;"  id="logout">注销</a></li>
					<%--<li><a href="add.admin">管理员注册</a></li>--%>
				</ul>
				<h4>
					<a id="logo" href="index.jsp"> 电商平台后台<strong>管理</strong>
					</a>
				</h4>
				<script>
					(function() {
						$("#logout").click(function(){
							if(confirm("您确定退出系统吗")){
								$(this).attr("href","logout.admin");
							}
						});
					}());
				</script>
			</div>
		</div>
	</div>

</div>
<div id="in-sub-nav">
	<div class="container">
		<div class="row">
			<div class="span12">
				<ul>
					<li><a href="index.jsp" class="active"><i
							class="batch home"></i><br />主页</a></li>
					<li><span class="label label-important pull-right">08</span><a
						href="adminlist.admin"><i class="batch stream"></i><br />管理员列表</a></li>
					<li><a href="userlist.user"><i class="batch users"></i><br />用户列表</a></li>
					<li><a href="list.category"><i class="batch forms"></i><br />类别列表</a></li>
					<li><a href="list.product"><i class="batch quill"></i><br />商品列表</a></li>
					<li><a href="list.order"><i class="batch plane"></i><br />订单列表</a></li>
					<li><a href="#"><i class="batch calendar"></i><br />其它扩展功能</a></li>
					<li><a href="#"><i class="batch settings"></i><br />系统设置</a></li>
				</ul>
			</div>
		</div>
	</div>
</div>