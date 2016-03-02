<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras"
	prefix="tilesx"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>

<meta charset="utf-8">
<title>Market Pos</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!-- Le styles -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/js/lib/bootstrap/css/bootstrap.css"
	media="screen">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/js/lib/bootstrap/css/bootstrap-responsive.css"
	media="screen">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/pos.css"
	media="screen">
<tiles:insertAttribute name="otherCss" />
</head>

<body>
	<tiles:insertAttribute name="header" />
	<div class="container-fluid">
		<div class="row-fluid">
			<tiles:insertAttribute name="content" />
		</div>
		<!--/row-->
		<hr>
		<tiles:insertAttribute name="footer" />
	</div>
	<!--/.fluid-container-->
	<tilesx:useAttribute name="useDefaultScript" />
	<c:if test="${useDefaultScript=='true'}">
		<script
			src="${pageContext.request.contextPath}/js/lib/jquery/jquery-1.9.1.js"></script>
		<script
			src="${pageContext.request.contextPath}/js/lib/jquery.i18n.js"></script>
		<script src="${pageContext.request.contextPath}/js/pos.js"></script>
		<script src="${pageContext.request.contextPath}/js/messages_zh_CN.js"></script>
		<script
			src="${pageContext.request.contextPath}/js/lib/bootstrap/js/bootstrap.js"></script>
	</c:if>
	<tiles:insertAttribute name="otherScript" />
</body>
</html>