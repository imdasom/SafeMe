<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="Home.jsp" flush="false" /><br /><br />
<style>
</style>




<title>공지사항</title>
<script language=javascript>
        function check() {
                if(document.myform.b_name.value.length==0) {
                        alert("이름을 써주세요");
                        myform.w_name.focus();
                        return false;
                }
                if(document.myform.b_title.value.length==0) {
                        alert("제목을 써주세요.");
                        myform.b_title.focus();
                        return false;
                }
                if(document.myform.b_content.value.length==0) {
                        alert("내용을 써주세요.");
                        myform.b_content.focus();
                        return false;
                }
                if(document.myform.b_pwd.value.length==0) {
                        alert("수정시 필요한 비밀번호를 써주세요.");
                        myform.b_pwd.focus();
                        return false;
                }
                document.myform.submit();
        }
</script>
</head>

<body bgcolor="white" text="black" link="blue" vlink="red" alink="red">

<p align="center"><font color="#00000" size="30"><font size="30"></font><b>공지 사항</b></font> 
<table border="1" cellpadding="4" align="center">
    <tr>
        <td width="89" bgcolor="#00000"><p align="center"><font size="2" color="white">이 
            름</font></td>
        <td width="645" style="text-align:left;"><form name="myform" method="post" action=write_act1.jsp>
            <p><input type="text" name="b_name" maxlength="10" size="10"></td>
    </tr>
    <tr>
        <td width="89" bgcolor="#00000"><p align="center"><font size="2" color="white">전자우편
</font></td>
        <td width="645" style="text-align:left;"><p><input type="text" name="b_mail" maxlength="30"></td>
    </tr>
    <tr>
        <td width="89" bgcolor="#00000"><p align="center"><font size="2" color="white">제 
            목</font></td>
        <td width="645" style="text-align:left;"><p><input type="text" name="b_title" maxlength="60" 
size="40"></td>
    </tr>
    <tr>
        <td width="89" bgcolor="#00000"><p align="center"><font size="2" color="white">내 
            용</font></td>
        <td width="645" style="text-align:left;"><p><textarea name="b_content" rows="5" 
cols="40"></textarea></td>
    </tr>
    <tr>
        <td width="89" bgcolor="#00000"><p align="center"><font size="2" color="white">비밀번호
</font></td>
        <td width="645" style="text-align:left;"><p><input type="password" name="b_pwd" maxlength="12"
             size="9"></td>
    </tr>
</table>
<div align="center">

<p><input type="button" value="글올리기" onclick="check()"> <input
 type="reset" value="다시쓰기">
</p></form>
<a href="list1.jsp"> 목록으로 </a>
</div>



</body>
</html> 