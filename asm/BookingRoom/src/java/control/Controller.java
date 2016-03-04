/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.DataProcess;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 *
 * @author mai
 */
public class Controller extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("SelectRoomAndService".equals(action)) {
            String checkinDate = request.getParameter("checkinDate");
            String checkoutDate = request.getParameter("checkoutDate");

            HttpSession session = request.getSession();
            session.setAttribute("checkoutDate", checkoutDate);
            session.setAttribute("checkinDate", checkinDate);

            // TODO:
            // Get list roomtype which has been available          
            response.sendRedirect("SelectRoomAndService.jsp");
        }

        if ("Confirmation".equals(action)) {
            // TODO:
            // Save the list service and roomType which has been selected
            String[] arrayService = request.getParameterValues("services");

            String roomTypeId = request.getParameter("roomType");

            String checkinDate = request.getParameter("checkinDate");
            String checkoutDate = request.getParameter("checkoutDate");

            String roomType = request.getParameter("roomType");

            String roomNumber = request.getParameter("roomNumber");
            if ( roomNumber == null ||
                    "".equals(roomNumber)){
                response.sendRedirect("SelectRoomAndService.jsp");
                return ;
            }

            HttpSession session = request.getSession();

            session.setAttribute("roomTypeId", roomTypeId);

            session.setAttribute("checkoutDate", checkoutDate);

            session.setAttribute("checkinDate", checkinDate);

            session.setAttribute("services", arrayService);

            session.setAttribute("roomType", roomType);

            session.setAttribute("roomNumber", roomNumber);

            response.sendRedirect("ConfirmationPage.jsp");
        }

        if ("Booking".equals(action)) {
            // Save to session
            // Add to database

            String fullName = request.getParameter("txtName");
            String address = request.getParameter("txtAddress");
            String phone = request.getParameter("txtPhoneNumber");
            String email = request.getParameter("txtEmail");
            String idCard = request.getParameter("txtIdentity");

            String nameInCardBilling = request.getParameter("txtNameCard");
            String cardNumber = request.getParameter("txtCardNumber");

            HttpSession session = request.getSession();
            String checkinDate = (String) session.getAttribute("checkinDate");
            String checkoutDate = (String) session.getAttribute("checkoutDate");
            String roomNumber = (String) session.getAttribute("roomNumber");

            String[] arrService = (String[]) session.getAttribute("services");

            // add to db
            DataProcess dp = new DataProcess();
            dp.booking(Integer.parseInt(roomNumber), checkinDate, checkoutDate, fullName, idCard, address, phone, email, arrService);

            response.sendRedirect("ConfirmationInfo.jsp");
        }

        if ("ChangeStatus".equals(action)) {
            String status = request.getParameter("status");
            int iStatus = Integer.parseInt(status);

            String bookingId = request.getParameter("bookingId");
            DataProcess dp = new DataProcess();
            dp.updateBookingStatus(bookingId, iStatus);

            response.sendRedirect("AdminPage.jsp");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
