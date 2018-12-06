<%@page contentType="text/html; charset=gbk" pageEncoding="gbk" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="gbk" />

<title>电商平台后台管理</title>
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
	<%@include file="../header.jsp" %>
	<div class="page">
		<div class="page-container">
			<div class="container">
				<div class="row">
					<div class="span12">
						<h4 class="header">修改管理员信息</h4>
						
						<form action="update.user" method="post">
							<table class="table table-striped sortable">
								<thead>
								</thead>
								<tbody>
								
										<input type="hidden" name="id" value="${requestScope.user.id}" />
									<tr>
										<th>用户ID</th>
										<td>${requestScope.user.id}</td>
									</tr>

									<tr>
										<th>用户名</th>
										<td><input type="text" name="aname"
											value="${requestScope.user.username}" readonly="readonly" /></td>
									</tr>

									<tr>
										<th>原始密码</th>
										<td><input type="password" name="apwd" /></td>
									</tr>
									<tr>
										<th>新密码</th>
										<td><input type="password" name="newpwd" /></td>
									</tr>
									<tr>
										<th>确认新密码</th>
										<td><input type="password" name="confirmpwd" /></td>
									</tr>
									<tr>
										<th>电话</th>
										<td><input type="text" name="phone" /></td>
									</tr>
									<tr>
										<th>送货地址</th>
										<td><textarea rows="9" cols="40" name="addr"></textarea></td>
									</tr>
									<tr>

										<td></td>
										<td><input class="btn btn-success" type="submit"
											value="修改" />&nbsp;&nbsp;&nbsp;<input class="btn btn-danger"
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
	<!-- footer -->
		<%@include file="../footer.jsp" %>
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