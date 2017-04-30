<%@page import="java.net.InetAddress"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*"%>
<%@ page import="org.json.simple.JSONObject"%>
<%@ page import="org.json.simple.JSONArray"%>

<%
	int id = 0;
	String phonenum = null;
	String filename = null;
	String loc_str = null;
	String loc_lat = null;
	String loc_lon = null;
	
	phonenum = request.getParameter("pnum");
	filename = request.getParameter("fname")+".mp4";
	loc_str = request.getParameter("str");
	loc_lat = request.getParameter("lat");
	loc_lon = request.getParameter("lon");
	loc_str.replace("%20", " ");
	System.out.println(loc_str);

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
		
		stmt = conn.prepareStatement("SELECT COUNT(*) FROM NOTIFY");
		rs = stmt.executeQuery();
		
		int count = 0;
		if(rs.next()){
			count = rs.getInt(1); // id 값 얻어오기 위해 수행한다.
		}
		
		id = count+1;
		out.print("id : " + id + "</br>");
		
		String query = "insert into notify (id, date, time, phonenum, filename, loc_str, loc_lat, loc_lng)"
				+ "values (?, now(), curtime(), ?, ?, ?, ?, ?)";
		stmt = conn.prepareStatement(query);
		System.out.println("prepareStatement");
		stmt.setInt(1, id);		
		stmt.setString(2, phonenum);
		stmt.setString(3, filename);
		stmt.setString(4, loc_str);
		stmt.setString(5, loc_lat);
		stmt.setString(6, loc_lon);

		System.out.println("setString");

		stmt.executeUpdate();
		System.out.println("executeUpdate");

		stmt = conn.prepareStatement("SELECT * FROM NOTIFY");
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