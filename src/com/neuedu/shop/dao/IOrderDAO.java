package com.neuedu.shop.dao;

import java.util.List;

import com.neuedu.shop.entity.Paging;
import com.neuedu.shop.entity.Salesorder;

public interface IOrderDAO {
	Paging<Salesorder> findByPage(int rownum , int pagesize);
	
	void delete(Integer id);
	
	void add(Salesorder so);
	
	Salesorder findById(Integer id);

	int count();

	void update(Salesorder salesorder);
}
