<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*"%>
<%@ page import="org.json.simple.JSONObject"%>
<%@ page import="org.json.simple.JSONArray"%>

<%
	String DB_URL = "jdbc:mysql://localhost:3306/safemetest?";
	String DB_USER = "root";
	String DB_PASSWORD = "test123";
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	
	int[] id = null;
	String[] lattitude = null;
	String[] longitude = null;

	try {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		stmt = conn.createStatement();
		
		String query = "SELECT COUNT(*) FROM FOOTPOS";
		rs = stmt.executeQuery(query);
		
		int number = 0;
		if(rs.next()){
			number = rs.getInt(1);
		}
		
		id = new int[number];
		lattitude = new String[number];
		longitude = new String[number];
		
		query = "select * from footpos";
		rs = stmt.executeQuery(query);
		
		String s;
		int idx = 0;
		while (rs.next()) {
			id[idx] = rs.getInt(1);
			lattitude[idx] = rs.getString(2);
			longitude[idx] = rs.getString(3);
			idx++;
		}
		rs.close();
		stmt.close();
		conn.close();
	} catch (Exception e) {
		out.print("Exception Error...");
		out.print(e.toString());
	} finally {

	}

	JSONObject jsonMain = new JSONObject();
	JSONArray jArray = new JSONArray();
	JSONObject jObject = new JSONObject();
	
	int idx2 = 0;
	while(idx2 < id.length){
		jObject = new JSONObject();
		jObject.put("id", id[idx2]);
		jObject.put("lat", lattitude[idx2]);
		jObject.put("lon", longitude[idx2]);
		jArray.add(jObject);
		idx2++;
	}
	
	jsonMain.put("footpos", jArray);

	out.println(jsonMain);
	System.out.println("------Footpos 전송 성공-----");
	out.flush();
%>