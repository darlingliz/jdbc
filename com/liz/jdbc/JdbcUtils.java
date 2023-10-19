package com.liz.jdbc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class JdbcUtils {
    /*
    属性定义为static?
    因为在整个的项目中可以保持不变，同时访问的时候不用创建多个对象
     */
    private static String user;
    private static String password;
    private static String url;
    private static String driver;

    static {
        //四个属性的赋值要做静态的代码快中
        /*
         * 可以保证在类的加载的时候只加载一次，减少代码的冗余性。
         * 其实这里还不太的理解
         */
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("test1\\src\\mysql.properties"));
            user = properties.getProperty("user");
            password = properties.getProperty("password");
            url = properties.getProperty("url");
            driver = properties.getProperty("driver");

        } catch (IOException e) {
            //为什么要把编译的异常变为运行时的异常？我还是不太懂
         /*
         1，调用者可以忽略或可以捕获。
         就是转成是运行时的异常后，可以不处理，默认的是throws，
         如果有异常的时候就会中断。
         或者知道这个代码有问题，自己try{}catch{}，自己捕获。
          */
            throw new RuntimeException(e);
        }

    }
/*
得到class.forName(com.mysql.cj.jdbc.Driver)在文件中有可以不写
DriverManager.registerDriver()在com.mysql.cj.jdbc.Driver的
静态的代码快中，也可以不写。
 */
    public Connection getConnection()  {
        try {

            return DriverManager.getConnection(url, user, password);
        }catch(SQLException e){
            throw  new RuntimeException(e);
        }
    }

    /*可能需要关闭的对象 resultset
    statement ,praparedstatement（praparestatement的接口是statement
    可以使用接口的动态），connection
    如果没有使用到就输入空。
     */

    public void close(ResultSet set, Statement statement,Connection connect){
        if(set != null){
                try {
                    set.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
            }
        }

        if(statement != null){
            try {
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if(connect != null){
            try{
                connect.close();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
    }
}
