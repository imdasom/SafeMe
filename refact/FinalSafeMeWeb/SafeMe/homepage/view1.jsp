<%@ page language="java" import="java.sql.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<jsp:include page="Home.jsp" flush="false" /></br></br>
<title>글 내용 보기</title>
</head>
<body bgcolor="white" text="black" link="blue" vlink="red" alink="red" style="font-family:돋움;">
<%
try {
        String b_name,b_mail,b_title,b_content,b_date,b_pwd,mailtoyou;
        int b_id,b_view;
        
        String bid=request.getParameter("bid"); 

        Class.forName("com.mysql.jdbc.Driver"); 

        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/safemetest","root","test123"); 

        Statement stmt = conn.createStatement();
	String sql="select b_id,b_name,b_mail,b_title,b_content,date_format(b_date,'%Y/%c/%e %H:%i:%s'),b_view,b_pwd from board where b_id="+bid; 
        ResultSet rs = stmt.executeQuery(sql);  
        if(rs.next()) {   
                b_id=rs.getInt(1);
                b_name=rs.getString(2);
                b_mail=rs.getString(3);
                b_title=rs.getString(4);
                b_content=rs.getString(5);
                b_content=this.change_Code(b_content,"\n","<br>"); 
                b_date=rs.getString(6);
                b_view=rs.getInt(7);
                b_pwd=rs.getString(8);
                if(!b_mail.equals("")) {
                        mailtoyou="(<font size=2><a href=mailto:"+b_mail+">"+b_mail+"</a></font>)";
                } else {
                        mailtoyou="";
                }
%>

<div align="center"><h1>글 내용보기</h1>
<table border="0" cellpadding="0" cellspacing="0" width="900">
    <tr>
        <td bgcolor="#000000"><table border="3" cellpadding="2" cellspacing="1"
             width="100%">
                <tr>
                    <th align="center" colspan="2" bgcolor="#000000"><p><font
                         size="2" color="white"><b><%=b_title%></b></font></th>
                </tr>
                <tr>
                    <th align="center" bgcolor="#FFFFFF"><p><font size="2">이름<br>
                        날짜<br>
                        조회<br></font></th>
                    <td bgcolor="#FFFFFF"><p><font size="2"><b><%=b_name%></b></font><font
                         size="2"><%=mailtoyou%><br>
                        </font><font size="2"><b><%=b_date%></b></font><font
                         size="2"><br>
                        </font><font size="2"><b><%=b_view%></b></font><font size="2"><br></font></td>
                </tr>
                <tr>
                    <td colspan="2" bgcolor="#FFFFFF"><table border="0" cellpadding="20"
                         cellspacing="0">
                            <tr>
                                <td><p><font size="2"><%=b_content%></font></td>
                            </tr>
                        </table></td>
                </tr>
            </table></td>
    </tr>
</table></div>
<p align=center><font size=2><a href=list1.jsp>목록으로</a> /
<a href=modify1.jsp?b_id=<%=b_id%>> 수정 </a> / 
<a href=delete1.jsp?b_id=<%=b_id%>> 삭제</a></font></p>
<%
    }
        rs.close(); 

        stmt.executeUpdate("update board set b_view=b_view+1 where b_id="+bid+"");  //조회수를 올린다

        stmt.close();
        conn.close();
} catch (Exception e) {
	out.println(e);
}
%>
</body>
</html>
<%-- 개행 처리를 위한 메소드 --%>
<%!
           public static String change_Code(String line, String oldString, String newString)
           {
                       for(int index = 0; (index = line.indexOf(oldString, index)) >= 0; index += newString.length())
                                   line = line.substring(0, index) + newString + line.substring(index + oldString.length());
                       return line;
           }
%> 
