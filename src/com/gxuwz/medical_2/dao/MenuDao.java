package com.gxuwz.medical_2.dao;
import com.gxuwz.medical_2.database.DbUtil;
import com.gxuwz.medical_2.domain.menu.Menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuDao {
	
	private Connection conn;
	private PreparedStatement ptmt;
	private ResultSet rs;
	
	public List<Menu> queryMenus(){
		List<Menu> result = new ArrayList<Menu>();
		StringBuilder sb=new StringBuilder();
		sb.append("SELECT * FROM t_menu  order by menuid asc");
		try {
			 conn = DbUtil.getConn();
			 ptmt = conn.prepareStatement(sb.toString());
			 rs = ptmt.executeQuery();
			 
			 Menu m = null;
			 while(rs.next()) {
				 m = new Menu();
				 m.setMenuid(rs.getString("menuid"));
				 m.setMenuname(rs.getString("menuname"));
				 m.setMenupid(rs.getString("menupid"));
				 m.setUrl(rs.getString("url"));
				 m.setLevel(rs.getInt("level"));
				 result.add(m);
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
	
	public List<String> queryMenuid(String roleid){
		List<String> result =new ArrayList<String>();
		StringBuilder sb=new StringBuilder();
		sb.append("SELECT menuid from t_menu order by menuid asc");
		try {
			 conn = DbUtil.getConn();
			 ptmt = conn.prepareStatement(sb.toString());
			 rs = ptmt.executeQuery();
			 
			 while(rs.next()) {
				 String menuid=rs.getString("menuid");
				 result.add(menuid);
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

	public List<Menu> queryMenus(String roleid) {
		List<Menu> result = new ArrayList<Menu>();
		StringBuilder sb=new StringBuilder();
		sb.append("SELECT a.menuid,menuname,menupid,url,level from  t_menu a,t_role_menu b  WHERE a.menuid = b.menuid AND roleid = ? order by a.menuid asc");
		try {
			 conn = DbUtil.getConn();
			 ptmt = conn.prepareStatement(sb.toString());
			 ptmt.setString(1, roleid);
			 rs = ptmt.executeQuery();
			 
			 Menu m = null;
			 while(rs.next()) {
				 m = new Menu();
				 m.setMenuid(rs.getString("menuid"));
				 m.setMenuname(rs.getString("menuname"));
				 m.setMenupid(rs.getString("menupid"));
				 m.setUrl(rs.getString("url"));
				 m.setLevel(rs.getInt("level"));
				 result.add(m);
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

	public List<Menu> queryMenu(Integer currentPage, Integer pageSize) {
		 List<Menu> result = new ArrayList<Menu>();
		 StringBuilder sb=new StringBuilder();
		 int startNo = (currentPage-1)*pageSize;
		 sb.append("select * from t_menu limit ?,?");
		 
		 try {
			 conn = DbUtil.getConn();
			 ptmt = conn.prepareStatement(sb.toString());
			 ptmt.setInt(1, startNo);
			 ptmt.setInt(2, pageSize);
			 rs = ptmt.executeQuery();
			 
			 Menu m =null;
			 while(rs.next()) {
				 m = new Menu();
				 m.setMenuid(rs.getString("menuid"));
				 m.setMenuname(rs.getString("menuname"));
				 m.setMenupid(rs.getString("menupid"));
				 m.setUrl(rs.getString("url"));
				 m.setLevel(rs.getInt("level"));
				 result.add(m);
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
	
	public List<Menu> queryMenu(){
		 List<Menu> result = new ArrayList<Menu>();
		 StringBuilder sb=new StringBuilder();
		 sb.append("select * from t_menu ");
		 try {
			 conn = DbUtil.getConn();
			 ptmt = conn.prepareStatement(sb.toString());
			 rs = ptmt.executeQuery();
			 
			 Menu m =null;
			 while(rs.next()) {
				 m = new Menu();
				 m.setMenuid(rs.getString("menuid"));
				 m.setMenuname(rs.getString("menuname"));
				 m.setMenupid(rs.getString("menupid"));
				 m.setUrl(rs.getString("url"));
				 m.setLevel(rs.getInt("level"));
				 result.add(m);
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
			return result;
		}

	
	
}
