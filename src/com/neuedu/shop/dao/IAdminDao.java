package com.neuedu.shop.dao;
/**
 * ��Admin������Ӧ��crud
 * @author ������
 *
 */

import java.util.List;

import com.neuedu.shop.entity.Admin;
import com.neuedu.shop.entity.Paging;

public interface IAdminDao {
	// ��ѯ����
	Paging<Admin> findByPage(int rownum, int pagesize);

	// ��Ӳ���
	void add(Admin admin);

	// ɾ��
	void delete(Integer id);

	// ����
	void update(Admin admin);

	// ����id���ҹ���Ա
	Admin findById(Integer id);
	
	/**
	 * ͨ�����ֽ��в�ѯ  ���ڵ�½ʱʹ��
	 */
	Admin findByName(String name);
}
