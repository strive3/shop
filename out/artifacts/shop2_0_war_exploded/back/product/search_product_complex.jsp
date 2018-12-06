<%@page contentType="text/html; charset=gbk" pageEncoding="gbk" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="gbk" />

<title>搜索商品</title>
<link rel="stylesheet" href="css/bootstrap.css" />
<link rel="stylesheet" href="css/bootstrap-responsive.css" />
<link rel="stylesheet" href="css/styles.css" />
<link rel="stylesheet" href="css/toastr.css" />
<link rel="stylesheet" href="css/fullcalendar.css" />
<style>
.rmb {
	width: 100px;
}
</style>
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
<!-- 日历的js -->
<script src="laydate/laydate.js"></script>

<script type="text/javascript">
	function calMemberPrice() {
		var normalprice = document.getElementById("normalprice").value;//1000.00
		//给用户显示80%的价格，并不会传入服务器端，也不允许用户输入
		document.getElementById("memberprice").value = normalprice * .8;
	}

	function checkForm(frm) {
		var lownormalprice = $("#lownormalprice").val();
		var highnormalprice = document.getElementById("highnormalprice").value;
		var lowmemberprice = document.getElementById("lowmemberprice").value;
		var highmemberprice = document.getElementById("highmemberprice").value;

		if (lownormalprice == "" || isNaN(lownormalprice)) {
			$("#lownormalprice").val(0);
		}
		if (highnormalprice == "" || isNaN(highnormalprice)) {
			document.getElementById("highnormalprice").value = 0;
		}
		if (lowmemberprice == "" || isNaN(lowmemberprice)) {
			document.getElementById("lowmemberprice").value = 0;
		}
		if (highmemberprice == "" || isNaN(highmemberprice)) {
			document.getElementById("highmemberprice").value = 0;
		}
		return true;
	}
</script>
<script type="text/javascript">
	//执行一个laydate实例
	laydate.render({
		elem : '#startdate' //指定元素
	});

	laydate.render({
		elem : '#enddate' //指定元素
	});
</script>
</head>
<body>
	<!-- header部分 -->
	<%@include file="../header.jsp"%>
	<div class="page">
		<div class="page-container">
			<div class="container">
				<div class="row">
					<div class="span12">
						<h4 class="header">搜索商品</h4>
						<form action="complex_search_complete.product" method="post"
							onsubmit="return checkForm(this)">
							<table class="table table-striped sortable">
								<thead>
								</thead>
								<tbody>
									<tr>
										<th>商品类别</th>
										<td><select name="categoryid">
												<option value="-1">----所有类别----</option>
												<!-- 显示类别 -->
												<c:forEach var="c" items="${categories }">
													<c:if test="${c.grade ==3 }">
														<option value="${c.id }">${c.name }</option>
													</c:if>
												</c:forEach>
										</select></td>
									</tr>
									<tr>
										<th>关键字</th>
										<td><input type="text" name="keywords" /></td>
									</tr>

									<tr>
										<th>普通价格</th>
										<td>&yen;<select class="rmb" name="lownormalprice">
												<option value="0">-选择价格-</option>
												<option value="1">1</option>
												<option value="100">100</option>
												<option value="500">500</option>
												<option value="1000">1000</option>
												<option value="2000">2000</option>
												<option value="5000">5000</option>

										</select> <!-- <input class="rmb" id="lownormalprice" type="text" name="lownormalprice" /> -->元~
											&yen;<select class="rmb" name="highnormalprice">
												<option value="0">-选择价格-</option>
												<option value="100">100</option>
												<option value="500">500</option>
												<option value="1000">1000</option>
												<option value="2000">2000</option>
												<option value="5000">5000</option>
												<option value="${Integer.MAX_VALUE }">5000以上</option>
										</select> <!-- <input class="rmb" id="highnormalprice" type="text" name="highnormalprice" /> -->元
										</td>
									</tr>
									<tr>
										<th>会员价格</th>
										<td>&yen;<select class="rmb" name="lowmemberprice" >
												<option value="0">-选择价格-</option>
												<option value="1">1</option>
												<option value="100">100</option>
												<option value="500">500</option>
												<option value="1000">1000</option>
												<option value="2000">2000</option>
												<option value="5000">5000</option>

										</select> <!-- <input class="rmb" id="lowmemberprice" type="text" name="lowmemberprice"/> -->元
											~ &yen;<select class="rmb" name="highmemberprice">
												<option value="0">-选择价格-</option>
												<option value="100">100</option>
												<option value="500">500</option>
												<option value="1000">1000</option>
												<option value="2000">2000</option>
												<option value="5000">5000</option>
												<option value="${Integer.MAX_VALUE }">5000以上</option>
										</select> <!-- <input class="rmb" id="highmemberprice" type="text" name="highmemberprice"/> -->元
										</td>
									</tr>

									<tr>
										<th>上架日期</th>
										<td>从<input id="startdate" type="text" name="startdate" />
											到<input id="enddate" type="text" name="enddate" />

										</td>
									</tr>

									<tr>
										<td></td>
										<td><input class="btn btn-success" type="submit"
											value="搜索" />&nbsp;&nbsp;&nbsp;<input class="btn btn-danger"
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