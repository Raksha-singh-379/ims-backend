package com.techvg.inventory.management.service.criteria;

import com.techvg.inventory.management.domain.enumeration.Status;
import com.techvg.inventory.management.domain.enumeration.TransactionType;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.techvg.inventory.management.domain.ProductTransaction} entity. This class is used
 * in {@link com.techvg.inventory.management.web.rest.ProductTransactionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /product-transactions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class ProductTransactionCriteria implements Serializable, Criteria {

    /**
     * Class for filtering TransactionType
     */
    public static class TransactionTypeFilter extends Filter<TransactionType> {

        public TransactionTypeFilter() {}

        public TransactionTypeFilter(TransactionTypeFilter filter) {
            super(filter);
        }

        @Override
        public TransactionTypeFilter copy() {
            return new TransactionTypeFilter(this);
        }
    }

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

    private LongFilter refrenceId;

    private TransactionTypeFilter transactionType;

    private StatusFilter transactionStatus;

    private StringFilter transactionDate;

    private StringFilter description;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter securityUserId;

    private LongFilter wareHouseId;

    private Boolean distinct;

    public ProductTransactionCriteria() {}

    public ProductTransactionCriteria(ProductTransactionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.refrenceId = other.refrenceId == null ? null : other.refrenceId.copy();
        this.transactionType = other.transactionType == null ? null : other.transactionType.copy();
        this.transactionStatus = other.transactionStatus == null ? null : other.transactionStatus.copy();
        this.transactionDate = other.transactionDate == null ? null : other.transactionDate.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.securityUserId = other.securityUserId == null ? null : other.securityUserId.copy();
        this.wareHouseId = other.wareHouseId == null ? null : other.wareHouseId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProductTransactionCriteria copy() {
        return new ProductTransactionCriteria(this);
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

    public LongFilter getRefrenceId() {
        return refrenceId;
    }

    public LongFilter refrenceId() {
        if (refrenceId == null) {
            refrenceId = new LongFilter();
        }
        return refrenceId;
    }

    public void setRefrenceId(LongFilter refrenceId) {
        this.refrenceId = refrenceId;
    }

    public TransactionTypeFilter getTransactionType() {
        return transactionType;
    }

    public TransactionTypeFilter transactionType() {
        if (transactionType == null) {
            transactionType = new TransactionTypeFilter();
        }
        return transactionType;
    }

    public void setTransactionType(TransactionTypeFilter transactionType) {
        this.transactionType = transactionType;
    }

    public StatusFilter getTransactionStatus() {
        return transactionStatus;
    }

    public StatusFilter transactionStatus() {
        if (transactionStatus == null) {
            transactionStatus = new StatusFilter();
        }
        return transactionStatus;
    }

    public void setTransactionStatus(StatusFilter transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public StringFilter getTransactionDate() {
        return transactionDate;
    }

    public StringFilter transactionDate() {
        if (transactionDate == null) {
            transactionDate = new StringFilter();
        }
        return transactionDate;
    }

    public void setTransactionDate(StringFilter transactionDate) {
        this.transactionDate = transactionDate;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
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

    public StringFilter getFreeField2() {
        return freeField2;
    }

    public StringFilter freeField2() {
        if (freeField2 == null) {
            freeField2 = new StringFilter();
        }
        return freeField2;
    }

    public void setFreeField2(StringFilter freeField2) {
        this.freeField2 = freeField2;
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
        final ProductTransactionCriteria that = (ProductTransactionCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(refrenceId, that.refrenceId) &&
            Objects.equals(transactionType, that.transactionType) &&
            Objects.equals(transactionStatus, that.transactionStatus) &&
            Objects.equals(transactionDate, that.transactionDate) &&
            Objects.equals(description, that.description) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(securityUserId, that.securityUserId) &&
            Objects.equals(wareHouseId, that.wareHouseId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            refrenceId,
            transactionType,
            transactionStatus,
            transactionDate,
            description,
            freeField1,
            freeField2,
            lastModified,
            lastModifiedBy,
            securityUserId,
            wareHouseId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductTransactionCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (refrenceId != null ? "refrenceId=" + refrenceId + ", " : "") +
            (transactionType != null ? "transactionType=" + transactionType + ", " : "") +
            (transactionStatus != null ? "transactionStatus=" + transactionStatus + ", " : "") +
            (transactionDate != null ? "transactionDate=" + transactionDate + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (securityUserId != null ? "securityUserId=" + securityUserId + ", " : "") +
            (wareHouseId != null ? "wareHouseId=" + wareHouseId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
