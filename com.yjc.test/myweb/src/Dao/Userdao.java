package Dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;


import yjc.utils.JdbcUtils;
import yjc.biean.User;

public class Userdao {


    private JdbcTemplate template = new JdbcTemplate(JdbcUtils.getsouce());

    //这里从数据库里边读取数据并提供一个返回对象
    public User Login(User user)
    {

        String sql = "select * from users where name = ? and password = ?";
        User userlogin;
        try {
         userlogin = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class)
                    , user.getName(), user.getPassword());

        }catch (Exception e)
        {
            userlogin = null;
        }
        return userlogin;

    }
}
