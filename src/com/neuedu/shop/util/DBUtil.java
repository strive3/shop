package com.neuedu.shop.util;
/**
 * ��װ���������ݿ���ط���
 * @author ������
 *
 */

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.jdbc.Statement;

public class DBUtil {
	//���������ļ�   ͨ���������������
	public static Properties prop = new Properties();

	static {
		try {
			prop.load(DBUtil.class.getClassLoader().getResourceAsStream("DB.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static final String DRIVER_NAME = prop.getProperty("dirver_name");
	public static final String PROTOCAL = prop.getProperty("protocal");
	public static final String HOST_IP = prop.getProperty("host_ip");
	public static final String PORT = prop.getProperty("port");
	public static final String DB_NAME = prop.getProperty("db_name");
	public static final String useSSL = prop.getProperty("useSSL");
	public static final String DB_USERNAME = prop.getProperty("db_username");
	public static final String DB_PASSWORD = prop.getProperty("db_password");

	/**
	 * ��ȡ���ӵķ���
	 * 
	 * @return
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(PROTOCAL + "://" + HOST_IP + ":" + PORT + "/" + DB_NAME + "?" + useSSL,
					DB_USERNAME, DB_PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * ��ȡsql���
	 * 
	 * @param conn
	 * @return
	 */
	public static PreparedStatement getPreparedStatement(Connection conn, String sql) {
		PreparedStatement prep = null;
		try {
			prep = conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return prep;
	}
	
	/**
	 * ��ȡsql���  (���Ի�ȡ������ֵ )
	 * 
	 * @param conn
	 * @return
	 */
	public static PreparedStatement getPreparedStatement(Connection conn, String sql, boolean getPK) {
		PreparedStatement prep = null;
		try {
			prep = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return prep;
	}

	/**
	 * ��ȡ���
	 * 
	 * @param prep
	 * @return
	 */
	public static ResultSet getResultSet(PreparedStatement prep) {
		ResultSet rs = null;
		try {
			rs = prep.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * �ر����ķ���
	 * 
	 * @param rs
	 */
	public static void close(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			rs = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close(PreparedStatement prep) {
		try {
			if (prep != null) {
				prep.close();
			}
			prep = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close(ResultSet rs, PreparedStatement prep, Connection conn) {
		close(rs);
		close(prep, conn);
	}

	public static void close(PreparedStatement prep, Connection conn) {
		close(prep);
		close(conn);
	}
	
	public static void close(PreparedStatement[] preps, Connection conn) {
		for(int i = 0; i < preps.length; i++) {
			close(preps[i]);
		}
		close(conn);
	}
	

}
