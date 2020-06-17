package com.gxuwz.medical_2.web.servlet;

import com.gxuwz.medical_2.dao.PolicyDao;
import com.gxuwz.medical_2.domain.Policy.Policy;
import com.gxuwz.medical_2.domain.vo.PageBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class PolicyServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String m = req.getParameter("m");
        if ("list".equals(m)) {
            list(req,resp);
        }else if ("input".equals(m)) {
            process(req, resp, "/page/policy/policy_add.jsp");
        } else if("add".equals(m)){
            add(req,resp);
        } else if ("get".equals(m)) {
            get(req, resp);
        }else if("edit".equals(m)){
            edit(req,resp);
        }else if("del".equals(m)){
            del(req,resp);
        }
    }

    /**
     * 慢性病报销政策显示列表
     */
    private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        pageDao(request, response);
        process(request, response, "/page/policy/policy_list.jsp");

    }

    private void pageDao(HttpServletRequest request, HttpServletResponse response) {

        PageBean pageBean = new PageBean();
        PolicyDao policyDao = new PolicyDao();
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
        int count = pageBean.queryCount("t_policy");
        pageBean.setTotalCount(count);
        //1.5 每页显示记录数
        pageBean.setPageSize(Integer.parseInt(pageSize));

        //从数据库中读取当前页数据
        List<Policy> policylist = policyDao.queryPages("select * from t_policy ORDER BY annual asc limit ?,?",pageBean.getCurrentPage(), pageBean.getPageSize());
        pageBean.setData(policylist);

        //2)把PageBean对象放入域对象中
        request.setAttribute("pageBean", pageBean);

        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("policylist", policylist);
    }


    /**
     * 慢性病报销政策添加方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String annual = request.getParameter("annual");
        Double topline =Double.parseDouble(request.getParameter("topline"));
        Double proportion = Double.parseDouble(request.getParameter("proportion"));
        Policy policy = new Policy();
        try {
            policy.add(annual,topline,proportion);
            list(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 慢性病政策修改方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PolicyDao policyDao = new PolicyDao();
        String id = request.getParameter("id");
        Policy policy = policyDao.queryById(id);
        request.setAttribute("policy", policy);
        process(request, response, "/page/policy/policy_edit.jsp");
    }
    //具体数据修改方法
    private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id =Integer.parseInt(request.getParameter("id"));
        System.out.println("id="+id);
        String annual = request.getParameter("annual");
        Double topline =Double.parseDouble(request.getParameter("topline"));
        Double proportion = Double.parseDouble(request.getParameter("proportion"));
        Policy policy = new Policy();
        try {
            policy.edit(id,annual,topline,proportion);
            list(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 慢性病报销政策删除方法
     * @param request
     * @param response
     */
    private void del(HttpServletRequest request, HttpServletResponse response) {
        int  id = Integer.parseInt(request.getParameter("id"));
        Policy policy = new Policy();
        try {
            policy.del(id);
            list(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
