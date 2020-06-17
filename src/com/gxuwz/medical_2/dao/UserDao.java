package com.gxuwz.medical_2.dao;

import com.gxuwz.medical_2.database.DbUtil;
import com.gxuwz.medical_2.domain.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private Connection conn;
    private PreparedStatement ptmt;
    private ResultSet rs;

    //查出表中数据
    public List<User> queryUser(Integer currentPage, Integer pageSize) {
        //数组列表
        List<User> userResult =new ArrayList<User>();
        StringBuilder stringBuilder =new StringBuilder();
        int startNum = (currentPage-1)*pageSize;
        //
        stringBuilder.append("select * from t_user limit ?,?");
        try {
            conn = DbUtil.getConn();
            ptmt = conn.prepareStatement(stringBuilder.toString());
            ptmt.setInt(1, startNum);
            ptmt.setInt(2, pageSize);
            rs = ptmt.executeQuery();

            User user =null;
            while(rs.next()) {
                user = new User();
                user.setUserid(rs.getString("userid"));
                user.setPwd(rs.getString("pwd"));
                user.setFullname(rs.getString("fullname"));
                user.setAgencode(rs.getString("agencode"));
                user.setStatus(rs.getString("status"));
                userResult.add(user);
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
        return userResult;
    }

    public User querybyId(String userid)throws Exception {
        StringBuffer sb=new StringBuffer();
        sb.append("select * from t_user where userid = ?");
        try {
            //获取链接
            conn = DbUtil.getConn();
            ptmt = conn.prepareStatement(sb.toString());
            ptmt.setString(1, userid);
            rs = ptmt.executeQuery();
            User user=new User();
            if (rs != null && rs.next()) {
                user.setFullname(rs.getString("fullname"));
                user.setPwd(rs.getString("pwd"));
                user.setUserid(rs.getString("userid"));
                user.setAgencode(rs.getString("agencode"));
                user.setStatus(rs.getString("status"));
            }
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }finally {
            DbUtil.close(rs, ptmt, conn);
        }
    }
}
