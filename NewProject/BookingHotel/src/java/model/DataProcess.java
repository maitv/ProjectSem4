/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.Booking;
import entity.Customer;
import entity.Floor;
import entity.PayPalAccount;
import entity.Room;
import entity.RoomType;
import entity.Service;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mai
 */
public class DataProcess {

    private String user;
    private String pass;
    private String dbName;
    private String host;
    private String port;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void loadConfiguration() {
        Properties p = new Properties();
        try {
            p.load(new FileInputStream(appPath() + "/DBConfig/db.ini"));
            
            host = p.getProperty("DatabaseLocationIP");
            dbName = p.getProperty("DatabaseName");
            user = p.getProperty("Username");
            pass = p.getProperty("Password");
            port = p.getProperty("MSPort");
        } catch (IOException ex) {
            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getConnection() {
        loadConfiguration();
        Connection cnn = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            String url = "jdbc:sqlserver://" + host + ":" + port + ";databaseName=" + dbName;

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

    public Room getRoomById(String id) {
        Room r = null;
        Connection cnn = getConnection();

        String query = "SELECT * FROM Room WHERE RoomNumber = ?";
        PreparedStatement prst;
        try {
            prst = cnn.prepareStatement(query);
            prst.setString(1, id);

            ResultSet rs = prst.executeQuery();
            if (rs.next()) {
                r = new Room();
                r.setRoomNumber(id);
                r.setRoomTypeId(rs.getInt(2));
                r.setRoomDesc(rs.getString(3));
                r.setRoomStatus(rs.getInt(4));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
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
                rt.setCurrentPrice(rs.getFloat(3));
                rt.setRoomTypeDesc(rs.getString(4));
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

    public ArrayList<Floor> getRoomsAvailableByFloor(String roomTypeId, Date s, Date e) {

        ArrayList<Room> roomList = getRoomsAvailable(roomTypeId, s, e);

        ArrayList<Floor> list = null;
        Map<String, Floor> map = new TreeMap<>();

        for (Room r : roomList) {
            int roomNo = Integer.parseInt(r.getRoomNumber());
            int floorNo = roomNo / 100; // roomNo format : FloorNo + RoomNo. ex: 101, 202,..

            if (map.containsKey(String.valueOf(floorNo))) {
                // first
                Floor floor = map.get(String.valueOf(floorNo));
                List<Room> liTemp = floor.getRoomList();
                liTemp.add(r);

                floor.setRoomList(liTemp);

                // update in map
                map.remove(String.valueOf(floorNo));
                map.put(String.valueOf(floorNo), floor);
            } else {
                // Not exist
                Floor floor = new Floor();
                floor.setFloorNo(String.valueOf(floorNo));

                ArrayList<Room> li = new ArrayList<>();
                li.add(r);

                floor.setRoomList(li);

                map.put(String.valueOf(floorNo), floor);
            }
        }

        list = new ArrayList<>();
        for (String key : map.keySet()) {
            Floor f = map.get(key);

            list.add(f);
        }

        return list;
    }

    public ArrayList<Room> getRoomsAvailable(String roomTypeId, Date s, Date e) {
        ArrayList<Room> list = new ArrayList();
        Connection cnn = getConnection();
        if (cnn == null) {
            return list;
        }

        String query = "SELECT * FROM Room WHERE roomTypeId=? AND roomStatus != 1 AND roomNumber NOT IN ("
                + "SELECT BookingRoom.roomNumber FROM BookingRoom INNER JOIN Booking ON BookingRoom.bookingId=Booking.bookingId"
                + " WHERE (Booking.status=0 OR Booking.status=1 OR Booking.status=2) AND (( ? BETWEEN Booking.checkinDate AND Booking.checkoutDate ) OR ( ? BETWEEN Booking.checkinDate AND Booking.checkoutDate )))";
        try {
            PreparedStatement prst = cnn.prepareCall(query);

            prst.setString(1, roomTypeId);
            prst.setDate(2, s);
            prst.setDate(3, e);

            ResultSet rs = prst.executeQuery();

            while (rs.next()) {
                Room r = new Room();
                String roomNo = rs.getString(1);

                r.setRoomNumber(roomNo);
                r.setRoomTypeId(Integer.parseInt(roomTypeId));
                r.setRoomDesc(rs.getString(3));

                list.add(r);
            }

            rs.close();
            prst.close();
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
                String imageSmall = rs.getString(4);
                String imageLarge = rs.getString(5);
                String des = rs.getString(6);

                r.setRoomTypeId(roomNo);
                r.setRoomType(roomType);
                r.setCurrentPrice(price);
                r.setRoomTypeDesc(des);
                r.setImageSmall(imageSmall);
                r.setImageLarge(imageLarge);

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
    private String orderID;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public boolean bookingPP(
            List<Room> selectedRoom,
            String checkinDate,
            String checkoutDate,
            String customerName,
            String country,
            String identityNo,
            java.util.Date dob,
            String address,
            String phoneNumber,
            String email,
            float total,
            List<Service> services,
            String ppID,
            float ppBalance) {
        // TODO:
        // 1. Add to Customer table
        if (!isExistCustomer(identityNo)) {
            if (!addCustomer(customerName, country, identityNo, dob, address, phoneNumber, email)) {
                return false;
            }
        }

        // 2. Add to Booking table
        String bookingId = getBookingId(identityNo);
        this.setOrderID(bookingId);
        if (!addBooking(bookingId, identityNo, checkinDate, checkoutDate)) {
            return false;
        }

        // 3. Add to Booking service table
        if (!addBookingService(bookingId, services)) {
            return false;
        }

        // 4. Add to BookingRoom table
        if (!addBookingRoom(bookingId, selectedRoom)) {
            return false;
        }

        //5. Add to Payments table
        if (!addPayment(bookingId, total, "booking")) {
            return false;
        }
        //6. Subtract from PayPalAccount table

        if (!subPP(ppID, ppBalance, total / 2)) {
            return false;
        }

        //7. Add to Receipts table
        java.util.Date date = new java.util.Date();
        return addPayPalReceipts(("Payment" + bookingId), (total / 2), date, "Paypal");
    }

    public boolean subPP(String id, float balance, float ammount) {
        Connection con = getConnection();
        String query = "UPDATE PayPalAccount SET balance=? WHERE id=?";
        try {
            PreparedStatement prst = con.prepareStatement(query);
            prst.setFloat(1, balance - ammount);
            prst.setString(2, id);
            prst.executeUpdate();
            prst.close();
        } catch (SQLException ex) {

            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public boolean booking(
            List<Room> selectedRoom,
            String checkinDate,
            String checkoutDate,
            String customerName,
            String country,
            String identityNo,
            java.util.Date dob,
            String address,
            String phoneNumber,
            String email,
            float total,
            List<Service> services) {
        // TODO:
        // 1. Add to Customer table
        if (!isExistCustomer(identityNo)) {
            if (!addCustomer(customerName, country, identityNo, dob, address, phoneNumber, email)) {
                return false;
            }
        }

        // 2. Add to Booking table
        String bookingId = getBookingId(identityNo);
        this.setOrderID(bookingId);
        if (!addBooking(bookingId, identityNo, checkinDate, checkoutDate)) {
            return false;
        }

        // 3. Add to Booking service table
        if (!addBookingService(bookingId, services)) {
            return false;
        }

        // 4. Add to BookingRoom table
        if (!addBookingRoom(bookingId, selectedRoom)) {
            return false;
        }

        //5. Add to Payments table
        if (!addPayment(bookingId, total, "booking")) {
            return false;
        }
        else return true;
    }

    public boolean addBookingService(String bookingId, List<Service> services) {
        Connection cnn = getConnection();
        if (cnn == null) {
            return false;
        }

        if (services == null) {
            return true;
        }

        String query = "INSERT INTO BookingService VALUES(?,?)";
        int size = services.size();
        for (int i = 0; i < size; i++) {
            try {
                PreparedStatement prst = cnn.prepareStatement(query);
                prst.setString(1, services.get(i).getServiceId());
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

    public boolean addBookingRoom(String bookingId, List<Room> selectedRoom) {
        Connection con = getConnection();
        if (con == null) {
            return false;
        }
        if (selectedRoom == null) {
            return false;
        }
        for (int i = 0; i < selectedRoom.size(); i++) {
            String query = "INSERT INTO BookingRoom VALUES (?,?)";
            PreparedStatement prst;
            try {
                prst = con.prepareStatement(query);
                prst.setString(1, bookingId);
                prst.setString(2, selectedRoom.get(i).getRoomNumber());
                prst.executeUpdate();
                prst.close();

            } catch (SQLException ex) {
                Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }

        }
        return true;
    }

    public boolean addBooking(String bookingId,
            String customerIdentfy,
            String checkinDate,
            String checkoutDate) {

        Connection cnn = getConnection();
        if (cnn == null) {
            return false;
        }
        String query = "INSERT INTO Booking VALUES(?,?,?,?,?,?,?)";
        PreparedStatement prst;
        try {
            prst = cnn.prepareStatement(query);
            prst.setString(1, bookingId);
            prst.setString(2, customerIdentfy);
            prst.setDate(3, new java.sql.Date(new java.util.Date().getTime()));
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            java.util.Date cinDate;
            java.util.Date coutDate;
            try {
                cinDate = sdf.parse(checkinDate);
                java.sql.Date ciDate = new java.sql.Date(cinDate.getTime());
                coutDate = sdf.parse(checkoutDate);
                java.sql.Date coDate = new java.sql.Date(coutDate.getTime());
                prst.setDate(4, ciDate);
                prst.setDate(5, coDate);
                prst.setString(6, "For the booking");
                prst.setInt(7, 0);
            } catch (ParseException ex) {
                Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
                prst.close();
                return false;
            }

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
        int second = Calendar.getInstance().get(Calendar.SECOND);

        bId = "" + year + "" + month + day + hour + minute + second + identifyCard;

        return bId;
    }

    public boolean isExistCustomer(String idCard) {
        boolean isFound = false;

        Connection cnn = getConnection();
        if (cnn == null) {
            return isFound;
        }

        String query = "SELECT * FROM Customer WHERE customerIdentityNo= ?";
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
    }/*
     public boolean addNewCustomer(Customer cus)
     {
     Connection cnn = getConnection();

     if (cnn == null) {
     return false;
     }

     String query = "INSERT INTO Customer(customerName,customerCountry,customerIdentityNo,customerDOB,customerAddress,customerPhone,customerEmail) VALUES(?,?,?,?,?,?,?)";
     try {
     PreparedStatement prst = cnn.prepareStatement(query);

     prst.setString(1, cus.getCustomerName());
     prst.setString(2, cus.getCustomerCountry());
     prst.setString(3, cus.getCustomerIdentityNo());
     prst.setDate(4, new java.sql.Date(cus.getCustomerDOB().getTime()));
     prst.setString(5, cus.getCustomerAddress());
     prst.setString(6, cus.getCustomerPhone());
     prst.setString(7, cus.getCustomerEmail());

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
     }*/


    public boolean addCustomer(String customerName,
            String country,
            String identityNo,
            java.util.Date dob,
            String address,
            String phoneNumber,
            String email) {
        Connection cnn = getConnection();

        if (cnn == null) {
            return false;
        }

        String qry = "INSERT INTO CustomerLastIndex(name) VALUES(?)";
        try {
            PreparedStatement prt = cnn.prepareStatement(qry);
            prt.setString(1, identityNo);
            prt.executeUpdate();
            prt.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
        }

        String query = "INSERT INTO Customer(customerId,customerName,customerCountry,customerIdentityNo,customerDOB,customerAddress,customerPhone,customerEmail) VALUES(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement prst = cnn.prepareStatement(query);

            prst.setString(1, identityNo);
            prst.setString(2, customerName);
            prst.setString(3, country);
            prst.setString(4, identityNo);
            prst.setDate(5, new java.sql.Date(dob.getTime()));
            prst.setString(6, address);
            prst.setString(7, phoneNumber);
            prst.setString(8, email);

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
                String roomNumber = rs.getString("roomNumber");
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

        String query = "SELECT * FROM Booking WHERE status != 4 ORDER BY status ASC";
        try {
            Statement st = cnn.createStatement();

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Booking r = new Booking();
                r.setBookingId(rs.getString("bookingId"));
                r.setCustomerId(rs.getString("customerId"));
                r.setBookingDate(rs.getDate("bookingDate"));
                r.setCheckinDate(rs.getDate("checkinDate"));
                r.setCheckoutDate(rs.getDate("checkoutDate"));
                r.setBookingComment(rs.getString("bookingComment"));
                r.setStatus(rs.getInt("status"));

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

        String query = "SELECT Customer.customerName FROM Customer WHERE customerIdentityNo = ?";
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

    public Customer getCustomerByIdentityNo(String IN) {
        Customer cus = null;
        Connection con = getConnection();
        String query = "Select * FROM Customer WHERE customerIdentityNo=?";
        try {
            PreparedStatement prst;
            prst = con.prepareStatement(query);
            prst.setString(1, IN);

            ResultSet rs = prst.executeQuery();
            if (rs.next()) {
                cus = new Customer();
                cus.setCustomerId(rs.getString("customerId"));
                cus.setCustomerName(rs.getString("customerName"));
                cus.setCustomerIdentityNo(rs.getString("customerIdentityNo"));
                cus.setCustomerCountry(rs.getString("customerCountry"));

                java.sql.Date dobSql = rs.getDate("customerDOB");
                java.util.Date utilDate = new java.util.Date(dobSql.getTime());

                cus.setCustomerDOB(utilDate);
                cus.setCustomerAddress(rs.getString("customerAddress"));
                cus.setCustomerPhone(rs.getString("customerPhone"));
                cus.setCustomerEmail(rs.getString("customerEmail"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cus;
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
        if (cnn == null) {
            return false;
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

    public boolean addPayPalReceipts(String paymentId, float amount, java.util.Date datePaid, String receiptComment) {
        String query = "INSERT INTO Receipts VALUES (?,?,?,?,?)";
        PreparedStatement prst;
        ResultSet rs = null;
        Connection con = getConnection();
        try {
            prst = con.prepareStatement(query);
            prst.setString(1, "PayPal" + paymentId);
            prst.setString(2, paymentId);
            prst.setFloat(3, amount);
            prst.setDate(4, new java.sql.Date(datePaid.getTime()));
            prst.setString(5, receiptComment);
            prst.executeUpdate();
            prst.close();

        } catch (SQLException ex) {
            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return true;
    }

    public boolean addPayment(String bookingId, float paymentAmmount, String paymentComment) {
        String pid = "Payment" + bookingId;
        String query = "INSERT INTO Payments VALUES(?,?,?,?,?)";
        PreparedStatement prst;
        ResultSet rs = null;
        Connection con = getConnection();
        try {
            prst = con.prepareStatement(query);
            prst.setString(1, pid);
            prst.setString(2, bookingId);
            prst.setFloat(3, paymentAmmount);
            prst.setFloat(4, paymentAmmount);
            prst.setString(5, paymentComment);
            prst.executeUpdate();
            prst.close();

        } catch (SQLException ex) {
            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public PayPalAccount checkLogin(String u, String p) {
        String query = "SELECT * FROM PayPalAccount WHERE username=? AND password=?";
        PreparedStatement prst;
        ResultSet rs = null;
        PayPalAccount acc = null;
        Connection con = getConnection();
        try {
            prst = con.prepareStatement(query);
            prst.setString(1, u);
            prst.setString(2, p);
            rs = prst.executeQuery();

            while (rs.next()) {
                acc = new PayPalAccount();
                acc.setId(rs.getString(1));
                acc.setUsername(rs.getString(2));
                acc.setPassword(rs.getString(3));
                acc.setFullname(rs.getString(4));
                acc.setCardnumber(rs.getString(5));
                acc.setEmail(rs.getString(6));
                acc.setBalance(rs.getFloat(7));
            }

            prst.close();

        } catch (SQLException ex) {
            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);

        }
        return acc;
    }

    public Booking getBookingById(String id) {
        String query = "SELECT * FROM Booking LEFT JOIN Customer ON Booking.customerId=Customer.customerId WHERE Booking.bookingId=? OR Booking.customerId=? OR Customer.customerName=?";
        PreparedStatement prst;
        ResultSet rs = null;
        Booking bk = null;
        Connection con = getConnection();
        try {
            prst = con.prepareStatement(query);
            prst.setString(1, id);
            prst.setString(2, id);
            prst.setString(3, id);
            rs = prst.executeQuery();

            if (rs.next()) {
                bk = new Booking();
                bk.setBookingId(rs.getString("bookingId"));
                bk.setCustomerId(rs.getString("customerId"));
                bk.setBookingDate(rs.getDate("bookingDate"));
                bk.setCheckinDate(rs.getDate("checkinDate"));
                bk.setCheckoutDate(rs.getDate("checkoutDate"));
                bk.setBookingComment(rs.getString("bookingComment"));
                bk.setStatus(rs.getInt("status"));
            }

            prst.close();

        } catch (SQLException ex) {
            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);

        }
        return bk;
    }

    public String getAutoIdentifyNumber() {
        int id = getLastId();
        String cid = "HTOX" + id;

        return cid;
    }

    public int getLastId() {
        Connection cnn = getConnection();
        String query = "SELECT * FROM CustomerLastIndex ORDER BY cid DESC";
        Statement st;
        int id = 0;
        try {
            st = cnn.createStatement();

            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                id = rs.getInt(1);

                id = id + 1;
            } else {
                // first time.
                id = 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return id;
    }

    public float getRoomPrice(String bookingId) {
        float price = 0;
        // get all room list
        List<Room> roomUsedList = getAllRoomListByBookingId(bookingId);

        return price;
    }

    public float getPriceAllRoomListByBookingId(String bookingId) {
        String query = "SELECT * FROM BookingRoom WHERE bookingId=?";
        float price = 0;
        PreparedStatement prst;
        ResultSet rs = null;
        Connection con = getConnection();
        try {
            prst = con.prepareStatement(query);
            prst.setString(1, bookingId);
            rs = prst.executeQuery();

            while (rs.next()) {
                String roomNumber = rs.getString("roomNumber");

                Room r = getRoomById(roomNumber);
                RoomType rt = getRoomTypeById(r.getRoomTypeId());
                float tmp = rt.getCurrentPrice();
                price += tmp;
            }

            prst.close();

        } catch (SQLException ex) {
            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);

        }

        return price;
    }

    public List<Room> getAllRoomListByBookingId(String bookingId) {
        String query = "SELECT * FROM BookingRoom WHERE bookingId=?";
        List<Room> li = new ArrayList<>();
        PreparedStatement prst;
        ResultSet rs = null;
        Connection con = getConnection();
        try {
            prst = con.prepareStatement(query);
            prst.setString(1, bookingId);
            rs = prst.executeQuery();

            while (rs.next()) {
                String roomNumber = rs.getString(2);
                Room r = getRoomById(roomNumber);
                li.add(r);
            }

            prst.close();

        } catch (SQLException ex) {
            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return li;
    }

    public float getPriceAllServiceListByBookingId(String bookingId) {
        String query = "SELECT * FROM BookingService WHERE bookingId=?";
        float price = 0;
        PreparedStatement prst;
        ResultSet rs = null;
        Connection con = getConnection();
        try {
            prst = con.prepareStatement(query);
            prst.setString(1, bookingId);
            rs = prst.executeQuery();

            while (rs.next()) {
                String svId = rs.getString("serviceId");

                Service sv = getServiceById(svId);
                float tmp = sv.getServicePrice();
                price += tmp;
            }

            prst.close();

        } catch (SQLException ex) {
            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);

        }

//        getRoomById
        return price;
    }

    public List<Service> getAllServiceListByBookingId(String bookingId) {
        String query = "SELECT * FROM BookingService WHERE bookingId=?";
        List<Service> li = new ArrayList<>();
        PreparedStatement prst;
        ResultSet rs = null;
        Connection con = getConnection();
        try {
            prst = con.prepareStatement(query);
            prst.setString(1, bookingId);
            rs = prst.executeQuery();

            while (rs.next()) {
                String svId = rs.getString("serviceId");
                Service sv = getServiceById(svId);
                li.add(sv);
            }

            prst.close();

        } catch (SQLException ex) {
            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return li;
    }

    public static String appPath() {
        try {
            String path = DataProcess.class.getResource("").getPath();
            String fullPath = URLDecoder.decode(path, "UTF-8");
            String pathArr[] = fullPath.split("/WEB-INF/classes");
            fullPath = pathArr[0];
            String reponsePath = new File(fullPath).getPath() + File.separatorChar;
            return reponsePath;
        } catch (UnsupportedEncodingException ex) {
            return "";
        }
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
//        Calendar cal = Calendar.getInstance();
//        Date s = new Date(cal.getTime().getTime());
//        Date e = new Date(cal.getTime().getTime());
//
//        System.out.println(s + " " + e);
//        List<Room> list = new DataProcess().getRoomsAvailable("1", s, e);
//
//        for (Room r : list) {
//            System.out.println(r.getRoomNumber());
//        }
//
//        System.out.println("\n\n");
//
//        ArrayList<Booking> li = new DataProcess().getAllBooking();
//        for (Booking r : li) {
//            System.out.println(r.getCheckinDate());
//            System.out.println(r.getCheckoutDate());
//        }
//        String test = "HTX1";
//        int a = Integer.valueOf(test);
//
//        System.out.println(" test : " + a);
        Properties p = new Properties();
        try {
            p.load(new FileInputStream(appPath() + "/DBConfig/db.ini"));
            System.out.println("DatabaseName = " + p.getProperty("DatabaseName"));
            System.out.println("UserName = " + p.getProperty("Username"));
            System.out.println("Username = " + p.getProperty("Username"));
        } catch (IOException ex) {
            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
