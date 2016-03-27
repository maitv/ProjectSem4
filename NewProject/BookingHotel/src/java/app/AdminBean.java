/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author mai
 */
@ManagedBean
@RequestScoped
public class AdminBean {

    private String username;
    private String password;

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
}
