/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import entity.RoomType;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import model.DataProcess;

/**
 *
 * @author mai
 */
@ManagedBean
@RequestScoped
public class BookingRoomBean {

    private String checkinDate;
    private String checkoutDate;

    // roomtype list
    private List<RoomType> roomTypeList;
    
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

    public String gotoSelectRoom() {
//        RequestCOn
        return "success";
    }

    public String SelectService( String id ) {
        selectedRoomTypeId = id;
        return "success";
    }
}
