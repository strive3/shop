package com.neuedu.shop.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.neuedu.shop.dao.IAdminDao;
import com.neuedu.shop.entity.Admin;
import com.neuedu.shop.entity.Paging;
import com.neuedu.shop.util.DBUtil;

public class AdminDAOImpl implements IAdminDao{
	/**
	 * 查找
	 */
	@Override
	public Paging<Admin> findByPage(int pageNumber, int pagesize) {
		AdminDAOImpl dao = new AdminDAOImpl();
		int count = dao.count();
		Paging<Admin> adminPaging = new Paging<>(pageNumber,pagesize,count);
		List<Admin> admins = new ArrayList<>();

		Connection conn = null;
		PreparedStatement prep = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from t_admin " +
					"limit " + adminPaging.getStartIndex() + "," + pagesize + ";";
			prep = DBUtil.getPreparedStatement(conn, sql);
			rs = DBUtil.getResultSet(prep);
			while (rs.next()) {
				admins.add(new Admin(rs.getInt("id"), rs.getString("aname"), rs.getString("apwd")));
			}
			adminPaging.setObjects(admins);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, prep, conn);
		}
		return adminPaging;
	}
	/**
	 * 添加
	 */
	@Override
	public void add(Admin admin) {
		Connection conn = null;
		PreparedStatement prep = null;
		conn = DBUtil.getConnection();
		String sql = "insert into t_admin values (null,?,MD5(?))";
		prep = DBUtil.getPreparedStatement(conn, sql);
		try {
			prep.setString(1, admin.getAname());
			prep.setString(2, admin.getSpwd());
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
		String sql = "delete from t_admin where id=?";
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
	public void update(Admin admin) {
		Connection conn = null;
		PreparedStatement prep = null;
		conn = DBUtil.getConnection();
		String sql = "update t_admin set apwd=MD5(?) where id=?";
		prep = DBUtil.getPreparedStatement(conn, sql);
		try {
			prep.setString(1, admin.getSpwd());
			prep.setInt(2, admin.getId());
			prep.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(prep, conn);
		}
	}
	
	@Override
	public Admin findById(Integer id) {
		Connection conn = null;
		PreparedStatement prep = null;
		ResultSet rs = null;
		Admin admin = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from t_admin where id=?";
			prep = DBUtil.getPreparedStatement(conn, sql);
			prep.setInt(1, id);
			rs = DBUtil.getResultSet(prep);
			if (rs.next()) {
				admin = new Admin(id, rs.getString("aname"), rs.getString("apwd"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, prep, conn);
		}
		return admin;
	}
	
	/**
	 * 通过名字进行查询
	 */
	@Override
	public Admin findByName(String name) {
		Connection conn = null;
		PreparedStatement prep = null;
		ResultSet rs = null;
		Admin admin = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from t_admin where aname=?";
			prep = DBUtil.getPreparedStatement(conn, sql);
			prep.setString(1, name);
			rs = DBUtil.getResultSet(prep);
			if (rs.next()) {
				admin = new Admin(rs.getInt("id"), rs.getString("aname"), rs.getString("apwd"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, prep, conn);
		}
		return admin;
	}
	/**
	 * 用来计算一共有多少个管理员
	 * @return
	 */
	public int count(){
		Connection conn = null;
		PreparedStatement prep = null;
		ResultSet rs = null;
		int count = 0;
		try {
			conn = DBUtil.getConnection();
			String sql = "select count(*) from t_admin";
			prep = DBUtil.getPreparedStatement(conn, sql);
			rs = DBUtil.getResultSet(prep);
			if (rs.next()) {
				count = rs.getInt("count(*)");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, prep, conn);
		}
		return count;

	}

}
