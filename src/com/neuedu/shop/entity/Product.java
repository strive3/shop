package com.neuedu.shop.entity;

import java.sql.Timestamp;

public class Product {
	private Integer id;
	private String name;
	private String descr;
	private double normalprice;
	private double memberprice;
	private Timestamp pDate;	//上架日期（禁止修改）
	private Category category;
	public Product() {
		super();
	}
	public Product(Integer id, String name, String descr, double normalprice, Double memberprice, Timestamp pDate,
			Category catagory) {
		super();
		this.id = id;
		this.name = name;
		this.descr = descr;
		this.normalprice = normalprice;
		this.memberprice = memberprice;
		this.pDate = pDate;
		this.category = catagory;
	}
	public Product(String name, String descr, Double normalprice, 
			Category catagory) {
		super();
		this.name = name;
		this.descr = descr;
		this.normalprice = normalprice;
		this.memberprice = getMemberprice();
		this.category = catagory;
	}
	public Product(String name, String descr, Double normalprice, Double memberprice,
			Category catagory) {
		super();
		this.name = name;
		this.descr = descr;
		this.normalprice = normalprice;
		this.memberprice = getMemberprice();
		this.category = catagory;
	}
	
	public Product(String name, String descr, double normalprice, Timestamp pDate,
			Category catagory) {
		super();
		this.name = name;
		this.descr = descr;
		this.normalprice = normalprice;
		this.memberprice = getMemberprice();
		this.pDate = pDate;
		this.category = catagory;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public Double getNormalprice() {
		return Math.round(normalprice * 100.00) / 100.00;
	}
	public void setNormalprice(Double normalprice) {
		this.normalprice = normalprice;
	}
	
	public void setMemberprice(double memberprice) {
		this.memberprice = memberprice;
	}
	/**
	 * 由业务决定  会员价格为普通价格的八折
	 * @return 会员价格
	 */
	public Double getMemberprice() {
		return Math.round(normalprice * 100.00 * 0.8) / 100.00;
	}
	public Timestamp getpDate() {
		return pDate;
	}
	public void setpDate(Timestamp pDate) {
		this.pDate = pDate;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category catagory) {
		this.category = catagory;
	}
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", descr=" + descr + ", normalprice=" + normalprice
				+ ", memberprice=" + memberprice + ", pDate=" + pDate + ", catagory=" + category + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((descr == null) ? 0 : descr.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		long temp;
		temp = Double.doubleToLongBits(memberprice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		temp = Double.doubleToLongBits(normalprice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((pDate == null) ? 0 : pDate.hashCode());
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
		Product other = (Product) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (descr == null) {
			if (other.descr != null)
				return false;
		} else if (!descr.equals(other.descr))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Double.doubleToLongBits(memberprice) != Double.doubleToLongBits(other.memberprice))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(normalprice) != Double.doubleToLongBits(other.normalprice))
			return false;
		if (pDate == null) {
			if (other.pDate != null)
				return false;
		} else if (!pDate.equals(other.pDate))
			return false;
		return true;
	}
	
}
