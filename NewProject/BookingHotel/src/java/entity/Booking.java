/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import model.DataProcess;

/**
 *
 * @author mai
 */
public class Booking implements Serializable {

    private String bookingId;
    private String customerId;
    private Date bookingDate;
    private Date checkinDate;
    private Date checkoutDate;
    private String bookingComment;
    private int status;

    public String getBookingComment() {
        return bookingComment;
    }

    public void setBookingComment(String bookingComment) {
        this.bookingComment = bookingComment;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public Date getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(Date checkinDate) {
        this.checkinDate = checkinDate;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStringStatus() {
        String str = "";
        switch (status) {
            case 0:
                str = "Unconfirmed";
                break;
            case 1:
                str = "Confirmed";
                break;
            case 2:
                str = "Completed";
                break;
            case 3:
                str = "Canceled";
                break;
            case 4:
                str = "Deleted";
                break;
            default:
                str = "Deleted";
                break;
        }

        return str;
    }

    public boolean isUnconfirmed() {
        return (status == 0);
    }

    public boolean isConfirmed() {
        return (status == 1);
    }

    public boolean isCompleted() {
        return (status == 2);
    }

    public boolean isCanceled() {
        return (status == 3);
    }

    public boolean isDeleted() {
        return (status == 4);
    }

    public int getDiffDays() {
        long diff = checkoutDate.getTime() - checkinDate.getTime();
        int diffDays = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

        return diffDays;
    }

    public List<Room> getListRoomUsed() {
        DataProcess dp = new DataProcess();

        List<Room> li = dp.getAllRoomListByBookingId(bookingId);

        return li;
    }

    public List<Service> getListServiceUsed() {
        DataProcess dp = new DataProcess();

        List<Service> li = dp.getAllServiceListByBookingId(bookingId);

        return li;
    }

    public float getTotalRoomPrice() {
        DataProcess dp = new DataProcess();
        float price = dp.getPriceAllRoomListByBookingId(bookingId);

        return price;
    }

    public float getTotalServicePrice() {
        DataProcess dp = new DataProcess();
        float price = dp.getPriceAllServiceListByBookingId(bookingId);

        return price;
    }

    public float getTotalPrice() {
        float price = getTotalRoomPrice() + getTotalServicePrice();
        return price;
    }

}
