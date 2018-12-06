<%@page contentType="text/html; charset=gbk" pageEncoding="gbk"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
</head>
<body>
	<!-- header部分 -->
	<%@include file="../header.jsp"%>
	<div class="page">
		<div class="page-container">
			<div class="container">
				<div class="row">
					<div class="span12">
						<a href="#newUserModal" data-toggle="modal" class="btn pull-right">添加新商品</a>
						<h4 class="header">商品列表</h4>
						<form action="simple_search.product" method="post">
							<input type="text" name="keywords" class="form-control"
								placeholder="请输入搜索关键字">&nbsp; <input
								class="btn btn-info" type="submit" value="搜索">
						</form>
						<table class="table table-striped sortable">
							<thead>
								<tr>
									<th>商品ID</th>
									<th>商品名称</th>
									<th>商品描述</th>
									<th>普通价格</th>
									<th>会员价格</th>
									<th>上架日期</th>
									<th>所属类别</th>
								</tr>
							</thead>
							<tbody>

								<c:forEach var="p" items="${products }">
									<tr>
										<td>${p.id }</td>
										<td>${p.name }</td>
										<td>${p.descr }</td>
										<td>${p.normalprice }</td>
										<td>${p.memberprice }</td>
										<td>${p.pDate }</td>
										<td>${p.category.name }</td>
										<td>
											<div class="btn-group">
												<button class="btn">操作</button>
												<button data-toggle="dropdown" class="btn dropdown-toggle">
													<span class="caret"></span>
												</button>
												<ul class="dropdown-menu">
													<li><a href="load.product?id=${p.id }">修改</a> <a onclick="del(${p.id })">删除</a>
													<script type="text/javascript">
															function del(id) {
																if(confirm("确定要删除吗")){
																	window.location.href="delete.product?id=" + id;
																}
															}
														</script></li>
													
												</ul>
											</div>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<div class="pagination pagination-centered">
							<ul>
								<li class="disabled"><a href="#">&laquo;</a></li>
								<li class="active"><a href="#">1</a></li>
								<li><a href="#">2</a></li>
								<li><a href="#">3</a></li>
								<li><a href="#">4</a></li>
								<li><a href="#">5</a></li>
								<li><a href="#">&raquo;</a></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div id="newUserModal" class="modal hide fade">
				<div class="modal-header">
					<button type="button" data-dismiss="modal" aria-hidden="true"
						class="close">&times;</button>
					<h3>添加新商品</h3>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" action="add.product" method="post">
						<div class="control-group">
							<label for="inputEmail" class="control-label">商品名称</label>
							<div class="controls">
								<input id="inputEmail" name="name" type="text"
									placeholder="请输入商品名称" />
							</div>
						</div>
						<div class="control-group">
							<label for="inputCurrentPassword" class="control-label">商品描述
							</label>
							<div class="controls">
								<input id="inputCurrentPassword" name="descr" type="text"
									placeholder="请输入商品描述" />
							</div>
						</div>
						<div class="control-group">
							<label for="inputCurrentPassword" class="control-label">普通价格
							</label>
							<div class="controls">
								<input id="normalprice" name="normalprice" type="text"
									placeholder="普通价格" onblur="calMemberPrice()" />
							</div>
						</div>
						<div class="control-group">
							<label for="inputCurrentPassword" class="control-label">会员价格
							</label>
							<div class="controls">
								<input id="memberprice" type="text" placeholder="会员价格" />
							</div>
						</div>
						<div class="control-group">
							<label for="inputCurrentPassword" class="control-label">商品类别
							</label>
							<div class="controls">
								<select name="categoryid">
									<c:forEach var="c" items="${categories }">
									<%-- 	<c:if test="${c.grade ==3 }"> --%>
											<option value="${c.id }">${c.name }</option>
									<%-- 	</c:if> --%>
									</c:forEach>
								</select>
							</div>

						</div>
						<div class="modal-footer">
							<a href="#" data-dismiss="modal" class="btn">关闭</a><input
								type="submit" class="btn btn-primary" value="添加商品" />
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- footer -->
	<%@include file="../footer.jsp"%>
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