<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"
	import="com.oreilly.servlet.*"
	import="org.apache.jasper.*" import="java.util.*, java.net.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta charset="utf-8">
<title>SAFEME WEB SERVER</title>
<script src="/WEB-INF/js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script type="text/javascript" src="/WEB-INF/js/jwplayer.js"></script>
<link rel="stylesheet" type="text/css" href="/WEB-INF/css/style.css" />
</head>

<body>
	<h1>SAFEME WEB SERVER</h1>
	
	<h2>서버 아이피  : <% out.println(InetAddress.getLocalHost().getHostAddress()); %></h2>
	
	<h2>Home Page</h2>
	
	<div>
	<h3>경찰서에서 사용하는 웹페이지</h3>
	<ul>
		<li><a href="homepage/Home.jsp">[Home.jsp] 경찰서에서 사용하는 웹Site </a>
	</ul>
	</div>
	
	<h2>Back Page</h2>
	
	<div>
	<h3>Notify</h3>
	<ul>
		<li><a href="backpage/Notify_updateDB.jsp">[Notify_updateDB.jsp] nofity 테이블에 데이터 insert</a>
		<li><a href="backpage/Notify_drawTable.jsp">[Notify_drawTable.jsp] nofity 테이블에 데이터 보기</a>
	</ul>
	</div>
	
	<div>
	<h3>Video</h3>
	<ul>
		<li><a href="backpage/Video_jwPlayer.jsp">[Video_jwPlayer.jsp] 비디오 플레이어 테스트 페이지</a>
		<li><a href="backpage/Video_saveFile.jsp">[Video_saveFile.jsp] 비디오파일 wowza 서버에 저장</a></br>
	</ul>
	</div>
	
	<div>
	<h3>Map</h3>
	<ul>
		<li><a href="backpage/Footpos_getPos.jsp">[Footpos_get.jsp] 전자발찌의 위치를 모바일앱으로 전송</a>
		<li><a href="backpage/Policepos_getPos.jsp">[Policeos_get.jsp] 경찰서의 위치를 모바일앱으로 전송</a>
		<li><a href="backpage/Footpos_updateDB.jsp">[Footpos_updateDB.jsp] 전자발찌의 위치를 DB에 insert/update</a>
		<li><a href="backpage/Policepos_updateDB.jsp">[Policepos_updateDB.jsp] policepos2 table에 아이디, 주소, 위도, 경도 insert</a>
		<li><a href="backpage/Policepos_sendAddress.jsp">[Policepos_sendAddress.jsp] policepos table에 저장된 경찰서의 주소 전송</a>
		
	</ul>
	</div>
	
	<h3>Server IP</h3>
	<ul>
		<li><a href="backpage/Server_getServerIp.jsp">[Server_getServerIp.jsp] 서버의 아이피 보내주기</a></li>
	</ul>
	
	
</body>

</html>