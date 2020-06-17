package com.gxuwz.medical_2.web.servlet;

import com.gxuwz.medical_2.dao.AccountArchivesDao;
import com.gxuwz.medical_2.dao.FamilyArchivesDao;
import com.gxuwz.medical_2.dao.PayRecordDao;
import com.gxuwz.medical_2.dao.PayStandardDao;
import com.gxuwz.medical_2.domain.FamilyArchives.FamilyArchives;
import com.gxuwz.medical_2.domain.accountArchives.AccountArchives;
import com.gxuwz.medical_2.domain.getAge.AgeUtil;
import com.gxuwz.medical_2.domain.getAge.NumUtil;
import com.gxuwz.medical_2.domain.payRecord.PayRecord;
import com.gxuwz.medical_2.domain.payStandard.PayStandard;
import com.gxuwz.medical_2.domain.vo.PageBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PayRecordServlet extends BaseServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String m =req.getParameter("m");
        if("list".equals(m)){
            list(req,resp);
            process(req, resp, "/page/payRecord/payRecord_list.jsp");
        }else if("detail".equals(m)){
            try {
                detail(req,resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if("calAccount".equals(m)){
            calAccount(req,resp);
        }else if("pay".equals(m)){
            try {
                pay(req,resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 缴费登记分页列表显示
     */

    private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PageBean page = new PageBean();
        String household=request.getParameter("household");
        String key = request.getParameter("keywords");
        System.out.println("key="+key);
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
        int count = FamilyArchivesDao.queryCount(household);
        page.setTotalCount(count);
        page.setPageSize(Integer.parseInt(pageSize));
        FamilyArchivesDao familyArchivesDao = new FamilyArchivesDao();
        List<FamilyArchives> payRecords = familyArchivesDao.queryFamilyarchives(page.getCurrentPage(), page.getPageSize(),key);
        page.setData(payRecords);
        request.setAttribute("page", page);
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("payRecords", payRecords);
    }

    /**
     * 缴费登记弹出框内容
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String homeid = request.getParameter("homeid");

        AccountArchivesDao archivesDao = new AccountArchivesDao();
        // 全部家庭成员的身份证
        List<String> homeAll = archivesDao.queryAllPersonid(homeid);
        // 已当前年度已参合家庭成员的身份证
        PayRecordDao payRecordDao = new PayRecordDao();
        List<String> hasPay = payRecordDao.queryHasPayPersonid(homeid);
        homeAll.removeAll(hasPay);
        request.setAttribute("homeAll", homeAll);
        System.out.println(homeAll.size());
        process(request, response, "/page/payRecord/payRecord_add.jsp");
    }

    /**
     * 缴费金额统计
     * @param request
     * @param response
     */
    private void calAccount(HttpServletRequest request, HttpServletResponse response) {
        // 1:接收表单传过来的缴费人数
        int payNum = Integer.parseInt(request.getParameter("payNum"));
        try {
            // 2:获取当前年度的缴费标准金额
            Calendar date = Calendar.getInstance();
            String annual = String.valueOf(date.get(Calendar.YEAR));


            PayStandard psd = new PayStandard(annual);
            Double account = psd.getPay_cost();
            // 3:执行计算方法
            PayRecordDao payRecordDao = new PayRecordDao();
            double amount = payRecordDao.calAccount(payNum, account);
            response.getWriter().write(""+amount);

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 缴费
     * @param request
     * @param response
     * @throws Exception
     */
    private void pay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] cardids=request.getParameterValues("ids");
        String operator =(String) request.getSession().getAttribute("username");
        NumUtil numUtil= new NumUtil();
        String invoiceNum = numUtil.creatid();
        String joinNum ="";
        PayRecord payRecord = new PayRecord();
        AgeUtil ageUtil = new AgeUtil();
        PayStandardDao payStandardDao = new PayStandardDao();
        PayStandard  stantard = payStandardDao.findById(ageUtil.getNowyear());
        if(check()==true){

            String homeid ="";
            for (int i = 0; i < cardids.length; i++) {
                AccountArchivesDao accountArchivesDao = new AccountArchivesDao();
                AccountArchives archives =  accountArchivesDao.queryPersonById(cardids[i].replace(" ", ""));
                joinNum = accountArchivesDao.queryNongheBycardid(cardids[i].replace(" ", ""));
                homeid=archives.getHomeid();
                payRecord.add(homeid,archives.getHousehold(),archives.getCardid(),archives.getName(),stantard.getPay_cost(),operator,invoiceNum,joinNum);
            }
        }else{
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out=response.getWriter();
            out.println("<script>");//输出script标签
            out.println("alert('不在缴费标准规定时间范围内');");//js语句：输出alert语句
            out.println("history.back();");//js语句：输出网页回退语句
            out.println("</script>");//输出script结尾标签
            out.flush();//清空缓存
            out.close();
        }
    }
    private boolean check() {
        try{
            boolean flag = false;
            // 获取系统当前年度以及当前时间
            Calendar date = Calendar.getInstance();
            String annual = String.valueOf(date.get(Calendar.YEAR));
            Date payTime = date.getTime();
            // 获取当前年度的缴费标准规定时间范围
            PayStandardDao payStandardDao = new PayStandardDao();
            PayStandard stantard = payStandardDao.findById(annual);
            // 设置当前时间
            date.setTime(payTime);
            // 设置缴费开始时间
            Calendar begin = Calendar.getInstance();
            begin.setTime(stantard.getStartTime());
            // 设置缴费结束时间
            Calendar end = Calendar.getInstance();
            end.setTime(stantard.getEndTime());

            // 判断当前时间是否等于缴费标准规定时间
            if(payTime == stantard.getStartTime() || payTime == stantard.getEndTime()) {
                flag = true;
            }
            // 判断当前时间是否在缴费标准规定时间范围内
            if (date.after(begin) && date.before(end)) {
                flag = true;
            }
            if(flag) {
                return true;
            }else {
                return false;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
