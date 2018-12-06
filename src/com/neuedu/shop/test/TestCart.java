package com.neuedu.shop.test;

import java.util.List;

import com.neuedu.shop.dao.impl.ProductDAOImpl;
import com.neuedu.shop.entity.Cart;
import com.neuedu.shop.entity.CartItem;

/**
 * ≤‚ ‘π∫ŒÔ≥µ
 * @author ∂≈œ˛≈Ù
 *
 */
public class TestCart {
	public static void main(String[] args) {
		Cart cart = new Cart();
		List<CartItem> cItems = cart.getcItems();
		
		CartItem ci = new CartItem();
		ci.setProduct(new ProductDAOImpl().findById(1000));
		ci.setQty(8);
		cItems.add(ci);
		
System.out.println(cItems);
		System.out.println(cart.getTotals());
	}
}
