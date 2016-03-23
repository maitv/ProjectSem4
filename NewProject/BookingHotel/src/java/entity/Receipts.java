/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author mai
 */
public class Receipts implements Serializable {
    private String receiptNo;
    private String paymentId;
    private float amount;
    private Date datePaid;
    private String receiptComment;

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getDatePaid() {
        return datePaid;
    }

    public void setDatePaid(Date datePaid) {
        this.datePaid = datePaid;
    }

    public String getReceiptComment() {
        return receiptComment;
    }

    public void setReceiptComment(String receiptComment) {
        this.receiptComment = receiptComment;
    }
    
    
}
