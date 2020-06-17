package com.gxuwz.medical_2.dao;

import com.gxuwz.medical_2.database.DbUtil;
import com.gxuwz.medical_2.domain.medicalCard.MedicalCard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicalExpenseDao {

    /**
     * 慢病报销调用该方法
     * 查询已经建档的参合农民
     * @param nongheCard
     * @param selectyear
     * @return
     */
    public MedicalCard queryillcard(String nongheCard, String selectyear)throws Exception {

            Connection conn =null;
            PreparedStatement ptmt=null;
            ResultSet rs=null;
            try {
                conn = DbUtil.getConn();
                StringBuffer sb=new StringBuffer("SELECT * from t_ill_card where 1=1 ");
                if(nongheCard!=null && !nongheCard.trim().equals("")){
                    sb.append(" and nongheCard = '"+nongheCard+"'"+"and startTime like '"+selectyear+"%'");
                }
                ptmt=conn.prepareStatement(sb.toString());
                rs=ptmt.executeQuery();
                MedicalCard model = new MedicalCard();
                if(rs.next()){
                    model.setIllCard(rs.getString("illCard"));
                    model.setStartTime(rs.getDate("startTime"));
                    model.setCardid(rs.getString("cardid"));
                    model.setNongheCard(rs.getString("nongheCard"));
                    model.setIllCode(rs.getString("illCode"));
                    return model;
                }
            } catch (SQLException e) {
                e.printStackTrace();

            }finally{
                DbUtil.close(rs, ptmt, conn);
            }
            return null;
        }

}
