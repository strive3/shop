package com.neuedu.shop.test;

import com.neuedu.shop.dao.IOrderDAO;
import com.neuedu.shop.dao.impl.OrderDAOImpl;
import com.neuedu.shop.dao.impl.ProductDAOImpl;
import com.neuedu.shop.dao.impl.UserDAOImpl;
import com.neuedu.shop.entity.Cart;
import com.neuedu.shop.entity.CartItem;
import com.neuedu.shop.entity.Product;
import com.neuedu.shop.entity.Salesorder;
import com.neuedu.shop.entity.User;

public class TestOrderDAO {
	public static IOrderDAO odao = new OrderDAOImpl();
	public static void main(String[] args) {

        //System.out.println(odao.findByPage(1,10));
        Salesorder salesorder = odao.findById(2);
        System.out.println(salesorder);
        salesorder.setStatus(1);
        odao.update(salesorder);
        System.out.println(salesorder);
    }
	public static void testorder(){
        Salesorder so = new Salesorder();

        UserDAOImpl udao = new UserDAOImpl();
        User user = udao.findById(10);

        //向购物车中添加购物项
        Cart cart = new Cart();
        ProductDAOImpl pdao = new ProductDAOImpl();
        Product p1 = pdao.findById(10);//买哪件商品
        Product p2 = pdao.findById(3);
        Product p3 = pdao.findById(4);
        //将这3个商品添加到购物项当中
        CartItem ci1 = new CartItem();
        ci1.setProduct(p1);
        ci1.setQty(10);
        CartItem ci2 = new CartItem();
        ci2.setProduct(p2);
        ci2.setQty(30);
        CartItem ci3 = new CartItem();
        ci3.setProduct(p3);
        ci3.setQty(10);
        //将这3个购物项添加到购物车当中
        cart.add(ci1);
        cart.add(ci2);
        cart.add(ci3);

        so.setUser(user);
        so.setCart(cart);
        so.setStatus(1);  //0认为是未发货
        odao.add(so);
    }
}
