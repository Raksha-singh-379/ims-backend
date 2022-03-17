package com.techvg.inventory.management.service.dto;

import com.techvg.inventory.management.domain.enumeration.Status;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.inventory.management.domain.Transfer} entity.
 */
public class TransferDTO implements Serializable {

    private Long id;

    private Instant tranferDate;

    private String comment;

    private Status status;

    private String sourceWareHouse;

    private String destinationWareHouse;

    private String freeField1;

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

    public Instant getTranferDate() {
        return tranferDate;
    }

    public void setTranferDate(Instant tranferDate) {
        this.tranferDate = tranferDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getSourceWareHouse() {
        return sourceWareHouse;
    }

    public void setSourceWareHouse(String sourceWareHouse) {
        this.sourceWareHouse = sourceWareHouse;
    }

    public String getDestinationWareHouse() {
        return destinationWareHouse;
    }

    public void setDestinationWareHouse(String destinationWareHouse) {
        this.destinationWareHouse = destinationWareHouse;
    }

    public String getFreeField1() {
        return freeField1;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
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
        if (!(o instanceof TransferDTO)) {
            return false;
        }

        TransferDTO transferDTO = (TransferDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, transferDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransferDTO{" +
            "id=" + getId() +
            ", tranferDate='" + getTranferDate() + "'" +
            ", comment='" + getComment() + "'" +
            ", status='" + getStatus() + "'" +
            ", sourceWareHouse='" + getSourceWareHouse() + "'" +
            ", destinationWareHouse='" + getDestinationWareHouse() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", securityUser=" + getSecurityUser() +
            ", wareHouse=" + getWareHouse() +
            "}";
    }
}
