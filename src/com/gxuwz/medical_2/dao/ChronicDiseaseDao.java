package com.gxuwz.medical_2.dao;

import com.gxuwz.medical_2.database.DbUtil;
import com.gxuwz.medical_2.domain.ChronicDisease.ChronicDisease;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChronicDiseaseDao extends GenericDao<ChronicDisease> {
    Connection conn =null;
    PreparedStatement ptmt =null;
    ResultSet rs =null;

    @Override
    protected ChronicDisease handle(ResultSet rs) throws SQLException {
        try{
            ChronicDisease entity=new ChronicDisease();

            entity.setIllcode(rs.getString("illcode"));//疾病编码
            entity.setIllname(rs.getString("illname"));//疾病名称
            return entity;
        }catch(SQLException e){
            throw new SQLException("结果集转为实例失败!",e);
        }
    }

    /**
     * 添加慢性病时判断该病症id是否已经存在
     */
    public boolean isExist(String illcode) throws Exception{
        Connection conn =null;
        PreparedStatement ptmt=null;
        ResultSet rs=null;
        try {
            conn = DbUtil.getConn();
            StringBuffer sb=new StringBuffer("select * from t_chronicdisease where illcode = ?");
            ptmt=conn.prepareStatement(sb.toString());
            ptmt.setString(1, illcode);
            rs=ptmt.executeQuery();
            if(rs.next()){
                return false;
            }else{
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally{
            DbUtil.close(rs, ptmt, conn);
        }
    }

    public ChronicDisease queryById(String illcode){
        StringBuffer sb=new StringBuffer("select * from t_chronicdisease where illcode = '"+illcode+"'");
        try {
            conn = DbUtil.getConn();
            ptmt = conn.prepareStatement(sb.toString());
            rs = ptmt.executeQuery();
            ChronicDisease entity = new ChronicDisease();
            if (rs != null && rs.next()) {
                entity.setIllcode(rs.getString("illcode"));//疾病编码
                entity.setIllname(rs.getString("illname"));//疾病名称
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
