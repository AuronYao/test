package jdbc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * 该类用于维护数据库连接
 * DBCP   DB Connection Pool
 * tom cat
 * @author Ynis
 *
 */
public class DBUtil {
	/*
	 * ThreadLocal用于线程跨方法共享数据使用
	 * ThreadLocal内部有一个Map,key为需要共享数据的线程本身,value就是其要共享的数据
	 */
	private static ThreadLocal<Connection> tl = null;
	private static BasicDataSource dataSource;
 	
	static{
		/*
		 * properties文件常被用作配置文件,结构简单
		 */
		Properties prop = new Properties();
		try {
		    prop.load(new InputStreamReader(DBUtil.class.getClassLoader().getResourceAsStream("dbconfig.properties"),"UTF-8"));
			String className = prop.getProperty("classname");
			String url = prop.getProperty("url");
			String username = prop.getProperty("username");
			String password = prop.getProperty("password");
			int maxActive = Integer.parseInt(prop.getProperty("maxactive"));
			long maxWait = Integer.parseInt(prop.getProperty("maxwait"));
			tl = new ThreadLocal<Connection>();
			//初始化连接池
			dataSource = new BasicDataSource();
			dataSource.setDriverClassName(className);
			dataSource.setUrl(url);
			dataSource.setUsername(username);
			dataSource.setPassword(password);
			//设置最大连接数
			dataSource.setMaxActive(maxActive);
			//设置最大等待时间
			dataSource.setMaxWait(maxWait);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		
	}
	/**
	 * 获取数据库连接
	 * @return
	 * @throws Exception 
	 */
	public static Connection getConnection() throws Exception{
		Connection conn = null;
		try {
			if(tl.get()==null){
				conn = dataSource.getConnection();
				tl.set(conn);
			}
			return tl.get();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	public static void closeConnection(){
		
		Connection conn = tl.get();
		tl.remove();
		if(conn!=null){
			try {
				conn.commit();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 开启事务
	 * @throws SQLException 
	 */
	public static void transBegin() throws Exception{
		try {
			tl.get().setAutoCommit(false);
		} catch (Exception e) {
			//此处可以记录异常,然后抛出
			throw e;
		}
	}
	
	
	/**
	 * 事物回滚
	 * @throws SQLException 
	 */
	public static void transRollBack() throws Exception{
		try {
			tl.get().rollback();
			tl.get().setAutoCommit(true);
		} catch (Exception e) {
			throw e;
		}
	}
	/**
	 * 提交事务
	 * @throws Exception 
	 */
	public static void transCommit() throws Exception{
		try {
			tl.get().commit();
			tl.get().setAutoCommit(true);
		} catch (Exception e) {
			throw e;
		}
	}

}

