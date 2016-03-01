/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Zito
 */
@Entity
@Table(name = "Booking")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Booking.findAll", query = "SELECT b FROM Booking b"),
    @NamedQuery(name = "Booking.findByBookingId", query = "SELECT b FROM Booking b WHERE b.bookingId = :bookingId"),
    @NamedQuery(name = "Booking.findByBookingDate", query = "SELECT b FROM Booking b WHERE b.bookingDate = :bookingDate"),
    @NamedQuery(name = "Booking.findByCheckinDate", query = "SELECT b FROM Booking b WHERE b.checkinDate = :checkinDate"),
    @NamedQuery(name = "Booking.findByCheckoutDate", query = "SELECT b FROM Booking b WHERE b.checkoutDate = :checkoutDate"),
    @NamedQuery(name = "Booking.findByBookingComment", query = "SELECT b FROM Booking b WHERE b.bookingComment = :bookingComment"),
    @NamedQuery(name = "Booking.findByStatus", query = "SELECT b FROM Booking b WHERE b.status = :status")})
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "bookingId")
    private String bookingId;
    @Column(name = "bookingDate")
    @Temporal(TemporalType.DATE)
    private Date bookingDate;
    @Column(name = "checkinDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date checkinDate;
    @Column(name = "checkoutDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date checkoutDate;
    @Size(max = 300)
    @Column(name = "bookingComment")
    private String bookingComment;
    @Column(name = "status")
    private Integer status;
    @JoinTable(name = "BookingRoom", joinColumns = {
        @JoinColumn(name = "bookingId", referencedColumnName = "bookingId")}, inverseJoinColumns = {
        @JoinColumn(name = "roomNumber", referencedColumnName = "roomNumber")})
    @ManyToMany
    private List<Room> roomList;
    @OneToMany(mappedBy = "bookingId")
    private List<Payments> paymentsList;
    @JoinColumn(name = "customerId", referencedColumnName = "customerId")
    @ManyToOne
    private Customer customerId;
    @OneToMany(mappedBy = "bookingId")
    private List<BookingService> bookingServiceList;

    public Booking() {
    }

    public Booking(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Date getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(Date checkinDate) {
        this.checkinDate = checkinDate;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public String getBookingComment() {
        return bookingComment;
    }

    public void setBookingComment(String bookingComment) {
        this.bookingComment = bookingComment;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @XmlTransient
    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }

    @XmlTransient
    public List<Payments> getPaymentsList() {
        return paymentsList;
    }

    public void setPaymentsList(List<Payments> paymentsList) {
        this.paymentsList = paymentsList;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }

    @XmlTransient
    public List<BookingService> getBookingServiceList() {
        return bookingServiceList;
    }

    public void setBookingServiceList(List<BookingService> bookingServiceList) {
        this.bookingServiceList = bookingServiceList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bookingId != null ? bookingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Booking)) {
            return false;
        }
        Booking other = (Booking) object;
        if ((this.bookingId == null && other.bookingId != null) || (this.bookingId != null && !this.bookingId.equals(other.bookingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Booking[ bookingId=" + bookingId + " ]";
    }
    
}
