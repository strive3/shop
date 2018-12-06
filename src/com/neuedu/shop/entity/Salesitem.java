package com.neuedu.shop.entity;

public class Salesitem {
	private Integer id;
	private Integer productid;
	private Double unitprice;
	private Integer pcount;
	private Integer orderid;
	
	
	public Salesitem() {
		super();
	}
	public Salesitem(Integer productid, Double unitprice, Integer pcount, Integer orderid) {
		super();
		this.productid = productid;
		this.unitprice = unitprice;
		this.pcount = pcount;
		this.orderid = orderid;
	}
	public Salesitem(Integer id, Integer productid, Double unitprice, Integer pcount, Integer orderid) {
		this(productid, unitprice, pcount, orderid);
		this.id = id;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getProductid() {
		return productid;
	}
	public void setProductid(Integer productid) {
		this.productid = productid;
	}
	public Double getUnitprice() {
		return unitprice;
	}
	public void setUnitprice(Double unitprice) {
		this.unitprice = unitprice;
	}
	public Integer getPcount() {
		return pcount;
	}
	public void setPcount(Integer pcount) {
		this.pcount = pcount;
	}
	public Integer getOrderid() {
		return orderid;
	}
	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((orderid == null) ? 0 : orderid.hashCode());
		result = prime * result + ((pcount == null) ? 0 : pcount.hashCode());
		result = prime * result + ((productid == null) ? 0 : productid.hashCode());
		result = prime * result + ((unitprice == null) ? 0 : unitprice.hashCode());
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
		Salesitem other = (Salesitem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (orderid == null) {
			if (other.orderid != null)
				return false;
		} else if (!orderid.equals(other.orderid))
			return false;
		if (pcount == null) {
			if (other.pcount != null)
				return false;
		} else if (!pcount.equals(other.pcount))
			return false;
		if (productid == null) {
			if (other.productid != null)
				return false;
		} else if (!productid.equals(other.productid))
			return false;
		if (unitprice == null) {
			if (other.unitprice != null)
				return false;
		} else if (!unitprice.equals(other.unitprice))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Salesitem [id=" + id + ", productid=" + productid + ", unitprice=" + unitprice + ", pcount=" + pcount
				+ ", orderid=" + orderid + "]";
	}
	
	
}
