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
import java.util.concurrent.TimeUnit;
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
    private String customerDOB;
    private String customerAddress;
    private String customerPhone;
    private String customerEmail;
    private String ppUsername;
    private String ppPassword;
    private String diffDays;

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
            return "failed";
        }

        return "success";
    }

    public String gotoUserInputInformation() {
        return "success";
    }

    public String gotoPayment() {
        return "success";
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
                    return "failed" ;
                }
                
                cus = new Customer();
                cus.setCustomerAddress(customerAddress);
                cus.setCustomerCountry(customerCountry);
                cus.setCustomerDOB(doBDate);
                cus.setCustomerEmail(customerEmail);
                cus.setCustomerIdentityNo(customerIdentityNo);
                cus.setCustomerName(customerName);
                cus.setCustomerPhone(customerPhone);
                return "success";
            } else {
                return "failed";
            }
        } else {
            cus = dp.getCustomerByIdentityNo(returnCustomerIdentityNo);
            if (cus.getCustomerId() != null) {
                return "success";
            }
            return "failed";
        }
//        return "success";
    }

    public String gotoppCheckout() {
        if ("test".equals(ppUsername)) {
            if ("test".equals(ppPassword)) {
                setPpName("Mr.Tester");
                setPpAccount(10000000);
                return "success";
            }
        }
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

    public String gotoppConfirmation() {
        //String s="failed";
        //if ((totalPrice / 2) < ppAccount) {
        setPpOrderID("PPOrder1");
        setPpEmail("tester@test.com");
            //s="success";
        // }
        return "success";
    }

    public String gotoNotice() {
        DataProcess dp = new DataProcess();

        dp.booking(selectedRoom, checkinDate, checkoutDate, customerName, customerCountry, customerIdentityNo, cus.getCustomerDOB(), customerAddress,
                customerPhone, customerEmail, totalPrice, selectedService);

        return "success";

    }
}
