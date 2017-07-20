package jdbctest;

import java.sql.Connection;


import jdbc.DBUtil;

public class Demo1 {
    
    public static void main(String[] args) {
        Connection conn = null;
        try{
            conn = DBUtil.getConnection();
            System.out.println(conn);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            DBUtil.closeConnection();
        }
        
        
       
        
        
    }
}
