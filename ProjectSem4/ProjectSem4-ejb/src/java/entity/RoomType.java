/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Zito
 */
@Entity
@Table(name = "RoomType")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RoomType.findAll", query = "SELECT r FROM RoomType r"),
    @NamedQuery(name = "RoomType.findByRoomTypeId", query = "SELECT r FROM RoomType r WHERE r.roomTypeId = :roomTypeId"),
    @NamedQuery(name = "RoomType.findByRoomType", query = "SELECT r FROM RoomType r WHERE r.roomType = :roomType"),
    @NamedQuery(name = "RoomType.findByCurrentPrice", query = "SELECT r FROM RoomType r WHERE r.currentPrice = :currentPrice"),
    @NamedQuery(name = "RoomType.findByRoomTypeDesc", query = "SELECT r FROM RoomType r WHERE r.roomTypeDesc = :roomTypeDesc")})
public class RoomType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "roomTypeId")
    private Integer roomTypeId;
    @Size(max = 50)
    @Column(name = "roomType")
    private String roomType;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "currentPrice")
    private Double currentPrice;
    @Size(max = 50)
    @Column(name = "roomTypeDesc")
    private String roomTypeDesc;
    @OneToMany(mappedBy = "roomTypeId")
    private List<Room> roomList;

    public RoomType() {
    }

    public RoomType(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getRoomTypeDesc() {
        return roomTypeDesc;
    }

    public void setRoomTypeDesc(String roomTypeDesc) {
        this.roomTypeDesc = roomTypeDesc;
    }

    @XmlTransient
    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roomTypeId != null ? roomTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RoomType)) {
            return false;
        }
        RoomType other = (RoomType) object;
        if ((this.roomTypeId == null && other.roomTypeId != null) || (this.roomTypeId != null && !this.roomTypeId.equals(other.roomTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.RoomType[ roomTypeId=" + roomTypeId + " ]";
    }
    
}
