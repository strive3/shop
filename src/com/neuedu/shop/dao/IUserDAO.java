package com.neuedu.shop.dao;
/**
 * ��Admin������Ӧ��crud
 * @author ������
 *
 */

import java.util.List;

import com.neuedu.shop.entity.Paging;
import com.neuedu.shop.entity.User;

public interface IUserDAO {
	// ��ѯ����
	Paging<User> findByPage(int rownum, int pagesize);

	// ��Ӳ���
	void add(User user);

	// ɾ��
	void delete(Integer id);

	// ����
	void update(User user);

	// ����id���ҹ���Ա
	User findById(Integer id);

	//����name���ҹ���Ա
	User findByName(String name);
}
