package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil  {
	private static Connection conn;  
//	private static String url = "jdbc:mysql://sql2.njit.edu/pvp32";  
//	private static String user = "pvp32";//Username of database  
//	private static String pwd = "forbes95";//Password of database
	
	private static String url = "jdbc:mysql://localhost/pvp32";  
	private static String user = "root";//Username of database  
	private static String pwd = "";//Password of database

	public static Connection dbConnect() throws SQLException{  
		try{  
			Class.forName("com.mysql.jdbc.Driver").newInstance();  
		}catch(ClassNotFoundException cnfe){  
			System.err.println("Error: "+cnfe.getMessage());  
		}catch(InstantiationException ie){  
			System.err.println("Error: "+ie.getMessage());  
		}catch(IllegalAccessException iae){  
			System.err.println("Error: "+iae.getMessage());  
		}  
		conn = (Connection) DriverManager.getConnection(url,user, pwd);  
		return conn;  
	}  

	public static Connection getConnection() throws SQLException, ClassNotFoundException{  
		if(conn !=null && !conn.isClosed())  
			return conn;  
		dbConnect();  
		return conn;  
	}

	//Close Connection
	public static void dbDisconnect() throws SQLException {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (Exception e){
			throw e;
		}
	}
}
