package com.gxuwz.medical_2.domain.role;

import com.gxuwz.medical_2.database.DbUtil;
import com.gxuwz.medical_2.domain.menu.Menu;
import com.gxuwz.medical_2.exception.RoleNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Role {
    /**
     * 权限编号,权限编号
     */
    private String roleid;
    private String roleName;

    /**
     * 权限编号 (自定义方法)
     */
    private Set<Menu> menus;

    private Connection conn;

    private PreparedStatement ptmt;

    private ResultSet rs;

    public Role(){

    }

    public Role(String roleid,String rolename){
        this.roleid =roleid;
        this.roleName =rolename;
    }

    public Role(String roleid) throws RoleNotFoundException,SQLException {
        load(roleid);
    }
    private void load(String roleid) throws RoleNotFoundException ,SQLException{
        try {
            conn = DbUtil.getConn();
            ptmt = conn.prepareStatement("select * from t_role where roleid=?");
            ptmt.setString(1, roleid);
            rs = ptmt.executeQuery();
            if(rs.next()){
                this.roleid = rs.getString("roleid");
                this.roleName = rs.getString("rolename");
            }else{
                throw new RoleNotFoundException("Role with id "
                        + roleid + " could not be loaded from the database.");
            }
        } catch (Exception e) {
            throw new RoleNotFoundException("Role with id " + roleid + " could not be loaded from the database.",e);
        }finally{
            DbUtil.close(rs, ptmt, conn);
        }

    }

    //自定义方法，通过roleid查出关联表中的menuid
    public Set<Menu> getMenus() {
        try {
            if (menus == null) {
                menus= new HashSet<Menu>();
                //进入getMenuids()方法进行查询
                List<String> menuids = getMenuids();
                for (String menuid : menuids) {
                    Menu menu = new Menu(menuid);
                    menus.add(menu);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return menus;
    }
    //从数据库中查出与roleid 对应的menuid
    private List<String> getMenuids()throws Exception {
        List<String> menuids = new ArrayList<String>();
        try {
            conn = DbUtil.getConn();
            ptmt = conn.prepareStatement("select menuid from t_role_menu where roleid=?");
            ptmt.setString(1, roleid);
            rs = ptmt.executeQuery();
            while(rs.next()){
                menuids.add(rs.getString(1));
            }
        } catch (SQLException e) {

            e.printStackTrace();

        } finally {
            DbUtil.close(rs, ptmt, conn);
        }
        return menuids;
    }

    //修改角色
    public void editRole(String[] menuids) throws Exception {
        try{
            conn =DbUtil.getConn();
            //1：开启手动提交事务
            conn.setAutoCommit(false);
            //2:更新角色信息到数据库
            eidtToDB();
            //3：删除原来角色关联
            unbindMenu();
            //4:循环关联菜单信息
            for(String menuid:menuids){
                bindMenu(menuid);
            }
            //4：提交事务
            conn.commit();
        }catch(SQLException e){
            conn.rollback();
            throw e;
        }finally{
           DbUtil.close(null,conn);
        }

    }
    //添加
    public void addRole(String rolename,String[]menuids) throws Exception {

        this.roleName = rolename;
        try{
            conn =DbUtil.getConn();
            //1：开启手动提交事务
            conn.setAutoCommit(false);
            //2:保存角色信息到数据库
            saveToDB();
            //3:循环关联菜单信息，将角色对应的权限存入t_role_menu中
            if(menuids!=null){
                for (String m : menuids) {
                    bindMenu(m);
                }
            }
            conn.commit();
        }catch (Exception e) {
            conn.rollback();
            throw e;
        }finally{
            DbUtil.close(ptmt,conn);
        }
    }
    //删除
    public void delRole(String roleid) throws Exception {
        this.roleid =roleid;
        try{
            conn =DbUtil.getConn();
            //1：开启手动提交事务
            conn.setAutoCommit(false);
            //2:删除角色信息
            deleteFromDB();
            //3:删除关联菜单信息
            unbindMenu();
            //4：提交事务
            conn.commit();
        }catch(SQLException e){
            conn.rollback();
            throw e;
        }finally{
            DbUtil.close(null,conn);
        }

    }

    private void bindMenu(String menuid) throws SQLException {
        try{
            StringBuffer sqlBuff=new StringBuffer("insert into t_role_menu(roleid,menuid)");
            sqlBuff.append("values(?,?)");
            ptmt=conn.prepareStatement(sqlBuff.toString());
            ptmt.setString(1, this.roleid);
            ptmt.setString(2, menuid);
            ptmt.executeUpdate();
        }catch(SQLException e){
            throw new SQLException("Failed to bind to menu!", e);
        }finally{
            DbUtil.close(ptmt,conn);
        }
    }

    //保存数据到数据库
    private void saveToDB() throws Exception {
        try{
            StringBuffer sb=new StringBuffer("insert into t_role(roleid,rolename)");
            sb.append("values(?,?)");
            conn = DbUtil.getConn();
            ptmt=conn.prepareStatement(sb.toString());
            ptmt.setString(1, this.roleid);
            ptmt.setString(2, this.roleName);
            ptmt.executeUpdate();
        }catch(SQLException e){
            throw new SQLException("Failed to insert into table !", e);
        }finally{
            DbUtil.close(ptmt,conn);
        }

    }

    private void unbindMenu() throws SQLException {
        try {
            StringBuffer sb=new StringBuffer("delete from t_role_menu where roleid=?");
            ptmt=conn.prepareStatement(sb.toString());
            ptmt.setString(1, this.roleid);
            ptmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to unbind to menu!", e);
        }finally{
            DbUtil.close(ptmt,null);
        }

    }
     //更新数据库角色表中的信息
    private void eidtToDB() throws SQLException {
        try{
            StringBuffer sb = new StringBuffer("update t_role t set t.rolename=?  where t.roleid=?");
            ptmt=conn.prepareStatement(sb.toString());
            ptmt.setString(1, this.roleName);
            ptmt.setString(2, this.roleid);
            ptmt.executeUpdate();
        }catch(SQLException e){
            throw new SQLException("Failed to update t_role  !", e);
        }finally{
            DbUtil.close(ptmt,null);
        }

    }


    private void deleteFromDB() throws Exception {
        try{
            StringBuffer sb=new StringBuffer("delete from t_role where roleid=?");
            ptmt=conn.prepareStatement(sb.toString());
            ptmt.setString(1, this.roleid);
            ptmt.executeUpdate();
        }catch(SQLException e){
            throw new SQLException("Failed to delete record from table !", e);
        }finally{
            DbUtil.close(ptmt,null);
        }

    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public PreparedStatement getPtmt() {
        return ptmt;
    }

    public void setPtmt(PreparedStatement ptmt) {
        this.ptmt = ptmt;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }
}
