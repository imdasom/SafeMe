<%@ page language="java" import="java.sql.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
        String b_name=null,b_mail=null,b_title=null,b_content=null;
        String b_id=request.getParameter("b_id");

        Class.forName("com.mysql.jdbc.Driver"); 

        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/people","root","dkwl12"); 

        Statement stmt= conn.createStatement();
	String sql="select b_name,b_mail,b_title,b_content from board where b_id="+b_id;
        ResultSet rs=stmt.executeQuery(sql);
                if(rs.next()) {
                        b_name=rs.getString(1);
                        b_mail=rs.getString(2);
                        b_title=rs.getString(3);
                        b_content=rs.getString(4);
                }       
        rs.close();
        stmt.close();
        conn.close();   
%>
<html>
<head>
<jsp:include page="Home.jsp" flush="false" /></br></br>
<title>수정하기</title>
<script language=javascript>
        function check() {
                if(document.form.b_name.value.length==0) {
                        alert("이름을 써주세요");
                        form.b_name.focus();
                        return false;
                }
                if(document.form.b_title.value.length==0) {
                        alert("제목을 써주세요.");
                        form.b_title.focus();
                        return false;
                }
                if(document.form.b_content.value.length==0) {
                        alert("내용을 써주세요.");
                        form.b_content.focus();
                        return false;
                }
                if(document.form.b_pwd.value.length==0) {
                        alert("수정시 필요한 비밀번호를 써주세요.");
                        form.b_pwd.focus();
                        return false;
                }
                document.form.submit();
        }
</script>
</head>

<body bgcolor="white" text="black" link="blue" vlink="red" alink="red" style="font-family:돋움;">
<center>
<p align="center"><font color="#00000" size="70"><font size="70"></font><b>수정하기</b><br><br><br></font>
<table border="1" cellpadding="1" width="900" height="350">
    <tr>
        <td width="89" bgcolor="#000000"><p align="center"><font size="2" color="white">이 
            름</font></td>
        <td width="645"><form name="form" method="post" action=modify_act1.jsp>
            <p><input type="text" name="b_name" maxlength="20" size="20" value="<%=b_name%>"></td>
    </tr>
    <tr>
        <td width="89" bgcolor="#000000"><p align="center"><font size="2" color="white">전자우편</font></td>
        <td width="645"><p><input type="text" name="b_mail" maxlength="30" value="<%=b_mail%>"></td>
    </tr>
    <tr>
        <td width="89" bgcolor="#000000"><p align="center"><font size="2" color="white">제 
            목</font></td>
        <td width="645"><p><input type="text" name="b_title" maxlength="60" size="40" value="<%=b_title%>"></td
>
    </tr>
    <tr>
        <td width="89" bgcolor="#000000"><p align="center"><font size="2" color="white">내 
            용</font></td>
        <td width="645"><p><textarea name="b_content" rows="5" cols="40"><%=b_content%></textarea></td>
    </tr>
    <tr>
        <td width="89" bgcolor="#000000"><p align="center"><font size="2" color="white">비밀번호</font></td>
        <td width="645"><p><input type="password" name="b_pwd" maxlength="12"
             size="9"></td>
    </tr>
</table>
<input type=hidden name=b_id value="<%=b_id%>">
<input type="button" value="수정하기" onclick="check()">&nbsp;<input type="reset" value="다시쓰기">
<p>
<a href="list1.jsp"> 목록으로 </a>
</form>
</center>
</body>
</html> 
