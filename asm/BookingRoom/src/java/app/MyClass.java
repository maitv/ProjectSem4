/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import entity.Booking;
import entity.Room;
import entity.RoomType;
import entity.Service;
import java.util.ArrayList;
import java.util.List;
import model.DataProcess;

/**
 *
 * @author mai
 */
public class MyClass {

    public static ArrayList<Room> getAllRoom() {
        DataProcess dp = new DataProcess();
        ArrayList<Room> list = dp.getAllRoom();

        return list;
    }

    public static ArrayList<RoomType> getAllRoomType() {
        DataProcess dp = new DataProcess();
        ArrayList<RoomType> list = dp.getAllRoomType();

        return list;
    }

    public static ArrayList<Service> getAllService() {
        DataProcess dp = new DataProcess();
        ArrayList<Service> list = dp.getAllService();

        return list;
    }

    public static String getRoomTypeById(String id) {
        DataProcess dp = new DataProcess();

        RoomType rt = dp.getRoomTypeById(Integer.parseInt(id));

        if (rt == null) {
            return "";
        }

        return rt.getRoomType();
    }

    public static String getServiceNameById(String id) {
        DataProcess dp = new DataProcess();

        Service s = dp.getServiceById(id);

        if (s == null) {
            return "";
        }

        return s.getServiceName();
    }

    public static double getTotalPrice(String[] arrayService, String roomTypeId) {
        double total = 0;
        DataProcess dp = new DataProcess();
        RoomType rt = dp.getRoomTypeById(Integer.parseInt(roomTypeId));

        total = rt.getPrice();

        if (arrayService != null) {
            for (int i = 0; i < arrayService.length; i++) {
                Service s = dp.getServiceById(arrayService[i]);

                total = total + s.getServicePrice();
            }
        }

        return total;
    }

    public static ArrayList<Booking> getAllBooking() {
        DataProcess dp = new DataProcess();
        ArrayList<Booking> list = dp.getAllBooking();

        return list;
    }

    public static String getCustomerNameByIdCard(String cardId) {
        DataProcess dp = new DataProcess();
        String name = dp.getCustomerNameByIdCard(cardId);

        return name;
    }
    
    public static boolean updateBookingStatus(String bookingId, int status) {
        DataProcess dp = new DataProcess();
        dp.updateBookingStatus(bookingId, status);

        System.out.println("3333333333");

        return true;
    }

    public static ArrayList<Room> getAllRoomByRoomTypeId(String roomTypeId) {
        DataProcess dp = new DataProcess();
        ArrayList<Room> list = null;
        try
        {
             list= dp.getAllRoomByRoomTypeId(Integer.parseInt(roomTypeId));    
        }
        catch(NumberFormatException e)
        {
            return null;
        }
        
        return list;
    }
}
