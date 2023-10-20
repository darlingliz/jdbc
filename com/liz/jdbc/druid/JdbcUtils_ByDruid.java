package org.example.jdbc.com.liz.jdbc.druid;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class JdbcUtils_ByDruid {
    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        //把resultset中的结果保留在集合中可以不受connection的关闭的影响
        ArrayList<Student> list = new ArrayList<Student>();
        try {

            connection = JdbcDruid.getConnection();
            String sql = "select id,name from student where id >=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 1);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                //new student（）把集合中的抽象成一个student对象存在集合中
                list.add(new Student(id,name));
            }
            /*
            结果输出是null，经过测试从resultset中取出的是有值的，
            问题就出在add或for()
            解决：Student的有参构造只传了参数，没有赋值。
             */
            for(Student student:list){
                System.out.println(student);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally{
            JdbcDruid.close(resultSet,preparedStatement,connection);
        }


    }
}

class JdbcDruid {
    static DataSource dataSource = null;
/*
一直报错，nullPointException，知道是dataSource是null的，
经过debug知道是properties,我之前一直以为是properties的路径写错了，
后来知道解决代码块前加static ,非static的代码块加载是在创建对象的时候，
但是这是一个工具类，所以的方法都是静态的，所以等于代码块中的内容根本没有
加载。
 */
    static{
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("src\\druid.properties"));
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        }catch(IOException e){
            /*
            因为是工具类，不能直接的把异常抛出（不然在使用方法的时候还是有编译异常要处理），
            可以使编译异常转成运行异常（可以捕获，可以默认处理）
             */
            throw  new RuntimeException(e);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void close(ResultSet set, Statement statement, Connection connection) {
        try {
            if (set != null) {
                set.close();
            }
            if (statement != null) {
                statement.close();
            }
            /*connection.close不是关闭了对数据库的连接
            只是把连接放回了连接池
            Connection是一个接口，不同的实现作用是不一样的，
            jdbc中的mysql实现的作用是对数据库的连接，所以
            jdbc中的close是关闭对数据库的连接，
            druid实现的是对连接池的连接，所以关闭的是对线程池
            的连接。
             */
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
