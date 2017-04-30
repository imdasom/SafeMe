<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<html>
<head>
<title>신고자 위치</title>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js">
</script>
<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false">
</script>
<script type="text/javascript">
	$(document).ready(function() {
	    var latlng = new google.maps.LatLng(37.487683, 126.825746);
	    var myOptions = {
	  	      zoom : 15,
	  	      center : latlng,
	  	      mapTypeId : google.maps.MapTypeId.ROADMAP
	  	}
	    var map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
	    var marker = new google.maps.Marker({
			position : latlng, 
    		map : map
    	});
	    
	    var geocoder = new google.maps.Geocoder();
	    
	    google.maps.event.addListener(map, 'click', function(event) {
	    	var location = event.latLng;
	    	geocoder.geocode({
	    		'latLng' : location
	    	},
	    	function(results, status){
	    		if( status == google.maps.GeocoderStatus.OK ) {
	    			$('#address').html(results[0].formatted_address);
	    			$('#lat').html(results[0].geometry.location.lat());
	    			$('#lng').html(results[0].geometry.location.lng());
	    		}
	    		else {
	    			alert("Geocoder failed due to: " + status);
	    		}
	    	});
	    	if( !marker ) {
	    		marker = new google.maps.Marker({
	    			position : location, 
		    		map : map
		    	});
	    	}
	    	else {
	    		marker.setMap(null);
	    		marker = new google.maps.Marker({
	    			position : location, 
		    		map : map
		    	});
	    	}
	    	map.setCenter(location);
	    });
	});
</script>
</head>
<body>
	<table border="1">
		<tr>
			<td colspan="2"><div id="map_canvas" style="width: 500px; height: 500px;"></div></td>
		</tr>
		<tr>
			<th width="100">위도</th>
			<td id="lat"></td>
		</tr>
		<tr>
			<th>경도</th>
			<td id="lng"></td>
		</tr>
		<tr>	
			<th>주소</th>
			<td id="address"></td>
		</tr>
	</table>
</body>
</html>
