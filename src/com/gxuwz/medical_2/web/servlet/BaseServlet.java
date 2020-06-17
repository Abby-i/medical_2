package com.gxuwz.medical_2.web.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 抽象基类
 * 
 * @author 演示
 * 
 */
public abstract class BaseServlet extends HttpServlet {

	private static final long serialVersionUID = 8196938314965941620L;

	protected void process(HttpServletRequest request,HttpServletResponse response, String path) throws ServletException,IOException {

		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);

	}
	
	protected void error(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {

		RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
		rd.forward(request, response);

	}
	
	protected void prompt(HttpServletRequest request,HttpServletResponse response,String promptStr) throws ServletException,IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		out.println("<script>");
		out.println("alert('"+promptStr+"');");
		out.println("history.back();");
		out.println("</script>");
		out.flush();
		out.close();
		return;
	}
	
	protected void closeLayer(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException  {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
        out.println("<script>");
        out.println("parent.layer.close(parent.layer.getFrameIndex(window.name));");
        out.println("</script>");
        out.flush();
        out.close();
        return;
	}
	
	
}
