package com.gxuwz.medical_2.dao;

import com.gxuwz.medical_2.database.DbUtil;
import com.gxuwz.medical_2.domain.accountArchives.AccountArchives;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountArchivesDao extends GenericDao{
    @Override
    protected AccountArchives handle(ResultSet rs) throws SQLException {
        try{
            AccountArchives entity = new AccountArchives();
            entity.setCardid(rs.getString("cardid"));
            entity.setName(rs.getString("name"));
            entity.setRelationship(rs.getString("relationship"));
            entity.setSex(rs.getString("sex"));
            entity.setHealthstatus(rs.getString("healthstatus"));
            entity.setEducationlevel(rs.getString("educationlevel"));
            entity.setAge(rs.getInt("age"));
            entity.setBirthday(rs.getDate("birthday"));
            entity.setProperty(rs.getString("property"));
            entity.setIscountryside(rs.getString("iscountryside"));
            entity.setJob(rs.getString("job"));
            entity.setOrganization(rs.getString("organization"));
            entity.setPhone(rs.getString("phone"));
            entity.setAddress(rs.getString("address"));
            entity.setInformation(rs.getString("information"));
            entity.setHomeid(rs.getString("homeid"));
            entity.setHousehold(rs.getString("household"));
            entity.setNongheCard(rs.getString("nongheCard"));
            return entity;
        }catch(SQLException e){
            throw new SQLException("结果集转为实例失败!",e);
        }

    }

    //查找个人档案判断是否添加重复
    public boolean isExit(String cardid)throws Exception {
        Connection conn =null;
        PreparedStatement ptmt=null;
        ResultSet rs=null;
        try {
            conn = DbUtil.getConn();
            StringBuffer sb=new StringBuffer("select cardid from t_accountarchives where cardid = ?");
            ptmt=conn.prepareStatement(sb.toString());
            ptmt.setString(1, cardid);
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

    /**
     * 查询这个家庭号下的所有成员
     * @param homeid
     * @return
     */
    //统计家庭人口数
    public int queryHomeNumCount(String homeid){

        Connection conn = null;
        PreparedStatement ptmt= null;
        ResultSet rs = null;

        String sql = "select count(*) from t_accountarchives where homeid =" + homeid;
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

    //统计农业人口数
    public int queryPropertyCount(String homeid){

        Connection conn = null;
        PreparedStatement ptmt= null;
        ResultSet rs = null;
        //String sql = "select count(*) from t_accountarchives a,t_homearchives b  where a.homeid=b.homeid and a.property = '农业' and homeid = ?";
        String sql = "select count(*) from t_accountarchives a,t_homearchives b  where a.homeid=b.homeid and a.property = '农业' and a.homeid = ?";
        StringBuffer sb=new StringBuffer();
        sb.append(sql);
        int rowCount = 0;

        try {
            conn = DbUtil.getConn();
            ptmt = conn.prepareStatement(sb.toString());
            ptmt.setString(1, homeid);
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
     * 通过homeid找到最大的农合证号
     * 返回给AccountArchivesDao中的bindAdd方法
     * @param homeid
     * @return
     */
    public String createNongheCard(String homeid) {
        Connection conn =null;
        PreparedStatement ptmt =null;
        ResultSet rs =null;
        StringBuilder sb=new StringBuilder();
        sb.append("select MAX(nongheCard) from t_accountarchives where homeid like '"+homeid+"%'");
        try {
            conn = DbUtil.getConn();
            ptmt = conn.prepareStatement(sb.toString());
            rs = ptmt.executeQuery();
            while(rs.next()) {
                return rs.getString("MAX(nongheCard)");
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
     * 通过页面给到的cardid 查出该id下的成员信息
     * @param cardid
     * @return
     */
    public AccountArchives queryPersonById(String cardid) {
        Connection conn =null;
        PreparedStatement ptmt =null;
        ResultSet rs =null;
        StringBuffer sb=new StringBuffer();
        sb.append("select * from t_accountarchives where cardid = ?");

        try {
            conn = DbUtil.getConn();
            ptmt = conn.prepareStatement(sb.toString());
            int index=1;
            ptmt.setString(index, cardid);
            rs = ptmt.executeQuery();
            AccountArchives entity = null;
            if (rs != null && rs.next()) {
                entity = new AccountArchives();
                entity.setCardid(rs.getString("cardid"));
                entity.setName(rs.getString("name"));
                entity.setRelationship(rs.getString("relationship"));
                entity.setSex(rs.getString("sex"));
                entity.setHealthstatus(rs.getString("healthstatus"));
                entity.setEducationlevel(rs.getString("educationlevel"));
                entity.setAge(rs.getInt("age"));
                entity.setBirthday(rs.getDate("birthday"));
                entity.setProperty(rs.getString("property"));
                entity.setIscountryside(rs.getString("iscountryside"));
                entity.setJob(rs.getString("job"));
                entity.setOrganization(rs.getString("organization"));
                entity.setPhone(rs.getString("phone"));
                entity.setAddress(rs.getString("address"));
                entity.setInformation(rs.getString("information"));
                entity.setHomeid(rs.getString("homeid"));
                entity.setHousehold(rs.getString("household"));
                entity.setNongheCard(rs.getString("nongheCard"));
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

    /**
     * 缴费登记的detail方法调用
     * 查询该homeid下的全部家庭成员
     * @param homeid
     * @return
     */
    public List<String> queryAllPersonid(String homeid) {
        Connection conn = null;
        PreparedStatement ptmt= null;
        ResultSet rs = null;
        List<String> result =new ArrayList<String>();
        StringBuilder sb=new StringBuilder();
        sb.append("SELECT cardid from t_accountarchives where homeid = ? order by cardid asc");
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
     * 缴费登记add.jsp中调用该方法
     * 根据cardid查找出成员所有信息
     * @param cardid
     * @return
     */
    public List<AccountArchives> quertByCardID(String cardid) {
        Connection conn =null;
        PreparedStatement ptmt =null;
        ResultSet rs =null;
        StringBuffer sb=new StringBuffer();
        List<AccountArchives> result = new ArrayList<AccountArchives>();
        sb.append("select * from t_accountarchives where cardid=? order by nongheCard ");
        try {
            conn = DbUtil.getConn();
            ptmt = conn.prepareStatement(sb.toString());
            int index=1;
            ptmt.setString(index, cardid);
            rs = ptmt.executeQuery();
            AccountArchives entity = null;
            if (rs != null && rs.next()) {
                entity = new AccountArchives();
                entity.setCardid(rs.getString("cardid"));
                entity.setName(rs.getString("name"));
                entity.setRelationship(rs.getString("relationship"));
                entity.setSex(rs.getString("sex"));
                entity.setHealthstatus(rs.getString("healthstatus"));
                entity.setEducationlevel(rs.getString("educationlevel"));
                entity.setAge(rs.getInt("age"));
                entity.setBirthday(rs.getDate("birthday"));
                entity.setProperty(rs.getString("property"));
                entity.setIscountryside(rs.getString("iscountryside"));
                entity.setJob(rs.getString("job"));
                entity.setOrganization(rs.getString("organization"));
                entity.setPhone(rs.getString("phone"));
                entity.setAddress(rs.getString("address"));
                entity.setInformation(rs.getString("information"));
                entity.setHomeid(rs.getString("homeid"));
                entity.setHousehold(rs.getString("household"));
                entity.setNongheCard(rs.getString("nongheCard"));
                result.add(entity);
                //return entity;
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
    public String queryNongheBycardid(String cardid) {

        Connection conn = null;
        PreparedStatement ptmt= null;
        ResultSet rs = null;
        StringBuilder sb=new StringBuilder();
        sb.append("SELECT nongheCard from t_accountarchives where cardid = ? order by nongheCard asc");
        try {
            conn = DbUtil.getConn();
            ptmt = conn.prepareStatement(sb.toString());
            ptmt.setString(1, cardid);
            rs = ptmt.executeQuery();

            while(rs.next()) {
                String nongheCard=rs.getString("nongheCard");
                return nongheCard;
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
     * 新增慢病档案调用该方法
     * 根据姓名查找到该户主下的成员
     * @param name
     * @return
     */
    public int queryCount(String name) {
        Connection conn = null;
        PreparedStatement ptmt= null;
        ResultSet rs = null;
        StringBuffer sb=new StringBuffer("select count(*) from t_accountarchives where 1=1 ");
        if(name!=null && !name.trim().equals("")){
            sb.append(" and name like '%"+name+"%'");
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
    public List<AccountArchives> queryAndSearchPages(String name, Integer currentPage, Integer pageSize) {
        Connection conn = null;
        PreparedStatement ptmt= null;
        ResultSet rs = null;
        List<AccountArchives> result = new ArrayList<AccountArchives>();
        StringBuilder sb=new StringBuilder();
        int startNo = (currentPage-1)*pageSize;
        sb.append("select * from t_accountarchives where 1=1 ");
        if(name!=null && !name.trim().equals("")){
            sb.append(" and name like '%"+name+"%'");
        }
        sb.append("order by cardid asc limit ?,?");
        try {
            conn = DbUtil.getConn();
            ptmt = conn.prepareStatement(sb.toString());
            ptmt.setInt(1, startNo);
            ptmt.setInt(2, pageSize);
            rs = ptmt.executeQuery();

            AccountArchives entity = null;
            while(rs.next()) {
                entity = new AccountArchives();
                entity.setCardid(rs.getString("cardid"));
                entity.setName(rs.getString("name"));
                entity.setRelationship(rs.getString("relationship"));
                entity.setSex(rs.getString("sex"));
                entity.setHealthstatus(rs.getString("healthstatus"));
                entity.setEducationlevel(rs.getString("educationlevel"));
                entity.setAge(rs.getInt("age"));
                entity.setBirthday(rs.getDate("birthday"));
                entity.setProperty(rs.getString("property"));
                entity.setIscountryside(rs.getString("iscountryside"));
                entity.setJob(rs.getString("job"));
                entity.setOrganization(rs.getString("organization"));
                entity.setPhone(rs.getString("phone"));
                entity.setAddress(rs.getString("address"));
                entity.setInformation(rs.getString("information"));
                entity.setHomeid(rs.getString("homeid"));
                entity.setHousehold(rs.getString("household"));
                entity.setNongheCard(rs.getString("nongheCard"));
                result.add(entity);
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

}
