<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="top.jsp"/>
<section id="content" class="table-layout animated fadeIn">
    <div class="tray tray-center">
        <div class="content-header">
            <h2> 报销单详情 </h2>
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
                    	<div class="col-md-1">处理人</div>
                        <div class="col-md-3">处理时间</div>
                        <div class="col-md-1">操作类型</div>
                        <div class="col-md-2">处理结果</div>
                        <div class="col-md-5">备注</div>
                        <br>
                    <c:forEach items="${receipts.records}" var="record">
                        <div class="col-md-1">${record.dealId}${record.dealer.workerName}</div>
                        <div class="col-md-3"><spring:eval expression="record.dealTime"/></div>
                        <div class="col-md-1">${record.dealType}</div>
                        <div class="col-md-2">${record.dealResult}</div>
                        <div class="col-md-5">${record.represention}</div>
                        <br>
                    </c:forEach>
                    </div>
                    <div class="panel-footer text-right">
                        <button type="button" class="button" onclick="javascript:window.history.go(-1);"> 返回 </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>


<jsp:include page="bottom.jsp"/>
