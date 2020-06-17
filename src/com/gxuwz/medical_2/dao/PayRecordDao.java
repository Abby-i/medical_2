package com.gxuwz.medical_2.dao;

import com.gxuwz.medical_2.database.DbUtil;
import com.gxuwz.medical_2.domain.FamilyArchives.FamilyArchives;
import com.gxuwz.medical_2.domain.Policy.Policy;
import com.gxuwz.medical_2.domain.getAge.AgeUtil;
import com.gxuwz.medical_2.domain.payRecord.PayRecord;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PayRecordDao {
    AgeUtil ageUtil = new AgeUtil();
    protected PayRecord handle(ResultSet rs,PayRecord model) throws SQLException {
        model.setHomeid(rs.getString("homeid"));
        model.setHousehold(rs.getString("household"));
        model.setCardid(rs.getString("cardid"));
        model.setJoinNum(rs.getString("joinNum"));
        model.setInvoiceNum(rs.getString("invoiceNum"));
        model.setName(rs.getString("name"));
        model.setPayAccount(rs.getDouble("payAccount"));
        model.setPayTime(rs.getDate("payTime"));
        model.setOperator(rs.getString("operator"));
        return model;
    }

    /**
     * 查询这个家庭成员是否已经缴费
     * @param homeid
     * @return
     */
    public int queryhasPay(String homeid){
        Connection conn = null;
        PreparedStatement ptmt= null;
        ResultSet rs = null;

        String sql = "select count(*) from t_pay_record where homeid =" + homeid;
        StringBuffer sb=new StringBuffer();
        sb.append(sql);
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

    /**
     * 查询某年家庭参合成员数
     * @param homeid
     * @return
     */
    //某年某家人的查身份证
    public List<String> queryHasPayPersonid(String homeid) {
        Connection conn = null;
        PreparedStatement ptmt= null;
        ResultSet rs = null;
        List<String> result =new ArrayList<String>();
        StringBuilder sb=new StringBuilder();

        sb.append("SELECT cardid from t_pay_record where homeid=? and payTime like '"+AgeUtil.getNowyear()+"%' order by cardid asc");
        try {
            conn = DbUtil.getConn();
            ptmt = conn.prepareStatement(sb.toString());
            ptmt.setString(1, homeid);
            rs = ptmt.executeQuery();

            while(rs.next()) {
                String cardid=rs.getString("cardid");
                result.add(cardid);
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
        return result;
    }

    /**
     * 计算呢勾选人数缴费金额
     * @param payNum
     * @param payAccount
     * @return
     */
    //计算指定人数的缴费总金额
    public double calAccount(int payNum, double payAccount) {
        double amount = 0.0;
        try {
            if(payNum !=0 && payAccount != 0) {
                amount = (payNum * payAccount);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return amount;
    }
}
