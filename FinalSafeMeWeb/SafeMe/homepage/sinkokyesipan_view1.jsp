<%@ page language="java" import="java.sql.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js">
	
</script>
<script type="text/javascript"
	src="http://maps.google.com/maps/api/js?sensor=false">
	
</script>


<style>
tbody tr:hover {
	
}

tbody tr {
	color: #000;
	text-align: center;
	border-bottom: 1px solid #555;
}

#player {
	width: 440px;
	height: 360px;
	background-color: #000;
	color: #FFF;
	display: table;
	opacity: 1;
}

table {
	border-collapse: none;
	text-align: center;
}

tbody tr:not(:first-child) {
    border-bottom: 1px solid #555;
}
</style>
<jsp:include page="Home.jsp" flush="false" />
<title>실시간 신고</title>
<script src="../js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script type="text/javascript" src="../js/jwplayer.js"></script>
<script src="../js/player.js" type="text/javascript"></script>
<script>
	jwplayer.key = "2k/OWiKCBtXbD9wRCh1hiuF5tCfq8UV1y7Vgfw=="
</script>
<link rel="stylesheet" type="text/css" href="../css/style.css" />
</head>




<body onload="initialize()" bgcolor="white" text="black" link="blue"
	vlink="red" alink="red">
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
	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							var latlng = new google.maps.LatLng(
	<%=b_loc_lat%>
		,
	<%=b_loc_lon%>
		);
							var myOptions = {
								zoom : 16,
								center : latlng,
								mapTypeId : google.maps.MapTypeId.ROADMAP
							}
							var map = new google.maps.Map(document
									.getElementById("map_canvas"), myOptions);
							var marker = new google.maps.Marker({
								position : latlng,
								map : map
							});
							var geocoder = new google.maps.Geocoder();
							google.maps.event
									.addListener(
											map,
											'click',
											function(event) {
												var location = event.latLng;
												geocoder
														.geocode(
																{
																	'latLng' : location
																},
																function(
																		results,
																		status) {
																	if (status == google.maps.GeocoderStatus.OK) {
																		$(
																				'#address')
																				.html(
																						results[0].formatted_address);
																		$(
																				'#lat')
																				.html(
																						results[0].geometry.location
																								.lat());
																		$(
																				'#lng')
																				.html(
																						results[0].geometry.location
																								.lng());
																	} else {
																		alert("Geocoder failed due to: "
																				+ status);
																	}
																});
												if (!marker) {
													marker = new google.maps.Marker(
															{
																position : location,
																map : map
															});
												} else {
													marker.setMap(null);
													marker = new google.maps.Marker(
															{
																position : location,
																map : map
															});
												}
												map.setCenter(location);
											});
						});
	</script>
	<div align="center" style="padding-top:60px">
		<h1>접수 정보</h1>
		<table border="1" cellpadding="1" cellspacing="0">
			<tr>
				<td bgcolor="#FFFFFF">
					<table bgcolor="#FFFFFF" border="1" cellpadding="2" cellspacing="1"
						style="width: 1200px; table-layout: fixed;">


						<tr>
							<th align="center" bgcolor="#FFFFFF"><p>
									<font style="font-size: 120%">파일이름<br></font></th>
							<td bgcolor="#FFFFFF"><p>
									<font style="font-size: 120%"><b><%=b_filename%></b></font><font
										size="2"><br>

										<th align="center" bgcolor="#FFFFFF"><p>
												<font style="font-size: 120%">신고날짜</font></th>
										<td bgcolor="#FFFFFF"><p>
												<font style="font-size: 120%"><b><%=b_date%></b></font></font>
						</tr>

						<tr>
							<th align="center" bgcolor="#FFFFFF"><p>
									<font style="font-size: 120%">전화번호<br></font></th>
							<td bgcolor="#FFFFFF"><p>
									<font style="font-size: 120%"><b><%=b_phonenum%></b></font><font
										size="2"><br>

										<th align="center" bgcolor="#FFFFFF"><p>
												<font style="font-size: 120%">신고시간</font></th>
										<td bgcolor="#FFFFFF"><p>
												<font style="font-size: 120%"><b><%=b_time%></b></font></font>
						</tr>


						<tr>
							<td colspan="2" bgcolor="#FFFFFF" style="border-right: 1px solid #555;">
								<table border="0" cellspacing="0"
									style="text-align: center; margin: 0px auto;">
									<tr>
										<p align="center" size="10">실시간 영상</p>
									</tr>

									<tr>     					   
									<!-- <div class="container"> -->
										<div id="video_preview">
											<div id="player" style="width: 200px"></div>
											<div class="clear"></div>
											<input type="hidden" id="stream_url"
												value="rtmp://192.168.43.23:1935/live/myStream" />
										</div>
										<div class="clear"></div>
										<!-- </div> -->
									</tr>
								</table>
							</td>

							<td colspan="2" bgcolor="#FFFFFF">
								<table border="0" cellspacing="0"
									style="text-align: center; margin: 0px auto; margin-top: 40px;">
									<tr>
										<p align="center" size="10">장소클릭(주소 알아내기)</p>
									</tr>

									<tr>
										<td colspan="2"><div id="map_canvas"
												style="border:none; width: 570px; height: 330px;"></div></td>

									</tr>
								</table>
							</td>
						<tr>
							<td colspan="2">
								<table border="0" cellpadding="20" cellspacing="0" style="width:100%">
									<tr>
										<p align="center" size="10">신고자 최초 위치</p>
									</tr>
									<tr>
										<th align="center" bgcolor="#FFFFFF"
											style="width: 30%; height: 50px;"><p>
												<font style="font-size: 120%">위경도</font></th>
										<td align="center" bgcolor="#FFFFFF"><p>
												<font style="font-size: 120%"><b><%=b_loc_lat%>,
														<%=b_loc_lon%></b></font>
									</tr>
									<tr>
										<th align="center" bgcolor="#FFFFFF"
											style="width: 30%; height: 50px;"><p>
												<font style="font-size: 120%">주소</font></th>
										<td align="center" bgcolor="#FFFFFF"><p>
												<font style="font-size: 120%"><b><%=b_loc_str%></b></font>
									</tr>

								</table>
							</td>
							<td colspan="2">
								<table border="0" cellpadding="20" cellspacing="0" style="width:100%">
									<tr>
										<p align="center" size="10">현재 마커 위치</p>
									<tr>
									<tr>
										<th align="center" bgcolor="#FFFFFF"
											style="width: 30%; height: 50px;"><p>
												<font style="font-size: 120%">위경도</font></th>
										<td align="center" bgcolor="#FFFFFF"><p>
												<font style="font-size: 120%"><b><span id="lat"></span>, 
														<span id="lng"></span></b></font>
									</tr>
									<tr>
										<th align="center" bgcolor="#FFFFFF"
											style="width: 30%; height: 50px;"><p>
												<font style="font-size: 120%">주소</font></th>
										<td align="center" bgcolor="#FFFFFF"><p>
												<font style="font-size: 120%"><b><span
														id="address"></span></b></font>
									</tr>
								</table>
							</td>
						</tr>
					</table>
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
