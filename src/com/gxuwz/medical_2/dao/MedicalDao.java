package com.gxuwz.medical_2.dao;

import com.gxuwz.medical_2.database.DbUtil;
import com.gxuwz.medical_2.domain.medical.Medical;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicalDao {
    protected Medical handle(ResultSet rs,Medical entity) throws SQLException {
        entity.setJgbm(rs.getString("jgbm"));
        entity.setZzjgbm(rs.getString("zzjgbm"));
        entity.setJgmc(rs.getString("jgmc"));
        entity.setDqbm(rs.getString("dqbm"));
        entity.setAreacode(rs.getString("areacode"));

        entity.setLsgx(rs.getString("lsgx"));
        entity.setLsgxname(rs.getString("lsgx"));
        entity.setJgjb(rs.getString("jgjb"));
        entity.setJgjbname(rs.getString("jgjb"));
        entity.setSbddlx(rs.getString("sbddlx"));
        entity.setSbddlxname(rs.getString("sbddlx"));
        entity.setPzddlx(rs.getString("pzddlx"));
        entity.setPzddlxname(rs.getString("pzddlx"));
        entity.setSsjjlx(rs.getString("ssjjlx"));
        entity.setSsjjlxname(rs.getString("ssjjlx"));
        entity.setWsjgdl(rs.getString("wsjgdl"));
        entity.setWsjgdlname(rs.getString("wsjgdl"));
        entity.setWsjgxl(rs.getString("wsjgxl"));
        entity.setWsjgxlname(rs.getString("wsjgxl"));

        entity.setZgdw(rs.getString("zgdw"));
        entity.setKysj(rs.getDate("kysj"));
        entity.setFrdb(rs.getString("frdb"));
        entity.setZczj(rs.getDouble("zczj"));
        return entity;
    }
    /**
     * 医疗机构信息 查出所有记录
     * @param currentPage
     * @param pageSize
     * @return
     */
    public List<Medical> queryMedical(Integer currentPage, Integer pageSize) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs =null;
        List<Medical> result = new ArrayList<Medical>();
        StringBuilder sql=new StringBuilder();
        int startNo = (currentPage-1)*pageSize;
        sql.append("select * from t_medical  limit ?,? ");
        try {
            conn = DbUtil.getConn();
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setInt(1, startNo);
            pstmt.setInt(2, pageSize);
            rs = pstmt.executeQuery();
            Medical entity =null;
            while(rs.next()) {
                entity =new Medical();
                result.add(handle(rs,entity));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }finally {
            try {
                DbUtil.close(rs, pstmt, conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     *根据机构编码找到对应数据的所有信息 返回到页面
     * @param jgbm
     * @return
     */
    public Medical queryById(String jgbm) {
        Connection conn =null;
        PreparedStatement ptmt=null;
        ResultSet rs=null;
        try {
            conn = DbUtil.getConn();
            StringBuffer sb=new StringBuffer("select * from t_medical where jgbm = ?");
            ptmt=conn.prepareStatement(sb.toString());
            ptmt.setString(1, jgbm);
            rs=ptmt.executeQuery();
            Medical m = null;
            while(rs.next()) {
                m = new Medical();
                m = handle(rs, m);
                return m;
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
