package com.techvg.inventory.management.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A GoodsRecived.
 */
@Entity
@Table(name = "goods_recived")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GoodsRecived implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "gr_date")
    private Instant grDate;

    @Column(name = "qty_ordered")
    private Double qtyOrdered;

    @Column(name = "qty_recieved")
    private Double qtyRecieved;

    @Column(name = "manufacturing_date")
    private Instant manufacturingDate;

    @Column(name = "expiry_date")
    private Instant expiryDate;

    @Column(name = "lot_no")
    private String lotNo;

    @Column(name = "free_field_1")
    private String freeField1;

    @Column(name = "free_field_2")
    private String freeField2;

    @Column(name = "free_field_3")
    private String freeField3;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "purchaseQuotationDetails", "goodsReciveds", "securityUser", "project", "clientDetails" },
        allowSetters = true
    )
    private PurchaseQuotation purchaseQuotation;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GoodsRecived id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getGrDate() {
        return this.grDate;
    }

    public GoodsRecived grDate(Instant grDate) {
        this.setGrDate(grDate);
        return this;
    }

    public void setGrDate(Instant grDate) {
        this.grDate = grDate;
    }

    public Double getQtyOrdered() {
        return this.qtyOrdered;
    }

    public GoodsRecived qtyOrdered(Double qtyOrdered) {
        this.setQtyOrdered(qtyOrdered);
        return this;
    }

    public void setQtyOrdered(Double qtyOrdered) {
        this.qtyOrdered = qtyOrdered;
    }

    public Double getQtyRecieved() {
        return this.qtyRecieved;
    }

    public GoodsRecived qtyRecieved(Double qtyRecieved) {
        this.setQtyRecieved(qtyRecieved);
        return this;
    }

    public void setQtyRecieved(Double qtyRecieved) {
        this.qtyRecieved = qtyRecieved;
    }

    public Instant getManufacturingDate() {
        return this.manufacturingDate;
    }

    public GoodsRecived manufacturingDate(Instant manufacturingDate) {
        this.setManufacturingDate(manufacturingDate);
        return this;
    }

    public void setManufacturingDate(Instant manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public Instant getExpiryDate() {
        return this.expiryDate;
    }

    public GoodsRecived expiryDate(Instant expiryDate) {
        this.setExpiryDate(expiryDate);
        return this;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getLotNo() {
        return this.lotNo;
    }

    public GoodsRecived lotNo(String lotNo) {
        this.setLotNo(lotNo);
        return this;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public GoodsRecived freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public GoodsRecived freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public GoodsRecived freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public PurchaseQuotation getPurchaseQuotation() {
        return this.purchaseQuotation;
    }

    public void setPurchaseQuotation(PurchaseQuotation purchaseQuotation) {
        this.purchaseQuotation = purchaseQuotation;
    }

    public GoodsRecived purchaseQuotation(PurchaseQuotation purchaseQuotation) {
        this.setPurchaseQuotation(purchaseQuotation);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GoodsRecived)) {
            return false;
        }
        return id != null && id.equals(((GoodsRecived) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GoodsRecived{" +
            "id=" + getId() +
            ", grDate='" + getGrDate() + "'" +
            ", qtyOrdered=" + getQtyOrdered() +
            ", qtyRecieved=" + getQtyRecieved() +
            ", manufacturingDate='" + getManufacturingDate() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", lotNo='" + getLotNo() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            "}";
    }
}
