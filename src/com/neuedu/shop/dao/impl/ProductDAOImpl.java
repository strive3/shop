package com.neuedu.shop.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.neuedu.shop.dao.IProductDAO;
import com.neuedu.shop.entity.Category;
import com.neuedu.shop.entity.Paging;
import com.neuedu.shop.entity.Product;
import com.neuedu.shop.entity.User;
import com.neuedu.shop.util.DBUtil;

public class ProductDAOImpl implements IProductDAO {
    /**
     * 查找
     */
    @Override
    public List<Product> findAll() {
        //表连接查询
        String sql = "select p.id,p.`name`,p.descr,p.normalprice,p.memberprice,p.pdate,p.categoryid,\r\n" +
                "c.id,c.`name`,c.descr,c.pid,c.leaf,c.grade\r\n" +
                "from t_product p join \r\n" +
                "t_category c on \r\n" +
                "p.categoryid = c.id;";
        List<Product> products = find(sql);
        return products;
    }

    /**
     * 添加  添加一个产品
     */
    @Override
    public void add(Product product) {
        Connection conn = DBUtil.getConnection();
        PreparedStatement prep = null;
        String sql = "insert into t_product values (null,?,?,?,?,now(),?)";
        try {
            prep = DBUtil.getPreparedStatement(conn, sql);
            prep.setString(1, product.getName());
            prep.setString(2, product.getDescr());
            prep.setDouble(3, product.getNormalprice());
            prep.setDouble(4, product.getMemberprice());
            prep.setInt(5, product.getCategory().getId());

            prep.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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
        String sql = "delete from t_product where id=?";
        try {
            conn = DBUtil.getConnection();
            prep = DBUtil.getPreparedStatement(conn, sql);
            prep.setInt(1, id);
            prep.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(prep, conn);
        }
    }

    /**
     * 更新
     */
    @Override
    public void update(Product product) {
        Connection conn = null;
        PreparedStatement prep = null;
        conn = DBUtil.getConnection();
        String sql = "update t_product set name=?,descr=?,normalprice=?,memberprice=?,pdate=?,categoryid=? where id=?";
        prep = DBUtil.getPreparedStatement(conn, sql);
        try {
            prep.setString(1, product.getName());
            prep.setString(2, product.getDescr());
            prep.setDouble(3, product.getNormalprice());
            prep.setDouble(4, product.getMemberprice());
            prep.setTimestamp(5, product.getpDate());
            prep.setInt(6, product.getCategory().getId());
            prep.setInt(7, product.getId());
            prep.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(prep, conn);
        }
    }

    /**
     * 通过 关键字  简单查找
     */
    @Override
    public Paging<Product> findByKeywords(String keywords , int pageNumber , int pagesize) {
        ProductDAOImpl dao = new ProductDAOImpl();
        int count = dao.countSimple(keywords);
        Paging<Product> paging = new Paging(pageNumber,pagesize,count);
        //表连接查询
        String sql = "select p.id,p.`name`,p.descr,p.normalprice,p.memberprice,p.pdate,p.categoryid,\r\n" +
                "c.id,c.`name`,c.descr,c.pid,c.leaf,c.grade\r\n" +
                "from t_product p join \r\n" +
                "t_category c on \r\n" +
                "p.categoryid = c.id\r\n" +
                "where p.name like '%" + keywords + "%'" + " or p.descr like '%" + keywords + "%'" +
                "limit " + paging.getStartIndex() + "," + pagesize + ";";
System.out.println("简单搜索sql语句:"+ sql);
        List<Product> products = find(sql);
        paging.setObjects(products);
        return paging;
    }

    @Override
    public Paging<Product> complexSearch(int categoryid, String keywords, int lownormalprice, int highnormalprice,
                                       int lowmemberprice, int highmemberprice, String startdate, String enddate ,int rownum, int pagesize) {

        int count = countComp(categoryid, keywords, lownormalprice, highnormalprice, lowmemberprice, highmemberprice, startdate, enddate);
        Paging<Product> paging = new Paging<>(rownum,pagesize,count);
        //表连接查询
        String sql = "select p.id,p.`name`,p.descr,p.normalprice,p.memberprice,p.pdate,p.categoryid,\r\n" +
                "c.id,c.`name`,c.descr,c.pid,c.leaf,c.grade\r\n" +
                "from t_product p join \r\n" +
                "t_category c on \r\n" +
                "p.categoryid = c.id\r\n" +
                "where 1=1";

        //业务逻辑   对sql 语句进行处理
        if (categoryid != -1) {
            sql += " and p.categoryid =" + categoryid;
        }
        //搜索关键字
        if (keywords != "" && keywords != null) {
            sql += " and p.name like '%" + keywords + "%'" + " or p.descr like '%" + keywords + "%'";
        }
        //正常价格
        if (lownormalprice != 0 && highnormalprice != 0) {
            sql += " and (p.normalprice between " + lownormalprice + " and " + highnormalprice + ")";
        }
        //会员价格
        if (lowmemberprice != 0 && highmemberprice != 0) {
            sql += " and (p.memberprice between " + lowmemberprice + " and " + highmemberprice + ")";
        }
        //日期
        if (startdate != "" && enddate != "" && startdate != null && enddate != null) {
            sql += " and (p.pdate between '" + startdate + "' and DATE_ADD('" + enddate + "',INTERVAL 1 DAY))";
        }
        sql += " limit " + paging.getStartIndex() + "," + paging.getPageSize() + ";";
System.out.println("复杂搜索sql语句:"+sql);

        List<Product> products = find(sql);
        paging.setObjects(products);
        return paging;
    }


    @Override
    public Product findById(Integer id) {
        Product p = new Product();
        Connection conn = null;
        PreparedStatement prep = null;
        ResultSet rs = null;
        //表连接查询
        String sql = "select p.id,p.`name`,p.descr,p.normalprice,p.memberprice,p.pdate,p.categoryid,\r\n" +
                "c.id,c.`name`,c.descr,c.pid,c.leaf,c.grade\r\n" +
                "from t_product p join \r\n" +
                "t_category c on \r\n" +
                "p.categoryid = c.id\r\n" +
                "where p.id =" + id;
        try {
            conn = DBUtil.getConnection();
            prep = DBUtil.getPreparedStatement(conn, sql);
            rs = DBUtil.getResultSet(prep);
            while (rs.next()) {
                p.setId(rs.getInt("p.id"));
                p.setName(rs.getString("p.name"));
                p.setDescr(rs.getString("p.descr"));
                p.setNormalprice(rs.getDouble("p.normalprice"));
                p.setMemberprice(rs.getDouble("p.memberprice"));
                p.setpDate(rs.getTimestamp("p.pdate"));


                Category c = new Category();
                c.setId(rs.getInt("c.id"));
                c.setName(rs.getString("c.name"));
                c.setDescr(rs.getString("c.descr"));
                c.setPid(rs.getInt("c.pid"));
                c.setLeaf(rs.getInt("c.leaf") == 1 ? true : false);
                c.setGrade(rs.getInt("c.grade"));
                p.setCategory(c);


            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, prep, conn);
        }
        return p;
    }

    /**
     * 传入一个sql语句   来进行查找
     *
     * @param sql
     * @return
     */
    private List<Product> find(String sql) {
        List<Product> products = new ArrayList<>();
        Connection conn = null;
        PreparedStatement prep = null;
        ResultSet rs = null;
        //表连接查询
        try {
            conn = DBUtil.getConnection();
            prep = DBUtil.getPreparedStatement(conn, sql);
            rs = DBUtil.getResultSet(prep);
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("p.id"));
                p.setName(rs.getString("p.name"));
                p.setDescr(rs.getString("p.descr"));
                p.setNormalprice(rs.getDouble("p.normalprice"));
                p.setMemberprice(rs.getDouble("p.memberprice"));
                p.setpDate(rs.getTimestamp("p.pdate"));


                Category c = new Category();
                c.setId(rs.getInt("c.id"));
                c.setName(rs.getString("c.name"));
                c.setDescr(rs.getString("c.descr"));
                c.setPid(rs.getInt("c.pid"));
                c.setLeaf(rs.getInt("c.leaf") == 1 ? true : false);
                c.setGrade(rs.getInt("c.grade"));
                p.setCategory(c);

                products.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, prep, conn);
        }
        return products;
    }

    /**
     * 查找的商品按照  最近上架的顺序进行排序
     *
     * @param rownum   偏移量
     * @param pagesize 每次显示的多少
     * @return
     */
    public Paging<Product> findLastByPaging(int rownum, int pagesize) {
        Paging<Product> paging  = new Paging<>(rownum,pagesize,countAll());
        String sql = "select p.id,p.`name`,p.descr,p.normalprice,p.memberprice,p.pdate,p.categoryid,\r\n" +
                "c.id,c.`name`,c.descr,c.pid,c.leaf,c.grade\r\n" +
                "from t_product p join \r\n" +
                "t_category c on \r\n" +
                "p.categoryid = c.id\r\n" +
                "order by p.pdate desc limit " + paging.getStartIndex() + "," + paging.getPageSize() + ";";
        List<Product> products = find(sql);
        paging.setObjects(products);
        return paging;
    }
    public List<Product> findLast(int rownum, int pagesize) {
        String sql = "select p.id,p.`name`,p.descr,p.normalprice,p.memberprice,p.pdate,p.categoryid,\r\n" +
                "c.id,c.`name`,c.descr,c.pid,c.leaf,c.grade\r\n" +
                "from t_product p join \r\n" +
                "t_category c on \r\n" +
                "p.categoryid = c.id\r\n" +
                "order by p.pdate desc limit " + (rownum - 1) * pagesize + "," + pagesize + ";";
        List<Product> products = find(sql);
        return products;
    }

    /**
     * 用来计算product有多少条记录
     * @return
     */
    public int count(String sql){
        Connection conn = null;
        PreparedStatement prep = null;
        ResultSet rs = null;
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
    public int countAll(){
        String sql = "select count(*) from t_product;";
        return count(sql);
    }
    public int countSimple(String keywords){
        String sql = "select count(*),p.id,p.`name`,p.descr,p.normalprice,p.memberprice,p.pdate,p.categoryid,\r\n" +
                "c.id,c.`name`,c.descr,c.pid,c.leaf,c.grade\r\n" +
                "from t_product p join \r\n" +
                "t_category c on \r\n" +
                "p.categoryid = c.id\r\n" +
                "where p.name like '%" + keywords + "%'" + " or p.descr like '%" + keywords + "%'" ;
        return count(sql);
    }
    public int countComp(int categoryid, String keywords, int lownormalprice, int highnormalprice,
                         int lowmemberprice, int highmemberprice, String startdate, String enddate){
        String sql = "select count(*),p.id,p.`name`,p.descr,p.normalprice,p.memberprice,p.pdate,p.categoryid,\r\n" +
                "c.id,c.`name`,c.descr,c.pid,c.leaf,c.grade\r\n" +
                "from t_product p join \r\n" +
                "t_category c on \r\n" +
                "p.categoryid = c.id\r\n" +
                "where 1=1";

        //业务逻辑   对sql 语句进行处理
        if (categoryid != -1) {
            sql += " and p.categoryid =" + categoryid;
        }
        //搜索关键字
        if (keywords != "" && keywords != null) {
            sql += " and p.name like '%" + keywords + "%'" + " or p.descr like '%" + keywords + "%'";
        }
        //正常价格
        if (lownormalprice != 0 && highnormalprice != 0) {
            sql += " and (p.normalprice between " + lownormalprice + " and " + highnormalprice + ")";
        }
        //会员价格
        if (lowmemberprice != 0 && highmemberprice != 0) {
            sql += " and (p.memberprice between " + lowmemberprice + " and " + highmemberprice + ")";
        }
        //日期
        if (startdate != "" && enddate != "" && startdate != null && enddate != null) {
            sql += " and (p.pdate between '" + startdate + "' and DATE_ADD('" + enddate + "',INTERVAL 1 DAY))";
        }
        return count(sql);
    }

}
