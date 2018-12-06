package com.neuedu.shop.dao;
/**
 * 对Admin操作对应的crud
 * @author 杜晓鹏
 *
 */

import java.util.List;

import com.neuedu.shop.entity.Admin;
import com.neuedu.shop.entity.Paging;

public interface IAdminDao {
	// 查询操作
	Paging<Admin> findByPage(int rownum, int pagesize);

	// 添加操作
	void add(Admin admin);

	// 删除
	void delete(Integer id);

	// 更新
	void update(Admin admin);

	// 根据id查找管理员
	Admin findById(Integer id);
	
	/**
	 * 通过名字进行查询  ，在登陆时使用
	 */
	Admin findByName(String name);
}
