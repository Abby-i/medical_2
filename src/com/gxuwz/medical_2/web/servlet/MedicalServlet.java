package com.gxuwz.medical_2.web.servlet;

import com.gxuwz.medical_2.dao.MedicalDao;
import com.gxuwz.medical_2.dao.S201Dao;
import com.gxuwz.medical_2.domain.S201.S201;
import com.gxuwz.medical_2.domain.getAge.AgeUtil;
import com.gxuwz.medical_2.domain.medical.Medical;
import com.gxuwz.medical_2.domain.vo.PageBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class MedicalServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String  m=req.getParameter("m");
        if("list".equals(m)){
            list(req,resp);
        }else if("input".equals(m)){
            try {
                //获得列表下拉框信息
                setListS201ToJsp(req,resp);
                process(req, resp, "/page/medical/medical_add.jsp");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else if ("add".equals(m)){
            add(req,resp);
        }else if("get".equals(m)){
            get(req,resp);
        }else if ("edit".equals(m)){
            edit(req,resp);
        }else if("del".equals(m)){
            del(req,resp);
        }
    }

    /**
     * 列表显示医疗机构信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PageBean page = new PageBean();

        MedicalDao medicalDao = new MedicalDao();
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
        int count = page.queryCount("t_medical");
        page.setTotalCount(count);
        //1.5 每页显示记录数
        page.setPageSize(Integer.parseInt(pageSize));

        //从数据库中读取当前页数据
        List<Medical> medicals = medicalDao.queryMedical(page.getCurrentPage(), page.getPageSize());
        page.setData(medicals);

        //2)把Page对象放入域对象中
        request.setAttribute("page", page);

        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("medicals", medicals);
        process(request, response, "/page/medical/medical_list.jsp");
    }

    private void setListS201ToJsp(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        S201Dao s201Dao = new S201Dao();
        List<S201> s20102List = null;
        try {
            s20102List = s201Dao.findListByType("02");
            List<S201> s20106List = s201Dao.findListByType("06");
            List<S201> s20104List = s201Dao.findListByType("04");
            List<S201> s20101List = s201Dao.findListByType("01");
            List<S201> s2010301List = s201Dao.findListByType("03");
            List<S201> s2010302List = s201Dao.findListByType("0301");
            request.setAttribute("s20102List", s20102List);
            request.setAttribute("s20106List", s20106List);
            request.setAttribute("s20104List", s20104List);
            request.setAttribute("s20101List", s20101List);
            request.setAttribute("s2010301List", s2010301List);
            request.setAttribute("s2010302List", s2010302List);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 医疗机构添加方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //接收页面传递过来的参数
        String jgbm=request.getParameter("jgbm");
        String zzjgbm=request.getParameter("zzjgbm");
        String jgmc=request.getParameter("jgmc");
        String dqbm=request.getParameter("dqbm");
        String areacode=request.getParameter("areacode");
        String lsgx=request.getParameter("lsgx");
        String jgjb=request.getParameter("jgjb");
        String sbddlx=request.getParameter("sbddlx");
        String pzddlx=request.getParameter("pzddlx");
        String ssjjlx=request.getParameter("ssjjlx");
        String wsjgdl=request.getParameter("wsjgdl");
        String wsjgxl=request.getParameter("wsjgxl");
        String zgdw=request.getParameter("zgdw");
        String kysjstr=request.getParameter("kysj");//日期
        String frdb=request.getParameter("frdb");
        String zczjnum=request.getParameter("zczj");//数值

        //Date  kysj=new java.util.Date();

        double zczj=Double.parseDouble(zczjnum);

        //调用添加方法
        try{
            AgeUtil ageUtil = new AgeUtil();
            Medical model=new Medical(jgbm, zzjgbm, jgmc, dqbm, areacode, lsgx, jgjb, sbddlx, pzddlx, ssjjlx, wsjgdl, wsjgxl, zgdw, ageUtil.createNowDateStr(kysjstr), frdb, zczj);
            model.add();
            list(request, response);
        }catch(Exception e){
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out=response.getWriter();
            out.println("<script>");//输出script标签
            out.println("alert('该编号已存在！');");//js语句：输出alert语句
            out.println("history.back();");//js语句：输出网页回退语句
            out.println("</script>");//输出script结尾标签
            out.flush();//清空缓存
            out.close();
        }
    }

    /**
     * 修改医疗机构信息对应的方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            setListS201ToJsp(request,response);
            String jgbm = request.getParameter("id");
            MedicalDao medicalDao = new MedicalDao();
            Medical medical = medicalDao.queryById(jgbm);
            request.setAttribute("medical", medical);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        process(request, response, "/page/medical/medical_edit.jsp");

    }
    private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jgbm=request.getParameter("jgbm");
        String zzjgbm=request.getParameter("zzjgbm");
        String jgmc=request.getParameter("jgmc");
        String dqbm=request.getParameter("dqbm");
        String areacode=request.getParameter("areacode");
        String lsgx=request.getParameter("lsgx");
        String jgjb=request.getParameter("jgjb");
        String sbddlx=request.getParameter("sbddlx");
        String pzddlx=request.getParameter("pzddlx");
        String ssjjlx=request.getParameter("ssjjlx");
        String wsjgdl=request.getParameter("wsjgdl");
        String wsjgxl=request.getParameter("wsjgxl");
        String zgdw=request.getParameter("zgdw");
        String kysjstr=request.getParameter("kysj");//日期
        String frdb=request.getParameter("frdb");
        String zczjstr=request.getParameter("zczj");//数值

        try {
            AgeUtil ageUtil = new AgeUtil();
            double zczjnum = Double.parseDouble(zczjstr);
            Medical medical = new Medical();
            medical.editToDB(jgbm, zzjgbm, jgmc, dqbm, areacode, lsgx, jgjb, sbddlx, pzddlx, ssjjlx, wsjgdl, wsjgxl, zgdw, ageUtil.createNowDateStr(kysjstr), frdb, zczjnum);
            list(request, response);

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    private void del(HttpServletRequest request, HttpServletResponse response) {
        String jgbm=request.getParameter("id");
        Medical medical = new Medical();
        try {
            medical.delToDB(jgbm);
            list(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
