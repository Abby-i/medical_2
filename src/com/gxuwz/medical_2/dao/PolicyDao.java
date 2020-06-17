package com.gxuwz.medical_2.dao;

import com.gxuwz.medical_2.database.DbUtil;
import com.gxuwz.medical_2.domain.Policy.Policy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PolicyDao extends GenericDao<Policy> {
    @Override
    protected Policy handle(ResultSet rs) throws SQLException {
        try{
            Policy entity=new Policy();
            entity.setId(rs.getInt("id"));
            entity.setAnnual(rs.getString("annual"));
            entity.setTopline(rs.getDouble("topline"));
            entity.setProportion(rs.getDouble("proportion"));

            return entity;
        }catch(SQLException e){
            throw new SQLException("结果集转为实例失败!",e);
        }
    }

    /**
     * 慢性病政策修改方法
     * @param id
     * @return
     */
    //查出原来数据库中的数据给到文本框
    public Policy queryById(String id) {
        Connection conn =null;
        PreparedStatement ptmt =null;
        ResultSet rs =null;
        StringBuffer sb=new StringBuffer();
        sb.append("select * from t_policy where id = ?");

        try {
            conn = DbUtil.getConn();
            ptmt = conn.prepareStatement(sb.toString());
            int index=1;
            ptmt.setInt(index, Integer.parseInt(id));
            rs = ptmt.executeQuery();
            Policy entity = null;
            if (rs != null && rs.next()) {
                entity = new Policy();
                entity.setId(rs.getInt("id"));
                entity.setAnnual(rs.getString("annual"));
                entity.setTopline(rs.getDouble("topline"));
                entity.setProportion(rs.getDouble("proportion"));
                return entity;
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }finally {
            try {
                DbUtil.close(rs, ptmt, conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 报销调用该方法
     * 查询当前年度慢病缴费政策
     * @param annual
     * @return
     */
    public Policy queryCeiling(String annual){
        System.out.println("annual:"+annual);
        Connection conn =null;
        PreparedStatement ptmt =null;
        ResultSet rs =null;
        StringBuffer sb=new StringBuffer();
        sb.append("select * from t_policy where annual = ?");

        try {
            conn = DbUtil.getConn();
            ptmt = conn.prepareStatement(sb.toString());
            ptmt.setString(1, annual);
            rs = ptmt.executeQuery();
            Policy entity = null;
            if (rs != null && rs.next()) {
                entity = new Policy();
                entity.setId(rs.getInt("id"));
                entity.setAnnual(rs.getString("annual"));
                entity.setTopline(rs.getDouble("topline"));
                entity.setProportion(rs.getDouble("proportion"));
                return entity;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("当前年度慢性病政策未设置");
            throw new RuntimeException();
        }finally {
            try {
                DbUtil.close(rs, ptmt, conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 报销慢病费用调用该方法
     * 计算报销金额
     * @param selectyear
     * @param idCard
     * @return
     */
    public double CountExpense(String selectyear, String idCard) {
        System.out.println("selectyear:"+selectyear);
        System.out.println("selectyear:"+idCard);
        Connection conn = null;
        PreparedStatement ptmt= null;
        ResultSet rs = null;
        StringBuffer sb=new StringBuffer("SELECT sum(expenseAccount) FROM t_ill_expense where 1=1 ");
        if(selectyear!=null && !selectyear.trim().equals("")){
            sb.append(" and expenseTime like '"+selectyear+"%'");
        }
        if(idCard!=null && !idCard.trim().equals("")){
            sb.append(" and idCard = '"+idCard+"'");
        }
        int rowCount = 0;
        try {
            conn = DbUtil.getConn();
            ptmt = conn.prepareStatement(sb.toString());
            rs = ptmt.executeQuery();
            while(rs.next()){
                return rs.getDouble("sum(expenseAccount)");
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }finally {
            try {
                DbUtil.close(rs, ptmt, conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rowCount;
    }

}
