<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.ljp.simpleoa.Constant" %>
<!DOCTYPE html>
<html>


<!-- Mirrored from admindesigns.com/demos/absolute/1.1/admin_forms-validation.html by HTTrack Website Copier/3.x [XR&CO'2014], Thu, 06 Aug 2015 02:56:15 GMT -->
<head>
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">

    <title> SimpleOA-欢迎使用自动化办公系统 </title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/skin/default_skin/css/theme.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/admin-tools/admin-forms/css/admin-forms.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/img/favicon.ico">
</head>

<body class="admin-validation-page" data-spy="scroll" data-target="#nav-spy" data-offset="200">
<security:authentication property="principal" var="worker"/>
<div id="main">
    <header class="navbar navbar-fixed-top navbar-shadow">
        <div class="navbar-branding">
            <a class="navbar-brand" href="#">
                <b>SimpleOA</b>
            </a>
            <span id="toggle_sidemenu_l" class="ad ad-lines"></span>
        </div>
        <ul class="nav navbar-nav navbar-right">
            <li class="dropdown menu-merge">
                <a href="#" class="dropdown-toggle fw600 p15" data-toggle="dropdown">
                    <img src="${pageContext.request.contextPath}/assets/img/avatars/5.jpg" alt="avatar" class="mw30 br64">
                    <span class="hidden-xs pl15"> ${worker.workerName} </span>
                    <span class="caret caret-tp hidden-xs"></span>
                </a>
                <ul class="dropdown-menu list-group dropdown-persist w250" role="menu">
                    <li class="list-group-item">
                        <a href="${pageContext.request.contextPath}/index" class="animated animated-short fadeInUp">
                            <span class="fa fa-user"></span> 个人信息
                            <span class="label label-warning"></span>
                        </a>
                    </li>
                    <li class="list-group-item">
                        <a href="${pageContext.request.contextPath}/to_change_password" class="animated animated-short fadeInUp">
                            <span class="fa fa-gear"></span> 设置密码 </a>
                    </li>
                    <!-- 退出 -->
                    <li class="dropdown-footer">
                    	<!-- post提交退出 -->
                    	<form action="${pageContext.request.contextPath}/logout" method="post">
                    		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    		<input type="submit" value="退出"/>
                    	</form>
                    	<!-- get提交退出 -->
                        <a href="${pageContext.request.contextPath}/logout"  class="">
                            <span class="fa fa-power-off pr5"></span> 退出 
                        </a>
                    </li>
                </ul>
            </li>
        </ul>
    </header>
    <aside id="sidebar_left" class="nano nano-light affix">
        <div class="sidebar-left-content nano-content">
            <header class="sidebar-header">
                <div class="sidebar-widget author-widget">
                    <div class="media">
                        <a class="media-left" href="${pageContext.request.contextPath}/index">
                            <img src="${pageContext.request.contextPath}/assets/img/avatars/3.jpg" class="img-responsive">
                        </a>
                        <div class="media-body">
                            <div class="media-author">${worker.workerName }</div>
                            <div class="media-links">
                                <a href="${pageContext.request.contextPath}/logout">退出</a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="sidebar-widget search-widget hidden">
                    <div class="input-group">
                        <span class="input-group-addon">
                        	<!-- i标签 斜体 -->
                            <i class="fa fa-search"></i>
                        </span>
                        <input type="text" id="sidebar-search" class="form-control" placeholder="Search...">
                    </div>
                </div>
            </header>
            <ul class="nav sidebar-menu">
                <li class="sidebar-label pt20">日常管理</li>
                <li>
                    <a href="${pageContext.request.contextPath }/receipts/deal">
                        <span class="glyphicon glyphicon-book"></span>
                        <span class="sidebar-title">待处理报销单</span>
                        <span class="sidebar-title-tray">
                <c:if test="${worker.workerName=='周仓1'}">
                <span class="label label-xs bg-primary">New</span>
                </c:if>
              </span>
                    </a>
                </li>
                <li class="active">
                    <a href="${pageContext.request.contextPath }/receipts/list">
                        <span class="glyphicon glyphicon-home"></span>
                        <span class="sidebar-title">个人报销单</span>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath }/receipts/toAdd">
                        <span class="fa fa-calendar"></span>
                        <span class="sidebar-title">填写报销单</span>
                    </a>
                </li>
	            <li class="sidebar-label pt15">基础信息管理</li>
                <security:authorize access="hasAnyAuthority('${Constant.ROLE_DM},${Constant.ROLE_GM}')">
	                <li>
	                    <a class="accordion-toggle" href="#">
	                        <span class="glyphicon glyphicon-check"></span>
	                        <span class="sidebar-title">员工管理</span>
	                        <span class="caret"></span>
	                    </a>
	                    <ul class="nav sub-nav">
	                        <li>
	                            <a href="${pageContext.request.contextPath}/worker/list">
	                                <span class="glyphicon glyphicon-calendar"></span> 所有员工 </a>
	                        </li>
	                        <li class="active">
	                            <a href="${pageContext.request.contextPath}/worker/to_add">
	                                <span class="glyphicon glyphicon-check"></span> 添加员工 </a>
	                        </li>
	                    </ul>
	                </li>
                </security:authorize>
                <security:authorize access="hasAuthority('${Constant.ROLE_GM}')">
	                <li>
	                    <a class="accordion-toggle" href="#">
	                        <span class="fa fa-columns"></span>
	                        <span class="sidebar-title">部门管理</span>
	                        <span class="caret"></span>
	                    </a>
	                    <ul class="nav sub-nav">
	                        <li>
	                            <a href="${pageContext.request.contextPath}/department/list">
	                                <span class="glyphicon glyphicon-calendar"></span> 所有部门 </a>
	                        </li>
	                        <li class="active">
	                            <a href="${pageContext.request.contextPath}/department/to_add">
	                                <span class="glyphicon glyphicon-check"></span> 添加部门 </a>
	                        </li>
	                    </ul>
	                </li>
                </security:authorize>
                <li>
                    <a class="accordion-toggle" href="#">
                        <span class="fa fa-columns"></span>
                        <span class="sidebar-title">网站管理</span>
                        <span class="caret"></span>
                    </a>
                    <ul class="nav sub-nav">
                        <li>
                            <a href="${pageContext.request.contextPath}/websiteInfo">
                                <span class="glyphicon glyphicon-calendar"></span> 网站信息 </a>
                        </li>
                    </ul>
                </li>
            </ul>
            <div class="sidebar-toggle-mini">
                <a href="#">
                    <span class="fa fa-sign-out"></span>
                </a>
            </div>
        </div>
    </aside>
    <section id="content_wrapper">