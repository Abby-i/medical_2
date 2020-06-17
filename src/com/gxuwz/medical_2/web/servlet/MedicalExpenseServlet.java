package com.gxuwz.medical_2.web.servlet;

import com.gxuwz.medical_2.dao.*;
import com.gxuwz.medical_2.domain.Policy.Policy;
import com.gxuwz.medical_2.domain.S201.S201;
import com.gxuwz.medical_2.domain.getAge.AgeUtil;
import com.gxuwz.medical_2.domain.medicalCard.MedicalCard;
import com.gxuwz.medical_2.domain.medicalExpense.MedicalExpense;
import com.gxuwz.medical_2.domain.payRecord.PayRecord;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class MedicalExpenseServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    PolicyDao policyDao = new PolicyDao();
    MedicalCardDao medicalCardDao = new MedicalCardDao();
    MedicalExpense medicalExpense = new MedicalExpense();
    ChronicDiseaseDao chronicDiseaseDao = new ChronicDiseaseDao();
    S201Dao s201Dao = new S201Dao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String m = request.getParameter("m");
        if ("input".equals(m)) {
            input(request, response);
        } else if ("search".equals(m)) {
            search(request, response);
        } else if ("add".equals(m)) {
            add(request, response);
        }/*else if("input".equals(m)){
            input(request,response);
        }else if("add".equals(m)){
            add(request,response);
        }else if("del".equals(m)){

        } else if("edit".equals(m)){

        }else if("search".equals(m)){
            search(request,response);
        }*/
    }

    /**
     * 跳转到search页面
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response, "/page/illExpense/illExpense_search.jsp");
    }

    /**
     * 查找输入的农合证号的信息
     * 获取该农合账号对应的档案信息
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void input(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nongheCard = request.getParameter("nongheCard");
        String selectyear = request.getParameter("selectyear");
        if (nongheCard.equals("")) {
            prompt(request, response, "請輸入农合证号");
            return;
        }
        MedicalExpenseDao medicalExpenseDao = new MedicalExpenseDao();
        /**
         * 判断是否建立檔案
         */
        MedicalCard medicalCard = null;
        try {
            medicalCard = medicalExpenseDao.queryillcard(nongheCard, selectyear);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (medicalCard == null) {
            prompt(request, response, "该農合證號为参合");
            return;
        } else {
            try {
                Policy policy = policyDao.queryCeiling(selectyear);//查询当前年度的慢病缴费政策
                request.setAttribute("payrecode", medicalCard);//
                request.setAttribute("selectyear", selectyear);
                request.setAttribute("policy", policy);
            } catch (Exception e) {
                prompt(request, response, "当前年度慢性病政策未设置，请于管理可与管");
                return;
            }
            process(request, response, "/page/illExpense/illExpense_add.jsp");
        }
    }

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idCard = request.getParameter("idCard");//身份证号
        String illCode = request.getParameter("illCode");//疾病编码
        String nongheCard = request.getParameter("nongheCard");//农合证号
        String medicalCost = request.getParameter("medicalCost");//医疗总费用
        String invoiceNum = request.getParameter("invoiceNum");//发票号
        String hospitalCode = request.getParameter("hospitalCode");//医院编号
        String clinicTimestr = request.getParameter("clinicTime");//就诊时间
        String isNative = request.getParameter("isNative");//是否本地
        String remittanceStatus = request.getParameter("remittanceStatus");//汇款状态
        String auditStatus = request.getParameter("auditStatus");//审核状态
        String operator = request.getParameter("operator");//
        String selectyear = request.getParameter("selectyear");
        String name = request.getParameter("name");
        System.out.println("hospitalCode:" + hospitalCode);

        S201 s201 = s201Dao.queryById(hospitalCode);
        String hospitalName = s201.getItemname();
        System.out.println("hospitalName:" + hospitalName);

        System.out.println("idCard:" + idCard);
        Date clinicTime = null;
        try {
            clinicTime = AgeUtil.toDateFormat(clinicTimestr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //如果illcode存在不为空。则符合条件select illCode from t_ill_card where selectyear like '"+selectyear+"%' and idCard = ?
        String illCard_illCode = MedicalCardDao.querySelectYear(selectyear);
        System.out.println(illCard_illCode.toString());
        if (illCard_illCode.equals("")) {
            prompt(request, response, "慢性病证已过期");
            return;
        }

        //系统查询当前年度慢性病封顶线及报销比例
        Policy policy = policyDao.queryCeiling(selectyear);
        double ceiling = policy.getTopline();//封顶线
        double ratio = policy.getProportion();//报销比例

        //系统预计算本次报销金额；
        double cost = Double.valueOf(medicalCost);
        System.out.println("cost:" + cost);
        double expense = cost * ratio;
        System.out.println("expense:" + expense);

        //系统计算当前年度慢性病已报销总金额；
        double totalExpense = policyDao.CountExpense(selectyear, idCard);
        System.out.println("totalExpense:" + totalExpense);

        //系统计算本次报销金额；
        if ((ceiling - totalExpense) >= expense) {
            //存预报销金额：
            double expenseAccount = expense;
            try {
                MedicalExpense.add(idCard, illCode, nongheCard, medicalCost, expenseAccount, invoiceNum, hospitalCode, clinicTime, isNative, remittanceStatus, auditStatus, operator, name, hospitalName);
                theScope(request, idCard, illCode, nongheCard, medicalCost, invoiceNum, isNative, operator, name,
                        hospitalName, clinicTimestr, expense);
                process(request, response, "/page/illExpense/illExpense_print.jsp");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if ((ceiling - totalExpense) > 0 && (ceiling - totalExpense) < expense) {
            //存(ceiling-totalExpense)  封顶线-年度已报销金额
            double expenseAccount = ceiling - totalExpense;
            try {
                MedicalExpense.add(idCard, illCode, nongheCard, medicalCost, expenseAccount, invoiceNum, hospitalCode, clinicTime, isNative, remittanceStatus, auditStatus, operator, name, hospitalName);

                theScope(request, idCard, illCode, nongheCard, medicalCost, invoiceNum, isNative, operator, name,
                        hospitalName, clinicTimestr, expense);
                process(request, response, "/page/illExpense/illExpense_print.jsp");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void theScope(HttpServletRequest request, String idCard, String illCode, String nongheCard,
                          String medicalCost, String invoiceNum, String isNative, String operator, String name, String hospitalName,
                          String clinicTimestr,Double expense) {
        request.setAttribute("idCard", idCard);
        request.setAttribute("name", name);
        request.setAttribute("illCode", illCode);
        request.setAttribute("nongheCard", nongheCard);
        request.setAttribute("medicalCost", medicalCost);
        request.setAttribute("invoiceNum", invoiceNum);
        request.setAttribute("hospitalName", hospitalName);
        request.setAttribute("medicalCost", medicalCost);
        request.setAttribute("invoiceNum", invoiceNum);
        request.setAttribute("clinicTime", clinicTimestr);
        request.setAttribute("isNative", isNative);
        request.setAttribute("operator", operator);
        request.setAttribute("expense", expense);
    }
}
