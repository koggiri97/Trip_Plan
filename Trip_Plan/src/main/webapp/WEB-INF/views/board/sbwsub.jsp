<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>subway sidemenu</title>
</head>
<body>
<div class="subway_tab">
			<div id="sbw_container">
				<h3>지역별 지하철 노선도</h3>
			</div>
			<div class="board-box">
				<table class="board-box-title">
					<tr>
						<th><a href="${contextPath}/board/subway">수도권</a></th>
						<th><a href="${contextPath}/board/sbwbs">부산</a></th>
						<th><a href="${contextPath}/board/sbwdg">대구</a></th>
						<th><a href="${contextPath}/board/sbwdj">대전</a></th>
						<th><a href="${contextPath}/board/sbwgj">광주</a></th>
					</tr>
				</table>
			</div>
</div>
</body>
</html>