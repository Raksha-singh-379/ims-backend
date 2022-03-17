package com.techvg.inventory.management.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TransferDetailsApprovals.
 */
@Entity
@Table(name = "transfer_details_approvals")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TransferDetailsApprovals implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "approval_date")
    private Instant approvalDate;

    @Column(name = "qty_requested")
    private Double qtyRequested;

    @Column(name = "qty_approved")
    private Double qtyApproved;

    @Column(name = "comment")
    private String comment;

    @Column(name = "free_field_1")
    private String freeField1;

    @Column(name = "last_modified")
    private String lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne
    @JsonIgnoreProperties(value = { "securityPermissions", "securityRoles", "wareHouses" }, allowSetters = true)
    private SecurityUser securityUser;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "transferDetails", "transferRecieveds", "transferDetailsApprovals", "securityUser", "wareHouse" },
        allowSetters = true
    )
    private Transfer transfer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TransferDetailsApprovals id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getApprovalDate() {
        return this.approvalDate;
    }

    public TransferDetailsApprovals approvalDate(Instant approvalDate) {
        this.setApprovalDate(approvalDate);
        return this;
    }

    public void setApprovalDate(Instant approvalDate) {
        this.approvalDate = approvalDate;
    }

    public Double getQtyRequested() {
        return this.qtyRequested;
    }

    public TransferDetailsApprovals qtyRequested(Double qtyRequested) {
        this.setQtyRequested(qtyRequested);
        return this;
    }

    public void setQtyRequested(Double qtyRequested) {
        this.qtyRequested = qtyRequested;
    }

    public Double getQtyApproved() {
        return this.qtyApproved;
    }

    public TransferDetailsApprovals qtyApproved(Double qtyApproved) {
        this.setQtyApproved(qtyApproved);
        return this;
    }

    public void setQtyApproved(Double qtyApproved) {
        this.qtyApproved = qtyApproved;
    }

    public String getComment() {
        return this.comment;
    }

    public TransferDetailsApprovals comment(String comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public TransferDetailsApprovals freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getLastModified() {
        return this.lastModified;
    }

    public TransferDetailsApprovals lastModified(String lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public TransferDetailsApprovals lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public TransferDetailsApprovals isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public TransferDetailsApprovals isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public SecurityUser getSecurityUser() {
        return this.securityUser;
    }

    public void setSecurityUser(SecurityUser securityUser) {
        this.securityUser = securityUser;
    }

    public TransferDetailsApprovals securityUser(SecurityUser securityUser) {
        this.setSecurityUser(securityUser);
        return this;
    }

    public Transfer getTransfer() {
        return this.transfer;
    }

    public void setTransfer(Transfer transfer) {
        this.transfer = transfer;
    }

    public TransferDetailsApprovals transfer(Transfer transfer) {
        this.setTransfer(transfer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransferDetailsApprovals)) {
            return false;
        }
        return id != null && id.equals(((TransferDetailsApprovals) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransferDetailsApprovals{" +
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
            "}";
    }
}
