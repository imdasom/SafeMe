<%@ page language="java" contentType="text/html; charset=EUC-KR"
   pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*"%>
<%@ page import="org.json.simple.JSONObject"%>
<%@ page import="org.json.simple.JSONArray"%>
<h1>전자발찌의 위치를 DB에 업로드하는 코드</h1>
<%
   String id = null;
   String lat = null;
   String lon = null;
   
   id = request.getParameter("id");
   lat = request.getParameter("lat");
   lon = request.getParameter("lon");
   System.out.println(id);
   System.out.println(lat);
   System.out.println(lon);

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
      
      stmt = conn.prepareStatement("SELECT COUNT(id) FROM footpos where id="+id);
      rs = stmt.executeQuery();
      int count = 0;
      if(rs.next()){
         count = rs.getInt(1); // id 값 얻어오기 위해 수행한다.
      }
      String query = "";
      
      if(count==1){
    	  query = "update footpos set lat='" + lat 
    		      + "', lon='" + lon + "' where id=" + id;
    	  stmt = conn.prepareStatement(query);
      }else{

    	  query = "insert into footpos (id, lat, lon)" + "values (?, ?, ?)";
          
          //insert 쿼리를 준비시킨다.
          stmt = conn.prepareStatement(query);
          
          //쿼리의 ? 부분을 채워준다.
          stmt.setInt(1, Integer.parseInt(id)); 
          stmt.setString(2, lat);
          stmt.setString(3, lon);

      }
      
      stmt.executeUpdate();
      System.out.println("executeUpdate");

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