package com.neuedu.shop.dao;

/**
 * �ӿ��еķ����Ƚ�����
 * 1��Ҫ����Ӹ����ķ���
 * 2��Ҫ����������ķ���
 * 3��findAll����Ҫ������״�б�����
 * 4��ɾ������ҲҪʵ�ֵݹ�ɾ��
 */
import java.util.List;

import com.neuedu.shop.entity.Category;

public interface ICategoryDAO {
	// ��ѯ����
	List<Category> findAll();
	/**
	 * ��Ӹ����ķ���
	 * @param name  �������
	 * @param descr �������
	 */
	void add(String name, String descr);
	/**
	 * ��������ķ���
	 * @param name  �������	
	 * @param descr	�������	
	 * @param pid	�Լ���pid   ��Ӧ�ĸ�����id
	 */
	void add(String name, String descr, int pid);
	// ����id���ҹ���Ա
	Category findById(Integer id);
	// ����
	void update(Category category);
	// ɾ��
	void delete(Integer id, Integer pid);
	
}
