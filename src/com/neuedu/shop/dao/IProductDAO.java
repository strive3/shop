package com.neuedu.shop.dao;
/**
 * ��Admin������Ӧ��crud
 * @author ������
 *
 */

import java.util.List;

import com.neuedu.shop.entity.Paging;
import com.neuedu.shop.entity.Product;

public interface IProductDAO {
	/**
	 * ��ѯ����
	 * 
	 * @return products
	 */
	List<Product> findAll();

	/**
	 * �����Ʒ
	 * 
	 * @param product
	 */
	void add(Product product);

	// ɾ��
	void delete(Integer id);

	// ����
	void update(Product product);
	/**
	 * ͨ��id�ҵ���Ʒ
	 * @param id
	 * @return
	 */
	Product findById(Integer id);
	/**
	 * ������ ���չؼ�������
	 */
	Paging<Product> findByKeywords(String keywords, int rownum, int pagesize);

	/**
	 * ���Ӳ�ѯ
	 */
	Paging<Product> complexSearch(int categoryid, String keywords, int lownormalprice, int highnormalprice,
                                int lowmemberprice, int highmemberprice, String startdate, String enddate ,int rownum, int pagesize);
	/**
	 * ��ѯһ������������
	 */
	int count(String sql);
}
