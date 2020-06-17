package com.gxuwz.medical_2.web.servlet;

import com.google.gson.Gson;
import com.gxuwz.medical_2.dao.AreaDao;
import com.gxuwz.medical_2.domain.area.Area;
import com.gxuwz.medical_2.domain.vo.PageBean;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AreaServlet extends BaseServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       doPost(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String m = req.getParameter("m");
        if ("list".equals(m)) {
            list(req,resp);
        }else if ("input".equals(m)) {
            input(req,resp);
        }else if ("add".equals(m)) {
            add(req,resp);
            //跳转到list页面
            PageDao(req, resp);
            process(req, resp, "/page/area/area_list.jsp");
        }else if("get".equals(m)){
            get(req,resp);
            process(req, resp, "/page/area/area_edit.jsp");
        }else if("edit".equals(m)){
            edit(req,resp);
        }else if("del".equals(m)){
            del(req,resp);
        }else if ("tojson".equals(m)){
            tojson(req,resp);
        }
    }

    /**
     * 列表显示数据
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    //列表显示
    private void list(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        PageDao(req, resp);
        process(req, resp, "/page/area/area_list.jsp");
    }

    private void PageDao(HttpServletRequest req, HttpServletResponse resp)throws  ServletException, IOException{
        PageBean pageBean = new PageBean();
        //1.3从用户参数中获取当前页数据（currPage）
        String currentPage = req.getParameter("currentPage");
        String pageSize = req.getParameter("pageSize");
        if(currentPage==null || currentPage.equals("")){
            currentPage="1";
        }
        if(pageSize==null || pageSize.equals("")){
            pageSize = "10";
        }
        pageBean.setCurrentPage(Integer.parseInt(currentPage));
        //从数据库查询出总数
        int count = pageBean.queryCount("t_area");
        pageBean.setTotalCount(count);
        //1.5 每页显示记录数
        pageBean.setPageSize(Integer.parseInt(pageSize));

        //从数据库中读取当前页数据
        AreaDao areaDao = new AreaDao();
        List<Area> areas = areaDao.queryPages("select * from t_area limit ?,?", pageBean.getCurrentPage(), pageBean.getPageSize());
        System.out.println(areas);
        pageBean.setData(areas);

        //2)把PageBean对象放入域对象中
        req.setAttribute("pageBean", pageBean);

        HttpSession httpSession = req.getSession();
        httpSession.setAttribute("areas", areas);
    }


    /**
     * 添加方法
     * @param req
     * @param resp
     */
    //添加页面的跳转
    private void input(HttpServletRequest req, HttpServletResponse resp){
        Object[]params={};
        try {
            AreaDao areaDao = new AreaDao();
            List<Area> areas= null;
            try {
                areas = areaDao.queryObject("select * from t_area where 1=1 ORDER BY areacode", params);
            } catch (Exception e) {
                e.printStackTrace();
            }
            req.setAttribute("areas", areas);
            process(req, resp, "/page/area/area_add.jsp");
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //获取页面输入数 给到areaDao中的addArea方法
    private void add(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        try {
            // 接收行政区域编码+行政区域名称，行政区域名称有可能出现中文乱码
            String areapid = request.getParameter("areacode");
            String areaname = request.getParameter("areaname");
            // 实例化Area，并调用添加子行政区域方法
            Area area = new Area();
            area.addArea(areapid, areaname);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改方法
     * @param req
     * @param resp
     */
    //将页面获取到的id传给Area
    private void get(HttpServletRequest req, HttpServletResponse resp) {
        String areacode = req.getParameter("id");
        try {
            Area area = new Area(areacode);
            req.setAttribute("area", area);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //修改方法（获取页面的数据，给到Area）
    private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String areacode = request.getParameter("areacode");
        String areaname = request.getParameter("areaname");
        String grades = request.getParameter("grade");
        System.out.println(grades);
        int grade = 0;
        if(grades.equals("市")){
            grade = 1;
        }else if(grades.equals("县")){
            grade = 2;
        }else if(grades.equals("镇")){
            grade = 3;
        }else if(grades.equals("村")){
            grade = 4;
        }else if(grades.equals("组")){
            grade = 5;
        }
        Area area = new Area();
        try {
            area.editToDB(areacode,areaname,grade);
            list(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    //获取页面id给到delArea
    private void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String areacode = request.getParameter("id");
        System.out.println(areacode);
        Area area = new Area();
        try {
            area.delArea(areacode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        list(request,response);
    }

    /**
     * 获取下拉框数据对应的方法
     */
    private void tojson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置请求编码格式
        request.setCharacterEncoding("utf-8");
        //设置响应编码格式
        response.setContentType("text/html;charset=utf-8");
        String areaupcode = request.getParameter("areaupcode");
        AreaDao areaDao = new AreaDao();
        List<Area> areas = areaDao.queryByupId(areaupcode);
        response.getWriter().write(new Gson().toJson(areas));

    }
}
