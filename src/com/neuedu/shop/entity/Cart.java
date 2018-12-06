package com.neuedu.shop.entity;

import java.util.ArrayList;
import java.util.List;
/**
 * 购物车 类
 * @author 杜晓鹏
 *
 */
public class Cart {
	private List<CartItem> cItems = new ArrayList<>();

	public List<CartItem> getcItems() {
		return cItems;
	}

	public void setcItems(List<CartItem> cItems) {
		this.cItems = cItems;
	}
	
	/**
	 * 往购物车中添加购物车项的add方法
	 */
	public void add(CartItem cartItem) {
		cItems.add(cartItem);
	}
	
	/**
	 * 计算总价
	 */
	public double getTotals() {
		double total = 0;
		for(int i = 0; i < cItems.size(); i++) {
			CartItem citem = cItems.get(i);
			total += citem.getTotals();
		}
		return total;
	}

	@Override
	public String toString() {
		return "Cart [cItems=" + cItems + "]";
	}
	
	
}
