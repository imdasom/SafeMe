<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="org.json.simple.JSONObject"%>
<%@ page import="org.json.simple.JSONArray"%>
<%
	request.setCharacterEncoding("UTF-8");
   String DB_URL = "jdbc:mysql://localhost:3306/safemetest?";
   String DB_USER = "root";
   String DB_PASSWORD = "test123";
   Connection conn = null;
   PreparedStatement stmt = null;
   ResultSet rs = null;
   
   
   String query;
   String[] id = null;
   String[] loc_str = null;
   String[] loc_lat = null;
   String[] loc_lon = null;
   int num = 0;

   try {
      Class.forName("com.mysql.jdbc.Driver");
      conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
      stmt = conn.prepareStatement("select count(*) from policepos");
      rs = stmt.executeQuery();

      if(rs.next()){
			num = rs.getInt(1);
		}

      id = new String[num];
      loc_str = new String[num];
      loc_lat = new String[num];
      loc_lon = new String[num];

      stmt = conn.prepareStatement("select * from policepos");
	  rs = stmt.executeQuery();
		
      String s;
      int idx = 0;
      while (rs.next()) {
         id[idx] = String.valueOf(rs.getInt(1));
         loc_str[idx] = rs.getString(2);
         loc_lat[idx] = rs.getString(3);
         loc_lon[idx] = rs.getString(4);
         //System.out.println("id : "+id[idx]);
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
      jObject.put("loc_str", loc_str[idx2]);
      jObject.put("loc_lat", loc_lat[idx2]);
      jObject.put("loc_lon", loc_lon[idx2]);

      //System.out.println("------JSON 파싱 전-----");
      //System.out.println("normal "+idx2+" : "+name[idx2]+", "+lattitude[idx2]+", "+longitude[idx2]);
      
      jArray.add(jObject);
      idx2++;
   }
      
   //System.out.println("here");

   jsonMain.put("policepos", jArray);

   out.println(jsonMain);
   System.out.println("------Policepos 전송 성공-----");
   out.flush();
%>


