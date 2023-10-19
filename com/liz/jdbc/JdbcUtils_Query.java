package com.liz.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUtils_Query {
    public static void main(String[] args) {
        
    }
    public static void query(){
        //数据库的连接
        // 为在finally中关闭，提高这些变量的作用域
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet set = null;
        try{
            /*connecion方法的异常转变成了运行时的异常。
            为防止异常放在try中
             */
            connection = JdbcUtils.getConnection();
            //sql语句的解析
            String sql = "select * from class";
            //执行sql语句
         preparedStatement = connection.prepareStatement(sql);
         //返回执行的结果集
          set = preparedStatement.executeQuery();
          //遍历输出结果，next()如果下一个没有数据返回false
            while(set.next()){
                //可以通过getXXX(“列名” or 列所在的位置)得到每一列的数据
                int id = set.getInt("id");
                String name = set.getString("name");
                System.out.println(id + " " + name );
            }
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            //关闭数据库的连接
            JdbcUtils.close(set,preparedStatement,connection);
        }
    }
}
