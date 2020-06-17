package com.gxuwz.medical_2.domain.Policy;

import com.gxuwz.medical_2.database.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Policy {
    private int id;
    private String annual;//年度
    private double topline;//封顶线
    private double proportion;//报销比例

    public Policy() {
    }

    public Policy(int id, String annual, double topline, double proportion) {
        this.id = id;
        this.annual = annual;
        this.topline = topline;
        this.proportion = proportion;
    }

    /**
     * 添加慢性病报销政策到数据库
     *
     * @throws Exception
     */
    public void add(String annual, Double topline, Double proportion)throws Exception {
        Connection conn=null;
        this.annual=annual;
        this.topline=topline;
        this.proportion=proportion;
        try{
            //加载父节点ID的对象
            //自动编号
            conn = DbUtil.getConn();
            //开启手动提交事务
            conn.setAutoCommit(false);
            //保存到数据库
            saveToDB(conn);
            //提交事务
            conn.commit();
        }catch (Exception e) {
            if(conn!=null){
                conn.rollback();
            }
            throw e;
        }finally{
            DbUtil.close(null,conn);
        }
    }
    private void saveToDB(Connection conn) throws SQLException {
        PreparedStatement pstmt=null;
        try{
            StringBuffer sqlBuff=new StringBuffer("insert into t_policy(annual,topline,proportion)");
            sqlBuff.append("values(?,?,?)");
            pstmt=conn.prepareStatement(sqlBuff.toString());
            pstmt.setString(1, this.annual);
            pstmt.setDouble(2, this.topline);
            pstmt.setDouble(3, this.proportion);
            pstmt.executeUpdate();
        }catch(SQLException e){
            throw e;
        }finally{
            DbUtil.close(pstmt,null);
        }
    }

    /**
     * 修改慢性病政策
     * @throws Exception
     */
    public void edit(int id, String annual, Double topline, Double proportion) throws Exception {
        Connection conn=null;
        PreparedStatement ptmt=null;
        try {
            conn = DbUtil.getConn();
            conn.setAutoCommit(false);
            StringBuffer sqlBuff=new StringBuffer("update t_policy t set t.annual=?,t.topline=?,proportion=?  where t.id=?");
            ptmt=conn.prepareStatement(sqlBuff.toString());
            ptmt.setString(1, annual);
            ptmt.setDouble(2, topline);
            ptmt.setDouble(3, proportion);
            ptmt.setInt(4, id);
            ptmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            if(conn!=null){
                conn.rollback();
            }
            throw new SQLException("Failed to update t_area  !", e);
        }finally{
            DbUtil.close(ptmt, conn);
        }
    }

    /**
     * 删除慢性病政策
     * @param id
     * @throws Exception
     */
    public void del(int id) throws Exception {
        Connection conn=null;
        PreparedStatement ptmt=null;
        this.id = id;
        try{
            conn = DbUtil.getConn();
            conn.setAutoCommit(false);

            StringBuffer sqlBuff=new StringBuffer("delete from t_policy where id=?");
            ptmt=conn.prepareStatement(sqlBuff.toString());
            ptmt.setInt(1, this.id);
            ptmt.executeUpdate();

            conn.commit();
        }catch(SQLException e){
            conn.rollback();
            throw new SQLException("Failed to delete record from table !", e);
        }finally {
            DbUtil.close(null,conn);
        }
    }
    /**
     * getter setter方法
     * @return
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnnual() {
        return annual;
    }

    public void setAnnual(String annual) {
        this.annual = annual;
    }

    public double getTopline() {
        return topline;
    }

    public void setTopline(double topline) {
        this.topline = topline;
    }

    public double getProportion() {
        return proportion;
    }

    public void setProportion(double proportion) {
        this.proportion = proportion;
    }
}
