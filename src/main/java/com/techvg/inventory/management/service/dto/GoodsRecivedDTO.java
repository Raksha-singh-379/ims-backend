package com.techvg.inventory.management.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.inventory.management.domain.GoodsRecived} entity.
 */
public class GoodsRecivedDTO implements Serializable {

    private Long id;

    private Instant grDate;

    private Double qtyOrdered;

    private Double qtyRecieved;

    private Instant manufacturingDate;

    private Instant expiryDate;

    private String lotNo;

    private String freeField1;

    private String freeField2;

    private String freeField3;

    private PurchaseQuotationDTO purchaseQuotation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getGrDate() {
        return grDate;
    }

    public void setGrDate(Instant grDate) {
        this.grDate = grDate;
    }

    public Double getQtyOrdered() {
        return qtyOrdered;
    }

    public void setQtyOrdered(Double qtyOrdered) {
        this.qtyOrdered = qtyOrdered;
    }

    public Double getQtyRecieved() {
        return qtyRecieved;
    }

    public void setQtyRecieved(Double qtyRecieved) {
        this.qtyRecieved = qtyRecieved;
    }

    public Instant getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(Instant manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    public String getFreeField1() {
        return freeField1;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return freeField2;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return freeField3;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public PurchaseQuotationDTO getPurchaseQuotation() {
        return purchaseQuotation;
    }

    public void setPurchaseQuotation(PurchaseQuotationDTO purchaseQuotation) {
        this.purchaseQuotation = purchaseQuotation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GoodsRecivedDTO)) {
            return false;
        }

        GoodsRecivedDTO goodsRecivedDTO = (GoodsRecivedDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, goodsRecivedDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GoodsRecivedDTO{" +
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
            ", purchaseQuotation=" + getPurchaseQuotation() +
            "}";
    }
}
