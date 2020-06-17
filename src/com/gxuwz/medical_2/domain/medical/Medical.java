package com.gxuwz.medical_2.domain.medical;

import com.gxuwz.medical_2.database.DbUtil;
import com.gxuwz.medical_2.domain.S201.S201;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class Medical {
    private String jgbm; //机构编码
    private String zzjgbm; //组织机构编码
    private String jgmc;//机构名称
    private String dqbm;//地区编码
    private String areacode;//行政区域编码
    S201 s201= new S201();
    private String lsgx; //隶属关系
    private String lsgxname;//隶属关系名称
    private String jgjb;//机构级别
    private String jgjbname; //机构级别名称
    private String sbddlx;//申报定点类型
    private String sbddlxname;
    private String pzddlx;//批准定点类型
    private String pzddlxname;
    private String ssjjlx;//所属经济类型
    private String ssjjlxname;
    private String wsjgdl; //卫生机构大类
    private String wsjgdlname;
    private String wsjgxl; //卫生机构小类
    private String wsjgxlname;
    private String zgdw; //主管单位
    private Date kysj; //开业时间
    private String frdb; //法人代表
    private Double zczj;//注册资金

    public Medical() {
    }

    public Medical(String jgbm, String zzjgbm, String jgmc, String dqbm, String areacode, S201 s201, String lsgx, String lsgxname, String jgjb, String jgjbname, String sbddlx, String sbddlxname, String pzddlx, String pzddlxname, String ssjjlx, String ssjjlxname, String wsjgdl, String wsjgdlname, String wsjgxl, String wsjgxlname, String zgdw, Date kysj, String frdb, Double zczj) {
        this.jgbm = jgbm;
        this.zzjgbm = zzjgbm;
        this.jgmc = jgmc;
        this.dqbm = dqbm;
        this.areacode = areacode;
        this.s201 = s201;
        this.lsgx = lsgx;
        this.lsgxname = lsgxname;
        this.jgjb = jgjb;
        this.jgjbname = jgjbname;
        this.sbddlx = sbddlx;
        this.sbddlxname = sbddlxname;
        this.pzddlx = pzddlx;
        this.pzddlxname = pzddlxname;
        this.ssjjlx = ssjjlx;
        this.ssjjlxname = ssjjlxname;
        this.wsjgdl = wsjgdl;
        this.wsjgdlname = wsjgdlname;
        this.wsjgxl = wsjgxl;
        this.wsjgxlname = wsjgxlname;
        this.zgdw = zgdw;
        this.kysj = kysj;
        this.frdb = frdb;
        this.zczj = zczj;
    }

    public Medical(String jgbm, String zzjgbm, String jgmc, String dqbm, String areacode, String lsgx, String jgjb, String sbddlx, String pzddlx, String ssjjlx, String wsjgdl, String wsjgxl, String zgdw, Object kysj, String frdb, double zczj) {
        super();
        this.jgbm = jgbm;
        this.zzjgbm = zzjgbm;
        this.jgmc = jgmc;
        this.dqbm = dqbm;
        this.areacode = areacode;
        this.lsgx = lsgx;
        this.jgjb = jgjb;
        this.sbddlx = sbddlx;
        this.pzddlx = pzddlx;
        this.ssjjlx = ssjjlx;
        this.wsjgdl = wsjgdl;
        this.wsjgxl = wsjgxl;
        this.zgdw = zgdw;
        this.kysj = (Date) kysj;
        this.frdb = frdb;
        this.zczj = zczj;
    }

    /**
     * 添加医疗机构
     * @throws Exception
     */
    public void add()throws Exception{
        Connection conn =null;
        try{
            //1:获得连接
            conn= DbUtil.getConn();
            //2;保存到数据库
            conn.setAutoCommit(false);
            saveToDB(conn);
            conn.commit();
        }catch(SQLException e){
            conn.rollback();
            e.printStackTrace();
            throw e;
        }finally{
            DbUtil.close(null,conn);
        }
    }
    private void saveToDB(Connection conn)throws SQLException{
        PreparedStatement pstmt=null;
        try{

            StringBuffer sqlBuff=new StringBuffer();
            sqlBuff.append("insert into t_medical( jgbm, zzjgbm, jgmc, dqbm, areacode, lsgx, jgjb, "
                    + "sbddlx, pzddlx, ssjjlx, wsjgdl, wsjgxl, zgdw, kysj , frdb,zczj)");
            sqlBuff.append("values");
            sqlBuff.append("(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ?,?)");
            String sql=sqlBuff.toString();
            pstmt=conn.prepareStatement(sql);
            //设置动态参数的值
            int index=1;
            pstmt.setString(index++, this.jgbm);
            pstmt.setString(index++, this.zzjgbm);
            pstmt.setString(index++, this.jgmc);
            pstmt.setString(index++, this.dqbm);
            pstmt.setString(index++, this.areacode);
            pstmt.setString(index++, this.lsgx);
            pstmt.setString(index++, this.jgjb);
            pstmt.setString(index++, this.sbddlx);
            pstmt.setString(index++, this.pzddlx);
            pstmt.setString(index++, this.ssjjlx);
            pstmt.setString(index++, this.wsjgdl);
            pstmt.setString(index++, this.wsjgxl);
            pstmt.setString(index++, this.zgdw);
            pstmt.setDate(index++, new java.sql.Date(this.kysj.getTime()));
            pstmt.setString(index++, this.frdb);
            pstmt.setDouble(index++, this.zczj);

            int count=pstmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
            throw e;
        }finally{
            DbUtil.close(pstmt,null);
        }
    }

    /**
     * 修改医疗机构信息
     * @throws Exception
     */
    public void editToDB(String jgbm, String zzjgbm, String jgmc, String dqbm, String areacode, String lsgx, String jgjb, String sbddlx, String pzddlx, String ssjjlx, String wsjgdl, String wsjgxl, Object zgdw, Object kysj, Object frdb, double zczj) throws Exception {
        Connection conn = null;
        PreparedStatement ptmt=null;
        StringBuffer sqlBuff=new StringBuffer("update t_medical t set t.jgbm=?,t.zzjgbm=?,t.jgmc=?,t.dqbm=?,t.areacode=?,"
                + "t.lsgx=?,t.jgjb=?,t.sbddlx=?,t.pzddlx=?,t.ssjjlx=?,t.wsjgdl=?,t.wsjgxl=?,t.zgdw=?,t.frdb=?,t.zczj=? "
                + "where jgbm=?");
        try{
            conn =DbUtil.getConn();
            conn.setAutoCommit(false);
            ptmt=conn.prepareStatement(sqlBuff.toString());
            int index=1;
            ptmt.setString(index++, jgbm);
            ptmt.setString(index++, zzjgbm);
            ptmt.setString(index++, jgmc);
            ptmt.setString(index++, dqbm);
            ptmt.setString(index++, areacode);
            ptmt.setString(index++, lsgx);
            ptmt.setString(index++, jgjb);
            ptmt.setString(index++, sbddlx);
            ptmt.setString(index++, pzddlx);
            ptmt.setString(index++, String.valueOf(ssjjlx));
            ptmt.setString(index++, wsjgdl);
            ptmt.setString(index++, wsjgxl);
            ptmt.setString(index++, String.valueOf(zgdw));
            ptmt.setString(index++, (String) frdb);
            ptmt.setDouble(index++, Double.parseDouble(String.valueOf(zczj)));
            ptmt.setString(index++, jgbm);
            ptmt.executeUpdate();
            conn.commit();
        }catch (Exception e) {
            e.printStackTrace();
            conn.rollback();
            throw e;
        }finally{
            DbUtil.close(ptmt, conn);
        }
    }

    /**
     * 删除医疗机构信息
     * @param jgbm
     * @throws Exception
     */
    public void delToDB(String jgbm) throws Exception {
        Connection conn = null;

        this.jgbm =jgbm;
        try{
            conn =DbUtil.getConn();
            //1：开启手动提交事务
            conn.setAutoCommit(false);
            //2:删除信息
            deleteFromDB(conn);
            //4：提交事务
            conn.commit();
        }catch (Exception e) {
            conn.rollback();
            throw e;
        }finally{
            DbUtil.close(null,conn);
        }

    }
    private void deleteFromDB(Connection conn) {
        PreparedStatement ptmt=null;
        StringBuffer sqlBuff=new StringBuffer("delete from t_medical where jgbm=?");
        try {
            ptmt=conn.prepareStatement(sqlBuff.toString());
            ptmt.setString(1, this.jgbm);
            ptmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                DbUtil.close(ptmt,null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
    /**
     * getter setter方法
     * @return
     */
    public String getJgbm() {
        return jgbm;
    }

    public void setJgbm(String jgbm) {
        this.jgbm = jgbm;
    }

    public String getZzjgbm() {
        return zzjgbm;
    }

    public void setZzjgbm(String zzjgbm) {
        this.zzjgbm = zzjgbm;
    }

    public String getJgmc() {
        return jgmc;
    }

    public void setJgmc(String jgmc) {
        this.jgmc = jgmc;
    }

    public String getDqbm() {
        return dqbm;
    }

    public void setDqbm(String dqbm) {
        this.dqbm = dqbm;
    }

    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    public S201 getS201() {
        return s201;
    }

    public void setS201(S201 s201) {
        this.s201 = s201;
    }

    public String getLsgx() {
        return lsgx;
    }

    public void setLsgx(String lsgx) {
        this.lsgx = lsgx;
    }

    public String getLsgxname() {
        return lsgxname;
    }

    //隶属关系名称，通过给定的type，在s201中找到对应信息
    public void setLsgxname(String lsgxname) {
        try {
            this.lsgxname = s201.selectName(lsgxname,"02");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getJgjb() {
        return jgjb;
    }

    public void setJgjb(String jgjb) {
        this.jgjb = jgjb;
    }

    public String getJgjbname() {
        return jgjbname;
    }

    public void setJgjbname(String jgjbname) {
        try {
            this.jgjbname = s201.selectName(jgjbname,"06");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getSbddlx() {
        return sbddlx;
    }

    public void setSbddlx(String sbddlx) {
        this.sbddlx = sbddlx;
    }

    public String getSbddlxname() {
        return sbddlxname;
    }

    public void setSbddlxname(String sbddlxname) {
        try {
            this.sbddlxname = s201.selectName(sbddlxname,"04");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPzddlx() {
        return pzddlx;
    }

    public void setPzddlx(String pzddlx) {
        this.pzddlx = pzddlx;
    }

    public String getPzddlxname() {
        return pzddlxname;
    }

    public void setPzddlxname(String pzddlxname) {
        try {
            this.pzddlxname = s201.selectName(pzddlxname,"04");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getSsjjlx() {
        return ssjjlx;
    }

    public void setSsjjlx(String ssjjlx) {
        this.ssjjlx = ssjjlx;
    }

    public String getSsjjlxname() {
        return ssjjlxname;
    }

    public void setSsjjlxname(String ssjjlid) {
        try {
            this.ssjjlxname = s201.selectName(ssjjlid,"01");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getWsjgdl() {
        return wsjgdl;
    }

    public void setWsjgdl(String wsjgdl) {
        this.wsjgdl = wsjgdl;
    }

    public String getWsjgdlname() {
        return wsjgdlname;
    }

    public void setWsjgdlname(String wsjgdlname) {
        try {
            this.wsjgdlname = s201.selectName(wsjgdlname,"03");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getWsjgxl() {
        return wsjgxl;
    }

    public void setWsjgxl(String wsjgxl) {
        this.wsjgxl = wsjgxl;
    }

    public String getWsjgxlname() {
        return wsjgxlname;
    }

    public void setWsjgxlname(String wsjgxlname) {
        try {
            this.wsjgxlname = s201.selectName(wsjgxlname,"03");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getZgdw() {
        return zgdw;
    }

    public void setZgdw(String zgdw) {
        this.zgdw = zgdw;
    }

    public Date getKysj() {
        return kysj;
    }

    public void setKysj(Date kysj) {
        this.kysj = kysj;
    }

    public String getFrdb() {
        return frdb;
    }

    public void setFrdb(String frdb) {
        this.frdb = frdb;
    }

    public Double getZczj() {
        return zczj;
    }

    public void setZczj(Double zczj) {
        this.zczj = zczj;
    }
}
