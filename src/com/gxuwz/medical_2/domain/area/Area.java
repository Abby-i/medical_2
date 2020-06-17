package com.gxuwz.medical_2.domain.area;

import com.gxuwz.medical_2.database.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Area {
    /**
     * 乡镇区域编码:县、镇、村、组
     */
    private String areacode;
    /**
     * 上一级编号
     */
    private String areaupcode;
    /**
     * 行政区域名称
     */
    private String areaname;
    /**
     * 行政区域级别：1表示县级，2表示镇级，3表示村，4表示组
     */
    private int grade;
    /**
     * 上一级(自定义)
     */
    private Area  parent;

    public Area(){
    }
    public Area(String areaid)throws Exception{
        this.areacode=areaid;
        loadDB(areaid);
    }

    /**
     * 添加行政区域信息
     */
    public void addArea(String areapid, String areaname)throws Exception {
        Connection conn=null;
        try{
            //加载父节点ID的对象
            this.parent=new Area(areapid);
            this.areaname=areaname;
            this.grade=this.parent.getGrade()+1;
            //自动编号
            this.areacode=createAreaid();
            conn =DbUtil.getConn();
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
    //自动生成行政区域编号
    private String createAreaid()throws Exception {
        Connection conn =null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        String areapid=this.parent.getAreacode();
        int grade=this.parent.getGrade()+1;
        String sql="select max(areacode) from t_area where areacode like'"+areapid+"%' and grade="+grade ;

        try{
            conn=DbUtil.getConn();
            pstmt=conn.prepareStatement(sql);
            rs=pstmt.executeQuery();
            String maxcode="";
            Integer number=1;
            if(rs.next()){
                maxcode=rs.getString(1);
            }

            if(maxcode!=null){
                int beginIndex=maxcode.length()-2;
                String no=maxcode.substring(beginIndex);
                number=Integer.parseInt(no);
                ++number;
                //使用0补足位数
                no=String.format("%02d", number);
                maxcode=this.parent.getAreacode()+no;
            }else{
                String no=String.format("%02d", number);
                maxcode=this.parent.getAreacode()+no;
            }
            return maxcode;
        }catch(SQLException e){

            throw e;
        }finally{
            DbUtil.close(rs, pstmt, conn);
        }
    }
    //保存到数据库
    private void saveToDB(Connection conn) throws SQLException{

        PreparedStatement pstmt=null;
        try{
            StringBuffer sqlBuff=new StringBuffer("insert into t_area(areacode,areaname,grade,areaupcode)");
            sqlBuff.append("values(?,?,?,?)");
            System.out.println(this.parent.areacode);
            pstmt=conn.prepareStatement(sqlBuff.toString());
            pstmt.setString(1, this.areacode);
            pstmt.setString(2, this.areaname);
            pstmt.setInt(3, this.grade);
            pstmt.setString(4, this.parent.areacode);
            pstmt.executeUpdate();
        }catch(SQLException e){
            throw e;
        }finally{
            DbUtil.close(pstmt,null);
        }
    }

    /**
     * 修改方法
     * @param areaid
     * @throws SQLException
     */
    private void loadDB(String areaid)throws Exception{
        Connection conn =null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try{
            conn= DbUtil.getConn();
            pstmt=conn.prepareStatement("select * from t_area where areacode=?");
            int index=1;
            pstmt.setString(index, this.areacode);
            rs=pstmt.executeQuery();
            if(rs.next()){
                this.areacode=rs.getString("areacode");
                this.areaname=rs.getString("areaname");
                this.grade=rs.getByte("grade");
                this.areaupcode=rs.getString("areaupcode");

            }
        }catch(SQLException e){
            throw e;
        }finally{
            DbUtil.close(rs, pstmt, conn);
        }
    }
    //将修改的数据给到数据库
    public void editToDB(String areacode,String areaname,int grade) throws Exception {
        Connection conn=null;
        PreparedStatement ptmt=null;
        try {
            conn = DbUtil.getConn();
            conn.setAutoCommit(false);
            StringBuffer sqlBuff=new StringBuffer("update t_area t set t.areacode=?,t.areaname=?,grade=?  where t.areacode=?");
            ptmt=conn.prepareStatement(sqlBuff.toString());
            ptmt.setString(1, areacode);
            ptmt.setString(2, areaname);
            ptmt.setInt(3, grade);
            ptmt.setString(4, areacode);
            ptmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            if(conn!=null){
                conn.rollback();
            }
            throw new SQLException("Failed to update t_area  !", e);
        }finally{
            DbUtil.close(ptmt, conn);
        }

    }

    /**
     * 删除方法
     * @param areacode
     * @throws SQLException
     */
    //删除数据库数据
    public void delArea(String areacode) throws Exception {
        Connection conn=null;
        PreparedStatement ptmt=null;
        this.areacode = areacode;
        try{
            conn = DbUtil.getConn();
            conn.setAutoCommit(false);

            StringBuffer sqlBuff=new StringBuffer("delete from t_area where areacode=?");
            ptmt=conn.prepareStatement(sqlBuff.toString());
            ptmt.setString(1, this.areacode);
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
     * 属性的getter setter方法
     * @return
     */
    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    public String getAreaupcode() {
        return areaupcode;
    }

    public void setAreaupcode(String areaupcode) {
        this.areaupcode = areaupcode;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Area getParent() {
        return parent;
    }

    public void setParent(Area parent) {
        this.parent = parent;
    }


}
