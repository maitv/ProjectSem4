/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import entity.Room;
import entity.RoomType;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        return "success";
    }

}
