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
 * Criteria class for the {@link com.techvg.inventory.management.domain.ProductInventory} entity. This class is used
 * in {@link com.techvg.inventory.management.web.rest.ProductInventoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /product-inventories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class ProductInventoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter inwardOutwardDate;

    private StringFilter inwardQty;

    private StringFilter outwardQty;

    private StringFilter totalQuanity;

    private LongFilter pricePerUnit;

    private StringFilter lotNo;

    private InstantFilter expiryDate;

    private StringFilter inventoryTypeId;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter lastModified;

    private StringFilter lastModifiedBy;

    private BooleanFilter isDeleted;

    private BooleanFilter isActive;

    private LongFilter consumptionDetailsId;

    private LongFilter productId;

    private LongFilter productTransactionId;

    private LongFilter securityUserId;

    private LongFilter wareHouseId;

    private Boolean distinct;

    public ProductInventoryCriteria() {}

    public ProductInventoryCriteria(ProductInventoryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.inwardOutwardDate = other.inwardOutwardDate == null ? null : other.inwardOutwardDate.copy();
        this.inwardQty = other.inwardQty == null ? null : other.inwardQty.copy();
        this.outwardQty = other.outwardQty == null ? null : other.outwardQty.copy();
        this.totalQuanity = other.totalQuanity == null ? null : other.totalQuanity.copy();
        this.pricePerUnit = other.pricePerUnit == null ? null : other.pricePerUnit.copy();
        this.lotNo = other.lotNo == null ? null : other.lotNo.copy();
        this.expiryDate = other.expiryDate == null ? null : other.expiryDate.copy();
        this.inventoryTypeId = other.inventoryTypeId == null ? null : other.inventoryTypeId.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.isDeleted = other.isDeleted == null ? null : other.isDeleted.copy();
        this.isActive = other.isActive == null ? null : other.isActive.copy();
        this.consumptionDetailsId = other.consumptionDetailsId == null ? null : other.consumptionDetailsId.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.productTransactionId = other.productTransactionId == null ? null : other.productTransactionId.copy();
        this.securityUserId = other.securityUserId == null ? null : other.securityUserId.copy();
        this.wareHouseId = other.wareHouseId == null ? null : other.wareHouseId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProductInventoryCriteria copy() {
        return new ProductInventoryCriteria(this);
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

    public InstantFilter getInwardOutwardDate() {
        return inwardOutwardDate;
    }

    public InstantFilter inwardOutwardDate() {
        if (inwardOutwardDate == null) {
            inwardOutwardDate = new InstantFilter();
        }
        return inwardOutwardDate;
    }

    public void setInwardOutwardDate(InstantFilter inwardOutwardDate) {
        this.inwardOutwardDate = inwardOutwardDate;
    }

    public StringFilter getInwardQty() {
        return inwardQty;
    }

    public StringFilter inwardQty() {
        if (inwardQty == null) {
            inwardQty = new StringFilter();
        }
        return inwardQty;
    }

    public void setInwardQty(StringFilter inwardQty) {
        this.inwardQty = inwardQty;
    }

    public StringFilter getOutwardQty() {
        return outwardQty;
    }

    public StringFilter outwardQty() {
        if (outwardQty == null) {
            outwardQty = new StringFilter();
        }
        return outwardQty;
    }

    public void setOutwardQty(StringFilter outwardQty) {
        this.outwardQty = outwardQty;
    }

    public StringFilter getTotalQuanity() {
        return totalQuanity;
    }

    public StringFilter totalQuanity() {
        if (totalQuanity == null) {
            totalQuanity = new StringFilter();
        }
        return totalQuanity;
    }

    public void setTotalQuanity(StringFilter totalQuanity) {
        this.totalQuanity = totalQuanity;
    }

    public LongFilter getPricePerUnit() {
        return pricePerUnit;
    }

    public LongFilter pricePerUnit() {
        if (pricePerUnit == null) {
            pricePerUnit = new LongFilter();
        }
        return pricePerUnit;
    }

    public void setPricePerUnit(LongFilter pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
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

    public StringFilter getInventoryTypeId() {
        return inventoryTypeId;
    }

    public StringFilter inventoryTypeId() {
        if (inventoryTypeId == null) {
            inventoryTypeId = new StringFilter();
        }
        return inventoryTypeId;
    }

    public void setInventoryTypeId(StringFilter inventoryTypeId) {
        this.inventoryTypeId = inventoryTypeId;
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

    public LongFilter getConsumptionDetailsId() {
        return consumptionDetailsId;
    }

    public LongFilter consumptionDetailsId() {
        if (consumptionDetailsId == null) {
            consumptionDetailsId = new LongFilter();
        }
        return consumptionDetailsId;
    }

    public void setConsumptionDetailsId(LongFilter consumptionDetailsId) {
        this.consumptionDetailsId = consumptionDetailsId;
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

    public LongFilter getProductTransactionId() {
        return productTransactionId;
    }

    public LongFilter productTransactionId() {
        if (productTransactionId == null) {
            productTransactionId = new LongFilter();
        }
        return productTransactionId;
    }

    public void setProductTransactionId(LongFilter productTransactionId) {
        this.productTransactionId = productTransactionId;
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
        final ProductInventoryCriteria that = (ProductInventoryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(inwardOutwardDate, that.inwardOutwardDate) &&
            Objects.equals(inwardQty, that.inwardQty) &&
            Objects.equals(outwardQty, that.outwardQty) &&
            Objects.equals(totalQuanity, that.totalQuanity) &&
            Objects.equals(pricePerUnit, that.pricePerUnit) &&
            Objects.equals(lotNo, that.lotNo) &&
            Objects.equals(expiryDate, that.expiryDate) &&
            Objects.equals(inventoryTypeId, that.inventoryTypeId) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(isDeleted, that.isDeleted) &&
            Objects.equals(isActive, that.isActive) &&
            Objects.equals(consumptionDetailsId, that.consumptionDetailsId) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(productTransactionId, that.productTransactionId) &&
            Objects.equals(securityUserId, that.securityUserId) &&
            Objects.equals(wareHouseId, that.wareHouseId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            inwardOutwardDate,
            inwardQty,
            outwardQty,
            totalQuanity,
            pricePerUnit,
            lotNo,
            expiryDate,
            inventoryTypeId,
            freeField1,
            freeField2,
            lastModified,
            lastModifiedBy,
            isDeleted,
            isActive,
            consumptionDetailsId,
            productId,
            productTransactionId,
            securityUserId,
            wareHouseId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductInventoryCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (inwardOutwardDate != null ? "inwardOutwardDate=" + inwardOutwardDate + ", " : "") +
            (inwardQty != null ? "inwardQty=" + inwardQty + ", " : "") +
            (outwardQty != null ? "outwardQty=" + outwardQty + ", " : "") +
            (totalQuanity != null ? "totalQuanity=" + totalQuanity + ", " : "") +
            (pricePerUnit != null ? "pricePerUnit=" + pricePerUnit + ", " : "") +
            (lotNo != null ? "lotNo=" + lotNo + ", " : "") +
            (expiryDate != null ? "expiryDate=" + expiryDate + ", " : "") +
            (inventoryTypeId != null ? "inventoryTypeId=" + inventoryTypeId + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (isDeleted != null ? "isDeleted=" + isDeleted + ", " : "") +
            (isActive != null ? "isActive=" + isActive + ", " : "") +
            (consumptionDetailsId != null ? "consumptionDetailsId=" + consumptionDetailsId + ", " : "") +
            (productId != null ? "productId=" + productId + ", " : "") +
            (productTransactionId != null ? "productTransactionId=" + productTransactionId + ", " : "") +
            (securityUserId != null ? "securityUserId=" + securityUserId + ", " : "") +
            (wareHouseId != null ? "wareHouseId=" + wareHouseId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
