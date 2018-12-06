package com.neuedu.shop.dao;
/**
 * 对Admin操作对应的crud
 * @author 杜晓鹏
 *
 */

import java.util.List;

import com.neuedu.shop.entity.Paging;
import com.neuedu.shop.entity.Product;

public interface IProductDAO {
	/**
	 * 查询操作
	 * 
	 * @return products
	 */
	List<Product> findAll();

	/**
	 * 添加商品
	 * 
	 * @param product
	 */
	void add(Product product);

	// 删除
	void delete(Integer id);

	// 更新
	void update(Product product);
	/**
	 * 通过id找到商品
	 * @param id
	 * @return
	 */
	Product findById(Integer id);
	/**
	 * 简单搜索 按照关键字搜索
	 */
	Paging<Product> findByKeywords(String keywords, int rownum, int pagesize);

	/**
	 * 复杂查询
	 */
	Paging<Product> complexSearch(int categoryid, String keywords, int lownormalprice, int highnormalprice,
                                int lowmemberprice, int highmemberprice, String startdate, String enddate ,int rownum, int pagesize);
	/**
	 * 查询一共多少条数据
	 */
	int count(String sql);
}
