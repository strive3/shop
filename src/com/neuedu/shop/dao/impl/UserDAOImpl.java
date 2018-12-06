package com.neuedu.shop.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.neuedu.shop.dao.IUserDAO;
import com.neuedu.shop.entity.Paging;
import com.neuedu.shop.entity.User;
import com.neuedu.shop.util.DBUtil;

public class UserDAOImpl implements IUserDAO{
	/**
	 * 查找
	 */
	@Override
	public Paging<User> findByPage(int pageNumber, int pagesize) {
		UserDAOImpl dao = new UserDAOImpl();
		int count = dao.count();

		Paging<User> userPaging = new Paging<>(pageNumber,pagesize,count);
		List<User> users = new ArrayList<>();

		Connection conn = null;
		PreparedStatement prep = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from t_user " +
					"limit " + userPaging.getStartIndex() + "," + pagesize + ";";
			prep = DBUtil.getPreparedStatement(conn, sql);
			rs = DBUtil.getResultSet(prep);
			while (rs.next()) {
				users.add(new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"),rs.getString("phone"), rs.getString("addr"), rs.getTimestamp("rdate")));
			}
			userPaging.setObjects(users);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, prep, conn);
		}
		return userPaging;
	}
	/**
	 * 添加
	 */
	@Override
	public void add(User user) {
		Connection conn = null;
		PreparedStatement prep = null;
		conn = DBUtil.getConnection();
		String sql = "insert into t_user values (null,?,MD5(?),?,?,now())";
		prep = DBUtil.getPreparedStatement(conn, sql);
		try {
			prep.setString(1, user.getUsername());
			prep.setString(2, user.getPassword());
			prep.setString(3, user.getPhone());
			prep.setString(4, user.getAddr());
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
		String sql = "delete from t_user where id=?";
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
	public void update(User user) {
		Connection conn = null;
		PreparedStatement prep = null;
		conn = DBUtil.getConnection();
		String sql = "update t_user set password=MD5(?),phone=?,addr=? where id=?";
		prep = DBUtil.getPreparedStatement(conn, sql);
		try {
			prep.setString(1, user.getPassword());
			prep.setString(2, user.getPhone());
			prep.setString(3, user.getAddr());
			prep.setInt(4, user.getId());
			prep.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(prep, conn);
		}
	}
	
	@Override
	public User findById(Integer id) {
		Connection conn = null;
		PreparedStatement prep = null;
		ResultSet rs = null;
		User user = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from t_user where id=?";
			prep = DBUtil.getPreparedStatement(conn, sql);
			prep.setInt(1, id);
			rs = DBUtil.getResultSet(prep);
			if (rs.next()) {
				user = new User(id, rs.getString("username"), rs.getString("password"),rs.getString("phone"),rs.getString("addr"),rs.getTimestamp("rDate"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, prep, conn);
		}
		return user;
	}

	/**
	 * 根据名字查找用户
	 * @param name
	 * @return
	 */
	@Override
	public User findByName(String name) {
		Connection conn = null;
		PreparedStatement prep = null;
		ResultSet rs = null;
		User user = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from t_user where username=?";
			prep = DBUtil.getPreparedStatement(conn, sql);
			prep.setString(1, name);
			rs = DBUtil.getResultSet(prep);
			if (rs.next()) {
				user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"),rs.getString("phone"),rs.getString("addr"),rs.getTimestamp("rDate"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, prep, conn);
		}
		return user;
	}

	/**
	 * 用来计算一共有多少个用户
	 * @return
	 */
	public int count(){
		Connection conn = null;
		PreparedStatement prep = null;
		ResultSet rs = null;
		int count = 0;
		try {
			conn = DBUtil.getConnection();
			String sql = "select count(*) from t_user";
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
