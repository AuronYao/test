package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import jdbc.DBUtil;

public class SearchDemo {
    
    public static void main(String[] args) {
        Connection conn = null;
        try{
            conn = DBUtil.getConnection();
            String sql = "select name,age,salary from emp where id=?";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setString(1, "emp001");
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                int age = rs.getInt("age");
                double salary = rs.getDouble("salary");
                System.out.println("name:" + name);
                System.out.println("age:" + age);
                System.out.println("salary:" + salary);
            }
            
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            DBUtil.closeConnection();
        }
        
        
       
        
        
    }
}
