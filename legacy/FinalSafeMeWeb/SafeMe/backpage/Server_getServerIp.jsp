<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.net.*, java.util.*"%>
<%@ page import="org.json.simple.JSONObject"%>
<%@ page import="org.json.simple.JSONArray"%>

<%
	JSONObject jsonMain = new JSONObject();
	JSONArray jArray = new JSONArray();
	JSONObject jObject = new JSONObject();
	
	String ip = InetAddress.getLocalHost().getHostAddress();
	
	jObject = new JSONObject();
	jObject.put("ip", ip);
	jArray.add(jObject);
	
	jsonMain.put("serverip", jArray);

	out.println(jsonMain);
	System.out.println("------Server IP 전송 성공-----");
	out.flush();
%>