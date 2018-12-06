<%@page import="com.neuedu.shop.entity.Cart"%>
<%@page contentType="text/html; charset=gbk" pageEncoding="gbk" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="header-wrapper">
	<header class="container">
		<div class="head-right">
			<ul class="top-nav">

				<li class=""><a href="404_error.html" title="我的账户">我的账户</a></li>
				<li class="my-wishlist"><a href="404_error.html" title="我的收藏">我的收藏</a></li>
				<li class="contact-us"><a href="contact_us.html" title="联系我们">联系我们</a></li>
				<li class="checkout"><a href="404_error.html" title="付款">付款</a></li>
				<c:choose>
					<c:when test="${user==null }">
						<li class="log-in"><a href="login.jsp" title="登录">请登录</a></li>
					</c:when>
					<c:otherwise>
						<li class="log-in"><a>您好，${user.username }</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
			<ul class="currencyBox">
				<li id="header_currancy" class="currency"><a
					class="mainCurrency" href="#">币种: RMB</a>
					<div id="currancyBox" class="currency_detial">
						<a href="#">￥</a> <a href="#"></a> <a href="#">$</a>
					</div></li>
			</ul>
			<%
				Cart cart = (Cart) session.getAttribute("cart");
			%>
			<section class="header-bottom">
				<div class="cart-block">
					<ul>
						<%
							if (cart == null) {
						%>
						<li>(0)</li>
						<%
							} else {
						%>
						<li>(<%=cart.getcItems().size()%>)
						</li>
						<%
							}
						%>

						<li><a href="cart.jsp" title="Cart"><img title="Item"
								alt="Item" src="images/item_icon.png" /></a></li>
						<li>Item</li>
					</ul>
					<div id="minicart" class="remain_cart" style="display: none;">
						<%
							if (cart == null) {
						%>
						<p class="empty">购物车中有(0)件商品</p>
						<%
							} else {
						%>
						<p class="empty">
							购物车中有(<%=cart.getcItems().size()%>)件商品
						</p>
						<%
							}
						%>
						<ol>
							<c:forEach var="ci" items="${cart.cItems }">
								<li>
									<div class="img-block">
										<img src="images/small_img.png" title="" alt="" />
									</div>
									<div class="detail-block">
										<h4>
											<a href="#" title="Htc Mobile 1120">${ci.product.name }</a>
										</h4>
										<p>
											<strong>${ci.qty }</strong> x ￥${ci.product.normalprice }
										</p>
										<a href="cart.pre" title="详情">详情</a>
									</div>
									<div class="edit-delete-block">
										<a href="#" title="编辑"><img src="images/edit_icon.png"
											alt="编辑" title="编辑" /></a> <a href="#" title="删除"><img
											src="images/delete_item_btn.png" alt="删除" title="删除" /></a>
									</div>
								</li>
							</c:forEach>
						</ol>
					</div>
				</div>
				<div class="search-block">
					<input type="text" placeholder="搜索商品" /> <input type="submit"
						value="搜索" title="搜索商品" />
				</div>
			</section>
		</div>
		<h1 class="logo">
			<a href="index.html" title="Logo"> <img title="Logo" alt="Logo"
				src="images/logo.jpg" />
			</a>
		</h1>
		<nav id="smoothmenu1" class="ddsmoothmenu mainMenu">
			<ul id="nav">
				<li class="active"><a href="index.pre" title="Home">首页</a></li>
				<li class=""><a href="category.html" title="商品类别">商品类别</a>
					<ul>
						<li><a href="category.html">女装</a></li>
						<li><a href="category.html">男装</a></li>
						<li><a href="category.html">饰品</a></li>
						<li><a href="category.html">移动设备</a>
							<ul>
								<li><a href="category.html">二级类别</a></li>
								<li><a href="category.html">二级类别</a></li>
								<li><a href="category.html">二级类别</a></li>
								<li><a href="category.html">二级类别</a></li>
								<li><a href="category.html">二级类别</a></li>
								<li><a href="category.html">二级类别</a></li>
							</ul></li>
						<li><a href="category.html">鞋</a></li>
						<li><a href="category.html">其他</a></li>
					</ul></li>
				<li class=""><a href="blog.html" title="博客">博客</a></li>
				<li class=""><a href="faq.html" title="FAQ">FAQ</a></li>
				<li class=""><a href="about_us.html" title="关于我们">关于我们</a></li>
				<li class=""><a href="404_error.html" title="Pages">Pages</a></li>
				<li class=""><a href="contact_us.html" title="联系我们">联系我们</a></li>
			</ul>
		</nav>

		<div class="mobMenu">
			<h1>
				<span>菜单</span> <a class="menuBox highlight"
					href="javascript:void(0);">ccc</a> <span class="clearfix"></span>
			</h1>
			<div id="menuInnner" style="display: none;">
				<ul class="accordion">
					<li class="active"><a href="index.html" title="主页">主页</a></li>
					<li class="parent"><a href="category.html" title="类别">类别</a>
						<ul>
							<li><a href="category.html">女装</a></li>
							<li><a href="category.html">男装</a></li>
							<li><a href="category.html">饰品</a></li>
							<li><a href="category.html">移动设备</a>
								<ul>
									<li><a href="category.html">二级类别</a></li>
									<li><a href="category.html">二级类别</a></li>
									<li><a href="category.html">二级类别</a></li>
									<li><a href="category.html">二级类别</a></li>
									<li><a href="category.html">二级类别</a></li>
									<li><a href="category.html">二级类别</a></li>
								</ul></li>
							<li><a href="category.html">鞋</a></li>
							<li><a href="category.html">其他</a></li>
						</ul></li>
					<li class=""><a href="blog.html" title="博客">博客</a></li>
					<li class=""><a href="faq.html" title="Faq">Faq</a></li>
					<li class=""><a href="about_us.html" title="关于我们">关于我们</a></li>
					<li class=""><a href="404_error.html" title="Pages">Pages</a></li>
					<li class=""><a href="contact_us.html" title="联系我们">联系我们</a></li>
					<span class="clearfix"></span>
				</ul>

			</div>
		</div>

	</header>
</div>