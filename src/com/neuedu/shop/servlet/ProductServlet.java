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
		//һҳ����������
		int pagesize = 10;
		//Ĭ��Ϊ��һҲҳ
		int pageNumber = 1;
        //�ж����pageNumber���ǿյĻ�������ǰ̨��ȡ����ֵ������
		if (request.getParameter("pageNumber") != null && !request.getParameter("pageNumber").equals("")){
			pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		}

		//��ʼ�ж�·�����Բ�ͬ��·������ͬ�Ĵ���
		if (path.equals("/list")) {
			List<Category> categories = cdao.findAll();
			Paging<Product> paging = pdao.findLastByPaging(pageNumber, pagesize);
            //ServletUtil.forwardPage("products", products, "product/products.jsp", request, response);
			request.setAttribute("paging",paging);
			request.setAttribute("categories",categories);
			request.getRequestDispatcher("product/products.jsp").forward(request,response);
		} else if (path.equals("/add_product")) {
			//��ѡ��
			List<Category> categories = cdao.findToTree();
			//����ת��add��ҳ��
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
            //���keywords��Ϊ�� �ͽ����浽session��
            if (keywords != null && !keywords.equals("")){
                session.setAttribute("keywords",keywords);
            }
            Paging<Product> paging = pdao.findByKeywords((String) session.getAttribute("keywords") , pageNumber , pagesize);

            request.setAttribute("paging",paging);
            //keywords = URLEncoder.encode(keywords, "utf-8");
			//keywords = URLDecoder.decode(keywords, "utf-8");
			//ServletUtil.forwardPage("products", products, "product/search_product_simple.jsp", request, response);
			//�󶨵�request
//System.out.println("ִ��get");
			//request.setAttribute("keywords", keywords);
			//��   ת��   ʵ�ַ�ҳ��ѯ
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
			//����Щ����������������Щ����������request�,�������һҳ��ʱ���ٽ���Щ����
			//������̨
			Paging<Product> paging = pdao.complexSearch((int)session.getAttribute("categoryid"),
                                                        (String) session.getAttribute("keywords"),
                                                        (int)session.getAttribute("lownormalprice"),
                                                        (int)session.getAttribute("highnormalprice"),
                                                        (int)session.getAttribute("lowmemberprice"),
                                                        (int)session.getAttribute("highmemberprice"),
                                                        (String)session.getAttribute("startdate"),
                                                        (String)session.getAttribute("enddate"),pageNumber , pagesize);
			//ServletUtil.forwardPage("products", products, "product/search_product_complex_complete.jsp", request, response);
			//��   ת��   ʵ�ַ�ҳ��ѯ
			request.setAttribute("paging",paging);
			request.getRequestDispatcher("product/search_product_complex_complete.jsp").forward(request,response);
		} else if (path.equals("/load")) {
			int id = Integer.parseInt(request.getParameter("id"));
			Product product = pdao.findById(id);
			
			List<Category> categories =  cdao.findToTree();
			//��requestЯ������  ����update��ҳ��
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
		//һҳ����������
		int pagesize = 10;
		//Ĭ��Ϊ��һҲҳ
		int pageNumber = 1;
		//һ������������
		int count = 0;
		//�ж����pageNumber���ǿյĻ�������ǰ̨��ȡ����ֵ������
		if (request.getParameter("pageNumber") != null && !request.getParameter("pageNumber").equals("")){
			pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		}

		//��ʼ�ж�·�����Բ�ͬ��·������ͬ�Ĵ���
		if (path.equals("/list")) {
			//��ѯһ���ж���������
			count = pdao.countAll();
			//һ������ҳ
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
			//��ѡ��
			List<Category> categories = cdao.findToTree();
			//����ת��add��ҳ��
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
			//�󶨵�request
System.out.println("ִ��post");
			//keywords = URLDecoder.decode(keywords,"utf-8");
			request.setAttribute("keywords", keywords);
			//��   ת��   ʵ�ַ�ҳ��ѯ
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
			//����Щ����������������Щ����������request�,�������һҳ��ʱ���ٽ���Щ����
			//������̨
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
			//һ������ҳ
			int pages = 0;
			if (count % pagesize ==0 ){
				pages = count / pagesize;
			} else {
				pages = count / pagesize + 1;
			}
			//ServletUtil.forwardPage("products", products, "product/search_product_complex_complete.jsp", request, response);
			//��   ת��   ʵ�ַ�ҳ��ѯ
			request.setAttribute("products",products);
			request.setAttribute("pageNumber",pageNumber);
			request.setAttribute("pages",pages);
			request.getRequestDispatcher("product/search_product_complex_complete.jsp").forward(request,response);
		} else if (path.equals("/load")) {
			int id = Integer.parseInt(request.getParameter("id"));
			Product product = pdao.findById(id);

			List<Category> categories =  cdao.findToTree();
			//��requestЯ������  ����update��ҳ��
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
