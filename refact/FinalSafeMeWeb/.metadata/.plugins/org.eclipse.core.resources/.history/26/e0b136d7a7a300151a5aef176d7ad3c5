<%@ page language="java" import="java.sql.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>글 삭제하기</title>
<jsp:include page="Home.jsp" flush="false" /></br></br>
<script language=javascript>
        function check() {
                if(document.del_form.b_pwd.value.length==0) {
                        alert("비밀번호를 입력해 주세요.");
                        del_form.b_name.focus();
                        return false;
                }
                document.del_form.submit();
        }

      function re() {
            history.go(-2);
      }
</script>
</head>
<%
        String b_id=request.getParameter("b_id");
%>
<body bgcolor="white" text="black" link="blue" vlink="purple" alink="red" style="font-family:돋움;">
<center width="500" height="500">
<font color="#000000"><h1>글 삭제</h1></font> 
<form name="del_form" method="post" action="delete_act1.jsp">
	<font size=2><b>비밀번호 : </b></font>
	<input type="password" name="b_pwd" size="30"><p>
	<input type=hidden name=b_id value="<%=b_id%>">
	<input type="button" value="글 삭제" onclick="check()">
	<input type="button" value="글 삭제 취소" onclick="re()">
</form>
</center>
</body>
</html> 
