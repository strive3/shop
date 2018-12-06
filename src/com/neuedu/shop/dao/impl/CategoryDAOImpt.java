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
     * ͨ�ò��ң���Ŀ�в�����
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
        //һ������������
        int count = count();
        Paging<Category> paging = new Paging<>(pageNumber, pageSize, count);
        List<Category> categories = new ArrayList<>();
        //������ݿ����е����
        findToTree(categories, 0);
        //˼�룺���մ����ݿ�����������ݵ�ǰҳ��    ���н�ȡ
        //��ȡ���յ�
        int realCount = pageSize;
        //����պ�������
        if (count % pageSize == 0){
            categories = categories.subList(paging.getStartIndex(), pageSize * pageNumber);
        } else{
            //���һ��ҳ�����˼�������
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
            // �ҵ���ǰ�����Ƿ��к��ӣ�����ǰ�����Ƿ���Ҷ�ӽ��
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
                // �ж��Ƿ����ӽڵ�
                if (!category.isLeaf()) {
                    // ��Ϊ�ýڵ��id��ĳ���ڵ��pid
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
     * ���
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
     * ��Ӹ����
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
        // ��Ϊ��Ӹ���� gradeһ��Ϊ1��pidΪ0��leafΪtrue
        add(new Category(name, descr, 0, true, 1));
    }

    /**
     * �������� ����Ҫ��1�������һ���������ô����Ҫ��ӵ�������pid ����1 ���ʱ��grade��1��grade+1����1��Ҷ�ӽڵ���µ���Ҷ�ӽ��
     */
    @Override
    public void add(String name, String desc, int pid) {
        Connection conn = null;
        PreparedStatement prep = null;
        // ��ѯ���ĸ���� id <- pid Ѱ�Ҹ����
        String sqlSelect = "select * from t_category where id =" + pid;
        String sql = "insert into t_category values (null,?,?,?,?,?)";
        // �����ĸ�����leaf��Ϊ0
        String sqlUpdate = "update t_category set leaf=0 where id=" + pid;
        int pgrade = 0;
        try {
            // ����������MySql���ݿ���ύ���Զ��ύ��
            // ����Ҫ��conn������Զ��ύ��Ϊ�ֶ��ύ
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            prep = DBUtil.getPreparedStatement(conn, sqlSelect);

            // 1.�ҵ�pidΪ����ֵ���������ݣ�ȡgradeֵ
            ResultSet rSelect = DBUtil.getResultSet(prep);
            if (rSelect.next()) {
                pgrade = rSelect.getInt("grade");
            }

            // 2.ִ��insert���
            prep = DBUtil.getPreparedStatement(conn, sql);
            prep.setString(1, name);
            prep.setString(2, desc);
            prep.setInt(3, pid);
            prep.setInt(4, 1);
            prep.setInt(5, pgrade + 1);
            prep.executeUpdate();

            // 3.���¸��ڵ�Ϊ��Ҷ�ӽ��
            prep = DBUtil.getPreparedStatement(conn, sqlUpdate);
            prep.executeUpdate();

            // ����ȫ����ɺ��ύ
            conn.commit();
        } catch (SQLException e) {
            // ��������쳣 ��������ع�
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
     * ͨ��id���Ҷ�Ӧ��category
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
     * ɾ�� ɾ��ָ��pid������ �Լ��������������� �������ֵܽڵ�ʱ�����ڵ���Ϊ���ڵ㣬���ֵܽڵ�ʱ�����ڵ����ΪҶ�ӽ��
     */
    private void delete(Integer id) throws Exception {
        PreparedStatement prep = null;
        ResultSet rsSelect = null;
        // ���ӽڵ� pid <- id ���Һ���
        String sqlSelect = "select * from t_category where pid =" + id;
        // ɾ���Լ�
        String sqlDelete = "delete from t_category where id=" + id;

        conn = DBUtil.getConnection();
        prep = DBUtil.getPreparedStatement(conn, sqlSelect);
        rsSelect = DBUtil.getResultSet(prep);
        while (rsSelect.next()) {
            // ����к��ӣ���ݹ飬���� ��ִ��������
            delete(rsSelect.getInt("id"));
        }

        // ɾ���ýڵ�
        prep = DBUtil.getPreparedStatement(conn, sqlDelete);
        prep.executeUpdate();

    }

    /**
     * �����õ���ɾ��
     */
    @Override
    public void delete(Integer id, Integer pid) {
        Connection conn = DBUtil.getConnection();
        PreparedStatement prep = null;
        ResultSet rs = null;
        int rscount = 0;
        // ��ѯ���ڵ㣬��û������Ҷ�ӽ��
        String sqlSelect = "select count(*) from t_category where pid =" + pid;
        // ���¸��ڵ��leaf�ֶ�Ϊ1
        String sqlUpdate = "update t_category set leaf=1 where id=" + pid;
        try {
            conn.setAutoCommit(false);
            // (1).�h��
            delete(id);
            // (2).����
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
     * ����pid  �ҵ�pname
     *
     * @param pid
     * @return
     */
    public String findNameForId(Integer pid) {
        Category category = findById(pid);
        return category.getName();
    }

}
