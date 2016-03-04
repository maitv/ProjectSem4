
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="myFN" uri="/WEB-INF/tlds/bookingFunction" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include  file="layout/layout.jsp"%>
        <script type="text/javascript" src="js/myScript.js"></script>
    </head>
    <body onreadystatechange="loadRoomList();">
        <%@include file="layout/header.jsp" %>
        <div class="container">
            <div class="wrap">
                <div class="result-title">
                    <h1>Check Result</h1>
                    <div class="sperad"></div>
                </div>
                <form action="Controller?action=Confirmation" name="SelectRoomAndService" onsubmit="return validateSelectRoomAndService()" method="POST"  class="main-product main-form">
                    <div class="form-group-service">
                        <label>Room Type</label>
                        <select class="form-control" id="room_type" name="roomType" onchange="loadRoomList();">
                            <c:set value="${myFN:getAllRoomType()}" var="roomtypes" scope="session"></c:set>
                            <c:forEach items="${roomtypes}" var="p">
                                <option value="${p.roomTypeId}" >${p.roomType}</option>
                            </c:forEach>
                        </select>
                    </div >
                    <div class="form-group-service" id="listRoom">
                        <label id="lbRoomList">Room No</label>
                        <ul class="form-control-service" id="listRoomUL">
                            
                        </ul>
                    </div>
                    <div class="form-group-service">
                        <label>Check in Date</label>
                        <input class="form-control datepicker" value="${sessionScope.checkinDate}" name="checkinDate" id="checkinDate"/>
                    </div>

                    <div class="form-group-service">
                        <label>Check out Date</label>
                        <input class="form-control datepicker" value="${sessionScope.checkoutDate}" name="checkoutDate" id="checkoutDate"/>
                    </div>

                    <div class="form-group-service">
                        <label>Service</label>
                        <ul class="form-control-service">
                            <c:set value="${myFN:getAllService()}" var="items" scope="session"></c:set>
                            <c:forEach items="${items}" var="p">
                                <li><input type="checkbox" value="${p.serviceId}" name="services" class="checkbox-service">${p.serviceName}</li>
                                </c:forEach>
                        </ul>
                    </div>
                    <div class="form-group text-center">
                        <button type="submit" class="btnSelect"> Next </button>
                    </div>
                </form>
            </div>
        </div>
        <%@include file="layout/footer.jsp" %>
    </body>
</html>
