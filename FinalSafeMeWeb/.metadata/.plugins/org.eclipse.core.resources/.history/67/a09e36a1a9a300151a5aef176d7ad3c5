<%@ page  language="java" import="java.sql.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	try {
	int b_id=0;

	String b_name=new String(request.getParameter("b_name"));
	String b_mail=request.getParameter("b_mail");
	String b_title=new String(request.getParameter("b_title"));
	String b_content=new String(request.getParameter("b_content"));
	String b_pwd=request.getParameter("b_pwd");

	Class.forName("com.mysql.jdbc.Driver"); 

    	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/safemetest","root","test123"); 

	Statement stmt=conn.createStatement();
	ResultSet rs=stmt.executeQuery("select max(b_id) from board");  
	if(rs.next()) {
		b_id=rs.getInt(1); 
		b_id=b_id+1;    
		rs.close();
		stmt.close();
	} else {
		b_id=1;   
	}

	PreparedStatement pstmt = conn.prepareStatement("insert into board(b_id,b_name,b_mail,b_title,b_content,b_date,b_view,b_pwd) values(?,?,?,?,?,now(),0,?)");
	pstmt.setInt(1,b_id);
	pstmt.setString(2,b_name);
	pstmt.setString(3,b_mail);
	pstmt.setString(4,b_title);
	pstmt.setString(5,b_content);
	pstmt.setString(6,b_pwd);

	pstmt.executeUpdate();
	pstmt.close();
	conn.close();

	response.sendRedirect("list1.jsp"); 

	} catch (Exception e) {
		out.println(e);
	}
%>
