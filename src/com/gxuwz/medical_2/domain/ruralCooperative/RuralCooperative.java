package com.gxuwz.medical_2.domain.ruralCooperative;

import com.gxuwz.medical_2.database.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RuralCooperative {
    private String farmerid;//农合机构编号
    private String farmername;//农合经办点
    private String areacode; //所属行政区域编号

    /**
     * 添加农合机构信息
     * @param farmerid
     * @param farmername
     * @param areacode
     * @throws SQLException
     */
    public void addRural(String farmerid, String farmername, String areacode) throws Exception{
        Connection conn=null;
        PreparedStatement ptmt=null;
        try {
            conn = DbUtil.getConn();
            conn.setAutoCommit(false);
            StringBuffer sqlBuff=new StringBuffer("insert into t_ruralcooprative(farmerid,farmername,areacode) values(?,?,?)");
            ptmt=conn.prepareStatement(sqlBuff.toString());
            ptmt.setString(1, farmerid);
            ptmt.setString(2, farmername);
            ptmt.setString(3, areacode);
            ptmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            e.printStackTrace();
        }finally{
            DbUtil.close(ptmt, conn);
        }
    }

    /**
     * 修改农合机构对应的数据库操作方法
     * @param farmerid
     * @param farmername
     * @param areacode
     * @throws SQLException
     */
    public void editRural(String farmerid, String farmername, String areacode) throws Exception {
        Connection conn=null;
        PreparedStatement ptmt=null;
        try {
            conn =DbUtil.getConn();
            conn.setAutoCommit(false);
            StringBuffer sqlBuff=new StringBuffer("update t_ruralcooprative t set t.farmerid=?,t.farmername=?,t.areacode=? where t.farmerid=? ");//update t_menu t set t.menuname=?,t.url=? where t.menuid=?
            ptmt=conn.prepareStatement(sqlBuff.toString());
            ptmt.setString(1, farmerid);
            ptmt.setString(2, farmername);
            ptmt.setString(3, areacode);
            ptmt.setString(4, farmerid);
            ptmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            e.printStackTrace();
        }finally{
            DbUtil.close(ptmt, conn);
        }
    }

    /**
     * 删除农合机构数据库数据
     * @param farmerid
     * @throws SQLException
     */
    public void delRural(String farmerid) throws Exception {

        Connection conn=null;
        PreparedStatement ptmt=null;
        try {
            conn =DbUtil.getConn();
            conn.setAutoCommit(false);
            StringBuffer sqlBuff=new StringBuffer("delete from t_ruralcooprative where farmerid=?");
            ptmt=conn.prepareStatement(sqlBuff.toString());
            ptmt.setString(1, farmerid);
            ptmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            e.printStackTrace();
        }finally{
            DbUtil.close(ptmt, conn);
        }

    }

    /**
     * getter setter方法
     * @return
     */
    public String getFarmerid() {
        return farmerid;
    }

    public void setFarmerid(String farmerid) {
        this.farmerid = farmerid;
    }

    public String getFarmername() {
        return farmername;
    }

    public void setFarmername(String farmername) {
        this.farmername = farmername;
    }

    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }
}
