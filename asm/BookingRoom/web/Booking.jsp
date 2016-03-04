<%-- 
    Document   : Booking
    Created on : Oct 12, 2015, 9:31:17 PM
    Author     : mai
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <div id="slider2_container" style="position: relative; top: 0px; left: 0px; width: 600px; height: 300px;">

                <!-- Slides Container -->
                <div u="slides" style="cursor: move; position: absolute; left: 0px; top: 0px; width: 600px; height: 300px; overflow: hidden;">
                    <div>
                        <a u=image href="#"><img src="img/slide/slider-1.jpg" /></a>
                    </div>
                    <div>
                        <a u=image href="#"><img src="img/slide/slider-2.jpg" /></a>
                    </div>
                    <div>
                        <a u=image href="#"><img src="img/slide/testimonial-bg.jpg" /></a>
                    </div>

                </div>

                <!-- bullet navigator container -->
                <div u="navigator" class="jssorb01" style="bottom: 16px; right: 10px;">
                    <div u="prototype"></div>
                </div>
                <!--<span u="arrowleft" class="jssora05l" style="top: 123px; left: 8px;"></span>
                <span u="arrowright" class="jssora05r" style="top: 123px; right: 8px;"></span>-->
            </div>
            <!--End Slider-->
            <form name="booking" action="Controller?action=SelectRoomAndService" method="POST" class="main-product main-form"  onsubmit="return validateForm()">               
                <div class="form-group">
                    <input class="form-control datepicker" id="checkinDate" name="checkinDate" placeholder="Check in Date"/>
                </div>
                
                <div class="form-group">
                    <input class="form-control datepicker" id="checkoutDate" name="checkoutDate" placeholder="Check out Date"/>
                </div>
                
                <div class="form-group">
                    <button type="submit" class="button"> Booking Now </button>
                </div>
            </form>
            <div class="col-md-12 main-product">
                <div class="room-title">
                    <h2>List room type in MGM</h2>
                    <div class="sperad"></div>
                </div>
                
                <div class="col-md-4 product">
                    <div class="image-room"><a href=""><img src="img/singleroom1.jpg"/></a></div>
                    <div class="room-action">
                        <p><a href="">Single Room</a></p>
                        <span>A room which has single bed facility</span>
                    </div>
                </div>
                <div class="col-md-4 product">
                    <div class="image-room"><a href=""><img src="img/doubleroom.jpg"/></a></div>
                    <div class="room-action">
                        <p><a href="">Double Room</a></p>
                        <span>A room which has double bed facility</span>
                    </div>
                </div>
                <div class="col-md-4 product">
                    <div class="image-room"><a href=""><img src="img/double double room.jpg"/></a></div>
                    <div class="room-action">
                        <p><a href="">Double Double Room</a></p>
                        <span>A room which has two double bed in room</span>
                    </div>
                </div>
                <div class="col-md-4 product">
                    <div class="image-room"><a href=""><img src="img/Duplex-Room_w.jpg"/></a></div>
                    <div class="room-action">
                        <p><a href="">Duplex Room</a></p>
                        <span>A room which is bean spread on two floor connected by an internal staircase</span>
                    </div>
                </div>
                <div class="col-md-4 product">
                    <div class="image-room"><a href=""><img src="img/suite-room.jpg"/></a></div>
                    <div class="room-action">
                        <p><a href="">Suite Room</a></p>
                        <span>A room comprise of two or more bedroom, a living room and a dinning area </span>
                    </div>
                </div>
                <div class="col-md-4 product">
                    <div class="image-room"><a href=""><img src="img/twin-room.jpg"/></a></div>
                    <div class="room-action">
                        <p><a href="">Twin Room</a></p>
                        <span>A room which has two single bed sperated by a center table</span>
                    </div>
                </div>
            </div>
            <div class="col-md-12 main-product">
                 <div class="room-title">
                    <h2>Gallery</h2>
                    <div class="sperad"></div>
                 </div>
                <div class="col-md-4 ">
                    <div class="blog-box">
                        <a href="#"><img src="img/blog-thumb-1.jpg"></a>     
                    </div>
                </div>   
                <div class="col-md-4">
                    <div class="blog-box">
                        <a href="#"><img src="img/blog-thumb-2.jpg"></a>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="blog-box">
                        <a href="#"><img src="img/blog-thumb-2.jpg"></a>
                    </div>
                </div>
                <div class="col-md-8 ">
                    <div class="blog-box">
                        <a href="#"><img src="img/blog-thumb-4.jpg"></a>
                    </div>
                </div>   
            </div>
            
        </div>
        
        <%@include file="layout/footer.jsp" %>
    </body>
</html>
