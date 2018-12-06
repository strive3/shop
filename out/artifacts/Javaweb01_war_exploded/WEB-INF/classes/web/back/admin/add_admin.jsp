<%@page contentType="text/html; charset=gbk" pageEncoding="gbk" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="gbk" />

<title>管理员注册</title>
<link rel="stylesheet" href="css/bootstrap.css" />
<link rel="stylesheet" href="css/bootstrap-responsive.css" />
<link rel="stylesheet" href="css/styles.css" />
<link rel="stylesheet" href="css/toastr.css" />
<link rel="stylesheet" href="css/fullcalendar.css" />
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.js"></script>
<script src="js/jquery.knob.js"></script>
<script src="js/jquery.sparkline.min.js"></script>
<script src="js/toastr.js"></script>
<script src="js/jquery.tablesorter.min.js"></script>
<script src="js/jquery.peity.min.js"></script>
<script src="js/fullcalendar.min.js"></script>
<script src="js/gcal.js"></script>
<script src="js/setup.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />

</head>
<body>
	<!-- header部分 -->
	<div id="in-nav">
		<div class="container">
			<div class="row">
				<div class="span12">
					<ul class="pull-right">
						<li><a href="#">链接1</a></li>
						<li><a href="#">链接2</a></li>
						<li><a href="#">链接3</a></li>
						<li><a href="login.html">登录</a></li>
					</ul>
					<a id="logo" href="index.html">
						<h4>
							电商平台后台<strong>管理</strong>
						</h4>
					</a>
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
						<li><a href="users.html"><i class="batch users"></i><br />用户列表</a></li>
						<li><a href="categories.html"><i class="batch forms"></i><br />类别列表</a></li>
						<li><a href="products.html"><i class="batch quill"></i><br />商品列表</a></li>
						<li><span class="label label-important pull-right">04</span><a
							href="orders.html"><i class="batch plane"></i><br />订单列表</a></li>
						<li><a href="anothers.html"><i class="batch calendar"></i><br />其它扩展功能</a></li>
						<li><a href="settings.html"><i class="batch settings"></i><br />系统设置</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="page">
		<div class="page-container">
			<div class="container">
				<div class="row">
					<div class="span12">
						<h4 class="header">管理员注册</h4>
						<form action="registAdmin.do" method="post">
							<table class="table table-striped sortable">
								<thead>
								</thead>
								<tbody>
									<tr>
										<th>用户名</th>

										<td><input type="text" name="aname" /><span class="error"></td>
									</tr>
									<tr>
										<th>密码</th>
										<td><input type="password" name="apwd" /></td>
									</tr>
									<tr>
										<th>确认密码</th>
										<td><input type="password" name="confirmpwd" /></td>
									</tr>
									<tr>
										<td></td>
										<td><input class="btn btn-success" type="submit"
											value="注册" />&nbsp;&nbsp;&nbsp;<input class="btn btn-danger"
											type="reset" value="重置" /></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
								</tbody>
							</table>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<footer>
		<div class="container">
			<div class="row">
				<div class="span12">
					<p class="pull-right">版权所有&nbsp;&nbsp;&nbsp;可以翻版</p>
					<p>&copy; Copyright 2018 Somnus</p>
				</div>
			</div>
		</div>
	</footer>
</body>
<script src="js/d3-setup.js"></script>
<script>
	protocol = window.location.protocol === 'http:' ? 'ws://' : 'wss://';
	address = protocol + window.location.host + window.location.pathname
			+ '/ws';
	socket = new WebSocket(address);
	socket.onmessage = function(msg) {
		msg.data == 'reload' && window.location.reload()
	}
</script>
</html>