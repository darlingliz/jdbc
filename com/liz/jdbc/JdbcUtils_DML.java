package com.liz.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//演示jdbctils的修改功能
public class JdbcUtils_DML {
    public static void main(String[]args){
        testUpdate();
    }

    public   static void testUpdate(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
           connection = JdbcUtils.getConnection();
            String sql = "update class set name = ? where id = ?";
          preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,"ppp");
            preparedStatement.setInt(2,1);
            int i = preparedStatement.executeUpdate();
            if(i > 0){
                System.out.println("修改成功");
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            JdbcUtils.close(null,preparedStatement,connection);
        }
    }
}
