<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="myFN" uri="/WEB-INF/tlds/bookingFunction" %>
<header>
    <div class="container">
        <div class="logo">
            <a href="Booking.jsp"><img src="img/mgm-grand-logo.png" height="170px" width="245px" /></a>
        </div>
        <div class="menu">
            <ul>
                <li><a href="Booking.jsp">Home</a></li>
                <li class="menu-room"> 
                    <a href="#">Rooms</a>
                    <div class="col-md-6 col-sm-6 dropdown-menu">
                        <ul class="col-md-6 col-sm-6">
                            <c:set value="${myFN:getAllRoomType()}" var="roomtypes" scope="session"></c:set>
                            <c:forEach items="${roomtypes}" var="p">
                                <li><a href="" title="${p.roomTypeId}">${p.roomType}</a></li>
                            </c:forEach>
                        </ul>
                        <div class="col-md-6 col-sm-6 image-room-menu">
                            <a><img src="img/prop-img-full-i53co6yh-eewl7o6ypzwg.jpg" height="170px" width="360px"/></a>
                        </div>
                    </div>
                </li>
                <li><a href="#">Booking</a></li>
                <li><a href="#">Gallery</a></li>
                <li><a href="#">About</a></li>
                <li><a href="#">Blog</a></li>
                <li><a href="#">Contact</a></li>
            </ul>
        </div>
    </div>
</header>