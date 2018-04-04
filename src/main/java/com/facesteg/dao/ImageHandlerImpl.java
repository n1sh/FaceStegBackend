package com.facesteg.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImageHandlerImpl implements ImageHandler{
	static Connection con = null;
	static PreparedStatement st = null;

	@Override
	public void saveName(int number, String name) {
		try {
			makeJDBCConnection();
			String insertQueryStatement = "INSERT  INTO  images_test  VALUES  (?,?)";
			st = con.prepareStatement(insertQueryStatement);
			st.setInt(1, number);
			st.setString(2, name);
			st.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getImage(int number) {
		try {
			// MySQL Select Query Tutorial
			String getQueryStatement = "SELECT * FROM images_test";
			st = con.prepareStatement(getQueryStatement);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				String name = rs.getString("img_name");
				int no = rs.getInt("img_no");
				if(no == number)
					return name;
			}
		} catch (
		SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	private static void makeJDBCConnection() {
		 
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return;
		}
 
		try {
			// DriverManager: The basic service for managing a set of JDBC drivers.
			//jdbc:mysql://localhost:3306/facesteg?autoReconnect=true&useSSL=false
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/facesteg?autoReconnect=true&useSSL=false", "root", "root");
			if (con != null) {
			} else {
				System.out.println("Failed to make connection!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}

	}

	@Override
	public int saveUser(String username, String name, String dob) {
		try {
			makeJDBCConnection();
			String insertQueryStatement = "INSERT  INTO  users(username,uname, dob)  VALUES  (?,?,?)";
			st = con.prepareStatement(insertQueryStatement);
			st.setString(1, username);
			st.setString(2, name);
			st.setString(3, dob);
			int x =st.executeUpdate();
			if(x!=0) {
				String idQuery = "select * from users where username = ?";
				st = con.prepareStatement(idQuery);
				st.setString(1, username);
				ResultSet rs = st.executeQuery();
				rs.next();
				int id = rs.getInt("id");
				System.out.println(id);
				return id;
			}
			else {
				return -1;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return -1;
		
	}

	@Override
	public String checkUser(int id) {
		try {
			makeJDBCConnection();
			String idQuery = "select * from users where id = ?";
			st = con.prepareStatement(idQuery);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			rs.next();
			String username = rs.getString("username");
			System.out.println(id);
			return username;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String checkUsername(String username) {
		try {
			makeJDBCConnection();
			String idQuery = "select * from users where username = ?";
			st = con.prepareStatement(idQuery);
			st.setString(1, username);
			ResultSet rs = st.executeQuery();
			if(!rs.next())
				return "yes";
			return "no";
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return "error";
	}

}
