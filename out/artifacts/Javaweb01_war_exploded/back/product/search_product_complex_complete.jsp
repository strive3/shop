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
	<%@include file="../header.jsp" %>
	<div class="page">
	
		<div class="page-container">
			<div class="container">
				<div class="row">
					<div class="span12">
						<h4 class="header">商品搜索结果</h4>
						
							<a class="btn btn-info" href="search_product_complex.html">继续搜索</a>
			
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
												<li><a href="#">修改</a> <a
													href="#">删除</a></li>
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