<%-- 
    Document   : AdminPage
    Created on : Oct 20, 2015, 8:30:18 AM
    Author     : Michael
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="myFN" uri="/WEB-INF/tlds/bookingFunction" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Page</title>
        <link rel="stylesheet" href="css/a.css">
    </head>
    <body>
        <div class="container">
            <div class="logo">
                <a href="Booking.jsp"><img src="img/mgm-grand-logo.png" height="170px" width="245px" /></a>
            </div>
            <div class="admin-form">
                <h1>MGM ADMINISTRATOR</h1>
                <c:set var="items" value="${myFN:getAllBooking()}" scope="session"></c:set>
                <table class="admin-table" >
                    <tr>
                        <th>Booking ID</th>
                        <th>Customer Name</th>
                        <th>Room Number </th>
                        <th>Check in Date</th>
                        <th>Check out Date</th>
                        <th>Status</th>
                    </tr>
                    <c:forEach items="${items}" var="b">
                        <tr>
                            <td>${b.bookingId}</td>
                            <td>${myFN:getCustomerNameByIdCard(b.customerIdentify)}</td>
                            <td>${b.roomNumber}</td>
                            <td>${b.checkinDate}</td>
                            <td>${b.checkoutDate}</td>
                            <c:choose>
                                <c:when test="${b.status==1}">
                                    <td><c:out value="Confirmed"></c:out></td>
                                    <td><a href="Controller?action=ChangeStatus&bookingId=${b.bookingId}&status=2">Complete</a></td>
                                    <td><a href="Controller?action=ChangeStatus&bookingId=${b.bookingId}&status=3">Cancel</a></td>
                                </c:when>
                                <c:when test="${b.status==2}">
                                    <td><c:out value="Completed"></c:out></td>
                                    <td><a href="Controller?action=ChangeStatus&bookingId=${b.bookingId}&status=99">Delete</a></td>
                                </c:when>
                                <c:when test="${b.status==3}">
                                    <td><c:out value="Canceled"></c:out></td>
                                    <td><a href="Controller?action=ChangeStatus&bookingId=${b.bookingId}&status=99">Delete</a></td>
                                </c:when>
                                <c:when test="${b.status==0}">
                                    <td><c:out value="Unconfirmed"></c:out></td>
                                    <td><a href="Controller?action=ChangeStatus&bookingId=${b.bookingId}&status=1">Confirm</a></td>
                                    <td><a href="Controller?action=ChangeStatus&bookingId=${b.bookingId}&status=99">Delete</a></td>
                                </c:when>
                                <c:otherwise>
                                    <td><c:out value="Unconfirmed"></c:out></td>
                                    <td><a href="Controller?action=ChangeStatus&bookingId=${b.bookingId}&status=1">Confirm</a></td>
                                    <td><a href="Controller?action=ChangeStatus&bookingId=${b.bookingId}&status=99">Delete</a></td>
                                </c:otherwise>
                            </c:choose>
                        </tr>
                    </c:forEach>
                </table>
            </div>

        </div>

    </body>
</html>
