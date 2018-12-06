package com.neuedu.shop.dao;

/**
 * 接口中的方法比较特殊
 * 1、要有添加根类别的方法
 * 2、要有添加子类别的方法
 * 3、findAll方法要生成树状列表重载
 * 4、删除方法也要实现递归删除
 */
import java.util.List;

import com.neuedu.shop.entity.Category;

public interface ICategoryDAO {
	// 查询操作
	List<Category> findAll();
	/**
	 * 添加根类别的方法
	 * @param name  类别名称
	 * @param descr 类别描述
	 */
	void add(String name, String descr);
	/**
	 * 添加子类别的方法
	 * @param name  类别名称	
	 * @param descr	类别描述	
	 * @param pid	自己的pid   对应的父类别的id
	 */
	void add(String name, String descr, int pid);
	// 根据id查找管理员
	Category findById(Integer id);
	// 更新
	void update(Category category);
	// 删除
	void delete(Integer id, Integer pid);
	
}
