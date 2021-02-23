<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.ljp.simpleoa.Constant" %>
<jsp:include page="top.jsp"/>
<section id="content" class="table-layout animated fadeIn">
    <div class="tray tray-center">
        <div class="content-header">
            <h2> 待处理报销单 </h2>
            <p class="lead"></p>
        </div>
        <div class="admin-form theme-primary mw1000 center-block" style="padding-bottom: 175px;">
            <div class="panel  heading-border">
                <div class="panel-menu">
                    <div class="row">
                        <div class="hidden-xs hidden-sm col-md-3">
                            <div class="btn-group">
                                <button type="button" class="btn btn-default light" onclick="javascript:window.location.href='${pageContext.request.contextPath}/receipts/deal';">
                                    <i class="fa fa-refresh"></i>
                                </button>
                                <button type="button" class="btn btn-default light">
                                    <i class="fa fa-trash"></i>
                                </button>
                                <button type="button" class="btn btn-default light">
                                    <i class="fa fa-plus" onclick="javascript:window.location.href='${pageContext.request.contextPath}/receipts/toAdd';"></i>
                                </button>
                            </div>
                        </div>
                        <div class="col-xs-12 col-md-9 text-right">
                            <div class="btn-group">
                                <button type="button" class="btn btn-default light" onclick="javascript:window.location.href='${pageContext.request.contextPath}/receipts/deal?toPage=last';">
                                    <i class="fa fa-chevron-left"></i>
                                </button>
                                <button type="button" class="btn btn-default light" onclick="javascript:window.location.href='${pageContext.request.contextPath}/receipts/deal?toPage=nest';">
                                    <i class="fa fa-chevron-right"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                    ${dealPageInfo}
                </div>
                <div class="panel-body pn">
                    <table id="message-table" class="table admin-form theme-warning tc-checkbox-1">
                        <thead>
                        <tr class="">
                            <th class="text-center hidden-xs">Select</th>
                            <th class="hidden-xs">事由</th>
                            <th>状态</th>
                            <th class="hidden-xs">创建人</th>
                            <th class="hidden-xs">金额</th>
                            <th class="text-center">创建时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${receiptsList}" var="cv">
                        <tr class="message-unread">
                            <td class="hidden-xs">
                                <label class="option block mn">
                                    <input type="checkbox" name="mobileos" value="FR">
                                    <span class="checkbox mn"></span>
                                </label>
                            </td>
                            <td>${cv.cause}</td>
                            <td class="hidden-xs">
                                <span class="badge badge-warning mr10 fs11">${cv.state}</span>
                            </td>
                            <td>${cv.creater.workerName}</td>
                            <td class="text-center fw600">${cv.totalmoney}</td>
                            <td><spring:eval expression="cv.creatTime"/></td>
                            <td>
                                <c:if test="${cv.state==Constant.STATE_MODIFY}">
                                    <a onclick="httpPost('${pageContext.request.contextPath }/receipts/toUpdate',{'receiptsId':${cv.receiptsId},'${_csrf.parameterName}':'${_csrf.token}'})">修改</a>
                                    <a onclick="httpPost('${pageContext.request.contextPath }/receipts/submit',{'receiptsId':${cv.receiptsId},'${_csrf.parameterName}':'${_csrf.token}'})">提交</a>
                                </c:if>
                                <c:if test="${cv.state==Constant.STATE_CHECK1 || cv.state==Constant.STATE_CHECK2}">
                                    <a onclick="httpPost('${pageContext.request.contextPath }/receipts/toCheck',{'receiptsId':${cv.receiptsId},'${_csrf.parameterName}':'${_csrf.token}'})">审核</a>
                                </c:if>
                                <c:if test="${cv.state==Constant.STATE_PAY}">
                                    <a onclick="httpPost('${pageContext.request.contextPath }/receipts/toCheck',{'receiptsId':${cv.receiptsId},'${_csrf.parameterName}':'${_csrf.token}'})">打款</a>
                                </c:if>
                                	<a onclick="httpPost('${pageContext.request.contextPath }/receipts/detail',{'receiptsId':${cv.receiptsId},'${_csrf.parameterName}':'${_csrf.token}'})">详细信息</a>
                            </td>
                        </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</section>

<jsp:include page="bottom.jsp"/>
