package com.gxuwz.medical_2.domain.payStandard;

import com.gxuwz.medical_2.database.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class PayStandard {
    private String annual; //年度
    private Double pay_cost;//缴费金额
    private Date startTime; //开始时间
    private Date endTime;  //结束时间

    public PayStandard() {
    }

    public PayStandard(String annual, Double pay_cost, Date startTime, Date endTime) {
        this.annual = annual;
        this.pay_cost = pay_cost;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * 缴费登记需要使用
     * 获取当前年度
     * @param annual
     */
    public PayStandard(String annual) {
        this.annual = annual;
        try {
            getByAnnual();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取指定年度的缴费标准
     * @Date 2019年5月10日上午10:27:33
     * @return void
     */
    private void getByAnnual() throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            conn = DbUtil.getConn();
            pstmt = conn.prepareStatement("select * from t_paystandard where annual=? ");
            int index = 1;
            // 把页面传过来的treeID的值赋给占位符
            pstmt.setString(index, this.annual);
            // 执行查询操作
            rs = pstmt.executeQuery();
            if(rs.next()){
                this.annual = rs.getString("annual");
                this.pay_cost = rs.getDouble("pay_cost");
                this.startTime = rs.getDate("startTime");
                this.endTime = rs.getDate("endTime");
            }
        }catch(SQLException e){
            throw e;
        }finally{
            DbUtil.close(rs, pstmt, conn);
        }
    }

    /**
     * 添加缴费标准到数据库
     */
    public void add() throws Exception {
        Connection conn=null;
        try{
            conn = DbUtil.getConn();
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
    //添加到数据库
    private void saveToDB(Connection conn) throws Exception {
        PreparedStatement ptmt=null;
        try{
            System.out.println(this.annual+this.pay_cost+"到这里了");
            StringBuffer sqlBuff = new StringBuffer("insert into t_paystandard(annual,pay_cost,startTime,endTime) values (?,?,?,?)");
            ptmt = conn.prepareStatement(sqlBuff.toString());
            ptmt.setString(1, this.annual);
            ptmt.setDouble(2, this.pay_cost);
            ptmt.setDate(3, new java.sql.Date(this.startTime.getTime()));
            ptmt.setDate(4, new java.sql.Date(this.endTime.getTime()));
            ptmt.executeUpdate();
        }catch(Exception e){
            throw e;
        }finally{
            DbUtil.close(ptmt,null);
        }
    }

    /**
     * 缴费标准修改方法
     * @throws Exception
     */
    public void edit() throws Exception {
        Connection conn=null;
        try{
            conn =DbUtil.getConn();
            conn.setAutoCommit(false);
            //保存到数据库
            editToDB(conn);
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
    //缴费标准修改数据库信息
    private void editToDB(Connection conn) throws SQLException {
        PreparedStatement pstmt=null;
        try{
            StringBuffer sqlBuff=new StringBuffer("update t_paystandard set pay_cost=?,startTime=?,endTime=?  where annual=?");
            pstmt=conn.prepareStatement(sqlBuff.toString());
            pstmt.setDouble(1, this.pay_cost);
            pstmt.setDate(2,  new java.sql.Date(this.startTime.getTime()));
            pstmt.setDate(3, new java.sql.Date(this.endTime.getTime()));
            pstmt.setString(4, this.annual);
            pstmt.executeUpdate();
        }catch(SQLException e){
            throw e;
        }finally{
            DbUtil.close(pstmt,null);
        }
    }

    /**
     * 删除缴费标准对应的数据库删除方法
     * @param annual
     * @throws Exception
     */
    public void del(String annual) throws Exception {
        Connection conn=null;
        this.annual=annual;
        try{
            conn =DbUtil.getConn();
            //开启手动提交事务
            conn.setAutoCommit(false);
            //保存到数据库
            delFromDB(conn);
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
    private void delFromDB(Connection conn) throws SQLException {
        PreparedStatement pstmt=null;
        try{
            StringBuffer sqlBuff=new StringBuffer("delete from t_paystandard where annual = ? ");
            pstmt=conn.prepareStatement(sqlBuff.toString());
            pstmt.setString(1, this.annual);
            pstmt.executeUpdate();
        }catch(SQLException e){
            throw e;
        }finally{
            DbUtil.close(pstmt,null);
        }
    }

    /**
     * getter setter方法
     * @return
     */
    public String getAnnual() {
        return annual;
    }

    public void setAnnual(String annual) {
        this.annual = annual;
    }

    public Double getPay_cost() {
        return pay_cost;
    }

    public void setPay_cost(Double pay_cost) {
        this.pay_cost = pay_cost;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
