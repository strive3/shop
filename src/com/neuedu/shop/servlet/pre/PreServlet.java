package com.neuedu.shop.servlet.pre;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.neuedu.shop.dao.IOrderDAO;
import com.neuedu.shop.dao.impl.OrderDAOImpl;
import com.neuedu.shop.dao.impl.ProductDAOImpl;
import com.neuedu.shop.dao.impl.UserDAOImpl;
import com.neuedu.shop.entity.*;
import com.neuedu.shop.util.CodeUtil;
import com.neuedu.shop.util.MailUtil;
import com.neuedu.shop.util.ServletUtil;
import com.neuedu.shop.util.VerifyCodeUtils;

/**
 * Servlet implementation class PreServlet
 */
@WebServlet("*.pre")
public class PreServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;



    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String path = ServletUtil.getPath(request, response);
        ProductDAOImpl pdao = new ProductDAOImpl();
        if (path.equals("/index")) {
            List<Product> lastproducts1 = pdao.findLast(1, 4);
            List<Product> lastproducts2 = pdao.findLast(2, 4);
            List<Product> recommend = pdao.findLast(2, 8);
            Product product1 = pdao.findById(10);
            Product product2 = pdao.findById(20);
            Product product3 = pdao.findById(30);
            Product product4 = pdao.findById(40);
            request.setAttribute("product1",product1);
            request.setAttribute("product2",product2);
            request.setAttribute("product3",product3);
            request.setAttribute("product4",product4);
            request.setAttribute("recommend", recommend);
            request.setAttribute("lastproducts1", lastproducts1);
            request.setAttribute("lastproducts2", lastproducts2);
            request.getRequestDispatcher("/pre/index.jsp").forward(request, response);

        } else if (path.equals("/view")) {
            // ��ҳ�滹û�н�����תʱ���õ�����id �󶨵�request�ϣ��ٽ���ҳ�����ת
            // �ҵ�id��Ӧ��product
            // ��֮��Ϳ��Դ�request��ȡ�ð󶨵�product
            int id = Integer.parseInt(request.getParameter("id"));
            Product product = pdao.findById(id);
            request.setAttribute("product", product);

            request.getRequestDispatcher("view.jsp").forward(request, response);
        } else if (path.equals("/addToCart")) {
            CartItem item = new CartItem();
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                cart = new Cart();
                session.setAttribute("cart", cart);
            }

            int id = Integer.parseInt(request.getParameter("id"));
            int qty = Integer.parseInt(request.getParameter("qty"));
            System.out.println("id" + id);
            item.setProduct(new ProductDAOImpl().findById(id));
            item.setQty(qty);
            cart.add(item);

            response.sendRedirect("cart.pre");
        } else if (path.equals("/cart")) {
            //���ش�session��ȡ����	cart����      session����ֱ����
			/*HttpSession session = request.getSession();
			Cart cart = (Cart)session.getAttribute("cart");*/
            //ת����cart.jspҳ�浱��
            request.getRequestDispatcher("cart.jsp").forward(request, response);
        } else if (path.equals("/login")){
            String username = request.getParameter("username");
            String psw = request.getParameter("psw");
            UserDAOImpl udao = new UserDAOImpl();
            User user = udao.findByName(username);
            if (user != null && user.getPassword().equals(ServletUtil.getMD5(psw))) {
                //�����½�ɹ� ��admin�󶨵�session��
                //Ŀ�����÷ǵ�½�Ĺ���Ա����ͨ��url��ֱַ�ӷ��ʵ�����ҳ��
                session.setAttribute("user", user);
                //��½�ɹ�
System.out.println("��½�ɹ�");
                response.sendRedirect("index.pre");
            } else {
                ServletUtil.forwardPage("error_msg", "�û������������", "login.jsp", request, response);
            }
        } else if (path.equals("/order")){
            //���ж�session���Ƿ���user   ��user��û�е�½  �����½ �õ���ǰuser
            User user = (User)session.getAttribute("user");
            if (user == null){
                response.sendRedirect("index.pre");
                return;
            }
            //��session��ȡ�����ﳵ
            Cart cart = (Cart)session.getAttribute("cart");
            IOrderDAO odao = new OrderDAOImpl();
            Salesorder so = new Salesorder();
            so.setCart(cart);
            so.setStatus(0);
            so.setUser(user);
            odao.add(so);
            //�������󶨵�session��
            session.setAttribute("salesorder",so);
            request.getRequestDispatcher("order.jsp").forward(request,response);
        } else if (path.equals("/pay")){
            //��ʱ������
           /* String test = request.getParameter("id");
            System.out.println(test);
            int soid = Integer.parseInt(request.getParameter("id"));
            OrderDAOImpl odao = new OrderDAOImpl();
            Salesorder salesorder = odao.findById(soid);
            salesorder.setStatus(1);
*/
        } else if (path.equals("/code")){
System.out.println("ִ��");
            String mail = request.getParameter("mail");
            PrintWriter out = response.getWriter();
            if(!mail.matches("^\\w+@(\\w+\\.)+\\w+$")){
                out.print("��������ȷ�������ʽ");
            }else{
                String code = CodeUtil.generateUniqueCode();
                session.setAttribute("precode",code);
                System.out.println("mail:"+mail +"code:"+code);
                new MailUtil(mail,code).run();
                out.print("�������ѷ�������,��ע�����");
                //request.getRequestDispatcher("register.jsp").forward(request,response);
            }
        } else if (path.equals("/register")){
            String username = request.getParameter("username");
            PrintWriter out = response.getWriter();
            if (username == null || username.equals("")){
                out.print("�������û���");
                return;
            }
            UserDAOImpl uImpl = new UserDAOImpl();
            User user = uImpl.findByName(username);
            String pwd = request.getParameter("pwd");
            String phone = request.getParameter("phone");
            String addr = request.getParameter("addr");
            String code = request.getParameter("code");
            String precode = (String) session.getAttribute("precode");
            if (user != null){
                out.print("�û����Ѵ���");
            }else {
                out.print("�û�������ʹ��");
                if (code.equals(precode)){
                    if (pwd != null && !pwd.equals("")){
                        uImpl.add(new User(username, pwd, phone, addr));
                        response.sendRedirect("login.jsp");
                    }
                }else {
                    request.setAttribute("err_msg","���������");

                    request.getRequestDispatcher("register.jsp").forward(request,response);
                }

            }


        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }


}
