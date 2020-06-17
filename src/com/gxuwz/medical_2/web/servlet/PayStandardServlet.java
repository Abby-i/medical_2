package com.gxuwz.medical_2.web.servlet;

import com.gxuwz.medical_2.dao.PayStandardDao;
import com.gxuwz.medical_2.domain.getAge.AgeUtil;
import com.gxuwz.medical_2.domain.payStandard.PayStandard;
import com.gxuwz.medical_2.domain.vo.PageBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class PayStandardServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String m =req.getParameter("m");
        if("list".equals(m)){
            list(req,resp);
        }else if ("input".equals(m)){
            input(req,resp);
        }else if("add".equals(m)){
            add(req,resp);
        }else if("get".equals(m)){
            get(req,resp);
        }else if("edit".equals(m)){
            edit(req,resp);
        }else if("del".equals(m)){
            del(req,resp);
        }
    }

    /**
     * 缴费标准列表显示方法
     */
    private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        pageDao(request, response);
        process(request, response, "/page/payStandard/payStandard_list.jsp");
    }

    private void pageDao(HttpServletRequest request, HttpServletResponse response) {
        PageBean pageBean = new PageBean();

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
        int count = pageBean.queryCount("t_paystandard");
        pageBean.setTotalCount(count);
        //1.5 每页显示记录数
        pageBean.setPageSize(Integer.parseInt(pageSize));

        //从数据库中读取当前页数据
        PayStandardDao payStandardDao = new PayStandardDao();
        List<PayStandard> standards = payStandardDao.queryPages("select * from t_paystandard limit ?,?", pageBean.getCurrentPage(), pageBean.getPageSize());
        pageBean.setData(standards);

        //2)把PageBean对象放入域对象中
        request.setAttribute("pageBean", pageBean);

        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("standards", standards);
    }


    /**
     * 点击跳转到缴费标准添加界面
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void input(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AgeUtil ageUtil = new AgeUtil();
        String nowyear = ageUtil.getNowyear();
        request.setAttribute("nowyear", nowyear);
        process(request, response, "/page/payStandard/payStandard_add.jsp");
    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String annual = request.getParameter("annual");
        Double pay_cost = Double.valueOf(request.getParameter("pay_cost"));
        String startTimeStr = request.getParameter("startTime");
        String endTimeStr = request.getParameter("endTime");
        AgeUtil ageUtil = new AgeUtil();
        try {
            Date startTime = ageUtil.toDateFormat(startTimeStr);
            Date endTime = ageUtil.toDateFormat(endTimeStr);
            PayStandard payStandard = new PayStandard(annual, pay_cost, startTime, endTime);
            payStandard.add();
        } catch (Exception e) {
            process(request, response, "该年度缴费标准已存在！");
        }
    }

    /**
     * 缴费标准修改方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    //获取原数据库信息
    private void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String annual = request.getParameter("id");
        PayStandardDao payStandardDao = new PayStandardDao();
        PayStandard payStandard =payStandardDao.findById(annual);
        request.setAttribute("payStandard",payStandard );
        process(request, response, "/page/payStandard/payStandard_edit.jsp");
    }

    private void edit(HttpServletRequest request, HttpServletResponse response) {
        String annual  = request.getParameter("annual");
        Double pay_cost  = Double.valueOf(request.getParameter("pay_cost"));
        String startTimeStr  = request.getParameter("startTime");
        String endTimeStr  = request.getParameter("endTime");
        AgeUtil ageUtil = new AgeUtil();
        try {
            Date startTime = ageUtil.toDateFormat(startTimeStr);
            Date endTime = ageUtil.toDateFormat(endTimeStr);
            PayStandard payStantard = new PayStandard(annual,pay_cost,startTime,endTime);
            payStantard.edit();
            list(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除缴费标准
     */
    private void del(HttpServletRequest request, HttpServletResponse response) {
        String annual  = request.getParameter("id");
        try {
            PayStandard payStandard = new PayStandard();
            payStandard.del(annual);
            list(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
