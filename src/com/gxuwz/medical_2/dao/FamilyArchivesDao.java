package com.gxuwz.medical_2.dao;

import com.gxuwz.medical_2.database.DbUtil;
import com.gxuwz.medical_2.domain.FamilyArchives.FamilyArchives;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FamilyArchivesDao extends GenericDao <FamilyArchives>{
    protected FamilyArchives handle(ResultSet rs, FamilyArchives entity) throws SQLException {
        try{
            entity.setId(rs.getString("id"));
            entity.setCountyid(rs.getString("countyid"));
            entity.setTownid(rs.getString("townid"));
            entity.setVillageid(rs.getString("villageid"));
            entity.setGroupid(rs.getString("groupid"));
            entity.setHomeid(rs.getString("homeid"));
            entity.setProperty(rs.getString("property"));
            entity.setHousehold(rs.getString("household"));
            entity.setFamilysize(rs.getInt("familysize"));
            entity.setFarmersize(rs.getInt("farmersize"));
            entity.setAddress(rs.getString("address"));
            entity.setCreatetime(rs.getDate("createtime"));
            entity.setRegistrar(rs.getString("registrar"));

            return entity;
        }catch(SQLException e){
            throw new SQLException("结果集转为实例失败!",e);
        }
    }

    /**
     * 添加方法
     * @param groupid
     * @return
     */
    //根据组编号生成家庭编号
    public String ToFamilyid(String groupid){
        Connection conn =null;
        PreparedStatement ptmt =null;
        ResultSet rs =null;
        StringBuilder sb=new StringBuilder();
        sb.append("select MAX(homeid) from t_homearchives where homeid like '"+groupid+"%'");
        try {
            conn = DbUtil.getConn();
            ptmt = conn.prepareStatement(sb.toString());
            rs = ptmt.executeQuery();
            while(rs.next()) {
                return rs.getString("MAX(homeid)");
            }
        } catch (Exception e) {
            e.printStackTrace();
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
     *根据户主姓名查询 查询该户主下的所有家庭成员
     * @param household
     * @return
     */
    public static int queryCount(String household) {
        Connection conn = null;
        PreparedStatement ptmt= null;
        ResultSet rs = null;
        StringBuffer sb=new StringBuffer("select count(*) from t_homearchives where 1=1 ");
        if(household!=null && !household.trim().equals("")){
            sb.append(" and household like '%"+household+"%'");
        }
        int rowCount = 0;
        try {
            conn = DbUtil.getConn();
            ptmt = conn.prepareStatement(sb.toString());
            rs = ptmt.executeQuery();
            while(rs.next()){
                rowCount = rs.getInt(1);
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

    public List<FamilyArchives> queryFamilyarchives(Integer currentPage, Integer pageSize, String household) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs =null;
        List<FamilyArchives> result = new ArrayList<FamilyArchives>();
        StringBuilder sql=new StringBuilder();
        int startNo = (currentPage-1)*pageSize;
        sql.append("select * from t_homearchives where 1=1");
        if(household!=null && !household.equals("")){
            sql.append(" and household like '%"+household+"%'");
        }
        sql.append(" limit ?,? ");
        try {
            conn = DbUtil.getConn();
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setInt(1, startNo);
            pstmt.setInt(2, pageSize);
            rs = pstmt.executeQuery();
            FamilyArchives entity =null;
            while(rs.next()) {
                entity =new FamilyArchives();
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

    @Override
    protected FamilyArchives handle(ResultSet rs) throws SQLException {
        try{
            FamilyArchives entity=new FamilyArchives();
            entity.setId(rs.getString("id"));
            entity.setCountyid(rs.getString("countyid"));
            entity.setTownid(rs.getString("townid"));
            entity.setVillageid(rs.getString("villageid"));
            entity.setGroupid(rs.getString("groupid"));
            entity.setHomeid(rs.getString("homeid"));
            entity.setProperty(rs.getString("property"));
            entity.setHousehold(rs.getString("household"));
            entity.setFamilysize(rs.getInt("familysize"));
            entity.setFarmersize(rs.getInt("farmersize"));
            entity.setAddress(rs.getString("address"));
            entity.setCreatetime(rs.getDate("createtime"));
            entity.setRegistrar(rs.getString("registrar"));

            return entity;
        }catch(SQLException e){
            throw new SQLException("结果集转为实例失败!",e);
        }
    }
}
