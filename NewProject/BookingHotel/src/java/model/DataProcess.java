/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.Booking;
import entity.Room;
import entity.RoomType;
import entity.Service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mai
 */
public class DataProcess {

    public Connection getConnection() {
        Connection cnn = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            String user = "sa";
            String pass = "123456";
            String databaseName = "BookingRoom";
            String url = "jdbc:sqlserver://127.0.0.1:1433;databaseName=" + databaseName;

            try {
                cnn = DriverManager.getConnection(url, user, pass);
            } catch (SQLException ex) {
                Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return cnn;
    }

    public RoomType getRoomTypeById(int roomTypeId) {
        RoomType rt = null;
        Connection cnn = getConnection();

        String query = "SELECT * FROM RoomType WHERE roomTypeId = ?";
        PreparedStatement prst;
        try {
            prst = cnn.prepareStatement(query);
            prst.setInt(1, roomTypeId);

            ResultSet rs = prst.executeQuery();
            if (rs.next()) {
                rt = new RoomType();
                rt.setRoomTypeId(roomTypeId);
                rt.setRoomType(rs.getString(2));
                rt.setPrice(rs.getFloat(3));
                rt.setDescription(rs.getString(4));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rt;
    }

    public Service getServiceById(String id) {
        Service s = null;
        Connection cnn = getConnection();

        String query = "SELECT * FROM Service WHERE serviceId = ?";
        PreparedStatement prst;
        try {
            prst = cnn.prepareStatement(query);
            prst.setString(1, id);

            ResultSet rs = prst.executeQuery();
            if (rs.next()) {
                s = new Service();
                s.setServiceId(id);
                s.setServiceName(rs.getString(2));
                s.setServicePrice(rs.getFloat(3));
                s.setServiceDesc(rs.getString(4));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }

    public ArrayList<Room> getAllRoom() {
        ArrayList<Room> list = new ArrayList();
        Connection cnn = getConnection();
        if (cnn == null) {
            return list;
        }

        String query = "SELECT * FROM Room";
        try {
            Statement st = cnn.createStatement();

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Room r = new Room();
                String roomNo = rs.getString(1);
                int roomTypeId = rs.getInt(2);

                r.setRoomNumber(roomNo);
                r.setRoomTypeId(roomTypeId);
                r.setRoomDesc(rs.getString(3));

                list.add(r);
            }

            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public ArrayList<RoomType> getAllRoomType() {
        ArrayList<RoomType> list = new ArrayList();
        Connection cnn = getConnection();
        if (cnn == null) {
            return list;
        }

        String query = "SELECT * FROM RoomType";
        try {
            Statement st = cnn.createStatement();

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                RoomType r = new RoomType();
                int roomNo = rs.getInt(1);
                String roomType = rs.getString(2);
                float price = rs.getFloat(3);
                String des = rs.getString(4);

                r.setRoomTypeId(roomNo);
                r.setRoomType(roomType);
                r.setPrice(price);
                r.setDescription(des);

                list.add(r);
            }

            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public ArrayList<Service> getAllService() {
        ArrayList<Service> list = new ArrayList();
        Connection cnn = getConnection();
        if (cnn == null) {
            return list;
        }

        String query = "SELECT * FROM Service";
        try {
            Statement st = cnn.createStatement();

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Service r = new Service();
                String sId = rs.getString(1);
                String sName = rs.getString(2);
                float price = rs.getFloat(3);
                String des = rs.getString(4);

                r.setServiceId(sId);
                r.setServiceName(sName);
                r.setServicePrice(price);
                r.setServiceDesc(des);

                list.add(r);
            }

            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public boolean booking(int roomNumber,String checkinDate,String checkoutDate,String customerName,String identifyCard,String address,String phoneNumber,String email,String[] services) {
        // TODO:
        // 1. Add to Customer table
        if (!isExistCustomer(identifyCard)) {
            if (!addCustomer(customerName, identifyCard, address, phoneNumber, email)) {
                return false;
            }
        }

        // 2. Add to Booking table
        String bookingId = getBookingId(identifyCard);
        if (!addBooking(bookingId, roomNumber, identifyCard, checkinDate, checkoutDate)) {
            return false;
        }

        // 3. Add to Booking service table
        return addBookingService(bookingId, services);
    }

    public boolean addBookingService(String bookingId, String[] services) {
        Connection cnn = getConnection();
        if (cnn == null) {
            return false;
        }

        if (services == null) {
            return true;
        }

        String query = "INSERT INTO BookingService VALUES(?,?)";
        int size = services.length;
        for (int i = 0; i < size; i++) {
            try {
                PreparedStatement prst = cnn.prepareStatement(query);
                prst.setString(1, services[i]);
                prst.setString(2, bookingId);

                int cnt = prst.executeUpdate();
                if (cnt == 0) {
                    return false;
                }
                prst.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }

    public boolean addBooking(String bookingId,int roomNumber,String customerIdentfy,String checkinDate,String checkoutDate) {
        Connection cnn = getConnection();
        if (cnn == null) {
            return false;
        }

        String query = "INSERT INTO Booking VALUES(?,?,?,?,?,?)";
        PreparedStatement prst;
        try {
            prst = cnn.prepareStatement(query);
            prst.setString(1, bookingId);
            prst.setInt(2, roomNumber);
            prst.setString(3, customerIdentfy);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date cinDate;
            java.util.Date coutDate;
            try {
                cinDate = sdf.parse(checkinDate);
                java.sql.Date ciDate = new java.sql.Date(cinDate.getTime());

                coutDate = sdf.parse(checkoutDate);
                java.sql.Date coDate = new java.sql.Date(coutDate.getTime());

                prst.setDate(4, ciDate);
                prst.setDate(5, coDate);
                prst.setInt(6, 0);
            } catch (ParseException ex) {
                Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
                prst.close();
                return false;
            }
            int status = 0;

            int cnt = prst.executeUpdate();
            if (cnt == 0) {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }

    public String getBookingId(String identifyCard) {
        String bId = "";

        //Date d = new Date();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minute = Calendar.getInstance().get(Calendar.MINUTE);

        bId = "" + year + "" + month + day + hour + minute + identifyCard;

        return bId;
    }

    public boolean isExistCustomer(String idCard) {
        boolean isFound = false;

        Connection cnn = getConnection();
        if (cnn == null) {
            return isFound;
        }

        String query = "SELECT * FROM Customer WHERE customerIndentifyCard = ?";
        PreparedStatement prst;
        try {
            prst = cnn.prepareStatement(query);
            prst.setString(1, idCard);

            ResultSet rs = prst.executeQuery();
            if (rs.next()) {
                isFound = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return isFound;
    }

    public boolean addCustomer(String customerName,String identifyCard,String address,String phoneNumber,String email) {
        Connection cnn = getConnection();

        if (cnn == null) {
            return false;
        }

        String query = "INSERT INTO Customer VALUES(?,?,?,?,?)";
        try {
            PreparedStatement prst = cnn.prepareStatement(query);

            prst.setString(1, identifyCard);
            prst.setString(2, customerName);
            prst.setString(3, address);
            prst.setString(4, phoneNumber);
            prst.setString(5, email);

            int cnt = 0;
            cnt = prst.executeUpdate();

            if (cnt == 0) {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);

            return false;
        }

        return true;
    }

    public ArrayList<Room> getAllRoomTypeAvailable() {
        ArrayList<Room> list = new ArrayList();
        Connection cnn = getConnection();
        if (cnn == null) {
            return list;
        }

        String query = "SELECT Room.roomNumber FROM Room LEFT JOIN Booking ON Room.roomNumber=Booking.roomNumber ORDER BY Room.roomNumber";
        try {
            Statement st = cnn.createStatement();

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Room r = new Room();
                String roomNumber = rs.getString(1);
                r.setRoomNumber(roomNumber);
                list.add(r);
            }

            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public ArrayList<Booking> getAllBooking() {
        ArrayList<Booking> list = new ArrayList();
        Connection cnn = getConnection();
        if (cnn == null) {
            return list;
        }

        String query = "SELECT * FROM Booking WHERE status != 99 ORDER BY status ASC";
        try {
            Statement st = cnn.createStatement();

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Booking r = new Booking();
                r.setBookingId(rs.getString(1));
//                r.setRoomNumber(rs.String(2));
//                r.setCustomerIdentify(rs.getString(3));
                r.setCheckinDate(rs.getDate(4));
                r.setCheckoutDate(rs.getDate(5));
                r.setStatus(rs.getInt(6));

                list.add(r);
            }

            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public String getCustomerNameByIdCard(String cardId) {
        String name = "";
        Connection cnn = getConnection();

        String query = "SELECT Customer.customerName FROM Customer WHERE customerIndentifyCard = ?";
        try {
            PreparedStatement prst;
            prst = cnn.prepareStatement(query);
            prst.setString(1, cardId);

            ResultSet rs = prst.executeQuery();
            if (rs.next()) {
                name = rs.getString(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return name;
    }

    public ArrayList<Room> getAllRoomByRoomTypeId(int roomTypeId) {
        ArrayList<Room> list = new ArrayList();
        Connection cnn = getConnection();
        if (cnn == null) {
            return list;
        }

        String query = "SELECT * FROM Room WHERE roomTypeId=?";
        try {
            PreparedStatement prst = cnn.prepareStatement(query);

            prst.setInt(1, roomTypeId);

            ResultSet rs = prst.executeQuery();

            while (rs.next()) {
                Room r = new Room();
//                r.setRoomNumber(rs.getInt(1));
                r.setRoomTypeId(roomTypeId);

                list.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public boolean updateBookingStatus(String bookingId, int status) {
        Connection cnn = getConnection();
        if ( cnn == null){
            return false ;
        }
        
        String query = "UPDATE Booking SET status =? WHERE bookingId=?";
        PreparedStatement prst;
        try {
            prst = cnn.prepareStatement(query);
            
            prst.setInt(1, status);
            prst.setString(2, bookingId);
            
            prst.executeUpdate();
            
            prst.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return true;
    }

    public static void main(String[] argv) {
//        List<RoomType> li = new DataProcess().getAllRoomType();
//        for (RoomType r : li) {
//            System.out.println(r.getRoomType());
//        }

//        List<Room> lis = new DataProcess().getAllRoomTypeAvailable();
//        for (Room r : lis) {
//            System.out.println(r.getRoomNumber());
//        }
//
//        String x = new DataProcess().getCustomerNameByIdCard("123");
        String x = new DataProcess().getBookingId("123");

        System.out.println(x);
    }
}
