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
		<!--Banner Block-->
		<section class="banner-wrapper">
			<div class="banner-block container">
				<div class="flexslider">
					<ul class="slides">
						<li><img title="Banner" alt="Banner"
							src="images/iPhoneX_big.jpg" /></li>
						<li><img title="Banner" alt="Banner"
							src="images/iPhone4S.jpg" /></li>
						<li><img title="Banner" alt="Banner" src="images/banner3.jpg" /></li>
						<li><img title="Banner" alt="Banner" src="images/iPhone5.jpg" /></li>
					</ul>
				</div>
				<ul class="banner-add">
					<li class="add1"><a href="#" title=""><img title="add1"
							alt="add1" src="images/banner_add1.png" /></a></li>
					<li class="add2"><a href="#" title=""><img title="add2"
							alt="add2" src="images/banner_add2.png" /></a></li>
				</ul>
			</div>
		</section>
		<!--Content Block-->
		<section class="content-wrapper">
			<div class="content-container container">
				<div class="heading-block">
					<h1>推荐商品</h1>
					<ul class="pagination">
						<li class="grid"><a href="#" title="Grid">Grid</a></li>
					</ul>
				</div>
				<div class="feature-block">
					<ul id="mix" class="product-grid">

						<c:forEach var="p" items="${recommend }">
						<li>
							<div class="pro-img">
								<img title="推荐商品" alt="推荐商品" src="images/iPhoneX.jpg" />
							</div>
							<div class="pro-hover-block">
								<h4 class="pro-name">${p.name }</h4>
								<div class="link-block">
									<a href="#quick-view-container" class="quickllook inline"
										title="快速预览">快速预览</a> <a href="view.pre?id=${p.id }" class="quickproLink"
										title="商品链接">商品链接</a>
								</div>
								<div class="pro-price">￥${p.normalprice }</div>
							</div>
						</li>
					</c:forEach>

					</ul>
				</div>
				<div class="heading-block">
					<h1>最新商品</h1>
				</div>
				<div class="new-product-block">
					<ul class="product-grid">
						<c:forEach var="p" items="${lastproducts1 }">
							<li>
								<div class="pro-img">
									<img title="最新商品" alt="最新商品" src="images/zhaji.jpg" />
								</div>
								<div class="pro-content">
									<p>${p.name }</p>
								</div>
								<div class="pro-price">￥${p.normalprice }</div>
								<div class="pro-btn-block">
									<a class="add-cart left" href="#" title="Add to Cart">添加购物车</a>
									<a class="add-cart right quickCart inline"
										href="#quick-view-container" title="快速查看">快速查看</a>
								</div>
								<div class="pro-link-block">
									<a class="add-wishlist left" href="#" title="Add to wishlist">收藏</a>
									<a class="add-compare right" href="#" title="同类比较">同类比较</a>
									<div class="clearfix"></div>
								</div>
							</li>
						</c:forEach>
					</ul>
					<ul class="product-grid">
						<c:forEach var="p" items="${lastproducts2 }">
							<li>
								<div class="pro-img">
									<img title="最新商品" alt="最新商品" src="images/zhaji.jpg" />
								</div>
								<div class="pro-content">
									<p>${p.name }</p>
								</div>
								<div class="pro-price">￥${p.normalprice }</div>
								<div class="pro-btn-block">
									<a class="add-cart left" href="#" title="Add to Cart">添加购物车</a>
									<a class="add-cart right quickCart inline"
										href="#quick-view-container" title="快速查看">快速查看</a>
								</div>
								<div class="pro-link-block">
									<a class="add-wishlist left" href="#" title="Add to wishlist">收藏</a>
									<a class="add-compare right" href="#" title="同类比较">同类比较</a>
									<div class="clearfix"></div>
								</div>
							</li>
						</c:forEach>
					</ul>
				</div>
				<div class="news-letter-container">
					<div class="free-shipping-block">
						<h1>ENJOY FREE SHIPPING</h1>
						<p>on all orders as our holiday gift for you!</p>
					</div>
					<div class="news-letter-block">
						<h2>SIGN UP FOR OUR NEWSLETTER</h2>
						<input type="text" value="Enter email address" title="" /> <input
							type="submit" value="Submit" title="Submit" />
					</div>
				</div>
			</div>
		</section>
	</div>
	<!--Quick view Block-->
	<script type="text/javascript">
		jQuery(function() {
			var tabContainers = jQuery('div.tabs > div');
			tabContainers.hide().filter(':first').show();
			jQuery('div.tabs ul.tabNavigation a').click(function() {
				tabContainers.hide();
				tabContainers.filter(this.hash).show();
				jQuery('div.tabs ul.tabNavigation a').removeClass('selected');
				jQuery(this).addClass('selected');
				return false;
			}).filter(':first').click();
		});
	</script>
	<article style="display: none;">
		<form action="addToCart.pre" method="post">
		<section id="quick-view-container" class="quick-view-wrapper">
			<div class="quick-view-container">
				<div class="quick-view-left">
					<h2></h2>
					<div class="product-img-box">
						<p class="product-image">
							<img src="images/zhaji.jpg" title="炸鸡腿" alt="炸鸡腿"
								class="sale-img" /> <a href="view.html" title="Image"><img
								src="images/quick_view_img.png" title="Image" alt="Image" /></a>
						</p>
						<ul class="thum-img">
							<li><img src="images/quick_thum_img.png" title="" alt="" /></li>
							<li><img src="images/quick_thum_img.png" title="" alt="" /></li>
						</ul>
					</div>
				</div>
				<div class="quick-view-right tabs">
					<ul class="tab-block tabNavigation">
						<li><a class="selected" title="Overview" href="#tabDetail">商品概述</a></li>
						<li><a title="Description" href="#tabDes">商品描述</a></li>
					</ul>
					<div id="tabDetail" class="tabDetail">
						<div class="first-review">快速查看商品</div>
						<div class="price-box">
							<span class="price">￥123132</span>
						</div>
						<div class="availability">In stock</div>
						<div class="color-size-block">
							<div class="label-row">
								<label><em>*</em> 颜色</label> <span class="required">* 必填项</span>
							</div>
							<div class="select-row">
								<select>
									<option>-- 请选择 --</option>
									<option>微辣</option>
									<option>普通</option>
									<option>特辣</option>
								</select>
							</div>
							<div class="label-row">
								<label><em>*</em> 大小</label>
							</div>
							<div class="select-row">
								<select>
									<option>-- 请选择 --</option>
									<option>-- 小只--</option>
									<option>-- 中只 --</option>
									<option>-- 大只 --</option>
								</select>
							</div>
						</div>
						<div class="add-to-cart-box">
							<span class="qty-box"> <label for="qty">数量：</label> <a
								class="prev" title="" href="#"><img alt="" title=""
									src="images/qty_prev.png"></a> <input type="text" name="qty"
								class="input-text qty" id="qty" maxlength="12" value="1">
								<a class="next" title="" href="#"><img alt="" title=""
									src="images/qty_next.png"></a>
							</span>
							<button title="添加购物车" class="form-button">
								<span>添加购物车</span>
							</button>
						</div>
					</div>
					<div id="tabDes" class="tabDes">
						<p>要不要吃炸鸡腿</p>
					</div>
				</div>
				<div class="clearfix"></div>
			</div>
		</section>
		</form>
	</article>
	<!--Quick view Block-->
	<!--页脚开始-->
	<%@include file="footer.jsp" %>
	<!--页脚结束-->
</body>
</html>