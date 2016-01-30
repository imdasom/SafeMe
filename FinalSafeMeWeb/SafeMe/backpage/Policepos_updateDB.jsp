<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*"%>
<%@ page import="org.json.simple.JSONObject"%>
<%@ page import="org.json.simple.JSONArray"%>

<%
	int id = 0;
	String loc_str = null;
	String loc_lat = null;
	String loc_lon = null;

	id = Integer.parseInt(request.getParameter("id"));
	loc_str = request.getParameter("str");
	loc_lat = request.getParameter("lat");
	loc_lon = request.getParameter("lon");

	String DB_URL = "jdbc:mysql://localhost:3306/safemetest?";
	String DB_USER = "root";
	String DB_PASSWORD = "test123";
	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;

	try {
		//DB connection
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

		//String query = "update policepos set loc_str = ?, loc_lat = ?, loc_lng = ? where id = ?";
		String query = "insert into policepos(id, loc_str, loc_lat, loc_lng) values (?, ?, ?, ?)";
		stmt = conn.prepareStatement(query);
		stmt.setInt(1, id);
		stmt.setString(2, loc_str);
		stmt.setString(3, loc_lat);
		stmt.setString(4, loc_lon);
		//stmt.setString(1, loc_lat);
		//stmt.setString(2, loc_lon);
		//stmt.setInt(3, id);
		
		stmt.executeUpdate();

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