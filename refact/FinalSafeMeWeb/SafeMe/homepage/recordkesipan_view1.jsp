<%@ page language="java" import="java.sql.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<jsp:include page="Home.jsp" flush="false" /></br>
</br>
<style>
tbody tr:hover {
    
}

tbody tr {
    color: #000;
    text-align: center;
    border-bottom: 1px solid #555;
}

</style>
<title>녹화된 영상</title>
<script src="../js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script type="text/javascript" src="../js/jwplayer.js"></script>
<script src="../js/player.js" type="text/javascript"></script>
<script>
	jwplayer.key = "2k/OWiKCBtXbD9wRCh1hiuF5tCfq8UV1y7Vgfw=="
</script>
<link rel="stylesheet" type="text/css" href="../css/style.css" />
</head>
<body bgcolor="white" text="black" link="blue" vlink="red" alink="red">
	<%
		try {
			String b_date, b_time, b_phonenum, b_filename, b_loc_str, b_loc_lat, b_loc_lon;
			int b_id;

			String bid = request.getParameter("bid");

			Class.forName("com.mysql.jdbc.Driver");

			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/safemetest", "root",
					"test123");

			Statement stmt = conn.createStatement();
			String sql = "select * from notify where id=" + bid;
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				b_id = rs.getInt(1);
				b_date = rs.getString(2);
				b_time = rs.getString(3);
				b_phonenum = rs.getString(4);
				b_filename = rs.getString(5);
				b_loc_str = rs.getString(6);
				b_loc_lat = rs.getString(7);
				b_loc_lon = rs.getString(8);
	%>

	<div align="center">
		<h1>파일 내용</h1>
		<table border="1" cellpadding="1" cellspacing="0">
			<tr>
				<td bgcolor="#FFFFFF">
				<table bgcolor="#FFFFFF" border="1" cellpadding="2" cellspacing="1"
						style="width: 700px; table-layout: fixed;">
						<tr>
							<th align="center" bgcolor="#FFFFFF" style="width: 30%; height: 50px;"><p>
									<font style="font-size: 120%">파일이름<br></font></th>
							<td align="center" bgcolor="#FFFFFF"><p>
									<font style="font-size: 120%"><b><%=b_filename%></b></font><font
										size="2" /><br>
						</tr>

						<tr>
							<th align="center" bgcolor="#FFFFFF" style="width: 30%; height: 50px;"><p>
									<font style="font-size: 120%">전화번호<br></font></th>
							<td align="center" bgcolor="#FFFFFF"><p>
									<font style="font-size: 120%"><b><%=b_phonenum%></b></font><font
										size="2" /><br>
						</tr>
						<tr>

						</tr>

						<tr>
							<td colspan="2" bgcolor="#FFFFFF" style="height: 600px;">
									<div id="video_preview">
										<div id="player"></div>
										<div class="clear"></div>
										<!-- vod 재생할때는 이런식으로 주소써야한다. rtmp://192.168.63.33:1935/vod/_definst_/safeme/VID_20151030_203026.mp4 -->
										<input type="hidden" id="stream_url"
											value="rtmp://192.168.43.23:1935/vod/_definst_/safeme/<%=b_filename%>" /><br />
									</div>
									<div class="clear"></div>
							</td>
						</tr>
						<tr>
							<th align="center" bgcolor="#FFFFFF" style="width: 30%; height: 50px;"><p>
									<font style="font-size: 120%">위도<br></font></th>
							<td align="center" bgcolor="#FFFFFF"><p>
									<font style="font-size: 120%"><b><%=b_loc_lat%></b></font><font
										size="2" /><br>
						</tr>
						<tr>
							<th align="center" bgcolor="#FFFFFF" style="width: 30%; height: 50px;"><p>
									<font style="font-size: 120%">경도<br></font></th>
							<td align="center" bgcolor="#FFFFFF"><p>
									<font style="font-size: 120%"><b><%=b_loc_lon%></b></font><font
										size="2" /><br>
						</tr>
						<tr>
							<th align="center" bgcolor="#FFFFFF" style="width: 30%; height: 50px;"><p>
									<font style="font-size: 120%">주소<br></font></th>
							<td align="center" bgcolor="#FFFFFF"><p>
									<font style="font-size: 120%"><b><%=b_loc_str%></b></font><br>
						</tr>

					</table></td>
			</tr>
		</table>
	</div>
	<p align=center>
		<font size=2><a href=sinkokyesipan.jsp>목록으로</a> <%
 	}
 		rs.close();
 		stmt.close();
 		conn.close();
 	} catch (Exception e) {
 		out.println(e);
 	}
 %>
</body>
</html>
