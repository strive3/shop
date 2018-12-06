package com.neuedu.shop.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neuedu.shop.dao.impl.CategoryDAOImpt;
import com.neuedu.shop.entity.Category;
import com.neuedu.shop.entity.Paging;
import com.neuedu.shop.util.ServletUtil;

/**
 * Servlet implementation class CategoryServlet
 */
@WebServlet("*.category")
public class CategoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = ServletUtil.getPath(request, response);

        CategoryDAOImpt dao = new CategoryDAOImpt();

        if (path.equals("/list")) {
            int pageNumber = 1;
            int pageSize = 10;
            String pageNumberS = request.getParameter("pageNumber");
            if ( pageNumberS!= null && !pageNumberS.equals("")){
                pageNumber = Integer.parseInt(pageNumberS);
            }
            Paging<Category> paging = dao.findToTreeForPaging(pageNumber,pageSize);
            String pname = "";
            // 创建一个map 存储 pid 对应的 pname
            Map<Integer, String> maps = new HashMap<>();
            for (int i = 0; i < paging.getObjects().size(); i++) {
                if (paging.getObjects().get(i).getPid() != 0) {
                    pname = dao.findNameForId(paging.getObjects().get(i).getPid());
                    maps.put(paging.getObjects().get(i).getPid(), pname);
                }
            }
            // ServletUtil.forwardPage("categories", categories, "category/categories.jsp",
            // request, response);
            request.setAttribute("paging",paging);
            request.setAttribute("maps", maps);
            request.getRequestDispatcher("category/categories.jsp").forward(request, response);
        } else if (path.equals("/add_root")) {
            // 页面跳转
            request.getRequestDispatcher("category/add_category_root.jsp").forward(request, response);
        } else if (path.equals("/addroot")) {
            String name = request.getParameter("name");
            String descr = request.getParameter("descr");

            dao.add(name, descr);
            response.sendRedirect("list.category");
        } else if (path.equals("/add_child")) {
            // 携带pid 跳转到添加子类别的页面
            int id = Integer.parseInt(request.getParameter("pid"));
            Category category = dao.findById(id);
            ServletUtil.forwardPage("category", category, "category/add_category_child.jsp", request, response);
        } else if (path.equals("/addchild")) {
            String name = request.getParameter("name");
            String descr = request.getParameter("descr");
            int id = Integer.parseInt(request.getParameter("id"));

            dao.add(name, descr, id);
            response.sendRedirect("list.category");
        } else if (path.equals("/delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            int pid = Integer.parseInt(request.getParameter("pid"));

            dao.delete(id, pid);

            response.sendRedirect("list.category");
        } else if (path.equals("/load")) {
            int id = Integer.parseInt(request.getParameter("id"));

            Category category = dao.findById(id);
            ServletUtil.forwardPage("category", category, "category/update_category.jsp", request, response);
        } else if (path.equals("/update")) {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String descr = request.getParameter("descr");
            Category category = dao.findById(id);
            category.setName(name);
            category.setDescr(descr);

            dao.update(category);

            response.sendRedirect("list.category");
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
