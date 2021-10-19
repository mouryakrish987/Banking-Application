package com.dbconection;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionInstance {
	public static Connection con= null;
	public static Connection getInstance() {
	String url=null;
	String username =null;
	String password =null;
	try(FileInputStream fis = new FileInputStream("dbconfig.properties")){
		Properties pro = new Properties();
		pro.load(fis);
		url=pro.getProperty("url");
		username = pro.getProperty("username");
		password = pro.getProperty("password");
			
	}catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	if(con==null) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con= DriverManager.getConnection(url, username, password);
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
	return con;
	
	}
	public static void CloseConnection() {
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

