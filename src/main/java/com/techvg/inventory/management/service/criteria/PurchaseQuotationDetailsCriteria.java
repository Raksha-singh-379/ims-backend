package com.techvg.inventory.management.service.criteria;

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
 * Criteria class for the {@link com.techvg.inventory.management.domain.PurchaseQuotationDetails} entity. This class is used
 * in {@link com.techvg.inventory.management.web.rest.PurchaseQuotationDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /purchase-quotation-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class PurchaseQuotationDetailsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter qtyordered;

    private IntegerFilter gstTaxPercentage;

    private DoubleFilter pricePerUnit;

    private DoubleFilter totalPrice;

    private DoubleFilter discount;

    private StringFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private LongFilter productId;

    private LongFilter purchaseQuotationId;

    private Boolean distinct;

    public PurchaseQuotationDetailsCriteria() {}

    public PurchaseQuotationDetailsCriteria(PurchaseQuotationDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.qtyordered = other.qtyordered == null ? null : other.qtyordered.copy();
        this.gstTaxPercentage = other.gstTaxPercentage == null ? null : other.gstTaxPercentage.copy();
        this.pricePerUnit = other.pricePerUnit == null ? null : other.pricePerUnit.copy();
        this.totalPrice = other.totalPrice == null ? null : other.totalPrice.copy();
        this.discount = other.discount == null ? null : other.discount.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.purchaseQuotationId = other.purchaseQuotationId == null ? null : other.purchaseQuotationId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PurchaseQuotationDetailsCriteria copy() {
        return new PurchaseQuotationDetailsCriteria(this);
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

    public DoubleFilter getQtyordered() {
        return qtyordered;
    }

    public DoubleFilter qtyordered() {
        if (qtyordered == null) {
            qtyordered = new DoubleFilter();
        }
        return qtyordered;
    }

    public void setQtyordered(DoubleFilter qtyordered) {
        this.qtyordered = qtyordered;
    }

    public IntegerFilter getGstTaxPercentage() {
        return gstTaxPercentage;
    }

    public IntegerFilter gstTaxPercentage() {
        if (gstTaxPercentage == null) {
            gstTaxPercentage = new IntegerFilter();
        }
        return gstTaxPercentage;
    }

    public void setGstTaxPercentage(IntegerFilter gstTaxPercentage) {
        this.gstTaxPercentage = gstTaxPercentage;
    }

    public DoubleFilter getPricePerUnit() {
        return pricePerUnit;
    }

    public DoubleFilter pricePerUnit() {
        if (pricePerUnit == null) {
            pricePerUnit = new DoubleFilter();
        }
        return pricePerUnit;
    }

    public void setPricePerUnit(DoubleFilter pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public DoubleFilter getTotalPrice() {
        return totalPrice;
    }

    public DoubleFilter totalPrice() {
        if (totalPrice == null) {
            totalPrice = new DoubleFilter();
        }
        return totalPrice;
    }

    public void setTotalPrice(DoubleFilter totalPrice) {
        this.totalPrice = totalPrice;
    }

    public DoubleFilter getDiscount() {
        return discount;
    }

    public DoubleFilter discount() {
        if (discount == null) {
            discount = new DoubleFilter();
        }
        return discount;
    }

    public void setDiscount(DoubleFilter discount) {
        this.discount = discount;
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

    public LongFilter getProductId() {
        return productId;
    }

    public LongFilter productId() {
        if (productId == null) {
            productId = new LongFilter();
        }
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
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
        final PurchaseQuotationDetailsCriteria that = (PurchaseQuotationDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(qtyordered, that.qtyordered) &&
            Objects.equals(gstTaxPercentage, that.gstTaxPercentage) &&
            Objects.equals(pricePerUnit, that.pricePerUnit) &&
            Objects.equals(totalPrice, that.totalPrice) &&
            Objects.equals(discount, that.discount) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(purchaseQuotationId, that.purchaseQuotationId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            qtyordered,
            gstTaxPercentage,
            pricePerUnit,
            totalPrice,
            discount,
            lastModified,
            lastModifiedBy,
            freeField1,
            freeField2,
            productId,
            purchaseQuotationId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PurchaseQuotationDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (qtyordered != null ? "qtyordered=" + qtyordered + ", " : "") +
            (gstTaxPercentage != null ? "gstTaxPercentage=" + gstTaxPercentage + ", " : "") +
            (pricePerUnit != null ? "pricePerUnit=" + pricePerUnit + ", " : "") +
            (totalPrice != null ? "totalPrice=" + totalPrice + ", " : "") +
            (discount != null ? "discount=" + discount + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (productId != null ? "productId=" + productId + ", " : "") +
            (purchaseQuotationId != null ? "purchaseQuotationId=" + purchaseQuotationId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
