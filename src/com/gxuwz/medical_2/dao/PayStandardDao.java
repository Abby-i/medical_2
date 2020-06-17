package com.gxuwz.medical_2.dao;

import com.gxuwz.medical_2.database.DbUtil;
import com.gxuwz.medical_2.domain.payStandard.PayStandard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PayStandardDao extends GenericDao<PayStandard> {
    @Override
    protected PayStandard handle(ResultSet rs) throws SQLException {
        try{
            PayStandard model = new PayStandard();
            model.setAnnual(rs.getString("annual"));
            model.setPay_cost(rs.getDouble("pay_cost"));
            model.setStartTime(rs.getDate("starttime"));
            model.setEndTime(rs.getDate("endtime"));
            return model;
        }catch(SQLException e){
            throw new SQLException("结果集转为实例失败!",e);
        }
    }

    /**
     * 缴费标准修改方法
     * 对应的查找原数据库数据的方法
     * @param annual
     * @return
     */
    public PayStandard findById(String annual) {
        Connection conn =null;
        PreparedStatement ptmt =null;
        ResultSet rs =null;
        StringBuffer sb=new StringBuffer();
        sb.append("select * from t_paystandard where annual = ?");

        try {
            conn = DbUtil.getConn();
            ptmt = conn.prepareStatement(sb.toString());
            ptmt.setString(1, annual);
            rs = ptmt.executeQuery();
            PayStandard entity = null;
            if (rs != null && rs.next()) {
                entity = new PayStandard();
                entity.setAnnual(rs.getString("annual"));
                entity.setPay_cost(rs.getDouble("pay_cost"));
                entity.setStartTime(rs.getDate("startTime"));
                entity.setEndTime(rs.getDate("endTime"));
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

}
