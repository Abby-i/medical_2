package com.gxuwz.medical_2.web.servlet;

import com.gxuwz.medical_2.dao.MenuDao;
import com.gxuwz.medical_2.domain.menu.Menu;
import com.gxuwz.medical_2.domain.vo.PageBean;
import com.gxuwz.medical_2.exception.MenuException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuServlet extends HttpServlet {

	private static final Logger LOG = LogManager.getLogger(MenuServlet.class);
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String m = request.getParameter("m");
		if ("tree".equals(m)) {
			tree(request, response);
		} else if ("list".equals(m)) {
			list(request,response);
		} else if ("input".equals(m)) {
			input(request,response);
		} else if ("add".equals(m)) {
			add(request, response);
		} else if ("edit".equals(m)) {
			edit(request, response);
		} else if ("del".equals(m)) {
			del(request, response);
		} else if ("get".equals(m)) {
			get(request, response);
		}else if("toJsonStr".equals(m)){
			toJsonStr(request, response);
		}
		
	}

	private void tree(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	private void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String menuid = request.getParameter("id");
			try {
				Menu menu = new Menu(menuid);
				request.setAttribute("menu", menu);
			} catch (MenuException e) {
				
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}


		request.getRequestDispatcher("/page/menu/menu_edit.jsp").forward(request, response);
	}

	private void input(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		MenuDao menudao = new MenuDao();
		List<Menu> menus = menudao.queryMenu();
		request.setAttribute("menus", menus);
		
		request.getRequestDispatcher("/page/menu/menu_add.jsp").forward(request, response);
	}

	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		pageDao(request, response);
		
		request.getRequestDispatcher("/page/menu/menu_list.jsp").forward(request, response);
		
	}

	private void toJsonStr(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String roleid = request.getParameter("id");
		MenuDao menudao = new MenuDao();
		if (roleid == null || "".equals(roleid)) {
			//查询出所有的权限模块
			List<Menu> menus = menudao.queryMenus();
			int size =  menus.size();
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			for(Menu module:menus){
				size--;
				sb.append("{\"id\":\"").append(module.getMenuid());
				sb.append("\",\"pId\":\"").append(module.getMenupid());
				sb.append("\",\"name\":\"").append(module.getMenuname());
				sb.append("\",\"checked\":\"");
				sb.append("false");
				sb.append("\"}");
				if(size>0){
					sb.append(",");
				}
			}	
			sb.append("]");
			response.setContentType("application/json;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			System.out.println(sb.toString());
			response.getWriter().write(sb.toString());
		} else {
			
			//查询出该角色已分配的权限
			List<Menu> hasChecked = menudao.queryMenus(roleid);
			//查询出该角色的权限
			List<Menu> list = menudao.queryMenus();
			
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			List<String> menuids = new ArrayList<String>();
			for (Menu m : hasChecked) {
				menuids.add(m.getMenuid());
			}
			for(Menu module:list){

					sb.append("{\"id\":\"").append(module.getMenuid());
					sb.append("\",\"pId\":\"").append(module.getMenupid());
					sb.append("\",\"name\":\"").append(module.getMenuname());
					sb.append("\",\"checked\":\"");
				
			
				if(menuids.contains(module.getMenuid())){
					sb.append("true");
				}else {
					sb.append("false");
				}
				sb.append("\"}");
				
				sb.append(",");
				
			}	
			sb.append("]");
			
			response.setContentType("application/json;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			System.out.println(sb.toString());
			response.getWriter().write(sb.toString());
			
		}
	}

	private void del(HttpServletRequest request, HttpServletResponse response) {
		String menuid = request.getParameter("id");
		// 实例化Menu
		Menu menu = new Menu();
		// 删除信息
		try {
			menu.delMenu(menuid);
			LOG.info("删除menuid为 "+menuid+" 的权限");
			list(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void edit(HttpServletRequest request, HttpServletResponse response) {
		String menuid = request.getParameter("menuid");
		String menuname = request.getParameter("menuname");
		String url = request.getParameter("url");
		
		try {
			// 实例化Menu
			Menu menu = new Menu(menuid);
			// 修改信息
			menu.editMenu(menuname, url);
			list(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String chooseid = request.getParameter("chooseid");
		
		String menupid = chooseid.split("-")[0];
		String plevel = chooseid.split("-")[1];
		
		String menuname = request.getParameter("menuname");
		String url = request.getParameter("url");
		
		// 实例化Menu
		int level = Integer.parseInt(plevel);
		Menu menu = new Menu(menuname, url);
		// 添加到父节点+级别
		try {
			menu.addMenu(menupid, level);
			list(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	private void pageDao(HttpServletRequest request, HttpServletResponse response){
		PageBean pageBean = new PageBean();
		
		MenuDao menudao = new MenuDao();
		//1.3从用户参数中获取当前页数据（currPage）
		String currentPage = request.getParameter("currentPage");
		String pageSize = request.getParameter("pageSize");
		if(currentPage==null || currentPage.equals("")){
			  currentPage="1";
		}
		if(pageSize==null || pageSize.equals("")){
			pageSize = "10";
		}
		pageBean.setCurrentPage(Integer.parseInt(currentPage));
		//从数据库查询出总数
		int count = pageBean.queryCount("t_menu");
		pageBean.setTotalCount(count);
		//1.5 每页显示记录数
		pageBean.setPageSize(Integer.parseInt(pageSize));		
		
		//从数据库中读取当前页数据
		List<Menu> menus = menudao.queryMenu(pageBean.getCurrentPage(), pageBean.getPageSize());
		pageBean.setData(menus);
				
		//2)把PageBean对象放入域对象中
		request.setAttribute("pageBean", pageBean);
		
		HttpSession httpSession = request.getSession();
		httpSession.setAttribute("menus", menus);
	}
	
}
