package com.neuedu.shop.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.neuedu.shop.dao.impl.AdminDAOImpl;
import com.neuedu.shop.entity.Admin;
import com.neuedu.shop.entity.Paging;
import com.neuedu.shop.util.ServletUtil;
import com.neuedu.shop.util.VerifyCodeUtils;

/**
 * Adminʵ�����е�����crud ʹ��ע���������@WebServlet ʹ��ͨ�����ɶ�Servlet����
 *
 * @author ������
 */
@WebServlet("*.admin")
public class AdminServlet extends HttpServlet {
    /**
     *
     */
    private static final long serialVersionUID = -2550332412219581465L;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = ServletUtil.getPath(request, response);
        AdminDAOImpl daoImpl = new AdminDAOImpl();


        if (path.equals("/adminlist")) {
            int pageNumber = 1;
            int pagesize = 10;
            if (request.getParameter("pageNumber") != null && !request.getParameter("pageNumber").equals("")){
                pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
            }
            Paging<Admin> adminPaging = daoImpl.findByPage(pageNumber,pagesize);
            // �������Ľ��������Ӧ��jspҳ��������
            // 1.������󶨵�request������
         /*   request.setAttribute("admins", adminPaging.getObjects());

            request.setAttribute("pageNumber",adminPaging.getPageNumber());
            request.setAttribute("pages",adminPaging.getPages());*/
            request.setAttribute("paging",adminPaging);
            // 2.��ȡһ��jsp��ת��������Ҫ�����ҳ�潻��˭����ʾ��
            RequestDispatcher rd = request.getRequestDispatcher("admin/admins.jsp");
            // 3.ת����������������ҳ����ʾ��
            rd.forward(request, response);
        } else if (path.equals("/add")) {
            // ��ǰ̨��ȡ���������
            String aname = request.getParameter("aname");
            String apwd = request.getParameter("apwd");
            daoImpl.add(new Admin(aname, apwd));
            // ҳ����ת���ض���
            response.sendRedirect("adminlist.admin");
        } else if (path.equals("/load")) {
            // ��ǰ̨��ȡ�������е����� �������еģ��൱�� name ��id �൱�� name="id"
            int id = Integer.parseInt(request.getParameter("id"));
            Admin admin = daoImpl.findById(id);

            request.setAttribute("admin", admin);
            request.getRequestDispatcher("admin/update_admin.jsp").forward(request, response);
        } else if (path.equals("/update")) {
            // ��ǰ̨��ȡ��������
            String newpwd = request.getParameter("newpwd");

            int id = Integer.parseInt(request.getParameter("id"));
            Admin admin = daoImpl.findById(id);
            admin.setSpwd(newpwd);
            daoImpl.update(admin);
            // �ض���list���
            response.sendRedirect("adminlist.admin");
        } else if (path.equals("/delete")) {
            int id = Integer.parseInt(request.getParameter("id"));

            daoImpl.delete(id);
            response.sendRedirect("adminlist.admin");
        } else if (path.equals("/login")) {
            //����Ա��½
            String name = request.getParameter("name");
            String pwd = request.getParameter("pwd");
            String code = request.getParameter("code");
            HttpSession session = request.getSession();
            String vercode = (String) session.getAttribute("verCode");
            Admin admin = daoImpl.findByName(name);
            //�ж� �û��� ������ �ܲ��ܵ�½
            if (code.toLowerCase().equals(vercode)) {
                if (admin != null && admin.getSpwd().equals(ServletUtil.getMD5(pwd))) {
                    //�����½�ɹ� ��admin�󶨵�session��
                    //Ŀ�����÷ǵ�½�Ĺ���Ա����ͨ��url��ֱַ�ӷ��ʵ�����ҳ��
                    session.setAttribute("admin", admin);
                    //��½�ɹ�
                    response.sendRedirect("index.jsp");
                } else {
                    ServletUtil.forwardPage("error_msg", "�û������������", "login.jsp", request, response);
                }

            } else {
                ServletUtil.forwardPage("error_msg_code", "��֤�����", "login.jsp", request, response);
            }

        } else if (path.equals("/code")) {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpeg");
            //��������ִ�
            String verifyCode = VerifyCodeUtils.generateVerifyCode(4);

System.out.println("���ɵ���֤��" + verifyCode);
            //����Ựsession
            HttpSession session = request.getSession(true);
            //ɾ����ǰ��
            session.removeAttribute("verCode");
            session.setAttribute("verCode", verifyCode.toLowerCase());
System.out.println("sessionID: " + session.getId());
            //����ͼƬ
            int w = 100, h = 30;
            VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
        } else if (path.equals("/logout")) {
            HttpSession session = request.getSession();
            session.invalidate();
            response.sendRedirect("login.jsp");
        }
    }
}
