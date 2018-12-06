package com.neuedu.shop.entity;

/**
 * 购物车项 类
 * 
 * @author 杜晓鹏
 *
 */

public class CartItem {
	private Product product;
	private int qty;
	

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	@Override
	public String toString() {
		return "CartItem [product=" + product + ", qty=" + qty + "]";
	}

	/**
	 * 计算该项购物车的小计
	 */
	public double getTotals() {
		return Math.round(this.product.getNormalprice() * this.qty * 100.00) / 100.00;
	}
}
