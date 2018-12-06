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
            // 在页面还没有进行跳转时，拿到参数id 绑定到request上，再进行页面的跳转
            // 找到id对应的product
            // 再之后就可以从request中取得绑定的product
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
            //加载从session中取到的	cart对象      session可以直接拿
			/*HttpSession session = request.getSession();
			Cart cart = (Cart)session.getAttribute("cart");*/
            //转发到cart.jsp页面当中
            request.getRequestDispatcher("cart.jsp").forward(request, response);
        } else if (path.equals("/login")){
            String username = request.getParameter("username");
            String psw = request.getParameter("psw");
            UserDAOImpl udao = new UserDAOImpl();
            User user = udao.findByName(username);
            if (user != null && user.getPassword().equals(ServletUtil.getMD5(psw))) {
                //如果登陆成功 将admin绑定到session中
                //目的是让非登陆的管理员不能通过url地址直接访问到其他页面
                session.setAttribute("user", user);
                //登陆成功
System.out.println("登陆成功");
                response.sendRedirect("index.pre");
            } else {
                ServletUtil.forwardPage("error_msg", "用户名或密码错误", "login.jsp", request, response);
            }
        } else if (path.equals("/order")){
            //先判断session中是否有user   即user有没有登陆  如果登陆 拿到当前user
            User user = (User)session.getAttribute("user");
            if (user == null){
                response.sendRedirect("index.pre");
                return;
            }
            //从session中取出购物车
            Cart cart = (Cart)session.getAttribute("cart");
            IOrderDAO odao = new OrderDAOImpl();
            Salesorder so = new Salesorder();
            so.setCart(cart);
            so.setStatus(0);
            so.setUser(user);
            odao.add(so);
            //将订单绑定到session中
            session.setAttribute("salesorder",so);
            request.getRequestDispatcher("order.jsp").forward(request,response);
        } else if (path.equals("/pay")){
            //暂时不能用
           /* String test = request.getParameter("id");
            System.out.println(test);
            int soid = Integer.parseInt(request.getParameter("id"));
            OrderDAOImpl odao = new OrderDAOImpl();
            Salesorder salesorder = odao.findById(soid);
            salesorder.setStatus(1);
*/
        } else if (path.equals("/code")){
System.out.println("执行");
            String mail = request.getParameter("mail");
            PrintWriter out = response.getWriter();
            if(!mail.matches("^\\w+@(\\w+\\.)+\\w+$")){
                out.print("请输入正确的邮箱格式");
            }else{
                String code = CodeUtil.generateUniqueCode();
                session.setAttribute("precode",code);
                System.out.println("mail:"+mail +"code:"+code);
                new MailUtil(mail,code).run();
                out.print("激活码已发至邮箱,请注意查收");
                //request.getRequestDispatcher("register.jsp").forward(request,response);
            }
        } else if (path.equals("/register")){
            String username = request.getParameter("username");
            PrintWriter out = response.getWriter();
            if (username == null || username.equals("")){
                out.print("请输入用户名");
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
                out.print("用户名已存在");
            }else {
                out.print("用户名可以使用");
                if (code.equals(precode)){
                    if (pwd != null && !pwd.equals("")){
                        uImpl.add(new User(username, pwd, phone, addr));
                        response.sendRedirect("login.jsp");
                    }
                }else {
                    request.setAttribute("err_msg","激活码错误");

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
