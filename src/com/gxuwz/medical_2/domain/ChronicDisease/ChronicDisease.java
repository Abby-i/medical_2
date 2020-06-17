package com.gxuwz.medical_2.domain.ChronicDisease;

import com.gxuwz.medical_2.database.DbUtil;
import com.gxuwz.medical_2.exception.DbException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChronicDisease {
    /**
     * 疾病编码,疾病名称
     */
    private String illcode;
    private String illname;

    public ChronicDisease(String illcode, String illname) {
        this.illcode = illcode;
        this.illname = illname;
    }
    public ChronicDisease() {
    }


    /**
     * 添加慢性病
     */
    //调用添加方法
    public void add()throws Exception {
        Connection conn=null;
        try{
            //1:对象属性赋值,无
            conn= DbUtil.getConn();
            //2；调用saveToDB方法保存到数据库
            saveToDB(conn);
        }catch(SQLException e){
            throw e;
        }finally{
            DbUtil.close(null,conn);
        }
    }
    //添加到数据库
    private void saveToDB(Connection conn)throws SQLException{
        PreparedStatement pstmt=null;
        try{
            //定义构造插入SQL语句字符串变量sql
            String  sql="insert into t_chronicdisease(illcode,illname)values(?,?)";
            //创建执行带动态参数的SQL的PreparedStatement pstmt
            pstmt=conn.prepareStatement(sql);
            //设置动态参数对应的值
            int index=1;
            pstmt.setString(index++, this.illcode);
            pstmt.setString(index++, this.illname);
            int count=pstmt.executeUpdate();

        }catch(SQLException e){
            throw e;
        }finally{
            DbUtil.close(pstmt,null);
        }
    }

    /**
     * 修改慢性病方法
     * @throws SQLException
     */
    public void edit()throws Exception{
        Connection conn =DbUtil.getConn();
        try{
            conn.setAutoCommit(false);
            this.updateToDB(conn);
            conn.commit();
        }catch(SQLException e){
            conn.rollback();
            throw e;
        }finally{
            DbUtil.close(null,conn);
        }
    }
    private void updateToDB(Connection conn)throws SQLException{
        PreparedStatement pstmt=null;
        try{
            //定义构造update SQL语句字符串变量sql
            String  sql="update t_chronicdisease set illname=? where illcode=?";
            //创建执行带动态参数的SQL的PreparedStatement pstmt
            pstmt=conn.prepareStatement(sql);
            //设置动态参数对应的值
            int index=1;
            pstmt.setString(index++, this.illname);
            pstmt.setString(index++, this.illcode);
            int count=pstmt.executeUpdate();

        }catch(SQLException e){
            throw e;
        }finally{
            DbUtil.close(pstmt,null);
        }
    }

    /**
     * 页面传入id值查找慢性病id
     * @param id
     * @throws Exception
     */
    public ChronicDisease(String id)throws Exception{
        this.illcode =id;
        try{
            load();
        }catch(Exception e){
            e.printStackTrace();
            throw new DbException("无法找到ID="+this.illcode+"对应信息");
        }
    }

    private void load()throws Exception{
        Connection conn=DbUtil.getConn();
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try{

            String sql="select * from t_chronicdisease where illcode=?";
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, this.illcode);
            rs=pstmt.executeQuery();
            if(rs.next()){
                this.illcode=rs.getString("illcode");
                this.illname =rs.getString("illname");
            }
        }catch(SQLException e){
            throw e;
        }
    }

    /**
     * 删除慢性病
     * @param illcode
     * @throws Exception
     */
    public void del(String illcode)throws Exception{

        Connection conn=null;
        try{
            //1:对象属性赋值
            this.illcode =illcode;
            conn=DbUtil.getConn();
            //2；调用delFromDB方法
            delFromDB(conn);
        }catch(SQLException e){
            throw e;
        }finally{
            DbUtil.close(null,conn);
        }
    }
    private void delFromDB(Connection conn)throws SQLException{
        PreparedStatement pstmt=null;
        try{
            //定义构造插入SQL语句字符串变量sql
            String  sql="delete from t_chronicdisease where illcode=?";
            //创建执行带动态参数的SQL的PreparedStatement pstmt
            pstmt=conn.prepareStatement(sql);
            //设置动态参数对应的值
            int index=1;
            pstmt.setString(index++, this.illcode);
            int count=pstmt.executeUpdate();
        }catch(SQLException e){
            throw e;
        }finally{
            DbUtil.close(pstmt,null);
        }
    }

    /**
     * getter setter方法
     */
    public String getIllcode() {
        return illcode;
    }

    public void setIllcode(String illcode) {
        this.illcode = illcode;
    }

    public String getIllname() {
        return illname;
    }

    public void setIllname(String illname) {
        this.illname = illname;
    }

}
