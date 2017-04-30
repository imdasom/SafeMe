<%@ page language="java" import="java.sql.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
try {

        int b_id=Integer.parseInt(request.getParameter("b_id"));
        String b_pwd=request.getParameter("b_pwd");  
	String b_pwd_db=null;  

        Class.forName("com.mysql.jdbc.Driver"); 

        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/people","root","dkwl12");
	Statement stmt= conn.createStatement();

        ResultSet rs=stmt.executeQuery("select b_pwd from board where b_id="+b_id+"");
		if(rs.next()) {
			b_pwd_db=rs.getString(1);
		}

        if(b_pwd.equals(b_pwd_db)) { 
		String sql="delete from board where b_id="+b_id;
        	stmt.executeUpdate(sql);
                response.sendRedirect("list1.jsp");
        } else {
%>
                        <script language=javascript>
                                alert("패스워드가 틀립니다");
                                history.back();
                        </script>
<%
        }
	stmt.close(); 
        conn.close();

} catch (Exception e) {
        out.println(e);
} 
%>
