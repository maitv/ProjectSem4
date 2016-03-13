$(document).ready(function() {
//    alert(1);
//        $("#checkinDate").datepicker();
//        $("#checkoutDate").datepicker();
    $(".datepicker").datepicker({
        dateFormat: 'dd-mm-yy',
        changeMonth: true,
        changeYear: true,
        minDate: 0
    });
    $(".dobpicker").datepicker({
        dateFormat: 'dd-mm-yy',
        changeMonth: true,
        changeYear: true,
    });
    $('.slider1').bxSlider({
        slideWidth: 1170,
        minSlides: 1,
        maxSlides: 1,
        slideMargin: 10
    });
});

