package yjc.utils;


import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 使用数据库连接池来连接数据库。
 *
 */
public class JdbcUtils {
    private static DataSource ds ;
    static {
        //加载配置文件  ?serverTimezone=UTC
        Properties properties = new Properties();
        /**
         * 加载这个类的字节码文件
         * getresource 可以拿到路径

        ClassLoader classLoader = JdbcUtils.class.getClassLoader();
        URL  path =classLoader.getResource("文件名");
        try {
            FileWriter fileWriter = new FileWriter(path.getFile());
         properties.load(fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }**/

        InputStream is = JdbcUtils.class.getClassLoader().getResourceAsStream("druid.properties");
        //初始化连接池
        try {
            properties.load(is);
         ds= DruidDataSourceFactory.createDataSource(properties);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static DataSource getsouce()
    {
        return ds;
    }

    public  static Connection getConnection() throws SQLException {
        return ds.getConnection();

    }


}
