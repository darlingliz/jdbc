package org.example.jdbc.com.liz.jdbc.druid;

import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.SQLException;

public class Dbutils_Dml {
    public static void main(String[] args) {
        /*虽然这里dml中的每一个方法中都有一个connection
        但是在调用dml的方法的时候发现这几个方法使用的是一个
        连接数据库的线程，因为这里使用的是druid的连接池
         */
        Dml dml = new Dml();
        dml.insert();
        dml.delete();
        dml.update();

    }
}

class Dml {
    public void insert() {
        Connection connection = null;
        try {
            connection = JdbcDruid.getConnection();
            QueryRunner queryRunner = new QueryRunner();
            String sql = "insert into student values (?,?,null)";
            int update = queryRunner.update(connection, sql, 300, "bob");
            if(update <= 0){
                System.out.println("insert()未能对数据库造成影响");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JdbcDruid.close(null,null,connection);
        }

    }
    public void delete(){
        Connection connection = null;
        try {
            connection = JdbcDruid.getConnection();
            QueryRunner queryRunner = new QueryRunner();
            String sql = "delete from student where id =? ";
            int update = queryRunner.update(connection, sql, 400);
            if(update <= 0){
                System.out.println("delete()未能对数据库造成影响");
                return;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JdbcDruid.close(null,null,connection);
        }

    }

    public void update(){
        Connection connection =null;
        try {
           connection =  JdbcDruid.getConnection();
            QueryRunner queryRunner = new QueryRunner();
            String sql = "update student set name = ? where id = ?";
            int update = queryRunner.update(connection, sql, "qqqqqq", 500);
            if(update <= 0){
                System.out.println("update()未能对数据库造成影响");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JdbcDruid.close(null,null,connection);
        }
    }

}