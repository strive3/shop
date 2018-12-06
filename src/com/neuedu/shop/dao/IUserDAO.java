package com.neuedu.shop.dao;
/**
 * 对Admin操作对应的crud
 * @author 杜晓鹏
 *
 */

import java.util.List;

import com.neuedu.shop.entity.Paging;
import com.neuedu.shop.entity.User;

public interface IUserDAO {
	// 查询操作
	Paging<User> findByPage(int rownum, int pagesize);

	// 添加操作
	void add(User user);

	// 删除
	void delete(Integer id);

	// 更新
	void update(User user);

	// 根据id查找管理员
	User findById(Integer id);

	//根据name查找管理员
	User findByName(String name);
}
