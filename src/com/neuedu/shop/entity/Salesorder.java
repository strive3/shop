package com.neuedu.shop.entity;

import java.sql.Timestamp;

public class Salesorder {
	private Integer id; 	//主键
	private User user;		
	private Timestamp odate;	//下单时间
	private Integer status;		//0：未付款  1：已付款
	private Cart cart;

	public Salesorder() {
		super();
	}

	public Salesorder(Integer id, User user, Timestamp odate, Integer status, Cart cart) {
		super();
		this.id = id;
		this.user = user;
		this.odate = odate;
		this.status = status;
		this.cart = cart;
	}

	public Salesorder(User user, Integer status, Cart cart) {
		super();
		this.user = user;
		this.status = status;
		this.cart = cart;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Timestamp getOdate() {
		return odate;
	}

	public void setOdate(Timestamp odate) {
		this.odate = odate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cart == null) ? 0 : cart.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((odate == null) ? 0 : odate.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Salesorder other = (Salesorder) obj;
		if (cart == null) {
			if (other.cart != null)
				return false;
		} else if (!cart.equals(other.cart))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (odate == null) {
			if (other.odate != null)
				return false;
		} else if (!odate.equals(other.odate))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Salesorder [id=" + id + ", user=" + user + ", odate=" + odate + ", status=" + status + ", cart=" + cart
				+ "]";
	}
	
	
	
}
