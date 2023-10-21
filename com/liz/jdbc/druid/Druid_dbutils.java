package org.example.jdbc.com.liz.jdbc.druid;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * druid + dbutils简化代码
 * druid简化的是connection
 * dbutils简化的是sql的增删改查。
 */
public class Druid_dbutils {

    public static void main(String[] args) {
        Dbutils_query dbutilsQuery = new Dbutils_query();
        System.out.println("多行多列查询");
        dbutilsQuery.beanListQuery();
        System.out.println("单行查询");
        dbutilsQuery.beanQuery();
        System.out.println("单行单列查询");
        dbutilsQuery.scalarQuerey();

    }
}
class Dbutils_query{
    public void beanListQuery(){
        Connection connection = null;
        try {
            connection = JdbcDruid.getConnection();
            //Queryrunner是dbutiis中的类
            QueryRunner queryRunner = new QueryRunner();
            String sql = "select id,name from student where id >=?";
            /**
             * connection 在Query的内部完成对sql语句的praparedstatement,resultset
             *
             * sql 具体的执行的sql语句
             *
             * BeanListHandler<Student.class> ,完成对resultset的查询，list的集合的遍历
             * list集合中放的javabean类的创建。。。
             * 在Query的方法中使用后自动的关闭resultset ,praparedstatement
             *
             * 1 是sql语句中的？要填入的数
             *
             */
            List<Student> query =
                    queryRunner.query(connection, sql, new BeanListHandler<Student>(Student.class), 1);
            for(Student student:query){
                System.out.println(student);
            }

        }catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JdbcDruid.close(null,null,connection);

        }
    }

    public void beanQuery()  {
        Connection connection = null;
        try {
      connection = JdbcDruid.getConnection();
            QueryRunner queryRunner = new QueryRunner();
            String sql = "select id,name from student where id =?";
            Student student = queryRunner.query(connection, sql, new BeanHandler<Student>(Student.class), 300);
           System.out.println(student);

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
JdbcDruid.close(null,null,connection);

    }

    }

    public void scalarQuerey(){
        Connection connection = null;
        try {
          connection = JdbcDruid.getConnection();
            QueryRunner queryRunner = new QueryRunner();
            String sql = "select name from student where id =?";
            Object result = queryRunner.query(connection, sql, new ScalarHandler(), 300);
            System.out.println(result);
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JdbcDruid.close(null,null,connection);
        }
    }
}
