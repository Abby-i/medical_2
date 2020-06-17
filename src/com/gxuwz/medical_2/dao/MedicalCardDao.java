package com.gxuwz.medical_2.dao;

import com.gxuwz.medical_2.database.DbUtil;
import com.gxuwz.medical_2.domain.medicalCard.MedicalCard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicalCardDao extends GenericDao<MedicalCard>{
    /**
     * 查询慢性病档案总记录数
     * @param illCard
     * @param idCard
     * @param illCode
     * @return
     */
    public int queryCount(String illCard, String idCard, String illCode) {
        Connection conn = null;
        PreparedStatement ptmt= null;
        ResultSet rs = null;

        StringBuffer sb=new StringBuffer("select count(*) from t_ill_card where 1=1 ");
        if(illCard!=null && !illCard.trim().equals("")){
            sb.append(" and illCard like '%"+illCard+"%'");
        }
        if(idCard!=null && !idCard.trim().equals("")){
            sb.append(" and idCard like '%"+idCard+"%'");
        }
        if(illCode!=null && !illCode.trim().equals("")){
            sb.append(" and illCode like '%"+illCode+"%'");
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

    public List<MedicalCard> queryAndSearchPages(String illCard, String idCard, String illCode, Integer currentPage,
                                             Integer pageSize) {
        Connection conn = null;
        PreparedStatement ptmt= null;
        ResultSet rs = null;
        List<MedicalCard> result = new ArrayList<MedicalCard>();
        StringBuilder sb=new StringBuilder();
        int startNo = (currentPage-1)*pageSize;
        sb.append("select * from t_ill_card where 1=1 ");
        if(illCard!=null && !illCard.trim().equals("")){
            sb.append(" and illCard like '%"+illCard+"%'");
        }
        if(idCard!=null && !idCard.trim().equals("")){
            sb.append(" and idCard like '%"+idCard+"%'");
        }
        if(illCode!=null && !illCode.trim().equals("")){
            sb.append(" and illCode like '%"+illCode+"%'");
        }
        sb.append("order by illCard asc limit ?,?");

        try {
            conn = DbUtil.getConn();
            ptmt = conn.prepareStatement(sb.toString());
            ptmt.setInt(1, startNo);
            ptmt.setInt(2, pageSize);
            rs = ptmt.executeQuery();
            MedicalCard model = null;
            while(rs.next()) {
                model = new MedicalCard();
                model.setId(rs.getInt("id"));
                model.setIllCard(rs.getString("illCard"));
                model.setNongheCard(rs.getString("nongheCard"));
                model.setCardid(rs.getString("cardid"));
                model.setIllCode(rs.getString("illCode"));
                model.setAttachment(rs.getString("attachment"));
                model.setStartTime(rs.getDate("startTime"));
                model.setEndTime(rs.getDate("endTime"));
                model.setRealName(rs.getString("realName"));
                result.add(model);
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


        return result;
    }


    /**
     * 修改方法
     * 根据id获取该id的一整行信息
     * @param
     * @return
     * @throws SQLException
     */
    public MedicalCard querybyid(String id) {
        Connection conn =null;
        PreparedStatement ptmt =null;
        ResultSet rs =null;
        StringBuffer sb=new StringBuffer();

        sb.append("select * from t_ill_card where id = ?");
        try {
            conn = DbUtil.getConn();
            ptmt = conn.prepareStatement(sb.toString());
            int index=1;
            ptmt.setInt(index, Integer.valueOf(id));
            rs = ptmt.executeQuery();
            MedicalCard entity = null;
            if (rs != null && rs.next()) {
                entity = new MedicalCard();
                entity.setId(rs.getInt("id"));
                entity.setIllCard(rs.getString("illCard"));
                entity.setNongheCard(rs.getString("nongheCard"));
                entity.setCardid(rs.getString("cardid"));
                entity.setIllCode(rs.getString("illCode"));
                entity.setAttachment(rs.getString("attachment"));
                entity.setStartTime(rs.getDate("startTime"));
                entity.setEndTime(rs.getDate("endTime"));
                entity.setRealName(rs.getString("realName"));
                return entity;
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
        return null;
    }

    /**
     * 慢性病报销调用该方法
     * 判断慢性病证是否过期
     * @return
     */
    public static String querySelectYear(String selectyear) {
        Connection conn =null;
        PreparedStatement ptmt =null;
        ResultSet rs =null;
        List<String> result = new ArrayList<String>();
        StringBuilder sb=new StringBuilder();
        sb.append("select illCode from t_ill_card where startTime like '"+selectyear+"%'");

        try {
            conn = DbUtil.getConn();
            ptmt = conn.prepareStatement(sb.toString());
            rs = ptmt.executeQuery();
            while(rs.next()) {
                String time=rs.getString("startTime");
                result.add(time);
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
        return String.valueOf(result);

    }

    @Override
    protected MedicalCard handle(ResultSet rs) throws SQLException {
        try{
            MedicalCard model = new MedicalCard();
            model.setId(rs.getInt("id"));
            model.setIllCard(rs.getString("illCard"));
            model.setNongheCard(rs.getString("nongheCard"));
            model.setCardid(rs.getString("cardid"));
            model.setIllCode(rs.getString("illCode"));
            model.setAttachment(rs.getString("attachment"));
            model.setStartTime(rs.getDate("startTime"));
            model.setEndTime(rs.getDate("endTime"));
            model.setRealName(rs.getString("realName"));
            return model;
        }catch(SQLException e){

            throw new SQLException("结果集转为实例失败!",e);
        }
    }
}
