<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>비디오 스트리밍 테스트</title>
        <script src="../js/jquery-1.11.3.min.js" type="text/javascript"></script>
        <script type="text/javascript" src="../js/jwplayer.js"></script>       
        <script src="../js/player.js" type="text/javascript"></script>
        <script>jwplayer.key = "2k/OWiKCBtXbD9wRCh1hiuF5tCfq8UV1y7Vgfw=="</script>
        <link rel="stylesheet" type="text/css" href="../css/style.css" />       
    </head>
 
    <body>
    <h1>jwPlayer를 이용한 비디오 스트리밍 테스트</h1>
	<div>(파이어폭스에서는 동작안함)</div>
         <!-- Video Preview -->
        <div class="container">            
            <div id="video_preview">                    
                <div id="player"></div><div class="clear"></div>
                <br/><br/><br/>
                <!-- vod 재생할때는 이런식으로 주소써야한다. rtmp://192.168.63.33:1935/vod/_definst_/safeme/VID_20151030_203026.mp4 -->
                <input type="text" id="stream_url" value="rtmp://192.168.63.33:1935/live/myStream"/><br/>
                <input type="button" id="btn_start" class="" value="Start" />
                <input type="button" id="btn_stop" class="" value="Stop"/>
            </div>
            <div class="clear"></div>
        </div>
        <!-- ./Video Preview -->
 
        
  ]  </body>
</html>