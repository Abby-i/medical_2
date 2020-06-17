package com.gxuwz.medical_2.dao;


import com.gxuwz.medical_2.database.DbUtil;
import com.gxuwz.medical_2.domain.area.Area;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AreaDao extends GenericDao<Area>{
    @Override
    protected Area handle(ResultSet rs) throws SQLException {
        try{
            Area model=new Area();
            model.setAreacode(rs.getString("areacode"));
            model.setAreaname(rs.getString("areaname"));
            model.setGrade(rs.getInt("grade"));
            model.setAreaupcode(rs.getString("areaupcode"));
            return model;
        }catch(SQLException e){

            throw new SQLException("结果集转为实例失败!",e);
        }
    }

    /**
     * 查询出所有的镇
     * @return
     */
    public List<Area> findtown(){
        Connection conn = null;
        PreparedStatement ptmt = null;
        ResultSet rs =null;
        List<Area> result = new ArrayList<Area>();
        StringBuffer sb=new StringBuffer();
        sb.append("select areacode,areaname,grade,areaupcode from t_area where grade=3 order by areacode asc");
        try{
            conn = DbUtil.getConn();
            ptmt = conn.prepareStatement(sb.toString());
            rs = ptmt.executeQuery();
            Area a = null;
            while(rs.next()) {
                a = new Area();
                a.setAreacode(rs.getString("areacode"));
                a.setAreaname(rs.getString("areaname"));
                a.setGrade(rs.getInt("grade"));
                a.setAreaupcode(rs.getString("areaupcode"));
                result.add(a);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                DbUtil.close(rs, ptmt, conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 通过areacode查询Area
     * 以此的到下拉框数据
     * @param
     * @return
     */
    public List<Area> queryByupId(String areacode) {
        Connection conn = null;
        PreparedStatement ptmt = null;
        ResultSet rs =null;
        List<Area> result = new ArrayList<Area>();
        StringBuffer sb=new StringBuffer();
        sb.append("select areacode,areaname,grade,areaupcode from t_area where areaupcode = ? order by areacode asc");
        try{
            conn = DbUtil.getConn();
            ptmt = conn.prepareStatement(sb.toString());
            ptmt.setString(1, areacode);
            rs = ptmt.executeQuery();
            Area a = null;
            while(rs.next()) {
                a = new Area();
                a.setAreacode(rs.getString("areacode"));
                a.setAreaname(rs.getString("areaname"));
                a.setGrade(rs.getInt("grade"));
                a.setAreaupcode(rs.getString("areaupcode"));
                result.add(a);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                DbUtil.close(rs, ptmt, conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 添加农合机构获取行政区域
     * ruralCooperativeServlet调用该方法
     */
    public List<Area> queryAreas() {
        Connection conn = null;
        PreparedStatement ptmt = null;
        ResultSet rs =null;
        List<Area> result = new ArrayList<Area>();
        StringBuilder sb=new StringBuilder();
        //查询出所属行政区域是否在镇级以上的区域
        sb.append("SELECT * FROM t_area WHERE  LENGTH(areacode)<=8 order by areacode asc");
        try{
            conn = DbUtil.getConn();
            ptmt = conn.prepareStatement(sb.toString());
            rs = ptmt.executeQuery();

            Area a = null;
            while(rs.next()){
                a = new Area();
                a.setAreacode(rs.getString("areacode"));
                a.setAreaname(rs.getString("areaname"));
                a.setGrade(rs.getInt("grade"));
                a.setAreaupcode(rs.getString("areaupcode"));
                result.add(a);
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
        return result;
    }

    /**
     * 修改农合机构信息
     * 通过行政区域id查找
     * @param areacode
     * @return
     */
    public Area querySelected(String areacode) {
        Connection conn = null;
        PreparedStatement ptmt = null;
        ResultSet rs =null;
        try {
            conn = DbUtil.getConn();
            StringBuffer sb = new StringBuffer("select a.areacode,a.areaname,grade,a.areaupcode FROM t_ruralcooprative f,t_area a WHERE f.areacode=a.areacode and a.areacode = ?");
            ptmt = conn.prepareStatement(sb.toString());
            ptmt.setString(1, areacode);
            rs = ptmt.executeQuery();
            Area a = null;
            while(rs.next()){
                a = new Area();
                a.setAreacode(rs.getString("areacode"));
                a.setAreaname(rs.getString("areaname"));
                a.setGrade(rs.getInt("grade"));
                a.setAreaupcode(rs.getString("areaupcode"));
                return a ;
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
