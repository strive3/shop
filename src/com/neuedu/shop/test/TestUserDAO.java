package com.neuedu.shop.test;

import java.util.List;

import com.neuedu.shop.dao.impl.UserDAOImpl;
import com.neuedu.shop.entity.Paging;
import com.neuedu.shop.entity.User;

public class TestUserDAO {
	public static void main(String[] args) {
		UserDAOImpl userDAO = new UserDAOImpl();
		Paging<User> users = userDAO.findByPage(1,10);
		for (User user : users.getObjects()) {
			System.out.println(user);
		}
	}
}
