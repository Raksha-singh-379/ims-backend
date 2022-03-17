package com.techvg.inventory.management.service.criteria;

import com.techvg.inventory.management.domain.enumeration.Status;
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
 * Criteria class for the {@link com.techvg.inventory.management.domain.Transfer} entity. This class is used
 * in {@link com.techvg.inventory.management.web.rest.TransferResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /transfers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class TransferCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Status
     */
    public static class StatusFilter extends Filter<Status> {

        public StatusFilter() {}

        public StatusFilter(StatusFilter filter) {
            super(filter);
        }

        @Override
        public StatusFilter copy() {
            return new StatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter tranferDate;

    private StringFilter comment;

    private StatusFilter status;

    private StringFilter sourceWareHouse;

    private StringFilter destinationWareHouse;

    private StringFilter freeField1;

    private StringFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter transferDetailsId;

    private LongFilter transferRecievedId;

    private LongFilter transferDetailsApprovalsId;

    private LongFilter securityUserId;

    private LongFilter wareHouseId;

    private Boolean distinct;

    public TransferCriteria() {}

    public TransferCriteria(TransferCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.tranferDate = other.tranferDate == null ? null : other.tranferDate.copy();
        this.comment = other.comment == null ? null : other.comment.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.sourceWareHouse = other.sourceWareHouse == null ? null : other.sourceWareHouse.copy();
        this.destinationWareHouse = other.destinationWareHouse == null ? null : other.destinationWareHouse.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.transferDetailsId = other.transferDetailsId == null ? null : other.transferDetailsId.copy();
        this.transferRecievedId = other.transferRecievedId == null ? null : other.transferRecievedId.copy();
        this.transferDetailsApprovalsId = other.transferDetailsApprovalsId == null ? null : other.transferDetailsApprovalsId.copy();
        this.securityUserId = other.securityUserId == null ? null : other.securityUserId.copy();
        this.wareHouseId = other.wareHouseId == null ? null : other.wareHouseId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TransferCriteria copy() {
        return new TransferCriteria(this);
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

    public InstantFilter getTranferDate() {
        return tranferDate;
    }

    public InstantFilter tranferDate() {
        if (tranferDate == null) {
            tranferDate = new InstantFilter();
        }
        return tranferDate;
    }

    public void setTranferDate(InstantFilter tranferDate) {
        this.tranferDate = tranferDate;
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

    public StatusFilter getStatus() {
        return status;
    }

    public StatusFilter status() {
        if (status == null) {
            status = new StatusFilter();
        }
        return status;
    }

    public void setStatus(StatusFilter status) {
        this.status = status;
    }

    public StringFilter getSourceWareHouse() {
        return sourceWareHouse;
    }

    public StringFilter sourceWareHouse() {
        if (sourceWareHouse == null) {
            sourceWareHouse = new StringFilter();
        }
        return sourceWareHouse;
    }

    public void setSourceWareHouse(StringFilter sourceWareHouse) {
        this.sourceWareHouse = sourceWareHouse;
    }

    public StringFilter getDestinationWareHouse() {
        return destinationWareHouse;
    }

    public StringFilter destinationWareHouse() {
        if (destinationWareHouse == null) {
            destinationWareHouse = new StringFilter();
        }
        return destinationWareHouse;
    }

    public void setDestinationWareHouse(StringFilter destinationWareHouse) {
        this.destinationWareHouse = destinationWareHouse;
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

    public LongFilter getTransferDetailsId() {
        return transferDetailsId;
    }

    public LongFilter transferDetailsId() {
        if (transferDetailsId == null) {
            transferDetailsId = new LongFilter();
        }
        return transferDetailsId;
    }

    public void setTransferDetailsId(LongFilter transferDetailsId) {
        this.transferDetailsId = transferDetailsId;
    }

    public LongFilter getTransferRecievedId() {
        return transferRecievedId;
    }

    public LongFilter transferRecievedId() {
        if (transferRecievedId == null) {
            transferRecievedId = new LongFilter();
        }
        return transferRecievedId;
    }

    public void setTransferRecievedId(LongFilter transferRecievedId) {
        this.transferRecievedId = transferRecievedId;
    }

    public LongFilter getTransferDetailsApprovalsId() {
        return transferDetailsApprovalsId;
    }

    public LongFilter transferDetailsApprovalsId() {
        if (transferDetailsApprovalsId == null) {
            transferDetailsApprovalsId = new LongFilter();
        }
        return transferDetailsApprovalsId;
    }

    public void setTransferDetailsApprovalsId(LongFilter transferDetailsApprovalsId) {
        this.transferDetailsApprovalsId = transferDetailsApprovalsId;
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

    public LongFilter getWareHouseId() {
        return wareHouseId;
    }

    public LongFilter wareHouseId() {
        if (wareHouseId == null) {
            wareHouseId = new LongFilter();
        }
        return wareHouseId;
    }

    public void setWareHouseId(LongFilter wareHouseId) {
        this.wareHouseId = wareHouseId;
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
        final TransferCriteria that = (TransferCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(tranferDate, that.tranferDate) &&
            Objects.equals(comment, that.comment) &&
            Objects.equals(status, that.status) &&
            Objects.equals(sourceWareHouse, that.sourceWareHouse) &&
            Objects.equals(destinationWareHouse, that.destinationWareHouse) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(transferDetailsId, that.transferDetailsId) &&
            Objects.equals(transferRecievedId, that.transferRecievedId) &&
            Objects.equals(transferDetailsApprovalsId, that.transferDetailsApprovalsId) &&
            Objects.equals(securityUserId, that.securityUserId) &&
            Objects.equals(wareHouseId, that.wareHouseId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            tranferDate,
            comment,
            status,
            sourceWareHouse,
            destinationWareHouse,
            freeField1,
            lastModified,
            lastModifiedBy,
            transferDetailsId,
            transferRecievedId,
            transferDetailsApprovalsId,
            securityUserId,
            wareHouseId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransferCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (tranferDate != null ? "tranferDate=" + tranferDate + ", " : "") +
            (comment != null ? "comment=" + comment + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (sourceWareHouse != null ? "sourceWareHouse=" + sourceWareHouse + ", " : "") +
            (destinationWareHouse != null ? "destinationWareHouse=" + destinationWareHouse + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (transferDetailsId != null ? "transferDetailsId=" + transferDetailsId + ", " : "") +
            (transferRecievedId != null ? "transferRecievedId=" + transferRecievedId + ", " : "") +
            (transferDetailsApprovalsId != null ? "transferDetailsApprovalsId=" + transferDetailsApprovalsId + ", " : "") +
            (securityUserId != null ? "securityUserId=" + securityUserId + ", " : "") +
            (wareHouseId != null ? "wareHouseId=" + wareHouseId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
