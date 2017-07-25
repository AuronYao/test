package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UpdateDemo {
    public static void main(String[] args) {
        Connection conn = null;
        try{
            conn = DBUtil.getConnection();
            String sql = "update emp set name='北京Eminem' where id=?";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setString(1, "emp001");
            int resultRow = state.executeUpdate();
            System.out.println("操作影响了：" + resultRow + "条记录");
            
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            DBUtil.closeConnection();
        }
        
        
       
        
        
    }
}
