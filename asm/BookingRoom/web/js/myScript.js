/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function validateForm()
{
    var cinDate = document.booking.checkinDate.value;
    var coutDate = document.booking.checkoutDate.value;
    if (cinDate == "" || coutDate == "")
    {
        alert("Please input your date!");
        return false;
    }
    else
    {
        var cid = new Date(cinDate);
        var cod = new Date(coutDate);

        if (cid >= cod)
        {
            alert("Checkout Date must be after Checkin Date!");
            return false;
        }

        var currentDate = new Date();

        var year = currentDate.getFullYear().toString();
        var month = (currentDate.getMonth() + 1).toString();
        var da = currentDate.getDate().toString();

        var currentDateInFormat = (month[1] ? month : "0" + month[0]) + "/" + (da[1] ? da : "0" + da[0]) + "/" + year;

        var currentD = new Date(currentDateInFormat);

        if (cid < currentD || cod < currentD)
        {
            alert("Checkout Date or Checkin Date must be greater or equal current date!");
            return false;
        }

        return true;
    }
}

function validateSelectRoomAndService()
{
    var cinDate = document.SelectRoomAndService.checkinDate.value;
    var coutDate = document.SelectRoomAndService.checkoutDate.value;

    if (cinDate == "" || coutDate == "")
    {
        alert("Please input your date!");
        return false;
    }
    else
    {
        var cid = new Date(cinDate);
        var cod = new Date(coutDate);

        if (cid >= cod)
        {
            alert("Checkout Date must be after Checkin Date!");
            return false;
        }

        var currentDate = new Date();

        var year = currentDate.getFullYear().toString();
        var month = (currentDate.getMonth() + 1).toString();
        var da = currentDate.getDate().toString();

        var currentDateInFormat = (month[1] ? month : "0" + month[0]) + "/" + (da[1] ? da : "0" + da[0]) + "/" + year;

        var currentD = new Date(currentDateInFormat);

        if (cid < currentD || cod < currentD)
        {
            alert("Checkout Date or Checkin Date must be greater or equal current date!");
            return false;
        }

        return true;
    }

    return true;
}

function validateConfirmation()
{
    var fullname = document.confirmation.txtName.value;
    var address = document.confirmation.txtAddress.value;
    var phonenumber = document.confirmation.txtPhoneNumber.value;
    var email = document.confirmation.txtEmail.value;
    var identity = document.confirmation.txtIdentity.value;
    var nameInCard = document.confirmation.txtNameCard.value;
    var cardNumber = document.confirmation.txtCardNumber.value;

    if (fullname == "" || address == "" ||
            phonenumber == "" || email == "" ||
            identity == "" || nameInCard == "" ||
            cardNumber == "")
    {
        alert("Please completed your information!");
        return false;
    }
    else
    {
        return true;
    }
}

function loadRoomList()
{
//    alert(1);
    var ele = document.getElementById("room_type").value;

    $.ajax({
        type: 'POST',
        url: "AjaxControl",
        data: {roomTypeId: ele},
        dataType: 'json',
        error: function() {

            alert("Error Occured");
        },
        success: function(data) {
            var receivedData = [];
            
            $("#listRoom ul").empty();

            $.each(data.id, function(index, element) {
                $("#listRoom").show();
                
                $("#listRoom ul").append('<li><input type="radio" name="roomNumber" value="' + element.number + '">' + "Room " + element.number + '</li>');
            });
        }
    });

//    $.get('AjaxControl', {
//        roomTypeId: ele
//    }, function(responseText) {
//        alert(responseText);
//    });
}

$(window).load(function() {
    $("#listRoom").hide();
    loadRoomList();
});
