package com.neuedu.shop.test;

import java.util.List;

import com.neuedu.shop.dao.impl.ProductDAOImpl;
import com.neuedu.shop.entity.Product;

public class TestProductDAO {
	public static void main(String[] args) {
		ProductDAOImpl dao = new ProductDAOImpl();
		List<Product> products = dao.findAll();
		
		/*for (Product product : products) {
			System.out.println(product);
		}*/
		Product product = dao.findById(100);
		System.out.println(product);
		System.out.println("----------------------------------");
		product.setNormalprice(20000.0);
		dao.update(product);
		product = dao.findById(100);
		System.out.println(product);

		System.out.println(dao.countAll());
	}
}
