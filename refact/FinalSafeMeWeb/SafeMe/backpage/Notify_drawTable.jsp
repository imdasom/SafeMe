<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*"%>
<%@ page import="org.json.simple.JSONObject"%>
<%@ page import="org.json.simple.JSONArray"%>
<h1> notifytest2 Å×ÀÌ÷ם </h1>
<%
	String DB_URL = "jdbc:mysql://localhost:3306/safemetest?";
	String DB_USER = "javauser";
	String DB_PASSWORD = "javadude";
	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;
	
	try {
		//DB connection
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		
		String query = "select * from notifytest2;";
		stmt = conn.prepareStatement(query);
		rs = stmt.executeQuery();
		
		int idx = 0;
		while(rs.next()){
			out.println(rs.getInt(1)+", "+rs.getString(2)+","
					+rs.getString(3)+", "+rs.getString(4)+", "+rs.getString(5)+","
					+rs.getString(6)+", "+rs.getString(7)+", "+rs.getString(8)+"<br>");
		}
		
		rs.close();
		stmt.close();
		conn.close();
		
	} catch (Exception e) {
		System.out.println("Exception Error...");
		System.out.println(e.toString());
		out.print("Exception Error...");
		out.print(e.toString());
	} finally {
	}
%>