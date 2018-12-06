package com.neuedu.shop.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.neuedu.shop.dao.ISalesitemDao;
import com.neuedu.shop.entity.Salesitem;
import com.neuedu.shop.util.DBUtil;

public class SalesitemDAOImpl implements ISalesitemDao{
	/**
	 * 查找
	 */
	@Override
	public List<Salesitem> findAll() {
		List<Salesitem> salesitems = new ArrayList<>();
		Connection conn = null;
		PreparedStatement prep = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from t_salesitem";
			prep = DBUtil.getPreparedStatement(conn, sql);
			rs = DBUtil.getResultSet(prep);
			while (rs.next()) {
				salesitems.add(new Salesitem(rs.getInt("id"), rs.getInt("productid"), rs.getDouble("unitprice"), rs.getInt("pcount"), rs.getInt("orderid")));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, prep, conn);
		}
		return salesitems;
	}
	/**
	 * 添加
	 */
	@Override
	public void add(Salesitem salesitem) {
		Connection conn = null;
		PreparedStatement prep = null;
		conn = DBUtil.getConnection();
		String sql = "insert into t_salesitem values (null,?,?,?,?)";
		prep = DBUtil.getPreparedStatement(conn, sql);
		try {
			prep.setInt(1, salesitem.getProductid());
			prep.setDouble(2, salesitem.getUnitprice());
			prep.setInt(3, salesitem.getPcount());
			prep.setInt(4, salesitem.getOrderid());
			prep.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(prep, conn);
		}
	}
	/**
	 * 删除
	 */
	@Override
	public void delete(Integer id) {
		Connection conn = null;
		PreparedStatement prep = null;
		String sql = "delete from t_salesitem where id=?";
		try {
			conn = DBUtil.getConnection();
			prep = DBUtil.getPreparedStatement(conn, sql);
			prep.setInt(1, id);
			prep.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(prep, conn);
		}
	}
	/**
	 * 更新
	 */
	@Override
	public void update(Salesitem salesitem) {
		Connection conn = null;
		PreparedStatement prep = null;
		conn = DBUtil.getConnection();
		String sql = "update t_product set productid=?,unitprice=?,pcount=?,orderid=? where id=?";
		prep = DBUtil.getPreparedStatement(conn, sql);
		try {
			prep.setInt(1, salesitem.getProductid());
			prep.setDouble(2, salesitem.getUnitprice());
			prep.setInt(3, salesitem.getPcount());
			prep.setInt(4, salesitem.getOrderid());
			prep.setInt(5, salesitem.getId());
			prep.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(prep, conn);
		}
	}
	
	@Override
	public Salesitem findById(Integer id) {
		Connection conn = null;
		PreparedStatement prep = null;
		ResultSet rs = null;
		Salesitem salesitem = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from t_salesitem where id=?";
			prep = DBUtil.getPreparedStatement(conn, sql);
			prep.setInt(1, id);
			rs = DBUtil.getResultSet(prep);
			if (rs.next()) {
				salesitem = new Salesitem(rs.getInt("id"), rs.getInt("productid"), rs.getDouble("unitprice"), rs.getInt("pcount"), rs.getInt("orderid"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, prep, conn);
		}
		return salesitem;
	}

}
