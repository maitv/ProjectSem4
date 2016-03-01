/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Zito
 */
@Entity
@Table(name = "Receipts")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Receipts.findAll", query = "SELECT r FROM Receipts r"),
    @NamedQuery(name = "Receipts.findByReceiptNo", query = "SELECT r FROM Receipts r WHERE r.receiptNo = :receiptNo"),
    @NamedQuery(name = "Receipts.findByAmount", query = "SELECT r FROM Receipts r WHERE r.amount = :amount"),
    @NamedQuery(name = "Receipts.findByDatePaid", query = "SELECT r FROM Receipts r WHERE r.datePaid = :datePaid"),
    @NamedQuery(name = "Receipts.findByReceiptComment", query = "SELECT r FROM Receipts r WHERE r.receiptComment = :receiptComment")})
public class Receipts implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "receiptNo")
    private String receiptNo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amount")
    private Double amount;
    @Column(name = "datePaid")
    @Temporal(TemporalType.DATE)
    private Date datePaid;
    @Size(max = 300)
    @Column(name = "receiptComment")
    private String receiptComment;
    @JoinColumn(name = "paymentId", referencedColumnName = "paymentId")
    @ManyToOne
    private Payments paymentId;

    public Receipts() {
    }

    public Receipts(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
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

    public Payments getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Payments paymentId) {
        this.paymentId = paymentId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (receiptNo != null ? receiptNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Receipts)) {
            return false;
        }
        Receipts other = (Receipts) object;
        if ((this.receiptNo == null && other.receiptNo != null) || (this.receiptNo != null && !this.receiptNo.equals(other.receiptNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Receipts[ receiptNo=" + receiptNo + " ]";
    }
    
}
