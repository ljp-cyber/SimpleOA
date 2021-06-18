<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="top.jsp"/>

<section id="content" class="table-layout animated fadeIn">
    <div class="tray tray-center">
        <div class="content-header">
            <h2> 修改报销单 </h2>
            <p class="lead"></p>
        </div>
        <div class="admin-form theme-primary mw1000 center-block" style="padding-bottom: 175px;">
            <div class="panel heading-border">
                <form:form id="admin-form" name="addForm" action="${pageContext.request.contextPath}/receipts/update" modelAttribute="receipts">
                    <form:hidden path="receiptsId"/>
                    <form:hidden path="createrId"/>
                    <form:hidden path="creatTime"/>
                    <form:hidden path="createrId"/>
                    <form:hidden path="state"/>
                    <div class="panel-body bg-light">
                        <div class="section-divider mt20 mb40">
                            <span> 基本信息 </span>
                        </div>
                        <div class="section">
                            <label for="cause" class="field prepend-icon">
                                <form:input path="cause" cssClass="gui-input" placeholder="事由..."/>
                                <label for="cause" class="field-icon">
                                    <i class="fa fa-lock"></i>
                                </label>
                            </label>
                        </div>
                        <div class="section-divider mt20 mb40">
                            <span> 费用明细 </span>
                        </div>
                        <div class="section row" id="items">
                            <c:forEach items="${receipts.items}" varStatus="sta">
                            <div>
                                <div class="col-md-3">
                                    <label for="items[${sta.index}].costType" class="field prepend-icon">
                                        <form:hidden path="items[${sta.index}].receiptsDetailsId"/>
                                        <form:hidden path="items[${sta.index}].receiptsId"/>
                                        <form:select path="items[${sta.index}].costType" cssClass="gui-input" placeholder="花销类型..." items="${items}"/>
                                    </label>
                                </div>
                                <div class="col-md-3">
                                    <label for="items[${sta.index}].costMoney" class="field prepend-icon">
                                        <form:input path="items[${sta.index}].costMoney" cssClass="gui-input money" placeholder="金额..."/>
                                        <label for="items[${sta.index}].costMoney" class="field-icon">
                                            <i class="fa fa-lock"></i>
                                        </label>
                                    </label>
                                </div>
                                <div class="col-md-5">
                                    <label for="items[${sta.index}].represention" class="field prepend-icon">
                                        <form:input path="items[${sta.index}].represention" cssClass="gui-input" placeholder="备注..." />
                                        <label for="items[${sta.index}].represention" class="field-icon">
                                            <i class="fa fa-lock"></i>
                                        </label>
                                    </label>
                                </div>
                                <div class="col-md-1" style="text-align:right;">
                                    <button type="button" class="button"> X </button>
                                </div>
                            </div>
                            </c:forEach>
                        </div>
                        <div class="section row">
                            <div class="col-md-3">
                                <label for="totalMoney" class="field prepend-icon">
                                    <form:input id="totalMoney" path="totalmoney" cssClass="gui-input" placeholder="总金额..." readonly="true"/>
                                    <label for="totalMoney" class="field-icon">
                                        <i class="fa fa-user"></i>
                                    </label>
                                </label>
                            </div>
                            <div class="section" style="text-align:right;">
                                <div class="col-md-9">
                                    <button type="button" class="button" id="addItemButton"> + </button>
                                </div>
                            </div>
                        </div>
                        <div class="panel-footer text-right">
                            <button type="submit" class="button"> 保存 </button>
                            <button type="button" class="button" onclick="javascript:window.history.go(-1);"> 返回 </button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</section>


<jsp:include page="bottom.jsp"/>