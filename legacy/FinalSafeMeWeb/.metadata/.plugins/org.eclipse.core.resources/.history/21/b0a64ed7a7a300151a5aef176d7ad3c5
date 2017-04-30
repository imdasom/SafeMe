<%@ page language="java" import="java.sql.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<jsp:include page="Home.jsp" flush="false" /><br /><br />
<title>검색 결과</title>
</head>
<body bgcolor="white" text="black" link="blue" vlink="red" alink="red" style="font-family:돋움;">
<p align="center"><br>
<div align="center"><h1>공지 사항</h1><br>
<table border="0" cellpadding="0" cellspacing="0" width="700">
    <tr>
        <td bgcolor="black"><table border="0" cellpadding="2" cellspacing="1"
             width="100%">
                <tr>
                    <th align="center" nowrap bgcolor="#000000"><p><font
                         size="2" color=white>번호</font></th>
                    <th width="364" align="center" nowrap bgcolor="#000000"><p><font
                         size="2" color=white>제 목</font></th>
                    <th width="94" align="center" nowrap bgcolor="#000000"><p><font
                         size="2" color=white>이름</font></th>
                    <th width="114" align="center" nowrap bgcolor="#000000"><p><font
                         size="2" color=white>날짜</font></th>
                    <th align="center" nowrap bgcolor="#000000"><p><font
                         size="2" color=white>조회</font></th>
                </tr>

<%
try {
        String b_name,b_mail,b_title,b_content,b_date,b_pwd,mailtoyou;
        int b_id,b_view;

	String search=request.getParameter("search");

	String keyword=new String(request.getParameter("keyword"));
	out.println(search+keyword);

	int datacount=0;  
        int pagecount,remcount;

        Class.forName("com.mysql.jdbc.Driver"); 
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/people","root","dkwl12"); 
	Statement stmt = conn.createStatement();  

        ResultSet rs0=stmt.executeQuery("select count(b_id) from board where "+search+" like '%"+keyword+"%'");
        if(rs0.next()) {
                datacount=rs0.getInt(1);  //총 글수
                rs0.close();
        }
        int pagesize=10;
        pagecount=datacount/(pagesize+1)+1;  

        int mypage=1;
        int abpage=1;
        if(request.getParameter("mypage")!=null) {
                mypage=Integer.parseInt(request.getParameter("mypage"));
                abpage=(mypage-1)*pagesize;
                if(abpage <= 0 ) abpage=1;
        }

	String sql="select b_id,b_name,b_mail,b_title,b_content,date_format(b_date,'%Y-%m-%e'),b_view,b_pwd from board where "+search+" like '%"+keyword+"%' order by b_id desc";
	ResultSet rs = stmt.executeQuery(sql);

	if(!rs.next()) {
		pagesize=0;
	} else {
		rs.absolute(abpage);
	}
	
	for(int k=1;k <= pagesize; k++) {
                b_id=rs.getInt(1);
                b_name=rs.getString(2);
                b_mail=rs.getString(3);
                b_title=rs.getString(4);
                b_content=rs.getString(5);
                b_date=rs.getString(6);
                b_view=rs.getInt(7);
                b_pwd=rs.getString(8);
                if(!b_mail.equals("")) {
                        mailtoyou="<a href=mailto:"+b_mail+">"+b_name+"</a>";
                } else {
                        mailtoyou=b_name;
                }
%>
                <tr>
                    <td align="center" bgcolor="#F6F6F6" ><p><font size="2"><%=b_id%></font></td>
                    <td width="364" align="left" bgcolor="#F6F6F6"><p><font
                         size="2">&nbsp;&nbsp;&nbsp;</font><a href="view1.jsp?bid=<%=b_id%>"><font
                         size="2"><%=b_title%></font></a></td>
                    <td width="94" align="center" nowrap bgcolor="#F6F6F6"><p><font
                         size="2"><%=mailtoyou%></font></td>
                    <td width="114" align="center" bgcolor="#F6F6F6"><p><font
                         size="2"><%=b_date%></font></td>
                    <td align="center" bgcolor="#F6F6F6" ><p><font size="2"><%=b_view%></font></td>
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
        int gopage;
	keyword=java.net.URLEncoder.encode(keyword);  
        if(mypage!=1) {
                gopage=mypage-1;
                out.println("<a href=search_result.jsp?mypage="+gopage+"&search="+search+"&keyword="+keyword+"><<이전</a>  ");
        } else {
		out.println("<<이전");
	}
%>
        </font><font size=2>
<%
        if(mypage!=pagecount) {
                gopage=mypage+1;
                out.println("<a href=search_result.jsp?mypage="+gopage+"&search="+search+"&keyword="+keyword+">다음>></a>  ");
        } else {
		out.println("다음>>");
	}

} catch (Exception e) {
	out.println(e);
}
%>
</font></p>
<p align=center><font size=2><a href="list1.jsp">목록으로</a></font></p>
</body>
</html>
