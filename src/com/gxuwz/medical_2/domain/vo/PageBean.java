package com.gxuwz.medical_2.domain.vo;


import com.gxuwz.medical_2.database.DbUtil;

import java.sql.*;
import java.util.List;

/**
 * 分页对象。用于封装当前页的分页相关的所有数据
 * @author APPle
 *
 */
public class PageBean {

	private List data;
	private Integer firstPage;//首页
	private Integer prePage;//上一页
	private Integer nextPage;//下一页
	private Integer totalPage;//末页、总页数
	private Integer currentPage;//当前页
	private Integer totalCount;//总记录数
	private Integer pageSize;//每页显示的记录数
	
	
	public PageBean() {

	}
	
	public PageBean(List data, Integer firstPage, Integer prePage,
			Integer nextPage, Integer totalPage, Integer currentPage,
			Integer totalCount, Integer pageSize) {
		this.data = data;
		this.firstPage = firstPage;
		this.prePage = prePage;
		this.nextPage = nextPage;
		this.totalPage = totalPage;
		this.currentPage = currentPage;
		this.totalCount = totalCount;
		this.pageSize = pageSize;
	}
	
	public Integer getFirstPage() {
		return 1;
	}
	
	public List getData() {
		return data;
	}
	public void setData(List data) {
		this.data = data;
	}
	public void setFirstPage(Integer firstPage) {
		this.firstPage = firstPage;
	}
	public Integer getPrePage() {
		return this.getCurrentPage()==this.getFirstPage() ? 1:this.getCurrentPage()-1;
	}
	public void setPrePage(Integer prePage) {
		this.prePage = prePage;
	}
	public Integer getNextPage() {
		return this.getCurrentPage() == this.getTotalPage()
				? this.getTotalPage()
				: this.getCurrentPage()+1;
	}
	public void setNextPage(Integer nextPage) {
		this.nextPage = nextPage;
	}
	public Integer getTotalPage() {
		return this.getTotalCount()%this.getPageSize()==0 
				? this.getTotalCount()/this.getPageSize()
				: this.getTotalCount()/this.getPageSize()+1;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	
	/**
	 * 查询总记录数
	 * @param formName 表名
	 * @return
	 */
	public int queryCount(String formName){
		
		Connection conn = null;
	    PreparedStatement ptmt= null;
		ResultSet rs = null;
		
		String sql = "select count(*) from " + formName;
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
	
public int queryCountbySQL(String sql)throws Exception{
		
		Connection conn = null;
	    PreparedStatement ptmt= null;
		ResultSet rs = null;
		
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
				DbUtil.close(rs, ptmt, conn);
			}
		 return rowCount;
		
	}

	

	
	
}