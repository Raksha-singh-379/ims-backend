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
 * Criteria class for the {@link com.techvg.inventory.management.domain.GoodsRecived} entity. This class is used
 * in {@link com.techvg.inventory.management.web.rest.GoodsRecivedResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /goods-reciveds?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class GoodsRecivedCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter grDate;

    private DoubleFilter qtyOrdered;

    private DoubleFilter qtyRecieved;

    private InstantFilter manufacturingDate;

    private InstantFilter expiryDate;

    private StringFilter lotNo;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private LongFilter purchaseQuotationId;

    private Boolean distinct;

    public GoodsRecivedCriteria() {}

    public GoodsRecivedCriteria(GoodsRecivedCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.grDate = other.grDate == null ? null : other.grDate.copy();
        this.qtyOrdered = other.qtyOrdered == null ? null : other.qtyOrdered.copy();
        this.qtyRecieved = other.qtyRecieved == null ? null : other.qtyRecieved.copy();
        this.manufacturingDate = other.manufacturingDate == null ? null : other.manufacturingDate.copy();
        this.expiryDate = other.expiryDate == null ? null : other.expiryDate.copy();
        this.lotNo = other.lotNo == null ? null : other.lotNo.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.purchaseQuotationId = other.purchaseQuotationId == null ? null : other.purchaseQuotationId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public GoodsRecivedCriteria copy() {
        return new GoodsRecivedCriteria(this);
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

    public InstantFilter getGrDate() {
        return grDate;
    }

    public InstantFilter grDate() {
        if (grDate == null) {
            grDate = new InstantFilter();
        }
        return grDate;
    }

    public void setGrDate(InstantFilter grDate) {
        this.grDate = grDate;
    }

    public DoubleFilter getQtyOrdered() {
        return qtyOrdered;
    }

    public DoubleFilter qtyOrdered() {
        if (qtyOrdered == null) {
            qtyOrdered = new DoubleFilter();
        }
        return qtyOrdered;
    }

    public void setQtyOrdered(DoubleFilter qtyOrdered) {
        this.qtyOrdered = qtyOrdered;
    }

    public DoubleFilter getQtyRecieved() {
        return qtyRecieved;
    }

    public DoubleFilter qtyRecieved() {
        if (qtyRecieved == null) {
            qtyRecieved = new DoubleFilter();
        }
        return qtyRecieved;
    }

    public void setQtyRecieved(DoubleFilter qtyRecieved) {
        this.qtyRecieved = qtyRecieved;
    }

    public InstantFilter getManufacturingDate() {
        return manufacturingDate;
    }

    public InstantFilter manufacturingDate() {
        if (manufacturingDate == null) {
            manufacturingDate = new InstantFilter();
        }
        return manufacturingDate;
    }

    public void setManufacturingDate(InstantFilter manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public InstantFilter getExpiryDate() {
        return expiryDate;
    }

    public InstantFilter expiryDate() {
        if (expiryDate == null) {
            expiryDate = new InstantFilter();
        }
        return expiryDate;
    }

    public void setExpiryDate(InstantFilter expiryDate) {
        this.expiryDate = expiryDate;
    }

    public StringFilter getLotNo() {
        return lotNo;
    }

    public StringFilter lotNo() {
        if (lotNo == null) {
            lotNo = new StringFilter();
        }
        return lotNo;
    }

    public void setLotNo(StringFilter lotNo) {
        this.lotNo = lotNo;
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

    public StringFilter getFreeField3() {
        return freeField3;
    }

    public StringFilter freeField3() {
        if (freeField3 == null) {
            freeField3 = new StringFilter();
        }
        return freeField3;
    }

    public void setFreeField3(StringFilter freeField3) {
        this.freeField3 = freeField3;
    }

    public LongFilter getPurchaseQuotationId() {
        return purchaseQuotationId;
    }

    public LongFilter purchaseQuotationId() {
        if (purchaseQuotationId == null) {
            purchaseQuotationId = new LongFilter();
        }
        return purchaseQuotationId;
    }

    public void setPurchaseQuotationId(LongFilter purchaseQuotationId) {
        this.purchaseQuotationId = purchaseQuotationId;
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
        final GoodsRecivedCriteria that = (GoodsRecivedCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(grDate, that.grDate) &&
            Objects.equals(qtyOrdered, that.qtyOrdered) &&
            Objects.equals(qtyRecieved, that.qtyRecieved) &&
            Objects.equals(manufacturingDate, that.manufacturingDate) &&
            Objects.equals(expiryDate, that.expiryDate) &&
            Objects.equals(lotNo, that.lotNo) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(freeField3, that.freeField3) &&
            Objects.equals(purchaseQuotationId, that.purchaseQuotationId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            grDate,
            qtyOrdered,
            qtyRecieved,
            manufacturingDate,
            expiryDate,
            lotNo,
            freeField1,
            freeField2,
            freeField3,
            purchaseQuotationId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GoodsRecivedCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (grDate != null ? "grDate=" + grDate + ", " : "") +
            (qtyOrdered != null ? "qtyOrdered=" + qtyOrdered + ", " : "") +
            (qtyRecieved != null ? "qtyRecieved=" + qtyRecieved + ", " : "") +
            (manufacturingDate != null ? "manufacturingDate=" + manufacturingDate + ", " : "") +
            (expiryDate != null ? "expiryDate=" + expiryDate + ", " : "") +
            (lotNo != null ? "lotNo=" + lotNo + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (freeField3 != null ? "freeField3=" + freeField3 + ", " : "") +
            (purchaseQuotationId != null ? "purchaseQuotationId=" + purchaseQuotationId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
