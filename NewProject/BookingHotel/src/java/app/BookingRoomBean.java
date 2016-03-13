/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import entity.*;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import model.DataProcess;

/**
 *
 * @author mai
 */
@ManagedBean
@SessionScoped
public class BookingRoomBean implements Serializable {

    private String checkinDate;
    private String checkoutDate;
    private Customer cus;
    private String customerName;
    private String customerCountry;
    private String customerIdentityNo;
    private java.util.Date customerDOB;
    private String customerAddress;
    private String customerPhone;
    private String customerEmail;

    public Customer getCus() {
        return cus;
    }

    public void setCus(Customer cus) {
        this.cus = cus;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerCountry() {
        return customerCountry;
    }

    public void setCustomerCountry(String customerCountry) {
        this.customerCountry = customerCountry;
    }

    public String getCustomerIdentityNo() {
        return customerIdentityNo;
    }

    public void setCustomerIdentityNo(String customerIdentityNo) {
        this.customerIdentityNo = customerIdentityNo;
    }

    public java.util.Date getCustomerDOB() {
        return customerDOB;
    }

    public void setCustomerDOB(java.util.Date customerDOB) {
        this.customerDOB = customerDOB;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    // roomtype list
    private List<RoomType> roomTypeList;

    private List<Room> selectedRoom;
    private String returnCustomerIdentityNo;

    public String getReturnCustomerIdentityNo() {
        return returnCustomerIdentityNo;
    }

    public void setReturnCustomerIdentityNo(String returnCustomerIdentityNo) {
        this.returnCustomerIdentityNo = returnCustomerIdentityNo;
    }

    public Float getPrice(String selectedRoomTypeId) {
        DataProcess dp = new DataProcess();
        RoomType r = dp.getRoomTypeById(Integer.parseInt(selectedRoomTypeId));
        return r.getCurrentPrice();
    }
    private List<Service> serviceList;

    public List<Service> getServiceList() {
        DataProcess dp = new DataProcess();
        serviceList = dp.getAllService();
        return serviceList;
    }

    public void setServiceList(List<Service> serviceList) {

        this.serviceList = serviceList;
    }

    private List<Service> selectedService;

    public List<Service> getSelectedService() {
        return selectedService;
    }

    public void setSelectedService(List<Service> selectedService) {
        this.selectedService = selectedService;
    }

    private float totalPrice;

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void getTotalServicePrice() {
        totalPrice = this.getTotalPrice();

    }

    public void test() {
        totalPrice=0;
        if (!selectedRoom.isEmpty()) {
            for (int i = 0; i < selectedRoom.size(); i++) {
                totalPrice += getPrice(String.valueOf(selectedRoom.get(i).getRoomTypeId()));
            }
        }
        if (!selectedService.isEmpty()) {
            for (int i = 0; i < selectedService.size(); i++) {
                totalPrice += selectedService.get(i).getServicePrice();
            }
        }
        setTotalPrice(totalPrice);
    }

    public List<Room> getSelectedRoom() {
        return selectedRoom;
    }

    public void setSelectedRoom(List<Room> selectedRoom) {
        this.selectedRoom = selectedRoom;
    }

    private String selectedRoomTypeId;

    public String getSelectedRoomTypeId() {
        return selectedRoomTypeId;
    }

    public void setSelectedRoomTypeId(String selectedRoomTypeId) {
        this.selectedRoomTypeId = selectedRoomTypeId;
    }

    public List<RoomType> getRoomTypeList() {
        DataProcess dp = new DataProcess();
        roomTypeList = dp.getAllRoomType();
        selectedRoomTypeId = roomTypeList.get(0).getRoomTypeId() + "";

        return roomTypeList;
    }

    public void setRoomTypeList(List<RoomType> roomTypeList) {
        this.roomTypeList = roomTypeList;
    }

    public String getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(String checkinDate) {
        this.checkinDate = checkinDate;
    }

    public String getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    /**
     * Creates a new instance of BookingRoomBean
     */
    public BookingRoomBean() {
    }

    public String gotoSearchRoom() {
//        RequestCOn
        return "success";
    }

    public String SelectService(String id) {
        selectedRoomTypeId = id;
        return "success";
    }

    public List<Floor> getRoomsAvailableByFloor() {
        if (checkinDate == null || "".equals(checkinDate)
                || checkoutDate == null || "".equals(checkoutDate)) {
            return null;
        }

        List<Floor> li = null;
        DataProcess dp = new DataProcess();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date cinDate;
        java.util.Date coutDate;

        try {
            cinDate = sdf.parse(checkinDate);
            java.sql.Date ciDate = new java.sql.Date(cinDate.getTime());

            coutDate = sdf.parse(checkoutDate);
            java.sql.Date coDate = new java.sql.Date(coutDate.getTime());
            li = dp.getRoomsAvailableByFloor(selectedRoomTypeId, ciDate, coDate);
        } catch (ParseException ex) {
            Logger.getLogger(BookingRoomBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return li;
    }

    public List<Room> getRoomAvailable() {
        if (checkinDate == null || "".equals(checkinDate)
                || checkoutDate == null || "".equals(checkoutDate)) {
            return null;
        }

        List<Floor> li = null;
        DataProcess dp = new DataProcess();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date cinDate;
        java.util.Date coutDate;

        try {
            cinDate = sdf.parse(checkinDate);
            java.sql.Date ciDate = new java.sql.Date(cinDate.getTime());

            coutDate = sdf.parse(checkoutDate);
            java.sql.Date coDate = new java.sql.Date(coutDate.getTime());
            li = dp.getRoomsAvailableByFloor(selectedRoomTypeId, ciDate, coDate);
        } catch (ParseException ex) {
            Logger.getLogger(BookingRoomBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return li;
    }

    public String gotoSelectRoom() {
        return "success";
    }

    public String gotoUserInputInformation() {
        return "success";
    }

    public String gotoUserConfirmation() {
        cus = new Customer();
        cus.setCustomerAddress(customerAddress);
        cus.setCustomerCountry(customerCountry);
        cus.setCustomerDOB(customerDOB);
        cus.setCustomerEmail(customerEmail);
        cus.setCustomerIdentityNo(customerIdentityNo);
        cus.setCustomerName(customerName);
        cus.setCustomerPhone(customerPhone);

        /*
        DataProcess dp=new DataProcess();
        dp.addCustomer(customerName,customerCountry,customerIdentityNo,customerDOB,customerAddress,customerPhone, customerEmail); */
        return "success";
    }

}
