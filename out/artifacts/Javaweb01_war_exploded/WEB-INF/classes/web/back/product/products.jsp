<%@page contentType="text/html; charset=gbk" pageEncoding="gbk"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="gbk" />

<title>����ƽ̨��̨����</title>
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
	<!-- header���� -->
	<%@include file="../header.jsp"%>
	<div class="page">
		<div class="page-container">
			<div class="container">
				<div class="row">
					<div class="span12">
						<a href="#newUserModal" data-toggle="modal" class="btn pull-right">�������Ʒ</a>
						<h4 class="header">��Ʒ�б�</h4>
						<form action="simple_search.product" method="post">
							<input type="text" name="keywords" class="form-control"
								placeholder="�����������ؼ���">&nbsp; <input
								class="btn btn-info" type="submit" value="����">
						</form>
						<table class="table table-striped sortable">
							<thead>
								<tr>
									<th>��ƷID</th>
									<th>��Ʒ����</th>
									<th>��Ʒ����</th>
									<th>��ͨ�۸�</th>
									<th>��Ա�۸�</th>
									<th>�ϼ�����</th>
									<th>�������</th>
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
												<button class="btn">����</button>
												<button data-toggle="dropdown" class="btn dropdown-toggle">
													<span class="caret"></span>
												</button>
												<ul class="dropdown-menu">
													<li><a href="load.product?id=${p.id }">�޸�</a> <a onclick="del(${p.id })">ɾ��</a>
													<script type="text/javascript">
															function del(id) {
																if(confirm("ȷ��Ҫɾ����")){
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
					<h3>�������Ʒ</h3>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" action="add.product" method="post">
						<div class="control-group">
							<label for="inputEmail" class="control-label">��Ʒ����</label>
							<div class="controls">
								<input id="inputEmail" name="name" type="text"
									placeholder="��������Ʒ����" />
							</div>
						</div>
						<div class="control-group">
							<label for="inputCurrentPassword" class="control-label">��Ʒ����
							</label>
							<div class="controls">
								<input id="inputCurrentPassword" name="descr" type="text"
									placeholder="��������Ʒ����" />
							</div>
						</div>
						<div class="control-group">
							<label for="inputCurrentPassword" class="control-label">��ͨ�۸�
							</label>
							<div class="controls">
								<input id="normalprice" name="normalprice" type="text"
									placeholder="��ͨ�۸�" onblur="calMemberPrice()" />
							</div>
						</div>
						<div class="control-group">
							<label for="inputCurrentPassword" class="control-label">��Ա�۸�
							</label>
							<div class="controls">
								<input id="memberprice" type="text" placeholder="��Ա�۸�" />
							</div>
						</div>
						<div class="control-group">
							<label for="inputCurrentPassword" class="control-label">��Ʒ���
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
							<a href="#" data-dismiss="modal" class="btn">�ر�</a><input
								type="submit" class="btn btn-primary" value="�����Ʒ" />
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