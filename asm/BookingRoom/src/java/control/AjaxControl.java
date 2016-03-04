/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import entity.Room;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.DataProcess;

/**
 *
 * @author mai
 */
public class AjaxControl extends HttpServlet {

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
        response.setContentType("application/json");

        PrintWriter out = response.getWriter();

        String roomTypeId = request.getParameter("roomTypeId");

        DataProcess dp = new DataProcess();

        String json = "{\"id\":[";
        if (roomTypeId != null && roomTypeId != "") {
            ArrayList<Room> li = dp.getAllRoomByRoomTypeId(Integer.parseInt(roomTypeId));
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < li.size() - 1; i++) {
                json = json + "{";
                Room temp = li.get(i);
                json = json + "\"number\":";
                json = json + temp.getRoomNumber();
                json = json + "}";
                json = json + ",";
            }

            json = json + "{";
            Room temp = li.get(li.size() - 1);
            json = json + "\"number\":";
            json = json + temp.getRoomNumber();
            json = json + "}";

            json = json + "]}";
            System.out.println(json);
            out.print(json);
            out.close();
        }
//        String json = "{\"name\":\"1\"}";
//        out.print(json);
//        out.close();
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
