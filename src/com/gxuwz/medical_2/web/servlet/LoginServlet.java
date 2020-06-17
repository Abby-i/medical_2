package com.gxuwz.medical_2.web.servlet;

import com.gxuwz.medical_2.domain.menu.Menu;
import com.gxuwz.medical_2.domain.role.Role;
import com.gxuwz.medical_2.domain.user.User;
import com.gxuwz.medical_2.exception.UserNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class LoginServlet extends BaseServlet{

    //页面提交方式，无论是以何种方式提交都会变成post方式
    public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        doPost(request,response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        login(request,response);
    }

    /**
     * 用户登录
     */
    private void login(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
       //跳转路径
        String path = "/login.jsp";
        try {
            //获取页面用户输入
            String userId = request.getParameter("userId");
            String password = request.getParameter("password");
            User user = new User(userId,password);
            //
            request.getSession().setAttribute("username", user.getFullname());
            /**
             * 查询出用户对的角色所拥有的权限，并存到session域对象
             * 循环查出user对应的角色，以及权限id
             */
            for(Role role:user.getRoles()){
                for(Menu menu:role.getMenus()){
                    String name=menu.getMenuid();
                    String value=menu.getMenuid();
                    request.getSession().setAttribute(name, value);
                }
            }

            path="index.jsp";
        }catch(UserNotFoundException e){
            path="login.jsp";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //跳转页面
        process(request, response, path);


    }
}
