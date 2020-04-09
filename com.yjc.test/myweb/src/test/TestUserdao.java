package test;

import Dao.Userdao;
import com.google.gson.Gson;
import org.junit.Test;

import yjc.biean.User;


public class TestUserdao {
    @Test
   public  void testlogin() {
        Userdao userdao = new Userdao();
        User person = new User();
        String name = "张三";
        person.setName(name);
        person.setPassword("12345");
        User loninperson =userdao.Login(person);
        if(null != loninperson) {
            System.out.println(loninperson.toString());
        }

    }
    @Test
    public void testJson()
    {
        Gson gson = new Gson();
        User person = new User();
        String name = "张三";
        person.setName(name);
        person.setPassword("12345");
        String s = gson.toJson(person);
        System.out.println(person.toString());
        System.out.println("-------------\n");
        System.out.println(s);
        System.out.println("-------------\n");
        User user = gson.fromJson(s, User.class);
        System.out.println(user);


    }
}
