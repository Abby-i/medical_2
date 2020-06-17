package com.gxuwz.medical_2.web.servlet;

import com.gxuwz.medical_2.dao.MedicalExpenseDao;
import com.gxuwz.medical_2.domain.medicalExpense.MedicalExpense;
import com.gxuwz.medical_2.domain.vo.PageBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CheckExpenseServlet {
    private static final long serialVersionUID = 1L;
    MedicalExpenseDao medicalExpenseDao = new MedicalExpenseDao();
    MedicalExpense medicalExpense = new MedicalExpense();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String m = request.getParameter("m");
        if ("list".equals(m)) {
            list(request,response);
        } else if("get".equals(m)){
            get(request,response);
        } else if("edit".equals(m)){
            edit(request,response);
        } else if("del".equals(m)){
            del(request,response);
        }
    }


    private void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        System.out.println("id:"+id);
        try {
            MedicalExpense.del(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        list(request, response);
    }

    private void edit(HttpServletRequest request, HttpServletResponse response) {
        String auditStatus = request.getParameter("auditStatus");
        String remittanceStatus = request.getParameter("remittanceStatus");
        String details = request.getParameter("details");
        String id = request.getParameter("id");
        String agreetor=request.getParameter("agreetor");
        System.out.println("id:"+auditStatus);
        try {
            MedicalExpense.update(auditStatus,details,agreetor,id,remittanceStatus);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        MedicalExpense illExpense = medicalExpenseDao.queryById(Integer.valueOf(id));
        System.out.println(illExpense);
        request.setAttribute("illExpense", illExpense);
        process(request, response, "/page/checkExpense/checkExpense_edit.jsp");
    }

    private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PageBean page = new PageBean();
        System.out.println("条件-----------------------------------");
        String town = request.getParameter("town");
        String village = request.getParameter("village");
        String group = request.getParameter("group");
        String name = request.getParameter("name");
        String checkStatus=request.getParameter("auditStatus");//审核状态
        System.out.println("town:"+town);
        System.out.println("village:"+village);
        System.out.println("group:"+group);
        System.out.println("name:"+name);
        System.out.println("checkStatus:"+checkStatus);
        System.out.println("条件-----------------------------------");
        //1.3从用户参数中获取当前页数据（currPage）
        String currentPage = request.getParameter("currentPage");
        String pageSize = request.getParameter("pageSize");
        if(currentPage==null || currentPage.equals("")){
            currentPage="1";
        }
        if(pageSize==null || pageSize.equals("")){
            pageSize = "10";
        }
        page.setCurrentPage(Integer.parseInt(currentPage));
        //从数据库查询出总数
        int count = medicalExpenseDao.queryCount(town,village,group,name,checkStatus);
        page.setTotalCount(count);
        //1.5 每页显示记录数
        page.setPageSize(Integer.parseInt(pageSize));

        //从数据库中读取当前页数据
        //System.out.println("household:"+household);
        List<MedicalExpense> medicalExpense = medicalExpenseDao.queryExpense(town,village,group,name,checkStatus, page.getCurrentPage(), page.getPageSize());

        page.setData(medicalExpense);

        //2)把Page对象放入域对象中
        request.setAttribute("page", page);

        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("illExpenses", medicalExpense);
        process(request, response, "/page/checkExpense/checkExpense_list.jsp");
    }

}
