package com.gxuwz.medical_2.dao;

import com.gxuwz.medical_2.database.DbUtil;
import com.gxuwz.medical_2.domain.role.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleDao{
	
	private Connection conn;
	private PreparedStatement ptmt;
	private ResultSet rs;

	/**
	 * 查询角色表的所有信息
	 * @return
	 */
	public List<Role> queryRoles() {
		List<Role> result = new ArrayList<Role>();
		StringBuilder sb=new StringBuilder();
		sb.append("select roleid,rolename from t_role");
		
		try {
			 conn = DbUtil.getConn();
			 ptmt = conn.prepareStatement(sb.toString());
			 rs = ptmt.executeQuery();
			 Role r =null;
			 while(rs.next()) {
				 r = new Role();
				 r.setRoleid(rs.getString("roleid"));
				 r.setRoleName(rs.getString("rolename"));
				 result.add(r);
			 }
		 }catch (Exception e) {
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
	 * 查询用户权限
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public List<Role> queryOjects(String sql,Object[]params)throws Exception{
		Connection conn =null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<Role> results=new ArrayList<Role>();
		try{
			conn=DbUtil.getConn();
			pstmt=conn.prepareStatement(sql);
			int index=1;
			if(params!=null){
			  for(Object param:params){
				  if(param instanceof String){
					  pstmt.setString(index++, (String)param);
				  }
				  if(param instanceof Integer){
					  pstmt.setInt(index++, (Integer)param);
				  }
				  if(param instanceof Float){
					  pstmt.setFloat(index++, (Float)param);
				  }
			  }
			}
			rs=pstmt.executeQuery();
			while(rs.next()){

				Role role=handle(rs);
				results.add(role);
			
			}
			return results;
		}catch(SQLException e){
			throw new SQLException("执行SQL["+sql+"]失败",e);
		}finally{
			DbUtil.close(rs, pstmt, conn);
		}
		
	}
	
	protected Role handle(ResultSet rs) throws SQLException {
		try{
			Role role=new Role();
			role.setRoleid(rs.getString("roleid"));
			role.setRoleName(rs.getString("rolename"));
			return role;
		}catch(SQLException e){
			
			throw new SQLException("结果集转为实例失败!",e);
		}
	}

	/**
	 * 通过userid找到关联表中对应的角色id
	 * @param userid
	 * @return
	 */
	public List<String> queryhasChecked(String userid) {
		List<String> result = new ArrayList<String>();
		StringBuilder sb=new StringBuilder();
		sb.append("select roleid from t_user_role where userid = ?");
		
		try {
			 conn = DbUtil.getConn();
			 ptmt = conn.prepareStatement(sb.toString());
			 ptmt.setString(1, userid);
			 rs = ptmt.executeQuery();
			 
			 while(rs.next()) {
				 String roleid=rs.getString("roleid");
				 result.add(roleid);
			 }
		 }catch (Exception e) {
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
	 * 查找role列表数据
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List<Role> queryRole(Integer currentPage, Integer pageSize) {
		List<Role> result = new ArrayList<Role>();
		 StringBuilder sb=new StringBuilder();
		 //从下标0开始的10条数据
		 int startNo = (currentPage-1)*pageSize;
		 sb.append("select * from t_role limit ?,?");
		 
		 try {
			 conn = DbUtil.getConn();
			 ptmt = conn.prepareStatement(sb.toString());
			 ptmt.setInt(1, startNo);
			 ptmt.setInt(2, pageSize);
			 rs = ptmt.executeQuery();
			 
			 Role r =null;
			 while(rs.next()) {
				 r = new Role();
				 r.setRoleid(rs.getString("roleid"));
				 r.setRoleName(rs.getString("rolename"));
				 result.add(r);
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

	/**
	 * 找到当前id对应的所有数据
	 * @param roleid
	 * @return
	 */
	public Role querybyId(String roleid) {
		StringBuffer sb=new StringBuffer();
		 sb.append("select * from t_role where roleid = ?");
		
		 try {
			conn = DbUtil.getConn();
			ptmt = conn.prepareStatement(sb.toString());
			int index=1;
			ptmt.setString(index, roleid);
			rs = ptmt.executeQuery();
			Role role=new Role();
			if (rs != null && rs.next()) {
				role.setRoleid(rs.getString("roleid"));
				role.setRoleName(rs.getString("rolename"));
			}
			return role;
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
	 }
}
