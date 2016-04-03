/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import entity.Booking;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import model.DataProcess;

/**
 *
 * @author mai
 */
@ManagedBean
@RequestScoped
public class AdminBean {

    private String username;
    private String password;

    private String txtSearch;

    public String getTxtSearch() {
        return txtSearch;
    }

    public void setTxtSearch(String txtSearch) {
        this.txtSearch = txtSearch;
    }

    private String bookingIdSelected;
    private Booking currentBooking;

    public Booking getCurrentBooking() {
        return currentBooking;
    }

    public void setCurrentBooking(Booking currentBooking) {
        this.currentBooking = currentBooking;
    }

    public String getBookingIdSelected() {
        return bookingIdSelected;
    }

    public void setBookingIdSelected(String bookingIdSelected) {
        DataProcess db = new DataProcess();

        currentBooking = db.getBookingById(bookingIdSelected);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Creates a new instance of AdminBean
     */
    public AdminBean() {
        DataProcess dp = new DataProcess();
        bookingList = dp.getAllBooking();
    }

    public String redirectToPanelIfLoggedIn() {
        String user = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        if (user != null) {
            return "success";
        }

        return "faied";
    }

    public boolean isLogin() {
        String user = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        if (user != null) {
            return true;
        }

        return false;
    }

    public String login() {
        if ("admin".equals(username) && "123456".equals(password)) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(
                    "user", username);
        }

        return "success";
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("user");
        return "success";
    }

    public String gotoDetail(String id) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(
                "SelectedId", id);
        bookingIdSelected = id;
        DataProcess dp = new DataProcess();
        currentBooking = dp.getBookingById(bookingIdSelected);

        return "detail";
    }

    public String changeBookingStatus(int status) {
        String selectedId = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("SelectedId");

        DataProcess dp = new DataProcess();
        dp.updateBookingStatus(selectedId, status);

        return "success";
    }

    public boolean isUnconfirmed() {
        String selectedId = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("SelectedId");
        DataProcess dp = new DataProcess();
        Booking bk = dp.getBookingById(selectedId);

        return (bk.getStatus() == 0);
    }

    public boolean isConfirmed() {
        String selectedId = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("SelectedId");
        DataProcess dp = new DataProcess();
        Booking bk = dp.getBookingById(selectedId);

        return (bk.getStatus() == 1);
    }

    public boolean isCompleted() {
        String selectedId = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("SelectedId");
        DataProcess dp = new DataProcess();
        Booking bk = dp.getBookingById(selectedId);

        return (bk.getStatus() == 2);
    }

    public boolean isCanceled() {
        String selectedId = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("SelectedId");
        DataProcess dp = new DataProcess();
        Booking bk = dp.getBookingById(selectedId);

        return (bk.getStatus() == 3);
    }

    public boolean isDeleted() {
        String selectedId = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("SelectedId");
        DataProcess dp = new DataProcess();
        Booking bk = dp.getBookingById(selectedId);

        return (bk.getStatus() == 4);
    }

    public String checkout() {
        return "checkout";
    }

    public String gohome() {
        return "success";
    }

    private List<Booking> bookingList;

    public List<Booking> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    public String search() {
        DataProcess dp = new DataProcess();
        if ("".equals(txtSearch)) {
            bookingList = dp.getAllBooking();
        } else {
            bookingList = new ArrayList<>();
            Booking b = dp.getBookingById(txtSearch);
            bookingList.add(b);
        }

        return "success";
    }
}
