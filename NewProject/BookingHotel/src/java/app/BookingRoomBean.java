/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author mai
 */
@ManagedBean
@RequestScoped
public class BookingRoomBean {

    private String checkinDate;
    private String checkoutDate;

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

    public String gotoSelectRoom() {
//        RequestCOn
        return "success";
    }
    
    public String SelectService() {
//        RequestCOn
        return "success";
    }
}
