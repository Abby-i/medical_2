package com.gxuwz.medical_2.dao;

import com.gxuwz.medical_2.database.DbUtil;
import com.gxuwz.medical_2.domain.area.Area;
import com.gxuwz.medical_2.domain.ruralCooperative.RuralCooperative;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RuralCooperativeDao extends GenericDao<RuralCooperative> {
    @Override
    protected RuralCooperative handle(ResultSet rs) throws SQLException {
        try {
            RuralCooperative model = new RuralCooperative();
            model.setFarmerid(rs.getString("farmerid"));
            model.setFarmername(rs.getString("farmername"));
            model.setAreacode(rs.getString("areacode"));
            return model;
        } catch (SQLException e) {
            throw new SQLException("结果集转为实例失败!", e);
        }
    }

    /**
     * 判断农合机构名字
     * 是否已经存在
     */
    public boolean isExist(String farmername) throws Exception {
        Connection conn = null;
        PreparedStatement ptmt = null;
        ResultSet rs = null;
        try {
            conn = DbUtil.getConn();
            StringBuffer sb = new StringBuffer("select farmername from t_ruralcooprative where farmername = ?");
            ptmt = conn.prepareStatement(sb.toString());
            ptmt.setString(1, farmername);
            rs = ptmt.executeQuery();
            if (rs.next()) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DbUtil.close(rs, ptmt, conn);
        }
    }

    /**
     * 修改方法
     * 通过farmerid查询农合机构信息
     * @param farmerid
     * @return
     */
    public RuralCooperative queryById(String farmerid) {
        Connection conn =null;
        PreparedStatement ptmt=null;
        ResultSet rs=null;
        try {
            conn = DbUtil.getConn();
            StringBuffer sb=new StringBuffer("select * from t_ruralcooprative where farmerid = ?");
            ptmt=conn.prepareStatement(sb.toString());
            ptmt.setString(1, farmerid);
            rs=ptmt.executeQuery();

            RuralCooperative rc = null;
            while(rs.next()) {
                rc = new RuralCooperative();
                rc.setFarmerid(rs.getString("farmerid"));
                rc.setFarmername(rs.getString("farmername"));
                rc.setAreacode(rs.getString("areacode"));
                return rc;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                DbUtil.close(rs, ptmt, conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
