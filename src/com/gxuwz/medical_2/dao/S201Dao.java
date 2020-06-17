package com.gxuwz.medical_2.dao;

import com.gxuwz.medical_2.database.DbUtil;
import com.gxuwz.medical_2.domain.S201.S201;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class S201Dao {
    protected S201 handle(ResultSet rs, S201 entity) throws SQLException {
        entity.setId(rs.getInt("id"));
        entity.setItemcode(rs.getString("itemcode"));
        entity.setItemname(rs.getString("itemname"));
        entity.setType(rs.getString("type"));
        return entity;
    }

    /**
     * 根据类型查找出所有对应的信息
     * @param type
     * @return
     * @throws Exception
     */
    public List<S201> findListByType(String type)throws Exception{
        List<S201> result = new ArrayList<S201>();
        String sql="select * from s201 where type=?";
        Connection conn = null;
        PreparedStatement ptmt = null;
        ResultSet rs = null;
        try{
            conn = DbUtil.getConn();
            ptmt = conn.prepareStatement(sql);
            int index = 1;
            ptmt.setString(index++,type);
            rs=ptmt.executeQuery();
            S201 entity =null;
            while(rs.next()){
                entity =new S201();
                result.add(handle(rs,entity));
            }
            return result;
        }catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("执行SQL["+sql+"]失败",e);
        }
    }

    /**
     * 慢病报销添加时调用该方法
     * 根据医院编号查找到该医院类型
     * @param itemcode
     * @return
     */
    public S201 queryById(String itemcode) {
        Connection conn =null;
        PreparedStatement ptmt =null;
        ResultSet rs =null;
        StringBuffer sb=new StringBuffer("select * from s201 where itemcode = ? ");
        try {
            conn = DbUtil.getConn();
            ptmt = conn.prepareStatement(sb.toString());
            int index=1;
            ptmt.setString(index, itemcode);
            rs = ptmt.executeQuery();
            S201 entity = new S201();
            if (rs != null && rs.next()) {
                entity.setId(rs.getInt("id"));
                entity.setItemcode(rs.getString("itemcode"));
                entity.setItemname(rs.getString("itemname"));
                entity.setType(rs.getString("type"));
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
}
