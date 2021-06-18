<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.ljp.common.*" %>
<jsp:include page="top.jsp"/>
<section id="content" class="table-layout animated fadeIn">
    <div class="tray tray-center">
        <div class="content-header">
            <h2> 处理报销单 </h2>
            <p class="lead"></p>
        </div>
        <div class="admin-form theme-primary mw1000 center-block" style="padding-bottom: 175px;">
            <div class="panel heading-border">
                <div class="panel-body bg-light">
                    <div class="section-divider mt20 mb40">
                        <span> 基本信息 </span>
                    </div>
                    <div class="section row">
                        <div class="col-md-2">事由</div>
                        <div class="col-md-6">${receipts.cause}</div>
                    </div>
                    <div class="section row">
                        <div class="col-md-2">创建人</div>
                        <div class="col-md-4">${receipts.creater.workerName}</div>
                        <div class="col-md-2">创建时间</div>
                        <div class="col-md-4"><spring:eval expression="receipts.creatTime"/></div>
                    </div>
                    <div class="section row">
                        <div class="col-md-2">待处理人</div>
                        <div class="col-md-4">${receipts.dealer.workerName}</div>
                        <div class="col-md-2">状态</div>
                        <div class="col-md-4">${receipts.state}</div>
                    </div>
                    <div class="section-divider mt20 mb40">
                        <span> 费用明细 </span>
                    </div>
                    <div class="section row">
                        <C:forEach items="${receipts.items}" var="item">
                        <div class="col-md-3">${item.costType}</div>
                        <div class="col-md-3">${item.costMoney}</div>
                        <div class="col-md-5">${item.represention}</div>
                        </C:forEach>
                    </div>
                    <div class="section row">
                        <div class="col-md-2">总金额</div>
                        <div class="col-md-6">${receipts.totalmoney}</div>
                    </div>
                    <div class="section-divider mt20 mb40">
                        <span> 处理流程 </span>
                    </div>
                    <div class="section row">
                    <c:forEach items="${receipts.records}" var="record">
                        <div class="col-md-1">${record.dealId}${record.dealer.workerName}</div>
                        <div class="col-md-3"><spring:eval expression="record.dealTime"/></div>
                        <div class="col-md-1">${record.dealType}</div>
                        <div class="col-md-2">${record.dealResult}</div>
                        <div class="col-md-5">备注：${record.represention}</div>
                    </c:forEach>
                    </div>
                    <form:form id="admin-form" name="addForm" action="${pageContext.request.contextPath }/receipts/check" modelAttribute="record">
                        <form:hidden path="receiptsId" />
                        <div class="panel-body bg-light">
                            <div class="section">
                                <label for="represention" class="field prepend-icon">
                                    <form:input path="represention" cssClass="gui-input" placeholder="备注..."/>
                                    <label for="represention" class="field-icon">
                                        <i class="fa fa-lock"></i>
                                    </label>
                                </label>
                            </div>
                            <div class="panel-footer text-right">
                                <c:if test="${sessionScope.worker.post==Constant.POST_GM || sessionScope.worker.post==Constant.POST_DM}">
                                <button type="submit" class="button" name="dealWay" value="${Constant.STEP_CHECK_PAST}" >${Constant.STEP_CHECK_PAST}</button>
                                <button type="submit" class="button" name="dealWay" value="${Constant.STEP_CHECK_BACK}" >${Constant.STEP_CHECK_BACK}</button>
                                <button type="submit" class="button" name="dealWay" value="${Constant.STEP_CHECK_REFUSE}" >${Constant.STEP_CHECK_REFUSE}</button>
                                </c:if>
                                <c:if test="${sessionScope.worker.post==Constant.POST_FM}">
                                <button type="submit" class="button" name="dealWay" value="${Constant.STEP_PAY_PAY}" >${Constant.STEP_PAY_PAY}</button>
                                </c:if>
                                <button type="button" class="button" onclick="javascript:window.history.go(-1);"> 返回 </button>
                            </div>
                        </div>
                    </form:form>

                </div>
            </div>
        </div>
    </div>
</section>


<jsp:include page="bottom.jsp"/>
