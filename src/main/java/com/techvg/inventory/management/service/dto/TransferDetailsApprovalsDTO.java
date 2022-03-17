package com.techvg.inventory.management.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.inventory.management.domain.TransferDetailsApprovals} entity.
 */
public class TransferDetailsApprovalsDTO implements Serializable {

    private Long id;

    private Instant approvalDate;

    private Double qtyRequested;

    private Double qtyApproved;

    private String comment;

    private String freeField1;

    private String lastModified;

    private String lastModifiedBy;

    private Boolean isDeleted;

    private Boolean isActive;

    private SecurityUserDTO securityUser;

    private TransferDTO transfer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Instant approvalDate) {
        this.approvalDate = approvalDate;
    }

    public Double getQtyRequested() {
        return qtyRequested;
    }

    public void setQtyRequested(Double qtyRequested) {
        this.qtyRequested = qtyRequested;
    }

    public Double getQtyApproved() {
        return qtyApproved;
    }

    public void setQtyApproved(Double qtyApproved) {
        this.qtyApproved = qtyApproved;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public SecurityUserDTO getSecurityUser() {
        return securityUser;
    }

    public void setSecurityUser(SecurityUserDTO securityUser) {
        this.securityUser = securityUser;
    }

    public TransferDTO getTransfer() {
        return transfer;
    }

    public void setTransfer(TransferDTO transfer) {
        this.transfer = transfer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransferDetailsApprovalsDTO)) {
            return false;
        }

        TransferDetailsApprovalsDTO transferDetailsApprovalsDTO = (TransferDetailsApprovalsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, transferDetailsApprovalsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransferDetailsApprovalsDTO{" +
            "id=" + getId() +
            ", approvalDate='" + getApprovalDate() + "'" +
            ", qtyRequested=" + getQtyRequested() +
            ", qtyApproved=" + getQtyApproved() +
            ", comment='" + getComment() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", securityUser=" + getSecurityUser() +
            ", transfer=" + getTransfer() +
            "}";
    }
}
