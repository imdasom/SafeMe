<%@ page language="java" import="java.sql.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
<jsp:include page="Home.jsp" flush="false" /></br></br>
<style>

</style>
<title>글 목록 보기</title>
</head>
<body bgcolor="white" text="black" link="blue" vlink="red" alink="red">
<p align="center"><br>
<div align="center"><h1>녹화된 게시물</h1>
<h1 style="font-size: 140%;">Record Board</h1>
<table border="0" cellpadding="0" cellspacing="0" width="700">
  	<tr style="background:#fof;">
        <td bgcolor="black"><table border="0" cellpadding="5" cellspacing="3" width="100%">
                <tr>
                    <th align="center" nowrap bgcolor="#FFFFFF"><p><font
                         size="2" color=black>번호</font></th>
                    <th width=250 align="center" nowrap bgcolor="#FFFFFF""><p><font
                         size="2" color=black>파일명</font></th>
                    <th width="200" align="center" nowrap bgcolor="#FFFFFF""><p><font
                         size="2" color=black>전화번호</font></th>
                    <th width="94" align="center" nowrap bgcolor="#FFFFFF""><p><font
                         size="2" color=black>시간</font></th>
                    <th width="94" align="center" nowrap bgcolor="#FFFFFF"><p><font
                         size="2" color=black>날짜</font></th>
                </tr>
<%
try {
	String b_date,b_time,b_phonenum,b_filename,b_loc_str,b_loc_lat,b_loc_lon;
        int b_id;

	int datacount=0;  
        int pagecount;

      Class.forName("com.mysql.jdbc.Driver");
      Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/safemetest","root","test123");
      Statement stmt = conn.createStatement();

        ResultSet rs0=stmt.executeQuery("select count(id) from notify");
		
        if(rs0.next()) {
                datacount=rs0.getInt(1); 
                rs0.close();
        }
        int pagesize=10;
        pagecount=datacount/(pagesize+1)+1;  
        int mypage=1;
        int abpage=1;  
        if(request.getParameter("mypage")!=null) {
                mypage=Integer.parseInt(request.getParameter("mypage"));
                abpage=(mypage-1)*pagesize+1;
		if(abpage <= 0 ) abpage=1; 
        }

       ResultSet rs = stmt.executeQuery("select * from notify order by id desc");

	if(!rs.next()) {  
		pagesize=0;
	} else {
		%>
		
		<%
		rs.absolute(abpage);
	}
	for(int k=1;k <= pagesize; k++) {  
                b_id=rs.getInt(1);
                b_date=rs.getString(2);
                b_time=rs.getString(3);
                b_phonenum=rs.getString(4);
                b_filename=rs.getString(5);
                b_loc_str=rs.getString(6);
                b_loc_lat=rs.getString(7);
                b_loc_lon=rs.getString(8);
%>
                <tr>
                  <td align="center" bgcolor="#FFFFFF" ><p><font size="2"><%=b_id%></font></td>
                  <td width="364" align="left" bgcolor="#FFFFFF"><p><font
                       size="2">&nbsp;&nbsp;&nbsp;</font><a href="recordkesipan_view1.jsp?bid=<%=b_id%>"><font
                       size="2"><%=b_filename%></font></a></td>
                  <td width="94" align="center" nowrap bgcolor="#FFFFFF"><p><font
                       size="2"><%=b_phonenum%></font></td>
                  <td width="114" align="center" bgcolor="#FFFFFF"><p><font
                       size="2"><%=b_date%></font></td>
                  <td align="center" bgcolor="#FFFFFF" ><p><font size="2"><%=b_time%></font></td>
                </tr>
<%
		if(rs.getRow()==datacount){  
			break;
		} else {
			rs.next();
		}
	} 

	rs.close();
	stmt.close(); 
	conn.close(); 

%>
            </table></td>
 </tr>
</table></div><br>
<p align="center"><font size=2>
<%
        int gopage=1;
        if(mypage!=1) {
                gopage=mypage-1;
                out.println("<a href=recordkesipan.jsp?mypage="+gopage+"><<이전</a>  ");
        } else {
		out.println("<<이전");
	}
%>
<%
        if(mypage!=pagecount) {
                gopage=mypage+1;
                out.println("<a href=recordkesipan.jsp?mypage="+gopage+">다음>></a>  ");
        } else {
		out.println("다음>>");
	}
} catch (Exception e) {
	out.println(e);
}
%>

</font></p>
</body>
</html>