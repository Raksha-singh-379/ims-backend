package com.techvg.inventory.management.service.dto;

import com.techvg.inventory.management.domain.enumeration.Status;
import com.techvg.inventory.management.domain.enumeration.TransactionType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.inventory.management.domain.ProductTransaction} entity.
 */
public class ProductTransactionDTO implements Serializable {

    private Long id;

    private Long refrenceId;

    private TransactionType transactionType;

    private Status transactionStatus;

    private String transactionDate;

    private String description;

    private String freeField1;

    private String freeField2;

    private String lastModified;

    private String lastModifiedBy;

    private SecurityUserDTO securityUser;

    private WareHouseDTO wareHouse;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRefrenceId() {
        return refrenceId;
    }

    public void setRefrenceId(Long refrenceId) {
        this.refrenceId = refrenceId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Status getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(Status transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public SecurityUserDTO getSecurityUser() {
        return securityUser;
    }

    public void setSecurityUser(SecurityUserDTO securityUser) {
        this.securityUser = securityUser;
    }

    public WareHouseDTO getWareHouse() {
        return wareHouse;
    }

    public void setWareHouse(WareHouseDTO wareHouse) {
        this.wareHouse = wareHouse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductTransactionDTO)) {
            return false;
        }

        ProductTransactionDTO productTransactionDTO = (ProductTransactionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productTransactionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductTransactionDTO{" +
            "id=" + getId() +
            ", refrenceId=" + getRefrenceId() +
            ", transactionType='" + getTransactionType() + "'" +
            ", transactionStatus='" + getTransactionStatus() + "'" +
            ", transactionDate='" + getTransactionDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", securityUser=" + getSecurityUser() +
            ", wareHouse=" + getWareHouse() +
            "}";
    }
}
