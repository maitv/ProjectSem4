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
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import model.DataProcess;
import org.primefaces.context.RequestContext;

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
    private String customerDOB;
    private String customerAddress;
    private String customerPhone;
    private String customerEmail;
    private String ppUsername;
    private String ppPassword;
    private String ppID;
    private String diffDays;
    private String uuid;

    private boolean isRoomAvailable;
    private boolean isSearchClick;

    public boolean isIsRoomAvailable() {
        return isRoomAvailable;
    }

    public void setIsRoomAvailable(boolean isRoomAvailable) {
        this.isRoomAvailable = isRoomAvailable;
    }

    public boolean isIsSearchClick() {
        return isSearchClick;
    }

    public void setIsSearchClick(boolean isSearchClick) {
        this.isSearchClick = isSearchClick;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDiffDays() {
        return diffDays;
    }

    public void setDiffDays(String diffDays) {
        this.diffDays = diffDays;
    }

    private String[] selectedRoomItems;

    public String getPpUsername() {
        return ppUsername;
    }

    public void setPpUsername(String ppUsername) {
        this.ppUsername = ppUsername;
    }

    public String getPpPassword() {
        return ppPassword;
    }

    public void setPpPassword(String ppPassword) {
        this.ppPassword = ppPassword;
    }

    public Customer getCus() {
        return cus;
    }

    public String[] getSelectedRoomItems() {
        return selectedRoomItems;
    }

    public void setSelectedRoomItems(String[] selectedRoomItems) {
        this.selectedRoomItems = selectedRoomItems;
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

    public String getCustomerDOB() {
        return customerDOB;
    }

    public void setCustomerDOB(String customerDOB) {
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

    private List<Booking> bookingList;

    public List<Booking> getAllBooking() {
        DataProcess dp = new DataProcess();
        bookingList = dp.getAllBooking();
        return bookingList;
    }

    private List<Booking> selectedBooking;

    public List<Booking> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    public List<Booking> getSelectedBooking() {
        return selectedBooking;
    }

    public void setSelectedBooking(List<Booking> selectedBooking) {
        this.selectedBooking = selectedBooking;
    }

    private List<Service> selectedService;

    public List<Service> getSelectedService() {
        return selectedService;
    }

    public void setSelectedService(List<Service> selectedService) {
        this.selectedService = selectedService;
    }

    private float totalPrice;
    private float minPaid;

    public float getMinPaid() {
        return minPaid;
    }

    public void setMinPaid(float minPaid) {
        this.minPaid = minPaid;
    }

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
        totalPrice = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date cinDate;
        java.util.Date coutDate;
        try {
            cinDate = sdf.parse(checkinDate);
            java.sql.Date ciDate = new java.sql.Date(cinDate.getTime());
            coutDate = sdf.parse(checkoutDate);
            java.sql.Date coDate = new java.sql.Date(coutDate.getTime());
            long diff = coutDate.getTime() - ciDate.getTime();

            if (!selectedRoom.isEmpty()) {
                for (int i = 0; i < selectedRoom.size(); i++) {
                    totalPrice += getPrice(String.valueOf(selectedRoom.get(i).getRoomTypeId())) * TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                }
            }
            if (!selectedService.isEmpty()) {
                for (int i = 0; i < selectedService.size(); i++) {
                    totalPrice += selectedService.get(i).getServicePrice();
                }
            }
            setTotalPrice(totalPrice);
            if (totalPrice != 0) {
                setMinPaid(totalPrice / 2);
            }

        } catch (ParseException ex) {
            Logger.getLogger(BookingRoomBean.class.getName()).log(Level.SEVERE, null, ex);
        }

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
        isRoomAvailable = false;
        isSearchClick = false;
    }

    public String gotoSearchRoom() {
//        RequestCOn
        return "success";
    }

    public String SelectService() {
        DataProcess dp = new DataProcess();

        if (selectedRoomItems == null) {
            return "failed";
        }

        ArrayList<Room> list = new ArrayList<>();
        for (String s : selectedRoomItems) {
            Room r = dp.getRoomById(s);
            list.add(r);
        }

        setSelectedRoom(list);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date cinDate;
        java.util.Date coutDate;

        try {
            cinDate = sdf.parse(checkinDate);
            java.sql.Date ciDate = new java.sql.Date(cinDate.getTime());
            coutDate = sdf.parse(checkoutDate);
            java.sql.Date coDate = new java.sql.Date(coutDate.getTime());
            long diff = coutDate.getTime() - ciDate.getTime();
            diffDays = String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
        } catch (ParseException ex) {
            Logger.getLogger(BookingRoomBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "success";
    }

    public String SelectService(String id) {
        DataProcess dp = new DataProcess();
        System.out.println(id);
        Room r = dp.getRoomById(id);
        System.out.println(r.getRoomNumber());
        ArrayList<Room> list = new ArrayList<>();
        list.add(r);
        setSelectedRoom(list);

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

        List<Room> li = null;
        DataProcess dp = new DataProcess();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date cinDate;
        java.util.Date coutDate;

        try {
            cinDate = sdf.parse(checkinDate);
            java.sql.Date ciDate = new java.sql.Date(cinDate.getTime());

            coutDate = sdf.parse(checkoutDate);
            java.sql.Date coDate = new java.sql.Date(coutDate.getTime());
            li = dp.getRoomsAvailable(selectedRoomTypeId, ciDate, coDate);
        } catch (ParseException ex) {
            Logger.getLogger(BookingRoomBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return li;
    }

    public String gotoSelectRoom() {
        if (checkinDate == null || "".equals(checkinDate)
                || checkoutDate == null || "".equals(checkoutDate)) {
            return "failed";
        }

        isSearchClick = true;

        List<Room> li = null;
        DataProcess dp = new DataProcess();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date cinDate;
        java.util.Date coutDate;

        try {
            cinDate = sdf.parse(checkinDate);
            java.sql.Date ciDate = new java.sql.Date(cinDate.getTime());

            coutDate = sdf.parse(checkoutDate);
            java.sql.Date coDate = new java.sql.Date(coutDate.getTime());
            li = dp.getRoomsAvailable(selectedRoomTypeId, ciDate, coDate);
        } catch (ParseException ex) {
            Logger.getLogger(BookingRoomBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (li == null || li.size() <= 0) {
            isRoomAvailable = false;
            return "failed";
        }

        isRoomAvailable = true;
        return "success";
    }

    public String gotoUserInputInformation() {
        // set id
        DataProcess dp = new DataProcess();
        String newId = dp.getAutoIdentifyNumber();

        customerIdentityNo = newId;

        return "success";
    }

    public String paymentMethod;

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String gotoPayment() {
        if ("PayPal".equals(paymentMethod)) {
            return "success";
        } else {
            gotoNotice();
            return "failed";
        }
    }

    public String gotoUserConfirmation() {
        DataProcess dp = new DataProcess();
        if (returnCustomerIdentityNo == null || "".equals(returnCustomerIdentityNo)) {
            cus = dp.getCustomerByIdentityNo(returnCustomerIdentityNo);
            if (cus == null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                java.util.Date doBDate = null;
                try {
                    doBDate = sdf.parse(customerDOB);
                } catch (ParseException ex) {
                    Logger.getLogger(BookingRoomBean.class.getName()).log(Level.SEVERE, null, ex);
                    return "failed";
                }

                cus = new Customer();
                cus.setCustomerAddress(customerAddress);
                cus.setCustomerCountry(customerCountry);
                cus.setCustomerDOB(doBDate);
                cus.setCustomerEmail(customerEmail);
                cus.setCustomerIdentityNo(customerIdentityNo);
                cus.setCustomerName(customerName);
                cus.setCustomerPhone(customerPhone);
                setPaymentMethod("PayPal");
                return "success";
            } else {
                return "failed";
            }
        } else {
            cus = dp.getCustomerByIdentityNo(returnCustomerIdentityNo);

            if (cus.getCustomerId() != null) {
                setPaymentMethod("PayPal");
                this.customerAddress = cus.getCustomerAddress();
                this.customerCountry = cus.getCustomerCountry();
                this.customerDOB = cus.getCustomerDOB().toString();
                this.customerEmail = cus.getCustomerEmail();
                this.customerIdentityNo = cus.getCustomerIdentityNo();
                this.customerName = cus.getCustomerName();
                this.customerPhone = cus.getCustomerPhone();
                return "success";
            }
            return "failed";
        }
//        return "success";
    }

    private String ppCardNumber;

    public String getPpCardNumber() {
        return ppCardNumber;
    }

    public void setPpCardNumber(String ppCardNumber) {
        this.ppCardNumber = ppCardNumber;
    }

    public String getPpID() {
        return ppID;
    }

    public void setPpID(String ppID) {
        this.ppID = ppID;
    }

    public String gotoppCheckout() {
        /* RequestContext context = RequestContext.getCurrentInstance();
         FacesMessage message = null;*/
        DataProcess dp = new DataProcess();
        if (dp.checkLogin(ppUsername, ppPassword) != null) {
            PayPalAccount acc = dp.checkLogin(ppUsername, ppPassword);
            this.setPpName(acc.getFullname());
            this.setPpEmail(acc.getEmail());
            this.setPpAccount(acc.getBalance());
            this.setPpCardNumber(acc.getCardnumber());
            this.setPpID(acc.getId());
            return "success";
        } else {
            //message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Invalid credentials");
        }
        //FacesContext.getCurrentInstance().addMessage(null, message);
        return "failed";
    }
    public String ppName;

    public String getPpName() {
        return ppName;
    }

    public void setPpName(String ppName) {
        this.ppName = ppName;
    }
    public float ppAccount;

    public float getPpAccount() {
        return ppAccount;
    }

    public void setPpAccount(float ppAccount) {
        this.ppAccount = ppAccount;
    }
    public String ppOrderID;

    public String getPpOrderID() {
        return ppOrderID;
    }

    public void setPpOrderID(String ppOrderID) {
        this.ppOrderID = ppOrderID;
    }

    public String ppEmail;

    public String getPpEmail() {
        return ppEmail;
    }

    public void setPpEmail(String ppEmail) {
        this.ppEmail = ppEmail;
    }

    public String logout() {
        return "logout";
    }

    public String gotoppConfirmation() {

        if ((totalPrice / 2) > ppAccount) {
            return "failed";
        }
        this.setUuid(UUID.randomUUID().toString().replaceAll("-", ""));
        return "success";
    }

    public String gotoNotice() {
        DataProcess dp = new DataProcess();

        String cusId = "";

        if (returnCustomerIdentityNo == null || "".equals(returnCustomerIdentityNo)) {
            cusId = customerIdentityNo;
        }else{
            cusId = returnCustomerIdentityNo;
        }

        dp.booking(selectedRoom, checkinDate, checkoutDate, customerName, customerCountry, cusId, cus.getCustomerDOB(), customerAddress,
                customerPhone, customerEmail, totalPrice, selectedService);
        setPpOrderID(dp.getOrderID());
        return "success";

    }

    public boolean isShowRoomAvailableMessage() {
        return ((!isRoomAvailable) && isSearchClick);
    }

    public String getDateInFormat(java.sql.Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String str = sdf.format(d);

        return str;
    }
    
    public String backToHomePage(){
        
        checkinDate = null;
        checkoutDate = null;
        
        selectedRoom = null ;
        
        return "success";
    }
}
