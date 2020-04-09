package web.servlet;

import Dao.Userdao;
import com.google.gson.Gson;
import yjc.biean.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;


@WebServlet("/demo")
public class ServeletTest extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        
        //改变接受来的文字编码格式，要不然中文乱码
        String temp = req.getParameter("application/json");
        Gson gson = new Gson();
        User person = gson.fromJson(temp, User.class);
        //System.out.println(name +"-----"+ pas);
        Userdao userdao = new Userdao();
        System.out.println(person.toString());
        User login = userdao.Login(person);
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        if(login != null) {
            //设置输入流的编码格式是utf-8 这样子才行
            resp.getOutputStream().write(login.toString().getBytes("UTF-8"));
        }else
        {
            resp.getOutputStream().write("null".toString().getBytes("UTF-8"));
        }
    }

}
