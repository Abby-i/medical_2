package com.gxuwz.medical_2.web.servlet;

import com.gxuwz.medical_2.dao.AccountArchivesDao;
import com.gxuwz.medical_2.dao.AreaDao;
import com.gxuwz.medical_2.dao.FamilyArchivesDao;
import com.gxuwz.medical_2.domain.FamilyArchives.FamilyArchives;
import com.gxuwz.medical_2.domain.area.Area;
import com.gxuwz.medical_2.domain.vo.PageBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FamilyArchivesServlet extends BaseServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }


    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String m = req.getParameter("m");
        if ("list".equals(m)) {
            list(req,resp);
        }else if ("input".equals(m)){
            input(req,resp);
        }else if("add".equals(m)){
            add(req,resp);
        }else if("del".equals(m)){
            del(req,resp);
        }else if("addFamilyMember".equals(m)){
            addFamilyMember(req,resp);
        }
    }


    /**
     * 家庭档案列表显示
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        pageDao(req, resp);
        process(req,resp,"/page/homeArchives/homeArchives_list.jsp");

    }
    //分页查询
    private void pageDao(HttpServletRequest req, HttpServletResponse resp) {

        PageBean pageBean = new PageBean();
        FamilyArchivesDao familyArchivesDao = new FamilyArchivesDao();
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
        int count = pageBean.queryCount("t_homearchives");
        pageBean.setTotalCount(count);
        //1.5 每页显示记录数
        pageBean.setPageSize(Integer.parseInt(pageSize));

        //从数据库中读取当前页数据
        List<FamilyArchives> homelist = familyArchivesDao.queryPages("select * from t_homearchives limit ?,?",pageBean.getCurrentPage(), pageBean.getPageSize());
        pageBean.setData(homelist);

        //2)把PageBean对象放入域对象中
        req.setAttribute("pageBean", pageBean);

        HttpSession httpSession = req.getSession();
        System.out.println(homelist.toString());
        httpSession.setAttribute("homelist", homelist);
    }

    /**
     * 添加家庭档案方法
     * @param req
     * @param resp
     */
    private void input(HttpServletRequest req, HttpServletResponse resp) {
        AreaDao areaDao = new AreaDao();
        List<Area> towns = areaDao.findtown();
        req.setAttribute("towns", towns);
        try {
            process(req, resp, "/page/homeArchives/homeArchives_add.jsp");
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //获取页面数据给到Dao
    private void add(HttpServletRequest request, HttpServletResponse response) {

        FamilyArchivesDao familyArchivesDao = new FamilyArchivesDao();
        AccountArchivesDao accountArchivesDao = new AccountArchivesDao();
        String countyid = "450323";//桂林市
        String townid = request.getParameter("town");
        String villageid = request.getParameter("village");
        String groupid = request.getParameter("group");
        //根据组编号生成家庭编号
        String homeid = "";
        if(familyArchivesDao.ToFamilyid(groupid)!=null){
            long homeidLong = Long.valueOf(familyArchivesDao.ToFamilyid(groupid))+1;
            homeid = String.valueOf(homeidLong);
        }else{
            long homeidLong = Long.valueOf(groupid+"000")+1;
            homeid = String.valueOf(homeidLong);
        }

        String property = request.getParameter("property");
        String household = request.getParameter("household");
        System.out.println("household:"+household);
        //String createtimeStr = request.getParameter("createtime");
        String registrar = request.getParameter("registrar");

        String address = request.getParameter("address");
        String cardid = request.getParameter("cardid");


        String phone = request.getParameter("phone");
        String sex = request.getParameter("sex");
        String healthstatus = request.getParameter("healthstatus");
        String educationlevel = request.getParameter("educationlevel");
        String birthdaystr = request.getParameter("birthday");
        String iscountryside = request.getParameter("iscountryside");
        String job = request.getParameter("job");
        String organization = request.getParameter("organization");


        FamilyArchives homearchives = new FamilyArchives();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //Date date = sdf.parse(createtimeStr);
            Date date2 = sdf.parse(birthdaystr);
            // 将接收的时间转换成SimpleDateFormat格式
            //String todateStr = sdf.format(date);
            String todateStr2 = sdf.format(date2);
            // 将String格式的时间转换成Date类型
            //Date createtime = sdf.parse(todateStr);
            Date birthday = sdf.parse(todateStr2);
            if(accountArchivesDao.isExit(cardid)){
                Date createtime = new Date();
                homearchives.add(countyid,townid,villageid,groupid,homeid,household,property,address,createtime,registrar,cardid,phone,sex,healthstatus,educationlevel,birthday,property,iscountryside,job,organization);
                list(request, response);
            } else if(accountArchivesDao.isExit(cardid)==false){
                response.setContentType("text/html;charset=UTF-8");
                PrintWriter out=response.getWriter();
                out.println("<script>");
                out.println("alert('该户主身份证已存在！');");
                out.println("history.back();");
                out.println("</script>");
                out.flush();
                out.close();
                //prompt(request, response, "该户主身份证已存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除家庭档案方法
     */
    private void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String homeid = request.getParameter("id");
        FamilyArchives familyArchives = new FamilyArchives();
        try {
            familyArchives.delDB(homeid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        list(request, response);
    }

    /**
     * 添加家庭成员的方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void addFamilyMember(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String homeid = request.getParameter("id");
        String households = request.getParameter("household");

       // String household = new String(households.getBytes("ISO-8859-1"),"utf-8");

        System.out.println(households);
        request.setAttribute("homeid", homeid);
        request.setAttribute("household", households);
        process(request, response, "/page/accountArchives/accountArchives_add.jsp");
    }
}
