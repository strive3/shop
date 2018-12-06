package com.neuedu.shop.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.neuedu.shop.dao.ICategoryDAO;
import com.neuedu.shop.entity.Category;
import com.neuedu.shop.entity.Paging;
import com.neuedu.shop.util.DBUtil;

public class CategoryDAOImpt implements ICategoryDAO {

    private Connection conn = DBUtil.getConnection();

    /**
     * 通用查找，项目中不适用
     */
    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        Connection conn = null;
        PreparedStatement prep = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from t_category";
            prep = DBUtil.getPreparedStatement(conn, sql);
            rs = DBUtil.getResultSet(prep);
            while (rs.next()) {
                categories.add(new Category(rs.getInt("id"), rs.getString("name"), rs.getString("descr"),
                        rs.getInt("pid"), rs.getInt("leaf") == 1 ? true : false, rs.getInt("grade")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, prep, conn);
        }
        return categories;
    }

    public int count() {
        List<Category> categories = new ArrayList<>();
        Connection conn = null;
        PreparedStatement prep = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from t_category";
            prep = DBUtil.getPreparedStatement(conn, sql);
            rs = DBUtil.getResultSet(prep);
            while (rs.next()) {
                categories.add(new Category(rs.getInt("id"), rs.getString("name"), rs.getString("descr"),
                        rs.getInt("pid"), rs.getInt("leaf") == 1 ? true : false, rs.getInt("grade")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, prep, conn);
        }
        return categories.size();
    }

    public Paging<Category> findToTreeForPaging(int pageNumber, int pageSize) {
        //一共多少条数据
        int count = count();
        Paging<Category> paging = new Paging<>(pageNumber, pageSize, count);
        List<Category> categories = new ArrayList<>();
        //查出数据库所有的类别
        findToTree(categories, 0);
        //思想：将刚从数据库查出的链表根据当前页数    进行截取
        //截取的终点
        int realCount = pageSize;
        //如果刚好是整的
        if (count % pageSize == 0){
            categories = categories.subList(paging.getStartIndex(), pageSize * pageNumber);
        } else{
            //如果一个页面多出了几条数据
            if (pageNumber >= paging.getEndPage()){
                categories = categories.subList(paging.getStartIndex(), count);
            } else {
                categories = categories.subList(paging.getStartIndex(), pageSize * pageNumber);
            }
        }
        paging.setObjects(categories);
        return paging;
    }

    public List<Category> findToTree() {
        List<Category> categories = new ArrayList<>();
        findToTree(categories, 0);
        return categories;
    }

    private List<Category> findToTree(List<Category> categories, Integer pid) {
        Connection conn = DBUtil.getConnection();
        PreparedStatement prep = null;
        ResultSet rs = null;
        try {
            // 找到当前数据是否有孩子，即当前数据是否是叶子结点
            String sql = "select * from t_category where pid=" + pid;
            prep = DBUtil.getPreparedStatement(conn, sql);
            rs = DBUtil.getResultSet(prep);
            while (rs.next()) {
                Category category = new Category(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("descr"),
                        rs.getInt("pid"),
                        rs.getInt("leaf") == 1 ? true : false,
                        rs.getInt("grade"));
                categories.add(category);
                // 判断是否还有子节点
                if (!category.isLeaf()) {
                    // 因为该节点的id是某个节点的pid
                    findToTree(categories, category.getId());
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, prep, conn);
        }
        return categories;
    }

    /**
     * 添加
     */
    private void add(Category category) {
        Connection conn = null;
        PreparedStatement prep = null;
        conn = DBUtil.getConnection();
        String sql = "insert into t_category values (null,?,?,?,?,?)";
        prep = DBUtil.getPreparedStatement(conn, sql);
        try {
            prep.setString(1, category.getName());
            prep.setString(2, category.getDescr());
            prep.setInt(3, category.getPid());
            prep.setInt(4, category.isLeaf() ? 1 : 0);
            prep.setInt(5, category.getGrade());
            prep.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(prep, conn);
        }
    }

    /**
     * 添加根类别
     *
     * @param name
     * @param descr
     */
    @Override
    public void add(String name, String descr) {
        /*
         * Category category = new Category(name, descr); category.setPid(0);
         * category.setLeaf(true); category.setGrade(1); add(category);
         */
        // 因为添加根类别 grade一定为1，pid为0，leaf为true
        add(new Category(name, descr, 0, true, 1));
    }

    /**
     * 添加子类别 比如要在1下面添加一个子类别那么，将要添加的子类别的pid 就是1 添加时，grade比1的grade+1，将1从叶子节点更新到非叶子结点
     */
    @Override
    public void add(String name, String desc, int pid) {
        Connection conn = null;
        PreparedStatement prep = null;
        // 查询他的根类别 id <- pid 寻找根类别
        String sqlSelect = "select * from t_category where id =" + pid;
        String sql = "insert into t_category values (null,?,?,?,?,?)";
        // 将它的跟类别的leaf置为0
        String sqlUpdate = "update t_category set leaf=0 where id=" + pid;
        int pgrade = 0;
        try {
            // 事务处理：由于MySql数据库的提交是自动提交，
            // 我们要将conn对象的自动提交改为手动提交
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            prep = DBUtil.getPreparedStatement(conn, sqlSelect);

            // 1.找到pid为传入值的那条数据，取grade值
            ResultSet rSelect = DBUtil.getResultSet(prep);
            if (rSelect.next()) {
                pgrade = rSelect.getInt("grade");
            }

            // 2.执行insert语句
            prep = DBUtil.getPreparedStatement(conn, sql);
            prep.setString(1, name);
            prep.setString(2, desc);
            prep.setInt(3, pid);
            prep.setInt(4, 1);
            prep.setInt(5, pgrade + 1);
            prep.executeUpdate();

            // 3.更新父节点为非叶子结点
            prep = DBUtil.getPreparedStatement(conn, sqlUpdate);
            prep.executeUpdate();

            // 事务全部完成后提交
            conn.commit();
        } catch (SQLException e) {
            // 如果出现异常 进行事务回滚
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            DBUtil.close(prep, conn);
        }
    }

    @Override
    public void update(Category category) {
        Connection conn = null;
        PreparedStatement prep = null;
        String sql = "update t_category set name=?,descr=? where id=?";
        try {
            conn = DBUtil.getConnection();
            prep = DBUtil.getPreparedStatement(conn, sql);
            prep.setString(1, category.getName());
            prep.setString(2, category.getDescr());
            prep.setInt(3, category.getId());
            prep.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(prep, conn);
        }
    }

    /**
     * 通过id查找对应的category
     */
    @Override
    public Category findById(Integer id) {
        Connection conn = null;
        PreparedStatement prep = null;
        ResultSet rs = null;
        Category category = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from t_category where id=?";
            prep = DBUtil.getPreparedStatement(conn, sql);
            prep.setInt(1, id);
            rs = DBUtil.getResultSet(prep);
            if (rs.next()) {
                category = new Category(id, rs.getString("name"), rs.getString("descr"), rs.getInt("pid"),
                        rs.getInt("leaf") == 1 ? true : false, rs.getInt("grade"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, prep, conn);
        }
        return category;
    }

    /**
     * 删除 删除指定pid的数据 以及它的所有子树， 当它有兄弟节点时，父节点仍为根节点，无兄弟节点时，父节点更新为叶子结点
     */
    private void delete(Integer id) throws Exception {
        PreparedStatement prep = null;
        ResultSet rsSelect = null;
        // 找子节点 pid <- id 是找孩子
        String sqlSelect = "select * from t_category where pid =" + id;
        // 删除自己
        String sqlDelete = "delete from t_category where id=" + id;

        conn = DBUtil.getConnection();
        prep = DBUtil.getPreparedStatement(conn, sqlSelect);
        rsSelect = DBUtil.getResultSet(prep);
        while (rsSelect.next()) {
            // 如果有孩子，则递归，否则 不执行这句语句
            delete(rsSelect.getInt("id"));
        }

        // 删除该节点
        prep = DBUtil.getPreparedStatement(conn, sqlDelete);
        prep.executeUpdate();

    }

    /**
     * 真正用到的删除
     */
    @Override
    public void delete(Integer id, Integer pid) {
        Connection conn = DBUtil.getConnection();
        PreparedStatement prep = null;
        ResultSet rs = null;
        int rscount = 0;
        // 查询父节点，有没有其他叶子结点
        String sqlSelect = "select count(*) from t_category where pid =" + pid;
        // 更新父节点的leaf字段为1
        String sqlUpdate = "update t_category set leaf=1 where id=" + pid;
        try {
            conn.setAutoCommit(false);
            // (1).刪除
            delete(id);
            // (2).更新
            prep = DBUtil.getPreparedStatement(conn, sqlSelect);
            rs = DBUtil.getResultSet(prep);
            rs.next();
            rscount = rs.getInt("count(*)");
            if (rscount <= 0) {
                prep = DBUtil.getPreparedStatement(conn, sqlUpdate);
                prep.executeUpdate();
            }

            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, prep, conn);
        }
    }

    /**
     * 根据pid  找到pname
     *
     * @param pid
     * @return
     */
    public String findNameForId(Integer pid) {
        Category category = findById(pid);
        return category.getName();
    }

}
