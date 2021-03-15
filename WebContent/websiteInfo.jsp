<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.ljp.simpleoa.Constant" %>
<jsp:include page="top.jsp"/>
<security:authentication property="principal" var="worker"/>
<section id="content" class="table-layout animated fadeIn">
    <div class="tray tray-center">
		<!-- 网站信息 -->
		<p>sessionCount:${sessionCount} userCount:${userCount}</p>
		<form action="${pageContext.request.contextPath}/backup" method="get">
		<input type="text" name = "pw" id = "pw">
		<input type="submit" value="获取验证码">
		</form>
		<form action="${pageContext.request.contextPath}/backup_do" method="get">
		<input type="text" name="code" id="code">
		<input type="submit" value="恢复">
		</form>
		<p>${message}</p>
    </div>
</section>
<jsp:include page="bottom.jsp"/>