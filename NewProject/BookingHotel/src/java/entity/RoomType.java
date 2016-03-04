/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author mai
 */
public class RoomType {

    private int roomTypeId;
    private String roomType;
    private float currentPrice;
    private String roomTypeDesc;

    public String getDescription() {
        return roomTypeDesc;
    }

    public void setDescription(String description) {
        this.roomTypeDesc = description;
    }

    public int getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(int roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public float getPrice() {
        return currentPrice;
    }

    public void setPrice(float price) {
        this.currentPrice = price;
    }
}
