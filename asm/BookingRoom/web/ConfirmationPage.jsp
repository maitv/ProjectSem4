<%-- 
    Document   : CheckoutPage
    Created on : Oct 20, 2015, 7:26:18 AM
    Author     : Michael
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="myFN" uri="/WEB-INF/tlds/bookingFunction" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="layout/layout.jsp" %>
        <script src="js/myScript.js"></script>
    </head>
    <body>
        <%@include file="layout/header.jsp" %>
        <div class="container">
            <div class="wrap">
                <div class="result-title">
                    <h1>Confirm Reservation</h1>
                    <div class="sperad"></div>
                </div>
                <div class="col-md-8">
                    <form name="confirmation" action="Controller?action=Booking" method="POST" class="main-product" onsubmit="return validateConfirmation()">
                        <h4><strong>Your Contact Details</strong></h4>
                        <div class="form-group-service">
                            <label><span class="red">*</span>Full Name</label>
                            <input type="text" name="txtName" id="txtName" class="form-control-service" value=""/>
                        </div>
                        <div class="form-group-service">
                            <label><span class="red">*</span>Address</label>
                            <input type="text" name="txtAddress" id="txtAddress" class="form-control-service" value=""/>
                        </div>
                        <div class="form-group-service">
                            <label><span class="red">*</span>Phone Number</label>
                            <input type="text" name="txtPhoneNumber" id="txtPhoneNumber" class="form-control-service" value=""/>
                        </div>
                        <div class="form-group-service">
                            <label><span class="red">*</span>Email</label>
                            <input type="text" name="txtEmail" id="txtEmail" class="form-control-service" value=""/>
                        </div>
                        <div class="form-group-service">
                            <label><span class="red">*</span>Identify Card</label>
                            <input type="text" name="txtIdentity" id="txtIdentity" class="form-control-service" value=""/>
                        </div>
                        <hr>
                        <h4><strong>Billing Information</strong></h4>
                        <div class="form-group-service">
                            <label><span class="red">*</span>Name of Card</label>
                            <input type="text" name="txtNameCard" id="txtNameCard" class="form-control-service" value=""/>
                        </div>
                        <div class="form-group-service">
                            <label><span class="red">*</span>Card Number</label>
                            <input type="text" name="txtCardNumber" id="txtCardNumber" class="form-control-service" value=""/>
                        </div>
                        <div class="form-group-service">
                            <label><span class="red">*</span>Accepted Card</label>
                            <select class="form-control" id="room-type" style="width: 150px;margin: 0px">
                                <option value="VP Bank">VP Bank</option>
                                <option value="Tecombank">Tecombank</option>
                                <option value="TP Bank">TP Bank</option>
                                <option value="Ocean Bank">Ocean Bank</option>
                                <option value="BIDV">BIDV</option>
                                <option value="Vietcombank">Vietcombank</option>
                            </select>
                        </div>
                        <div class="form-group text-center">
                            <button type="submit" class="btnSelect"> Confirm </button>
                        </div>
                    </form>
                </div>
                <div class="col-md-4">
                    <h4><strong>Order Information</strong></h4>
                    <ul>
                        <li><b>Room Type: ${myFN:getRoomTypeById(sessionScope.roomType)}</b></li>
                        <!--li><b>Room Name: </b></li-->
                        <li><b>Check in: ${sessionScope.checkinDate}</b></li>
                        <li><b>Check out: ${sessionScope.checkoutDate}</b></li>
                        <li><b>Service : 
                                <c:forEach var="p" items="${sessionScope.services}">
                                    <c:out value="${myFN:getServiceNameById(p)}"></c:out>
                                    <c:out value="  "></c:out>
                                </c:forEach>
                            </b></li>
                    </ul>
                    <hr>
                    <b>Total Price: ${myFN:getTotalPrice(sessionScope.services,sessionScope.roomType) }</b>
                </div>
            </div>
        </div>
        <%@include file="layout/footer.jsp" %>
    </body>
</html>
