package com.neuedu.shop.dao;
/**
 * 对Admin操作对应的crud
 * @author 杜晓鹏
 *
 */

import java.util.List;

import com.neuedu.shop.entity.Salesitem;


public interface ISalesitemDao {
	// 查询操作
	List<Salesitem> findAll();

	// 添加操作
	void add(Salesitem salesitem);

	// 删除
	void delete(Integer id);

	// 更新
	void update(Salesitem salesitem);

	// 根据id查找管理员
	Salesitem findById(Integer id);
}
