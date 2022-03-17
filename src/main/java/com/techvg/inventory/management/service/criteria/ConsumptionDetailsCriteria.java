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
 * Criteria class for the {@link com.techvg.inventory.management.domain.ConsumptionDetails} entity. This class is used
 * in {@link com.techvg.inventory.management.web.rest.ConsumptionDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /consumption-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class ConsumptionDetailsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter comsumptionDate;

    private DoubleFilter qtyConsumed;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter securityUserId;

    private LongFilter projectId;

    private LongFilter productInventoryId;

    private Boolean distinct;

    public ConsumptionDetailsCriteria() {}

    public ConsumptionDetailsCriteria(ConsumptionDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.comsumptionDate = other.comsumptionDate == null ? null : other.comsumptionDate.copy();
        this.qtyConsumed = other.qtyConsumed == null ? null : other.qtyConsumed.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.securityUserId = other.securityUserId == null ? null : other.securityUserId.copy();
        this.projectId = other.projectId == null ? null : other.projectId.copy();
        this.productInventoryId = other.productInventoryId == null ? null : other.productInventoryId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ConsumptionDetailsCriteria copy() {
        return new ConsumptionDetailsCriteria(this);
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

    public InstantFilter getComsumptionDate() {
        return comsumptionDate;
    }

    public InstantFilter comsumptionDate() {
        if (comsumptionDate == null) {
            comsumptionDate = new InstantFilter();
        }
        return comsumptionDate;
    }

    public void setComsumptionDate(InstantFilter comsumptionDate) {
        this.comsumptionDate = comsumptionDate;
    }

    public DoubleFilter getQtyConsumed() {
        return qtyConsumed;
    }

    public DoubleFilter qtyConsumed() {
        if (qtyConsumed == null) {
            qtyConsumed = new DoubleFilter();
        }
        return qtyConsumed;
    }

    public void setQtyConsumed(DoubleFilter qtyConsumed) {
        this.qtyConsumed = qtyConsumed;
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

    public LongFilter getProjectId() {
        return projectId;
    }

    public LongFilter projectId() {
        if (projectId == null) {
            projectId = new LongFilter();
        }
        return projectId;
    }

    public void setProjectId(LongFilter projectId) {
        this.projectId = projectId;
    }

    public LongFilter getProductInventoryId() {
        return productInventoryId;
    }

    public LongFilter productInventoryId() {
        if (productInventoryId == null) {
            productInventoryId = new LongFilter();
        }
        return productInventoryId;
    }

    public void setProductInventoryId(LongFilter productInventoryId) {
        this.productInventoryId = productInventoryId;
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
        final ConsumptionDetailsCriteria that = (ConsumptionDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(comsumptionDate, that.comsumptionDate) &&
            Objects.equals(qtyConsumed, that.qtyConsumed) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(securityUserId, that.securityUserId) &&
            Objects.equals(projectId, that.projectId) &&
            Objects.equals(productInventoryId, that.productInventoryId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            comsumptionDate,
            qtyConsumed,
            freeField1,
            freeField2,
            lastModified,
            lastModifiedBy,
            securityUserId,
            projectId,
            productInventoryId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConsumptionDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (comsumptionDate != null ? "comsumptionDate=" + comsumptionDate + ", " : "") +
            (qtyConsumed != null ? "qtyConsumed=" + qtyConsumed + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (securityUserId != null ? "securityUserId=" + securityUserId + ", " : "") +
            (projectId != null ? "projectId=" + projectId + ", " : "") +
            (productInventoryId != null ? "productInventoryId=" + productInventoryId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
