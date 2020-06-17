package com.gxuwz.medical_2.database;
import java.sql.*;

/**
 * 数据库连接模块
 */
public class DbUtil {
    private static String JDBC_URL;
    private static String JDBC_DRIVER;
    private static String JDBC_USER;
    private static String JDBC_PWD;
    static {
        JDBC_URL="jdbc:mysql://127.0.0.1:3306/medical2db";
        JDBC_DRIVER="com.mysql.jdbc.Driver";
        JDBC_USER="root";
        JDBC_PWD="123456";
    }

    /**
     * 无参构造函数
     */
    public DbUtil () {

    }
    /**
     * 获得连接对象实例
     * @return
     * @throws SQLException
     */
    public static Connection getConn()throws Exception{
        try {
            Class.forName(JDBC_DRIVER);
            Connection conn= DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PWD);
            return conn;
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 关闭数据库资源
     * @param rs
     * @param stmt
     * @param conn
     * @throws SQLException
     */
    public static void close(ResultSet rs, Statement stmt, Connection conn)throws SQLException{
        try {
            if (rs!=null){
                rs.close();
            }
            if (stmt!=null){
                stmt.close();
            }
            if (conn!=null){
                conn.close();
            }
        }catch (SQLException e){
        }
    }
    /**
     * 重载关闭数据库资源
     * @param rs
     * @param stmt
     * @param conn
     * @throws SQLException
     */
    public static void close(Statement stmt, Connection conn)throws SQLException{
        close(null,stmt,conn);
    }
/*   public static void main(String[] args) throws Exception {
    Connection conn = getConn();
    System.out.println(conn);
}*/
}