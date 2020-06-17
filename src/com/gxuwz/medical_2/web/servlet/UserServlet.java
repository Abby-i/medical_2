package com.gxuwz.medical_2.web.servlet;

import com.gxuwz.medical_2.dao.RoleDao;
import com.gxuwz.medical_2.dao.UserDao;
import com.gxuwz.medical_2.domain.role.Role;
import com.gxuwz.medical_2.domain.user.User;
import com.gxuwz.medical_2.domain.vo.PageBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UserServlet extends BaseServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String m = req.getParameter("m");
        if("list".equals(m)){
            list(req,resp);
        }
        if("input".equals(m)){
            input(req,resp);
        }
        if("add".equals(m)){
            add(req,resp);
        }
        if("get".equals(m)){
            get(req,resp);
        }
        if("edit".equals(m)){
            edit(req,resp);
        }
        if("del".equals(m)){
            del(req,resp);
        }
    }
    //列表显示
    private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        pageDao(req, resp);
       process(req,resp,"/page/user/user_list.jsp");
    }
    private void pageDao(HttpServletRequest req, HttpServletResponse resp){
        PageBean pageBean = new PageBean();

        UserDao userdao = new UserDao();
        //1.3从用户参数中获取当前页数据（currPage）
        String currentPage = req.getParameter("currentPage");
        //当前页记录数
        String pageSize = req.getParameter("pageSize");
        if(currentPage==null || currentPage.equals("")){
            currentPage="1";
        }
        if(pageSize==null || pageSize.equals("")){
            pageSize = "10";
        }
        pageBean.setCurrentPage(Integer.parseInt(currentPage));
        //（传表名）从数据库查询出总数
        int count = pageBean.queryCount("t_user");
        pageBean.setTotalCount(count);
        //1.5 每页显示记录数
        pageBean.setPageSize(Integer.parseInt(pageSize));

        //从数据库中读取当前页数据
        List<User> users = userdao.queryUser(pageBean.getCurrentPage(), pageBean.getPageSize());
        pageBean.setData(users);

        //2)把PageBean对象放入域对象中
        req.setAttribute("pageBean", pageBean);

        HttpSession httpSession = req.getSession();
        httpSession.setAttribute("users", users);

    }

    /**
     * 添加方法
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    //跳转到添加页面
    private void input(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        RoleDao roledao = new RoleDao();
        List<Role> roles = roledao.queryRoles();

        req.setAttribute("roles", roles);

        process(req,resp,"/page/user/add.jsp");
    }
    //添加方法
    private void add(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException  {
        //获取页面输入数据
        String userid = request.getParameter("userid");
        String pwd = request.getParameter("pwd");
        String fullname = request.getParameter("fullname");
        String agencode = request.getParameter("agencode");
        String status ="1";//默认正常
        String[] roleids = request.getParameterValues("roleids");
        //1:实例化User
        User user =new User(userid, pwd, fullname, status, agencode);
        //2:调用方法
        try{
            user.addUser(roleids);
            request.getRequestDispatcher("/page/user/tips.jsp").forward(request, response);
        }catch(Exception e){
            e.printStackTrace();
            request.getRequestDispatcher("/page/user/tips_error.jsp").forward(request, response);
        }
    }

    /**
     * 修改方法
     * @param req
     * @param resp
     */
    //获取要修改的原信息，并跳转到修改页面
    private void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取选中的用户id
        String userid = req.getParameter("userid");
        //创建userDao对象
        UserDao userdao = new UserDao();
        //查询出该用户的信息
        User user = null;
        try {
            user = userdao.querybyId(userid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        req.setAttribute("user", user);
        RoleDao roledao = new RoleDao();
        //查询出所有角色信息
        try {
            String sql="select * from t_role";
            Object[]params={};
            List<Role> roleList= null;
            try {
                roleList = roledao.queryOjects(sql, params);
            } catch (Exception e) {
                e.printStackTrace();
            }

            req.setAttribute("roleList", roleList);

            //查询出原已选中的角色信息
            List<String> hasChecked = roledao.queryhasChecked(userid);

            req.setAttribute("hasChecked", hasChecked);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
         process(req,resp,"/page/user/edit.jsp");
    }
    //获取页面数据，传入UserDao进行数据库数据的修改
    private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        String userid = request.getParameter("userid");
        String pwd = request.getParameter("pwd");
        String fullname = request.getParameter("fullname");
        String agencode = request.getParameter("agencode");
        String status ="1";//默认正常
        String[] roleids = request.getParameterValues("roleids");
        //1:实例化User
        User user =new User(userid, pwd, fullname, status, agencode);
        //
        try {
            user.editUser(roleids);
            list(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除选中的用户信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void del(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String userid = request.getParameter("userid");
        System.out.println(userid);
        try {
            //1:实例化User
            User user =new User();
            //删除+解除绑定（将选中删除的id传入User）
            user.delUser(userid);
            request.getRequestDispatcher("/system/UserServlet?m=list").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
