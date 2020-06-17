package com.gxuwz.medical_2.web.servlet;

import com.gxuwz.medical_2.dao.AreaDao;
import com.gxuwz.medical_2.dao.RuralCooperativeDao;
import com.gxuwz.medical_2.domain.area.Area;
import com.gxuwz.medical_2.domain.ruralCooperative.RuralCooperative;
import com.gxuwz.medical_2.domain.vo.PageBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class RuralCooperativeServlet extends BaseServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String m = req.getParameter("m");
        if ("list".equals(m)) {
            list(req, resp);
        } else if ("input".equals(m)) {
            input(req, resp);
        } else if ("add".equals(m)) {
            add(req, resp);
        }else if("get".equals(m)){
            get(req,resp);
        }else if("edit".equals(m)){
            edit(req,resp);
        }else if("del".equals(m)){
            try {
                del(req,resp);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            list(req, resp);
            }
    }

    /**
     * 农合机构列表显示方法
     *
     * @param req
     * @param resp
     */
    private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PageDao(req, resp);
        process(req, resp, "/page/ruralCooperative/ruralCooperative_list.jsp");
    }

    private void PageDao(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PageBean pageBean = new PageBean();
        RuralCooperativeDao ruralCooperativeDao = new RuralCooperativeDao();
        //1.3从用户参数中获取当前页数据（currPage）
        String currentPage = request.getParameter("currentPage");
        String pageSize = request.getParameter("pageSize");
        if (currentPage == null || currentPage.equals("")) {
            currentPage = "1";
        }
        if (pageSize == null || pageSize.equals("")) {
            pageSize = "10";
        }
        pageBean.setCurrentPage(Integer.parseInt(currentPage));
        //从数据库查询出总数
        int count = pageBean.queryCount("t_ruralcooprative");
        pageBean.setTotalCount(count);
        //1.5 每页显示记录数
        pageBean.setPageSize(Integer.parseInt(pageSize));
        //从数据库中读取当前页数据
        List<RuralCooperative> farmers = ruralCooperativeDao.queryPages("select * from t_ruralcooprative limit ?,?", pageBean.getCurrentPage(), pageBean.getPageSize());
        System.out.println(farmers);
        pageBean.setData(farmers);
        //2)把PageBean对象放入域对象中
        request.setAttribute("pageBean", pageBean);
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("farmers", farmers);
    }


    /**
     * 添加农合经办点
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void input(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AreaDao areaDao = new AreaDao();
        List<Area> areas = areaDao.queryAreas();
        request.setAttribute("areas", areas);
        process(request, response, "/page/ruralCooperative/ruralCooperative_add.jsp");

    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String farmerid = UUID.randomUUID().toString().replaceAll("-", "");
        String areacode = request.getParameter("areacode");
        String farmername = request.getParameter("farmername");
        RuralCooperativeDao ruralCooperativeDao = new RuralCooperativeDao();
        //是否已存在该农合机构
        try {
            if(ruralCooperativeDao.isExist(farmername)== true){
                RuralCooperative ruralCooperative = new RuralCooperative();
                try {
                    ruralCooperative.addRural(farmerid,farmername,areacode);
                    list(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else{
                response.setContentType("text/html;charset=UTF-8");
                PrintWriter out=response.getWriter();
                out.println("<script>");//输出script标签
                out.println("alert('该机构名称已存在！');");//js语句：输出alert语句
                out.println("history.back();");//js语句：输出网页回退语句
                out.println("</script>");//输出script结尾标签
                out.flush();//清空缓存
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 修改农合机构
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    //获取数据库信息
    private void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String farmerid  =request.getParameter("id");
        RuralCooperativeDao ruralCooperativeDao = new RuralCooperativeDao();
        AreaDao areaDao = new AreaDao();
        RuralCooperative ruralCooperative = ruralCooperativeDao.queryById(farmerid);
        Area selected = areaDao.querySelected(ruralCooperative.getAreacode());

        List<Area> areas = areaDao.queryAreas();
        request.setAttribute("ruralCooperative", ruralCooperative);
        request.setAttribute("areas", areas);
        request.setAttribute("selected", selected);
        process(request, response, "/page/ruralCooperative/ruralCooperative_edit.jsp");

    }
    private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String farmerid = request.getParameter("farmerid");
        String farmername = request.getParameter("farmername");
        String areacode = request.getParameter("areacode");
        RuralCooperative ruralCooperative = new RuralCooperative();
        try {
            ruralCooperative.editRural(farmerid,farmername,areacode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        list(request, response);
    }

    /**
     * 删除农合机构
     * @param request
     * @param response
     * @throws SQLException
     */
    private void del(HttpServletRequest request, HttpServletResponse response) throws SQLException {

        RuralCooperative ruralCooperative = new RuralCooperative();
        String farmerid = request.getParameter("id");
        System.out.println(farmerid);
        try {
            ruralCooperative.delRural(farmerid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
