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
		
		//MultipartRequest�� �پ��ϰ� Ȱ���ϱ� ���� ���(Ȯ��..)
		/* Enumeration formNames = multi.getFileNames(); //���� �̸� ����
		String formName = (String)formNames.nextElement(); //�ڷᰡ ���� ��쿣 while���� ���
		String fileName = multi.getFilesystemName(formName); //������ �̸� ��� */
		
		//multi.getFile("images");
	} catch (Exception e) {
		out.print("<br>");
		out.print("<br>");
		out.print("���� ��Ȳ �߻�..!");
		out.print("<br>���ܻ�Ȳ : ");
		out.print(e);
	} finally{
		out.print("<br>");
		out.print("����Ϸ�!");
	}
%>
<p></p>