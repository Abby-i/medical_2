package com.gxuwz.medical_2.web.servlet;

import com.gxuwz.medical_2.dao.ChronicDiseaseDao;
import com.gxuwz.medical_2.domain.ChronicDisease.ChronicDisease;
import com.gxuwz.medical_2.domain.vo.PageBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class ChronicDiseaseServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String m=req.getParameter("m");//请求处理动作类型:list:显示列表；get：根据ID读取记录;input:新增数据;add:保存新记录；edit：更新记录
        if("list".equals(m)){
            list(req,resp);
        }else if("input".equals(m)){
            input(req,resp);
        }else if("add".equals(m)){
            add(req,resp);
        }else if("get".equals(m)){
            process(req, resp, "/page/chronicDisease/chronicDisease_edit.jsp");
        }else if ("edit".equals(m)){
            edit(req,resp);
        }else if("del".equals(m)){
            del(req,resp);
        }
    }

    /**
     * 慢性病列表显示
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        pageDao(request, response);
        process(request, response, "/page/chronicDisease/chronicDisease_list.jsp");
    }

    private void pageDao(HttpServletRequest request, HttpServletResponse response) {

        PageBean pageBean = new PageBean();
        ChronicDiseaseDao chronicDiseaseDao = new ChronicDiseaseDao();
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
        int count = pageBean.queryCount("t_chronicdisease");
        pageBean.setTotalCount(count);
        //1.5 每页显示记录数
        pageBean.setPageSize(Integer.parseInt(pageSize));

        //从数据库中读取当前页数据
        List<ChronicDisease> chronicdis = chronicDiseaseDao.queryPages("select * from t_chronicdisease limit ?,?",pageBean.getCurrentPage(), pageBean.getPageSize());
        pageBean.setData(chronicdis);

        //2)把PageBean对象放入域对象中
        request.setAttribute("pageBean", pageBean);
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("chronicdis", chronicdis);
    }

    /**
     * 慢性病管理添加方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void input(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response, "/page/chronicDisease/chronicDisease_add.jsp");
    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1：接收参数
        String illcode=request.getParameter("illcode");
        String illname=request.getParameter("illname");
        //2. 系统校验疾病编号是否重复
        ChronicDiseaseDao chronicDiseaseDao = new ChronicDiseaseDao();
        //判断此illcode是否已经存在
        try {
            if(chronicDiseaseDao.isExist(illcode)==true){
                ChronicDisease model=new ChronicDisease(illcode, illname);
                try {
                    model.add();
                    list(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else{
                response.setContentType("text/html;charset=UTF-8");
                PrintWriter out=response.getWriter();
                out.println("<script>");//输出script标签
                out.println("alert('该疾病编号已存在！');");//js语句：输出alert语句
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
     * 修改方法
     * @param request
     * @param response
     */
    private void edit(HttpServletRequest request, HttpServletResponse response) {
        //1：接收参数
        String illcode=request.getParameter("illcode");
        String illname=request.getParameter("illname");
        //2:构造新慢病信息对象
        ChronicDisease model=new ChronicDisease(illcode, illname);
        //3：调用保存的方法
        try{
            model.edit();
            list(request, response);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 删除慢性病
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1：接收参数
        String illcode=request.getParameter("id");

        //2:构造新慢病信息对象
        ChronicDisease model=new ChronicDisease();
        try{
            model.del(illcode);
            process(request, response, "/page/chronicDisease/chronicDisease_list.jsp");
        }catch(Exception e){
            error(request, response);
        }

    }
}
