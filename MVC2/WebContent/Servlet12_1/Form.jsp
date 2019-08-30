<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Form.jsp</title>
</head>
<body>
	<fieldset>
		<legend>FirstController에서 포워드된 페이지</legend>
		<form action="<c:url value='/Basic/second.kosmo'/>" method="post">
			<input type="submit" value="POST"/>
		</form>
	</fieldset>
</body>
</html>
