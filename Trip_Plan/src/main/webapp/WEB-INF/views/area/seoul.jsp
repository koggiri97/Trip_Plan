<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"	uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%  request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>seoul</title>
<style>
        body {
            margin-left:50px;
            margin-right:50px;
        }
</style>
</head>
<body>
<!-- top menu -->
     <div>
        <jsp:include page="../common/topmenu.jsp"/>
    </div>
<!-- side menu -->
     <div>
        <jsp:include page="../common/sidemenu.jsp"/>
    </div>
<!-- 본문 내용 -->    
<!-- Image Map Generated by http://www.image-map.net/ -->
<div class="container">
<img src="${contextPath}/resources/images/map.png" usemap="#image-map">

<map name="image-map">
    <area target="_self" alt="서울" title="서울" href="${contextPath}/area/incheon.do" coords="407,221,397,222,393,231,386,233,379,241,370,239,373,250,379,256,386,263,398,260,414,258,416,251,418,242,411,243,409,232" shape="poly">
</map>
</div>

   <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
   <script src="https://cdnjs.cloudflare.com/ajax/libs/jQuery-rwdImageMaps/1.6/jquery.rwdImageMaps.min.js"></script>
   <script>
   $(document).ready(function() {
	   $('img[usemap]').rwdImageMaps();
   });
   </script>


<!-- footer -->
     <div>
        <jsp:include page="../common/footer.jsp"/>
    </div>
</body>
</html>