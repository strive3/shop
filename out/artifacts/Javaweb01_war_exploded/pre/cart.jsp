<%@page contentType="text/html; charset=gbk" pageEncoding="gbk"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>

<html>
<head>
<meta charset="gbk">
<title>电商平台首页</title>
<!--js-->
<script src="js/jquery-1.8.2.min.js"></script>
<script src="js/common.js"></script>
<script src="js/jquery.easing.1.3.js"></script>
<script src="js/ddsmoothmenu.js"></script>
<script src="js/jquery.flexslider.js"></script>
<script src="js/jquery.elastislide.js"></script>
<script src="js/jquery.jcarousel.min.js"></script>
<script src="js/jquery.accordion.js"></script>
<script src="js/light_box.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$(".inline").colorbox({
			inline : true,
			width : "50%"
		});
	});
</script>
<!--end js-->

<!-- Mobile Specific Metas ================================================== -->
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">

<!-- CSS ================================================== -->

<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/orange.css">
<link rel="stylesheet" href="css/skeleton.css">
<link rel="stylesheet" href="css/layout.css">
<link rel="stylesheet" href="css/ddsmoothmenu.css" />
<link rel="stylesheet" href="css/elastislide.css" />
<link rel="stylesheet" href="css/home_flexslider.css" />

<link rel="stylesheet" href="css/light_box.css" />
<script src="js/html5.js"></script>


</head>
<body>
	<div class="mainContainer sixteen container">
		<!--Header Block-->
		<%@include file="header.jsp" %>
		<!--Content Block-->
		<section class="content-wrapper">
			<div class="content-container container">
				<div class="col-1-layout">
					<c:forEach var="ci" items="${cart.cItems }">
					<ul class="shopping-cart-table">
						
						<li>
							<div class="img-box">
								<img src="images/cart_img.png" title="" alt="" />
							</div> <a class="edit-btn" href="#" title="编辑">编辑</a>
						</li>
						<li>
							<div class="remove-item-btn">
								<a href="#" title="Remove"><img
									src="images/delete_item_btn.png" title="删除" alt="删除" /></a>
							</div>
							<div class="pro-name">${ci.product.name }</div>
							<div class="pro-qty">
								<input type="text" value="${ci.qty }" />
							</div>
							<div class="pro-price">￥${ci.product.normalprice }</div>
							<div class="pro-price">小计￥${ci.totals }</div>
						</li>
					</ul>
					</c:forEach>
					<div class="show-option-block">
						<a href="#" title="Show Options">显示选项</a>
					</div>
					<div class="update-shopping-cart">
						<<a href="#" />
						<button class="orange-btn">更新购物车</button>
					</div>
					<div class="shopping-cart-collaterals">
						<div class="cart-box">
							<div class="box-title">折扣</div>
							<div class="box-content">
								<p>如果您有优惠券，请输入您的优惠券代码</p>
							</div>
							<button class="orange-btn" title="Apply Coupon">使用优惠券</button>
						</div>
						<div class="cart-box">
							<div class="box-title">运费及税估价;</div>
							<div class="box-content">
								<p>Enter your destnation to get a shipping estimate</p>
								<ul>
									<li><label>国家<em>*</em></label> <select><option>中国</option></select>
									</li>
									<li><label>地区</label> <select><option>请选择地区、州或省</option></select>
									</li>
									<li><label>邮编</label> <input type="text" /></li>
								</ul>
							</div>
							<button class="orange-btn" title="获取报价">获取报价</button>
						</div>
					</div>
					<div class="shopping-cart-totals">
						<!-- <div class="subtotal-row">
							<div class="left">小计</div>
							<div class="right">￥1,000,00</div>
						</div> -->
						<div class="grand-row">
							<div class="left">总计</div>
							<div class="right">￥${cart.totals }</div>
						</div>
						<ul class="checkout-types">
							<li><button class="orange-btn" title="付款" onclick="location.href='order.html'">付款</button></li>
							<li><a href="#" title="更多地址">更多地址</a></li>
						</ul>
					</div>
					<div class="generic-product-list">
						<h4>猜你喜欢：</h4>
						<ul>
							<li>
								<div class="left-img">
									<img src="images/tshir_img.png" title="" alt="" />
								</div>
								<div class="right-content">
									<div class="pro-name">
										White Round Neck <br>T-Shirt
									</div>
									<div class="pro-price">$600.00</div>
									<div class="add-cart-row">
										<button class="orange-btn" title="添加购物车">添加购物车</button>
									</div>
									<a href="#" class="add-wishlist" title="添加收藏">添加收藏</a>
									<a href="#" class="add-compare" title="同类比较">同类比较</a>
								</div>
							</li>
							<li>
								<div class="left-img">
									<img src="images/tshir_img.png" title="" alt="" />
								</div>
								<div class="right-content">
									<div class="pro-name">
										White Round Neck <br>T-Shirt
									</div>
									<div class="pro-price">$600.00</div>
									<div class="add-cart-row">
										<button class="orange-btn" title="添加购物车">添加购物车</button>
									</div>
									<a href="#" class="add-wishlist" title="添加收藏">添加收藏</a>
									<a href="#" class="add-compare" title="同类比较">同类比较</a>
								</div>
							</li>
							<li>
								<div class="left-img">
									<img src="images/tshir_img.png" title="" alt="" />
								</div>
								<div class="right-content">
									<div class="pro-name">
										White Round Neck <br>T-Shirt
									</div>
									<div class="pro-price">$600.00</div>
									<div class="add-cart-row">
										<button class="orange-btn" title="添加购物车">添加购物车</button>
									</div>
									<a href="#" class="add-wishlist" title="添加收藏">添加收藏</a>
									<a href="#" class="add-compare" title="同类比较">同类比较</a>
								</div>
							</li>
							<li>
								<div class="left-img">
									<img src="images/tshir_img.png" title="" alt="" />
								</div>
								<div class="right-content">
									<div class="pro-name">
										White Round Neck <br>T-Shirt
									</div>
									<div class="pro-price">$600.00</div>
									<div class="add-cart-row">
										<button class="orange-btn" title="添加购物车">添加购物车</button>
									</div>
									<a href="#" class="add-wishlist" title="添加收藏">添加收藏</a>
									<a href="#" class="add-compare" title="同类比较">同类比较</a>
								</div>
							</li>
						</ul>
					</div>
				</div>
				<div class="clearfix"></div>
				<div class="news-letter-container">
					<div class="free-shipping-block">
						<h1>ENJOY FREE SHIPPING</h1>
						<p>on all orders as our holiday gift for you!</p>
					</div>
					<div class="news-letter-block">
						<h2>SIGN UP FOR OUR NEWSLETTER</h2>
						<input type="text" value="Enter email address" title="" /> <input
							class="submit-btn" type="submit" value="Submit" title="Submit" />
					</div>
				</div>
			</div>
		</section>
	</div>
	<!--页脚开始-->
	<%@include file="footer.jsp" %>
	<!--页脚结束-->
</body>
</html>