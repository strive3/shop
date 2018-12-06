package com.neuedu.shop.servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.neuedu.shop.dao.impl.CategoryDAOImpt;
import com.neuedu.shop.dao.impl.ProductDAOImpl;
import com.neuedu.shop.entity.Category;
import com.neuedu.shop.entity.Paging;
import com.neuedu.shop.entity.Product;
import com.neuedu.shop.util.ServletUtil;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet("*.product")
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = ServletUtil.getPath(request, response);
		ProductDAOImpl pdao = new ProductDAOImpl();
		CategoryDAOImpt cdao = new CategoryDAOImpt();
		HttpSession session = request.getSession();
		//一页多少条数据
		int pagesize = 10;
		//默认为第一也页
		int pageNumber = 1;
        //判断如果pageNumber不是空的话，将从前台获取到的值赋给他
		if (request.getParameter("pageNumber") != null && !request.getParameter("pageNumber").equals("")){
			pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		}

		//开始判断路径，对不同的路径做不同的处理
		if (path.equals("/list")) {
			List<Category> categories = cdao.findAll();
			Paging<Product> paging = pdao.findLastByPaging(pageNumber, pagesize);
            //ServletUtil.forwardPage("products", products, "product/products.jsp", request, response);
			request.setAttribute("paging",paging);
			request.setAttribute("categories",categories);
			request.getRequestDispatcher("product/products.jsp").forward(request,response);
		} else if (path.equals("/add_product")) {
			//多选框
			List<Category> categories = cdao.findToTree();
			//先跳转到add的页面
			ServletUtil.forwardPage("categories", categories, "product/add_product.jsp", request, response);

		} else if (path.equals("/add")) {

			String name = request.getParameter("name");
			String descr = request.getParameter("descr");
			Double normalprice = Double.valueOf(request.getParameter("normalprice"));
			Double memberprice = Double.valueOf(request.getParameter("memberprice"));
			Category category = cdao.findById(Integer.valueOf(request.getParameter("categoryid")));


			pdao.add(new Product(name, descr, normalprice, memberprice,category));
			response.sendRedirect("list.product");
		} else if (path.equals("/simple_search")) {
            String keywords = request.getParameter("keywords");
            //如果keywords不为空 就将它存到session中
            if (keywords != null && !keywords.equals("")){
                session.setAttribute("keywords",keywords);
            }
            Paging<Product> paging = pdao.findByKeywords((String) session.getAttribute("keywords") , pageNumber , pagesize);

            request.setAttribute("paging",paging);
            //keywords = URLEncoder.encode(keywords, "utf-8");
			//keywords = URLDecoder.decode(keywords, "utf-8");
			//ServletUtil.forwardPage("products", products, "product/search_product_simple.jsp", request, response);
			//绑定到request
//System.out.println("执行get");
			//request.setAttribute("keywords", keywords);
			//绑定   转发   实现分页查询
            request.getRequestDispatcher("product/search_product_simple.jsp").forward(request,response);
        } else if (path.equals("/complex_search")) {
			List<Category> categories =  cdao.findToTree();
			ServletUtil.forwardPage("categories", categories, "product/search_product_complex.jsp", request, response);
		} else if (path.equals("/complex_search_complete")) {
            if (request.getParameter("categoryid") != null && !request.getParameter("categoryid").equals("")){
                int categoryid = Integer.parseInt(request.getParameter("categoryid"));
                session.setAttribute("categoryid",categoryid);
            }
            if (request.getParameter("keywords") != null && !request.getParameter("keywords").equals("")){
                String keywords = request.getParameter("keywords");
                session.setAttribute("keywords",keywords);
            }
            if (request.getParameter("lownormalprice") != null && !request.getParameter("lownormalprice").equals("")){
                int lownormalprice = Integer.parseInt(request.getParameter("lownormalprice"));
                session.setAttribute("lownormalprice",lownormalprice);
            }
            if (request.getParameter("highnormalprice") != null && !request.getParameter("highnormalprice").equals("")){
                int highnormalprice = Integer.parseInt(request.getParameter("highnormalprice"));
                session.setAttribute("highnormalprice",highnormalprice);
            }
            if (request.getParameter("lowmemberprice") != null && !request.getParameter("lowmemberprice").equals("")){
                int lowmemberprice = Integer.parseInt(request.getParameter("lowmemberprice"));
                session.setAttribute("lowmemberprice",lowmemberprice);
            }
            if (request.getParameter("highmemberprice") != null && !request.getParameter("highmemberprice").equals("")){
                int highmemberprice = Integer.parseInt(request.getParameter("highmemberprice"));
                session.setAttribute("highmemberprice",highmemberprice);
            }
            if (request.getParameter("startdate") != null && !request.getParameter("startdate").equals("")){
                String startdate = request.getParameter("startdate");
                session.setAttribute("startdate",startdate);
            }
            if (request.getParameter("enddate") != null && !request.getParameter("enddate").equals("")){
                String enddate = request.getParameter("enddate");
                session.setAttribute("enddate",enddate);
            }
			//将这些条件绑定起来（将这些条件保存在request里）,当点击下一页的时候，再将这些条件
			//传到后台
			Paging<Product> paging = pdao.complexSearch((int)session.getAttribute("categoryid"),
                                                        (String) session.getAttribute("keywords"),
                                                        (int)session.getAttribute("lownormalprice"),
                                                        (int)session.getAttribute("highnormalprice"),
                                                        (int)session.getAttribute("lowmemberprice"),
                                                        (int)session.getAttribute("highmemberprice"),
                                                        (String)session.getAttribute("startdate"),
                                                        (String)session.getAttribute("enddate"),pageNumber , pagesize);
			//ServletUtil.forwardPage("products", products, "product/search_product_complex_complete.jsp", request, response);
			//绑定   转发   实现分页查询
			request.setAttribute("paging",paging);
			request.getRequestDispatcher("product/search_product_complex_complete.jsp").forward(request,response);
		} else if (path.equals("/load")) {
			int id = Integer.parseInt(request.getParameter("id"));
			Product product = pdao.findById(id);
			
			List<Category> categories =  cdao.findToTree();
			//让request携带数据  交给update的页面
			request.setAttribute("categories", categories);
			request.setAttribute("product", product);
			request.getRequestDispatcher("product/update_product.jsp").forward(request, response);
		} else if (path.equals("/update")) {
			int id = Integer.parseInt(request.getParameter("id"));
			Product product = pdao.findById(id);
			
			String name = request.getParameter("name");
			String descr = request.getParameter("descr");
			Double normalprice = Double.valueOf(request.getParameter("normalprice"));
			Double memberprice = Double.valueOf(request.getParameter("memberprice"));
			Category category = cdao.findById(Integer.valueOf(request.getParameter("categoryid")));
			
			product.setName(name);
			product.setDescr(descr);
			product.setNormalprice(normalprice);
			product.setMemberprice(memberprice);
			product.setCategory(category);
			pdao.update(product);
			
			response.sendRedirect("list.product");
		} else if (path.equals("/delete")) {
			int id = Integer.parseInt(request.getParameter("id"));
			
			pdao.delete(id);
			
			response.sendRedirect("list.product");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*String path = ServletUtil.getPath(request, response);
		ProductDAOImpl pdao = new ProductDAOImpl();
		CategoryDAOImpt cdao = new CategoryDAOImpt();
		HttpSession session = request.getSession();
		//一页多少条数据
		int pagesize = 10;
		//默认为第一也页
		int pageNumber = 1;
		//一共多少条数据
		int count = 0;
		//判断如果pageNumber不是空的话，将从前台获取到的值赋给他
		if (request.getParameter("pageNumber") != null && !request.getParameter("pageNumber").equals("")){
			pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		}

		//开始判断路径，对不同的路径做不同的处理
		if (path.equals("/list")) {
			//查询一共有多少条数据
			count = pdao.countAll();
			//一共多少页
			int pages = 0;
			if (count % pagesize == 0 ){
				pages = count / pagesize;
			} else {
				pages = count / pagesize + 1;
			}
			if (pageNumber > pages ){
				pageNumber = pages;
			}else if (pageNumber < 1){
				pageNumber = 1;
			}
			List<Product> products = pdao.findLast(pageNumber, pagesize);
			//ServletUtil.forwardPage("products", products, "product/products.jsp", request, response);
			request.setAttribute("products",products);
			request.setAttribute("pageNumber",pageNumber);
			request.setAttribute("pages",pages);
			request.getRequestDispatcher("product/products.jsp").forward(request,response);
		} else if (path.equals("/add_product")) {
			//多选框
			List<Category> categories = cdao.findToTree();
			//先跳转到add的页面
			ServletUtil.forwardPage("categories", categories, "product/add_product.jsp", request, response);

		} else if (path.equals("/add")) {

			String name = request.getParameter("name");
			String descr = request.getParameter("descr");
			Double normalprice = Double.valueOf(request.getParameter("normalprice"));
			Double memberprice = Double.valueOf(request.getParameter("memberprice"));
			Category category = cdao.findById(Integer.valueOf(request.getParameter("categoryid")));


			pdao.add(new Product(name, descr, normalprice, memberprice,category));
			response.sendRedirect("list.product");
		} else if (path.equals("/simple_search")) {
			String keywords = request.getParameter("keywords");
			//ServletUtil.forwardPage("products", products, "product/search_product_simple.jsp", request, response);
			Paging<Product> paging =  pdao.findByKeywords(keywords , pageNumber , pagesize);
			//绑定到request
System.out.println("执行post");
			//keywords = URLDecoder.decode(keywords,"utf-8");
			request.setAttribute("keywords", keywords);
			//绑定   转发   实现分页查询
			request.setAttribute("paging",paging);
			request.getRequestDispatcher("product/search_product_simple.jsp").forward(request,response);
		} else if (path.equals("/complex_search")) {
			List<Category> categories =  cdao.findToTree();
			ServletUtil.forwardPage("categories", categories, "product/search_product_complex.jsp", request, response);
		} else if (path.equals("/complex_search_complete")) {
			int categoryid = Integer.parseInt(request.getParameter("categoryid"));
			String keywords = request.getParameter("keywords");
			int lownormalprice = Integer.parseInt(request.getParameter("lownormalprice"));
			int highnormalprice = Integer.parseInt(request.getParameter("highnormalprice"));
			int lowmemberprice = Integer.parseInt(request.getParameter("lowmemberprice"));
			int highmemberprice = Integer.parseInt(request.getParameter("highmemberprice"));
			String startdate = request.getParameter("startdate");
			String enddate = request.getParameter("enddate");
			//将这些条件绑定起来（将这些条件保存在request里）,当点击下一页的时候，再将这些条件
			//传到后台
			request.setAttribute("categoryid",categoryid);
			request.setAttribute("keywords",keywords);
			request.setAttribute("lownormalprice",lownormalprice);
			request.setAttribute("highnormalprice",highnormalprice);
			request.setAttribute("lowmemberprice",lowmemberprice);
			request.setAttribute("highmemberprice",highmemberprice);
			request.setAttribute("startdate",startdate);
			request.setAttribute("enddate",enddate);
			List<Product> products = pdao.complexSearch(categoryid, keywords, lownormalprice, highnormalprice, lowmemberprice, highmemberprice, startdate, enddate ,pageNumber , pagesize);
			count = pdao.countComp(categoryid, keywords, lownormalprice, highnormalprice, lowmemberprice, highmemberprice, startdate, enddate);
			//一共多少页
			int pages = 0;
			if (count % pagesize ==0 ){
				pages = count / pagesize;
			} else {
				pages = count / pagesize + 1;
			}
			//ServletUtil.forwardPage("products", products, "product/search_product_complex_complete.jsp", request, response);
			//绑定   转发   实现分页查询
			request.setAttribute("products",products);
			request.setAttribute("pageNumber",pageNumber);
			request.setAttribute("pages",pages);
			request.getRequestDispatcher("product/search_product_complex_complete.jsp").forward(request,response);
		} else if (path.equals("/load")) {
			int id = Integer.parseInt(request.getParameter("id"));
			Product product = pdao.findById(id);

			List<Category> categories =  cdao.findToTree();
			//让request携带数据  交给update的页面
			request.setAttribute("categories", categories);
			request.setAttribute("product", product);
			request.getRequestDispatcher("product/update_product.jsp").forward(request, response);
		} else if (path.equals("/update")) {
			int id = Integer.parseInt(request.getParameter("id"));
			Product product = pdao.findById(id);

			String name = request.getParameter("name");
			String descr = request.getParameter("descr");
			Double normalprice = Double.valueOf(request.getParameter("normalprice"));
			Double memberprice = Double.valueOf(request.getParameter("memberprice"));
			Category category = cdao.findById(Integer.valueOf(request.getParameter("categoryid")));

			product.setName(name);
			product.setDescr(descr);
			product.setNormalprice(normalprice);
			product.setMemberprice(memberprice);
			product.setCategory(category);
			pdao.update(product);

			response.sendRedirect("list.product");
		} else if (path.equals("/delete")) {
			int id = Integer.parseInt(request.getParameter("id"));

			pdao.delete(id);

			response.sendRedirect("list.product");
		}*/
		doGet(request,response);
	}

}
