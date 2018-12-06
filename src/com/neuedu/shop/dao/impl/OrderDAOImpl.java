package com.neuedu.shop.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.neuedu.shop.dao.IOrderDAO;
import com.neuedu.shop.entity.*;
import com.neuedu.shop.util.DBUtil;

public class OrderDAOImpl implements IOrderDAO{

	@Override
	public Paging<Salesorder> findByPage(int pageNumber , int pagesize) {
		List<Salesorder> salesorders = new ArrayList<>();
		OrderDAOImpl dao = new OrderDAOImpl();
		int count = dao.count();
		Paging<Salesorder> paging = new Paging<>(pageNumber,pagesize,count);

		Connection conn = null;
		PreparedStatement prep = null;
		PreparedStatement prep2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from t_salesorder " +
					"limit " + paging.getStartIndex() + "," + pagesize + ";";
			String sql2 = "SELECT * from t_salesitem WHERE orderid = ?";
			prep = DBUtil.getPreparedStatement(conn, sql);
			rs = DBUtil.getResultSet(prep);
            while (rs.next()) {
                int userid = rs.getInt("userid");
                UserDAOImpl userDAO = new UserDAOImpl();
                User user = userDAO.findById(userid);
                //拿到当前订单的id，通过id去订单项表里面查询
                int orderid = rs.getInt("id");
//System.out.println("orderid:"+orderid);
                prep2 = DBUtil.getPreparedStatement(conn, sql2);
				prep2.setInt(1,orderid);
				rs2 = DBUtil.getResultSet(prep2);
                Cart cart = new Cart();
                while (rs2.next()){
                    //通过产品id 找到产品
                    int productid = rs2.getInt("productid");
                    ProductDAOImpl pdao = new ProductDAOImpl();
                    Product product = pdao.findById(productid);
                    int pcount = rs2.getInt("pcount");
                    CartItem cartItem = new CartItem();
                    cartItem.setProduct(product);
                    cartItem.setQty(pcount);

                    cart.add(cartItem);
                }
                Salesorder salesorder = new Salesorder();
                salesorder.setId(orderid);
                salesorder.setUser(user);
                salesorder.setOdate(rs.getTimestamp("odate"));
                salesorder.setCart(cart);
                salesorder.setStatus(rs.getInt("status"));
                salesorders.add(salesorder);
            }
            paging.setObjects(salesorders);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, prep, conn);
			DBUtil.close(prep2);
			DBUtil.close(rs2);
		}
		return paging;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(Salesorder so) {
		Connection conn = DBUtil.getConnection();
		PreparedStatement prep1 = null;
		PreparedStatement prep2 = null;
		String sqlOrder = "insert into t_salesorder values(null,?,?,now(),?)";
		
		String sqlItem = "insert into t_salesitem values(null,?,?,?,?)";
		try {
			conn.setAutoCommit(false);
			//1.向t_salesorder添加数据
			prep1 = DBUtil.getPreparedStatement(conn, sqlOrder, true);
			prep1.setInt(1, so.getUser().getId());
			prep1.setString(2, so.getUser().getAddr());
			prep1.setInt(3, so.getStatus());
			prep1.executeUpdate();
			//获取到以上语句生成的主键结果集（联合主键）
			ResultSet rSet = prep1.getGeneratedKeys();
			rSet.next();
			int key = rSet.getInt(1);
			
			//向t_salesitem添加数据
			prep2 = DBUtil.getPreparedStatement(conn, sqlItem);
			List<CartItem> items = so.getCart().getcItems();
			for(int i =0; i < items.size(); i++) {
				CartItem cartItem = items.get(i);
				prep2.setInt(1, cartItem.getProduct().getId());
				prep2.setDouble(2, cartItem.getProduct().getNormalprice());
				prep2.setInt(3, cartItem.getQty());
				prep2.setInt(4, key);
				//由于有迭代存在，使用批处理来解决
				prep2.addBatch();
			}
			prep2.executeBatch();
			
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			DBUtil.close(new PreparedStatement[] {prep2,prep1}, conn);
		}
		
	}

	@Override
	public Salesorder findById(Integer id) {
		Connection conn = null;
		PreparedStatement prep1 = null;
		PreparedStatement prep2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		Salesorder salesorder = null;
		try {
			conn = DBUtil.getConnection();
			String sql1 = "select * FROM t_salesorder WHERE id = " + id;
			String sql2 = "SELECT * from t_salesitem WHERE orderid = ?";
			prep1 = DBUtil.getPreparedStatement(conn, sql1);
			rs1 = DBUtil.getResultSet(prep1);
			if (rs1.next()) {
				salesorder = new Salesorder();
				//user
				int userid = rs1.getInt("userid");
				User user = new UserDAOImpl().findById(userid);
				//cart
				prep2 = DBUtil.getPreparedStatement(conn,sql2);
				prep2.setInt(1,id);
				rs2 = DBUtil.getResultSet(prep2);
				Cart cart = new Cart();
				while (rs2.next()){
					//通过产品id 找到产品
					int productid = rs2.getInt("productid");
					ProductDAOImpl pdao = new ProductDAOImpl();
					Product product = pdao.findById(productid);
					int pcount = rs2.getInt("pcount");
					CartItem cartItem = new CartItem();
					cartItem.setProduct(product);
					cartItem.setQty(pcount);

					cart.add(cartItem);
				}
				salesorder.setId(id);
				salesorder.setUser(user);
				salesorder.setOdate(rs1.getTimestamp("odate"));
				salesorder.setCart(cart);
				salesorder.setStatus(rs1.getInt("status"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs1, prep1, conn);
			DBUtil.close(rs2);
			DBUtil.close(prep2);
		}
		return salesorder;
	}

	@Override
	public int count() {
		Connection conn = null;
		PreparedStatement prep = null;
		ResultSet rs = null;
		String sql = "select count(*) from t_salesorder ";
		int count = 0;
		try {
			conn = DBUtil.getConnection();
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

	@Override
	public void update(Salesorder salesorder) {
		Connection conn = null;
		PreparedStatement prep = null;
		conn = DBUtil.getConnection();
		String sql = "update t_salesorder set status=? where id=?";
		prep = DBUtil.getPreparedStatement(conn, sql);
		try {
			prep.setInt(1,salesorder.getStatus());
			prep.setInt(2, salesorder.getId());
			prep.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(prep, conn);
		}
	}

}
