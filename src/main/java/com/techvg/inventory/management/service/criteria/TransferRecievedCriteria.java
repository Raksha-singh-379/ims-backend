package com.techvg.inventory.management.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.techvg.inventory.management.domain.TransferRecieved} entity. This class is used
 * in {@link com.techvg.inventory.management.web.rest.TransferRecievedResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /transfer-recieveds?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class TransferRecievedCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter transferDate;

    private DoubleFilter qtyTransfered;

    private DoubleFilter qtyReceived;

    private StringFilter comment;

    private StringFilter freeField1;

    private StringFilter lastModified;

    private StringFilter lastModifiedBy;

    private BooleanFilter isDeleted;

    private BooleanFilter isActive;

    private LongFilter securityUserId;

    private LongFilter transferId;

    private Boolean distinct;

    public TransferRecievedCriteria() {}

    public TransferRecievedCriteria(TransferRecievedCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.transferDate = other.transferDate == null ? null : other.transferDate.copy();
        this.qtyTransfered = other.qtyTransfered == null ? null : other.qtyTransfered.copy();
        this.qtyReceived = other.qtyReceived == null ? null : other.qtyReceived.copy();
        this.comment = other.comment == null ? null : other.comment.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.isDeleted = other.isDeleted == null ? null : other.isDeleted.copy();
        this.isActive = other.isActive == null ? null : other.isActive.copy();
        this.securityUserId = other.securityUserId == null ? null : other.securityUserId.copy();
        this.transferId = other.transferId == null ? null : other.transferId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TransferRecievedCriteria copy() {
        return new TransferRecievedCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getTransferDate() {
        return transferDate;
    }

    public InstantFilter transferDate() {
        if (transferDate == null) {
            transferDate = new InstantFilter();
        }
        return transferDate;
    }

    public void setTransferDate(InstantFilter transferDate) {
        this.transferDate = transferDate;
    }

    public DoubleFilter getQtyTransfered() {
        return qtyTransfered;
    }

    public DoubleFilter qtyTransfered() {
        if (qtyTransfered == null) {
            qtyTransfered = new DoubleFilter();
        }
        return qtyTransfered;
    }

    public void setQtyTransfered(DoubleFilter qtyTransfered) {
        this.qtyTransfered = qtyTransfered;
    }

    public DoubleFilter getQtyReceived() {
        return qtyReceived;
    }

    public DoubleFilter qtyReceived() {
        if (qtyReceived == null) {
            qtyReceived = new DoubleFilter();
        }
        return qtyReceived;
    }

    public void setQtyReceived(DoubleFilter qtyReceived) {
        this.qtyReceived = qtyReceived;
    }

    public StringFilter getComment() {
        return comment;
    }

    public StringFilter comment() {
        if (comment == null) {
            comment = new StringFilter();
        }
        return comment;
    }

    public void setComment(StringFilter comment) {
        this.comment = comment;
    }

    public StringFilter getFreeField1() {
        return freeField1;
    }

    public StringFilter freeField1() {
        if (freeField1 == null) {
            freeField1 = new StringFilter();
        }
        return freeField1;
    }

    public void setFreeField1(StringFilter freeField1) {
        this.freeField1 = freeField1;
    }

    public StringFilter getLastModified() {
        return lastModified;
    }

    public StringFilter lastModified() {
        if (lastModified == null) {
            lastModified = new StringFilter();
        }
        return lastModified;
    }

    public void setLastModified(StringFilter lastModified) {
        this.lastModified = lastModified;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public StringFilter lastModifiedBy() {
        if (lastModifiedBy == null) {
            lastModifiedBy = new StringFilter();
        }
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public BooleanFilter getIsDeleted() {
        return isDeleted;
    }

    public BooleanFilter isDeleted() {
        if (isDeleted == null) {
            isDeleted = new BooleanFilter();
        }
        return isDeleted;
    }

    public void setIsDeleted(BooleanFilter isDeleted) {
        this.isDeleted = isDeleted;
    }

    public BooleanFilter getIsActive() {
        return isActive;
    }

    public BooleanFilter isActive() {
        if (isActive == null) {
            isActive = new BooleanFilter();
        }
        return isActive;
    }

    public void setIsActive(BooleanFilter isActive) {
        this.isActive = isActive;
    }

    public LongFilter getSecurityUserId() {
        return securityUserId;
    }

    public LongFilter securityUserId() {
        if (securityUserId == null) {
            securityUserId = new LongFilter();
        }
        return securityUserId;
    }

    public void setSecurityUserId(LongFilter securityUserId) {
        this.securityUserId = securityUserId;
    }

    public LongFilter getTransferId() {
        return transferId;
    }

    public LongFilter transferId() {
        if (transferId == null) {
            transferId = new LongFilter();
        }
        return transferId;
    }

    public void setTransferId(LongFilter transferId) {
        this.transferId = transferId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TransferRecievedCriteria that = (TransferRecievedCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(transferDate, that.transferDate) &&
            Objects.equals(qtyTransfered, that.qtyTransfered) &&
            Objects.equals(qtyReceived, that.qtyReceived) &&
            Objects.equals(comment, that.comment) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(isDeleted, that.isDeleted) &&
            Objects.equals(isActive, that.isActive) &&
            Objects.equals(securityUserId, that.securityUserId) &&
            Objects.equals(transferId, that.transferId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            transferDate,
            qtyTransfered,
            qtyReceived,
            comment,
            freeField1,
            lastModified,
            lastModifiedBy,
            isDeleted,
            isActive,
            securityUserId,
            transferId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransferRecievedCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (transferDate != null ? "transferDate=" + transferDate + ", " : "") +
            (qtyTransfered != null ? "qtyTransfered=" + qtyTransfered + ", " : "") +
            (qtyReceived != null ? "qtyReceived=" + qtyReceived + ", " : "") +
            (comment != null ? "comment=" + comment + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (isDeleted != null ? "isDeleted=" + isDeleted + ", " : "") +
            (isActive != null ? "isActive=" + isActive + ", " : "") +
            (securityUserId != null ? "securityUserId=" + securityUserId + ", " : "") +
            (transferId != null ? "transferId=" + transferId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
