$(document).ready(function() {
//    alert(1);
//        $("#checkinDate").datepicker();
//        $("#checkoutDate").datepicker();
//        
//    $(".datepicker").datepicker({
//        dateFormat: 'dd-mm-yy',
//        changeMonth: true,
//        changeYear: true,
//    });
    $(".datepicker1").datepicker({
        dateFormat: 'dd-mm-yy',
        changeMonth: true,
        changeYear: true,
        minDate: 0 ,
        onSelect: function(selected) {
          $(".datepicker2").datepicker("option","minDate", selected);
        }
    });
    $(".datepicker2").datepicker({ 
        dateFormat: 'dd-mm-yy',
        changeMonth: true,
        changeYear: true,
        onSelect: function(selected) {
           $(".datepicker1").datepicker("option","maxDate", selected);
        }
    });  
    $(".dobpicker").datepicker({
        dateFormat: 'dd-mm-yy',
        changeMonth: true,
        changeYear: true,
        maxDate: 0
    });
    $("#rccheckbox").click(function(){
        if (!$(this).is(':checked')) {
            $(".dobpicker").datepicker(this.checked ? "disable" : "enable");
        }
        
    });
    
    $('.slider1').bxSlider({
        slideWidth: 1170,
        minSlides: 1,
        maxSlides: 1,
        slideMargin: 10
    });
});

