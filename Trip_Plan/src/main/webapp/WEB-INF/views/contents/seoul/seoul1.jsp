<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%  request.setCharacterEncoding("UTF-8"); %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>seoul1</title>
<style>
        body {
        text-align: center;
            margin-left:200px;
            margin-right:50px;
        }
</style>
</head>
<body>
<!-- top menu -->
     <div>
        <jsp:include page="../../common/topmenu.jsp"/>
    </div>
<!-- side menu --> 
     <div>
        <jsp:include page="../../common/sidemenu.jsp"/>
    </div>
<!-- 본문 내용 -->
<div class="container text-center">
	<div id="map" style="width:500px;height:400px;"></div>
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=2b2ca9aeab329e586b8575704eaf0afa"></script>
	<script>
		var container = document.getElementById('map');
		var options = {
			center: new kakao.maps.LatLng(33.452739313807456, 126.5709308145358),
			level: 3
		};

		var map = new kakao.maps.Map(container, options);
		var linePath = [
		    new kakao.maps.LatLng(33.452344169439975, 126.56878163224233),
		    new kakao.maps.LatLng(33.452739313807456, 126.5709308145358),
		    new kakao.maps.LatLng(33.45178067090639, 126.5726886938753) 
		];

		// 지도에 표시할 선을 생성합니다
		var polyline = new kakao.maps.Polyline({
		    path: linePath, // 선을 구성하는 좌표배열 입니다
		    strokeWeight: 3, // 선의 두께 입니다
		    strokeColor: '#e05b36', // 선의 색깔입니다
		    strokeOpacity: 0.7, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
		    strokeStyle: 'solid' // 선의 스타일입니다
		});
		
		// 마커가 표시될 위치입니다 
		var markerPosition  = new kakao.maps.LatLng(37.581840913446065, 126.99162787647798); //창덕궁
		var markerPosition2  = new kakao.maps.LatLng(37.58170870919596, 126.97906123732443); //국립민속박물관
		var markerPosition3  = new kakao.maps.LatLng(37.578611720269464, 126.98008664070879); //국립현대미술관
		var markerPosition4  = new kakao.maps.LatLng(37.57609047340471, 126.99420109470466); //종묘
		var markerPosition5  = new kakao.maps.LatLng(37.57030172336719, 126.99990095364751); //광장시장
		

		// 마커를 생성합니다
		var marker = new kakao.maps.Marker({
		    position: markerPosition
		});
		var marker2 = new kakao.maps.Marker({
		    position: markerPosition2
		});
		var marker3 = new kakao.maps.Marker({
		    position: markerPosition3
		});
		
		var marker4 = new kakao.maps.Marker({
		    position: markerPosition3
		});
		
		var marker5 = new kakao.maps.Marker({
		    position: markerPosition3
		});
		
		// 마커가 지도 위에 표시되도록 설정합니다
		marker.setMap(map);
		marker2.setMap(map);
		marker3.setMap(map);
		marker4.setMap(map);
		marker5.setMap(map);

		// 지도에 선을 표시합니다 
		polyline.setMap(map);  
	</script>
</div>	
	<!-- footer -->
     <div>
        <jsp:include page="../../common/footer.jsp"/>
    </div>
</body>
</html>