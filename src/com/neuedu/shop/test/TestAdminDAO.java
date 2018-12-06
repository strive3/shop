package com.neuedu.shop.test;

import java.util.List;

import com.neuedu.shop.dao.impl.AdminDAOImpl;
import com.neuedu.shop.entity.Admin;
import com.neuedu.shop.util.ServletUtil;

public class TestAdminDAO {
	public static void main(String[] args) {
		/*AdminDAOImpl daoImpl = new AdminDAOImpl();
		List<Admin> admins = daoImpl.findAll();
		
		for (Admin admin : admins) {
			System.out.println(admin);
		}*/
		
		System.out.println(ServletUtil.getMD5("111111"));
	}
}
