<%@ page language="java" import="java.sql.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
try {
        String b_name=new String(request.getParameter("b_name"));
        String b_mail=request.getParameter("b_mail");
        String b_title=new String(request.getParameter("b_title"));
        String b_content=new String(request.getParameter("b_content"));
        String b_pwd=request.getParameter("b_pwd");  
        int b_id=Integer.parseInt(request.getParameter("b_id"));
	String b_pwd_db=null;  

        Class.forName("com.mysql.jdbc.Driver"); 

        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/people","root","dkwl12");
	Statement stmt= conn.createStatement();

        ResultSet rs=stmt.executeQuery("select b_pwd from board where b_id="+b_id+"");  

		if(rs.next()) {
			b_pwd_db=rs.getString(1);
		}

        if(b_pwd.equals(b_pwd_db)) { 
		String sql="update board set b_name='"+b_name+"',b_mail='"+b_mail+"',b_title='"+b_title+"',b_content='"+b_content+"' where b_id="+b_id;
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
