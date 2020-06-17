package com.gxuwz.medical_2.domain.S201;

import com.gxuwz.medical_2.database.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class S201 {
    private int id;
    private String itemcode;
    private String itemname;
    private String type;

    public S201() {
    }

    public S201(int id, String itemcode, String itemname, String type) {
        this.id = id;
        this.itemcode = itemcode;
        this.itemname = itemname;
        this.type = type;
    }

    /**
     * 医疗机构
     根据编号找到类型编号
     * @param itemcode
     * @param type
     * @return
     * @throws Exception
     */
    public String selectName(String itemcode,String type)throws Exception {
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs =null;
        try{
            String sql="select * from s201 where itemcode=? and type=?";
            conn= DbUtil.getConn();
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, itemcode);
            pstmt.setString(2, type);
            rs=pstmt.executeQuery();
            if(rs.next()){
                this.id=rs.getInt(1);
                this.itemcode=rs.getString(2);
                this.itemname=rs.getString(3);
            }
            return this.itemname;
        }catch(SQLException e){
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * getter setter方法
     * @return
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
