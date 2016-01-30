<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>

<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>

<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"
	import="com.oreilly.servlet.*"
	import="org.apache.jasper.*" import="java.util.*" import="java.io.*"%>
<%
	//String savePath = "C:\\Program Files\\Wowza Media Systems\\Wowza Streaming Engine 4.2.0\\content\\safeme";
	String savePath = "C:\\Program Files (x86)\\Wowza Media Systems\\Wowza Streaming Engine 4.3.0\\content\\safeme";	
	int sizelimit = 50 * 1024 * 1024;
	try {
		out.print("<br>");
		out.print("SavePath : "+savePath);
		MultipartRequest multi = new MultipartRequest(request, savePath, sizelimit, new DefaultFileRenamePolicy());
		
		//MultipartRequest를 다양하게 활용하기 위한 방법(확장..)
		/* Enumeration formNames = multi.getFileNames(); //폼의 이름 변경
		String formName = (String)formNames.nextElement(); //자료가 많을 경우엔 while문을 사용
		String fileName = multi.getFilesystemName(formName); //파일의 이름 얻기 */
		
		//multi.getFile("images");
	} catch (Exception e) {
		out.print("<br>");
		out.print("<br>");
		out.print("예외 상황 발생..!");
		out.print("<br>예외상황 : ");
		out.print(e);
	} finally{
		out.print("<br>");
		out.print("저장완료!");
	}
%>
<p></p>