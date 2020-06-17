package com.gxuwz.medical_2.domain.medicalCard;

import com.gxuwz.medical_2.database.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MedicalCard {
    private int id;
    private String illCard;//慢性病证号
    private String nongheCard;//农合证号
    private String cardid;//身份证号*
    private String illCode;//疾病编码*
    private String realName;//文件名*
    private String attachment;//证明附件（存到数据库的名字）*
    private Date startTime;//起始时间*
    private Date endTime;//终止时间*

    public MedicalCard(String illCard, String nongheCard, String cardid, String illCode, String realName, String attachment, Date startTime, Date endTime) {
        this.illCard = illCard;
        this.nongheCard = nongheCard;
        this.cardid = cardid;
        this.illCode = illCode;
        this.realName = realName;
        this.attachment = attachment;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public MedicalCard(int id, String illCard, String nongheCard, String cardid, String illCode, String realName, String attachment, Date startTime, Date endTime) {
        this.id = id;
        this.illCard = illCard;
        this.nongheCard = nongheCard;
        this.cardid = cardid;
        this.illCode = illCode;
        this.realName = realName;
        this.attachment = attachment;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public MedicalCard() {

    }


    /**
     * 增加慢性病报销档案
     * @throws Exception
     */
    public void add() throws Exception {
        Connection conn=null;
        try{
            conn = DbUtil.getConn();
            //开启手动提交事务
            conn.setAutoCommit(false);
            //保存到数据库
            saveToDB(conn);
            //提交事务
            conn.commit();
        }catch (Exception e) {
            if(conn!=null){
                conn.rollback();
            }
            throw e;
        }finally{
            DbUtil.close(null,conn);
        }
    }

    private void saveToDB(Connection conn) throws SQLException {
        Date date=new Date();
        SimpleDateFormat sim=new SimpleDateFormat("yyyyMMdd");
        String illCard=sim.format(date)+(int)(Math.random()*(999-100-1));
        this.illCard = illCard;
        PreparedStatement ptmt=null;
        try{
            StringBuffer sqlBuff=new StringBuffer("insert into t_ill_card("
                    + "illCard,nongheCard,cardid,illCode,realName,attachment,startTime,endTime)");
            sqlBuff.append("values(?,?,?,?,?,?,?,?)");
            ptmt=conn.prepareStatement(sqlBuff.toString());
            ptmt.setString(1, this.illCard);
            ptmt.setString(2, this.nongheCard);
            ptmt.setString(3, this.cardid);
            ptmt.setString(4, this.illCode);
            ptmt.setString(5, this.realName);
            ptmt.setString(6, this.attachment);
            ptmt.setDate(7, new java.sql.Date(this.startTime.getTime()));
            ptmt.setDate(8, new java.sql.Date(this.endTime.getTime()));
            ptmt.executeUpdate();
        }catch(SQLException e){
            throw e;
        }finally{
            DbUtil.close(ptmt,null);
        }

    }

    /**
     * 修改慢病档案对应数据库操作方法
     * @throws Exception
     */
    public void edit() throws Exception {
        Connection conn = null;
        try {
            conn = DbUtil.getConn();
            // 1：开启手动提交事务
            conn.setAutoCommit(false);
            eidtToDB(conn);
            // 4：提交事务
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            DbUtil.close(null,conn);
        }
    }

    private void eidtToDB(Connection conn) throws Exception {
        PreparedStatement ptmt = null;
        try{
            StringBuffer sb = new StringBuffer("update t_ill_card t set "
                    + "illCard=?,nongheCard=?,idCard=?,illCode=?,realName=?,attachment=?,startTime=?,endTime=? "
                    + " where id=?");
            ptmt=conn.prepareStatement(sb.toString());
            ptmt.setString(1, this.illCard);
            ptmt.setString(2, this.nongheCard);
            ptmt.setString(3, this.cardid);
            ptmt.setString(4, this.illCode);
            ptmt.setString(5, this.realName);
            ptmt.setString(6, this.attachment);
            ptmt.setDate(7, new java.sql.Date(this.startTime.getTime()));
            ptmt.setDate(8, new java.sql.Date(this.endTime.getTime()));
            ptmt.setInt(9, this.id);
            ptmt.executeUpdate();
        }catch(SQLException e){
            throw new SQLException("Failed to update t_ill_card  !", e);
        }finally{
            DbUtil.close(ptmt,null);
        }
    }

    /**
     * 删除慢病档案对应的数据库操作方法
     * @param id2
     * @throws Exception
     */
    public void del(String id2) throws Exception {
        Connection conn=null;
        PreparedStatement ptmt=null;
        try{
            conn = DbUtil.getConn();
            conn.setAutoCommit(false);

            StringBuffer sqlBuff=new StringBuffer("delete from t_ill_card where id=?");
            ptmt=conn.prepareStatement(sqlBuff.toString());
            int id = Integer.valueOf(id2);
            ptmt.setInt(1, id);
            ptmt.executeUpdate();
            conn.commit();
        }catch(SQLException e){
            conn.rollback();
            throw new SQLException("Failed to delete record from table !", e);
        }finally {
            DbUtil.close(null,conn);
        }
    }


    /**
     * getter setter
     * @return
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIllCard() {
        return illCard;
    }

    public void setIllCard(String illCard) {
        this.illCard = illCard;
    }

    public String getNongheCard() {
        return nongheCard;
    }

    public void setNongheCard(String nongheCard) {
        this.nongheCard = nongheCard;
    }

    public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid;
    }

    public String getIllCode() {
        return illCode;
    }

    public void setIllCode(String illCode) {
        this.illCode = illCode;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
