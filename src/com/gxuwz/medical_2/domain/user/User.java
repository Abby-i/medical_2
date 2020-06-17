package com.gxuwz.medical_2.domain.user;

import com.gxuwz.medical_2.database.DbUtil;
import com.gxuwz.medical_2.domain.role.Role;
import com.gxuwz.medical_2.exception.UserNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户信息实体类
 *
 */
public class User {
   	/**
     * 工号，密码，姓名，所在农合机构编码
     */
    private String userid;
    private String pwd;
    private String fullname;
    private String agencode;
    private String status;

     /**
      * 自定义(数据库)
      */
    private Connection conn;//连接
    private PreparedStatement ptmt;//预处理
    private ResultSet rs;//结果集
    /**
     * 用户对应角色列表
     */
    private Set<Role> roles;

    public User() { }

    public User(String userid, String pwd, String fullname, String status, String agencode) {
        this.userid = userid;
        this.pwd = pwd;
        this.fullname = fullname;
        this.status = status;
        this.agencode = agencode;
    }

    /**
     * 获取页面传入数据，判断用户登录
     * @param userId
     * @param password
     * @throws UserNotFoundException
     */
    public User(String userId, String password) throws UserNotFoundException, SQLException {
        try {
            conn = DbUtil.getConn();
            ptmt = conn.prepareStatement("select * from t_user where userid=? and pwd=?");
            ptmt.setString(1, userId);
            ptmt.setString(2, password);
            rs = ptmt.executeQuery();
            if (rs != null && rs.next()) {
                this.fullname = rs.getString("fullname");
                this.pwd = rs.getString("pwd");
                this.userid = rs.getString("userid");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new UserNotFoundException("User with id " + userid
                    + " could not be loaded from the database.");
        }finally {
            DbUtil.close(rs,ptmt,conn);
        }
    }
    //自定义空的roles列表，通过getRoleids获取用户对应的角色id
    public Set<Role> getRoles() {
        try{
            if(roles == null){
                roles = new HashSet<Role>();
                //通过进入getRoleids()方法，得到用户角色
                List<String> roleids = getRoleids();
                for(String roleid : roleids){
                    Role role = new Role(roleid);
                    roles.add(role);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return roles;
    }
    //通过userid在t_user_role关联表里获取roleid
    private List<String> getRoleids() throws SQLException{
        List<String> roleids = new ArrayList<String>();

        try {
            conn = DbUtil.getConn();
            ptmt = conn.prepareStatement("select roleid from t_user_role where userid=?");
            ptmt.setString(1, userid);
            rs = ptmt.executeQuery();
            while(rs.next()){
                roleids.add(rs.getString(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DbUtil.close(rs, ptmt, conn);
        }
        return roleids;
    }

    /**
     * 添加用户
     * @param roleids
     * @throws Exception
     */
    public void addUser(String[] roleids) throws Exception {
        try {
            conn = DbUtil.getConn();
            conn.setAutoCommit(false);
            //保存用户信息
            SaveUser();
            //建立角色关联
            if(roleids!=null){
                for(String roleid:roleids){
                    bindRole(roleid);
                }
            }
            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            throw e;
        }finally{
            DbUtil.close(null,conn);
        }

    }
    //保存用户信息到数据库
    private void SaveUser() throws Exception {
        try{
            StringBuilder sb=new StringBuilder();
            sb.append("insert into t_user");
            sb.append("(userid,fullname,pwd,agencode,status)");
            sb.append("values(?,?,?,?,?)");
            ptmt=conn.prepareStatement(sb.toString());
            ptmt.setString(1, this.userid);
            ptmt.setString(2, this.fullname);
            ptmt.setString(3, this.pwd);
            ptmt.setString(4, this.agencode);
            ptmt.setString(5, this.status);
            ptmt.executeUpdate();
        }catch (Exception e) {
            throw e;
        }finally{
            DbUtil.close(ptmt,null);
        }
    }
    //（修改，保存方法共用）保存用户信息到用户角色关联表
    private void bindRole(String roleid) throws SQLException {

        try {
            StringBuilder sb=new StringBuilder("insert into t_user_role(userid,roleid)");
            sb.append("values(?,?)");
            ptmt = conn.prepareStatement(sb.toString());
            ptmt.setString(1, this.userid);
            ptmt.setString(2, roleid);
            ptmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to bind to Role!", e);
        }finally{
            DbUtil.close(ptmt,null);
        }
    }

    /**
     * 修改用户信息
     * @param roleids
     * @throws Exception
     */
    //将修改后的用户信息保存到数据库
    public void editUser(String[] roleids) throws Exception {
        try {
            conn = DbUtil.getConn();
            conn.setAutoCommit(false);
            //更新用户信息到数据库
            this.editToDB();
            //删除原有关联菜单信息
            this.unbindRole();
            for (String roleid : roleids) {
                //再次绑定关联用户、角色
                //(将修改后的用户数据给到用户角色关联表添加方法)
                this.bindRole(roleid);
            }
            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            throw e;
        }finally{
            DbUtil.close(null,conn);
        }
    }
    //将修改后的用户数据保存到数据库
    private void editToDB() throws SQLException {

        try {
            StringBuffer sb=new StringBuffer("update t_user t set t.fullname=?,t.pwd=?,agencode=?,status=?  where t.userid=?");
            ptmt = conn.prepareStatement(sb.toString());
            ptmt.setString(1, this.fullname);
            ptmt.setString(2, this.pwd);
            ptmt.setString(3, this.agencode);
            ptmt.setString(4, this.status);
            ptmt.setString(5, this.userid);
            ptmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to update t_user  !", e);
        }finally{
            DbUtil.close(ptmt,null);
        }
    }
    //（修改，删除方法共用）删除原有用户角色关联表中的对应用户信息
    private void unbindRole() throws SQLException {

        try {
            StringBuffer sb=new StringBuffer("delete from t_user_role where userid=?");
            ptmt = conn.prepareStatement(sb.toString());
            ptmt.setString(1, this.userid);
            ptmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to unbind to Role !", e);
        }finally{
            DbUtil.close(ptmt,null);
        }
    }

    /**
     * 删除选中的用户信息
     * @param userid
     * @throws Exception
     */
    //接收servlet传入的id，调用数据库数据删除方法
    public void delUser(String userid) throws Exception {
        this.userid =userid;
        try{
            conn =DbUtil.getConn();
            //1：开启手动提交事务
            conn.setAutoCommit(false);
            //2:删除用户信息
            deleteDB();
            //3:删除用户角色关联表信息
            unbindRole();
            //4：提交事务
            conn.commit();
        }catch(SQLException e){
            conn.rollback();
            throw e;
        }finally{
            DbUtil.close(null,conn);
        }
    }
    //实现数据库user表数据的删除
    private void deleteDB() throws Exception {
        StringBuffer sb=new StringBuffer("delete from t_user where userid=?");
        try {
            ptmt=conn.prepareStatement(sb.toString());
            ptmt.setString(1, this.userid);
            ptmt.executeUpdate();
        } catch(SQLException e){
            throw new SQLException("Failed to delete record from table !", e);
        }finally{
            DbUtil.close(ptmt,null);
        }

    }


    /**
     * 属性的getter,setter方法
     * @return
     */
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAgencode() {
        return agencode;
    }

    public void setAgencode(String agencode) {
        this.agencode = agencode;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


}
