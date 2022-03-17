package com.techvg.inventory.management.service.criteria;

import com.techvg.inventory.management.domain.enumeration.ClientType;
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
 * Criteria class for the {@link com.techvg.inventory.management.domain.ClientDetails} entity. This class is used
 * in {@link com.techvg.inventory.management.web.rest.ClientDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /client-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class ClientDetailsCriteria implements Serializable, Criteria {

    /**
     * Class for filtering ClientType
     */
    public static class ClientTypeFilter extends Filter<ClientType> {

        public ClientTypeFilter() {}

        public ClientTypeFilter(ClientTypeFilter filter) {
            super(filter);
        }

        @Override
        public ClientTypeFilter copy() {
            return new ClientTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter clientName;

    private StringFilter mobileNo;

    private StringFilter email;

    private StringFilter billingAddress;

    private StringFilter companyName;

    private StringFilter companyContactNo;

    private StringFilter website;

    private StringFilter gstinNumber;

    private StringFilter description;

    private ClientTypeFilter clientType;

    private BooleanFilter isactivated;

    private StringFilter freeField1;

    private StringFilter lastModified;

    private StringFilter lastModifiedBy;

    private Boolean distinct;

    public ClientDetailsCriteria() {}

    public ClientDetailsCriteria(ClientDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.clientName = other.clientName == null ? null : other.clientName.copy();
        this.mobileNo = other.mobileNo == null ? null : other.mobileNo.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.billingAddress = other.billingAddress == null ? null : other.billingAddress.copy();
        this.companyName = other.companyName == null ? null : other.companyName.copy();
        this.companyContactNo = other.companyContactNo == null ? null : other.companyContactNo.copy();
        this.website = other.website == null ? null : other.website.copy();
        this.gstinNumber = other.gstinNumber == null ? null : other.gstinNumber.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.clientType = other.clientType == null ? null : other.clientType.copy();
        this.isactivated = other.isactivated == null ? null : other.isactivated.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ClientDetailsCriteria copy() {
        return new ClientDetailsCriteria(this);
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

    public StringFilter getClientName() {
        return clientName;
    }

    public StringFilter clientName() {
        if (clientName == null) {
            clientName = new StringFilter();
        }
        return clientName;
    }

    public void setClientName(StringFilter clientName) {
        this.clientName = clientName;
    }

    public StringFilter getMobileNo() {
        return mobileNo;
    }

    public StringFilter mobileNo() {
        if (mobileNo == null) {
            mobileNo = new StringFilter();
        }
        return mobileNo;
    }

    public void setMobileNo(StringFilter mobileNo) {
        this.mobileNo = mobileNo;
    }

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getBillingAddress() {
        return billingAddress;
    }

    public StringFilter billingAddress() {
        if (billingAddress == null) {
            billingAddress = new StringFilter();
        }
        return billingAddress;
    }

    public void setBillingAddress(StringFilter billingAddress) {
        this.billingAddress = billingAddress;
    }

    public StringFilter getCompanyName() {
        return companyName;
    }

    public StringFilter companyName() {
        if (companyName == null) {
            companyName = new StringFilter();
        }
        return companyName;
    }

    public void setCompanyName(StringFilter companyName) {
        this.companyName = companyName;
    }

    public StringFilter getCompanyContactNo() {
        return companyContactNo;
    }

    public StringFilter companyContactNo() {
        if (companyContactNo == null) {
            companyContactNo = new StringFilter();
        }
        return companyContactNo;
    }

    public void setCompanyContactNo(StringFilter companyContactNo) {
        this.companyContactNo = companyContactNo;
    }

    public StringFilter getWebsite() {
        return website;
    }

    public StringFilter website() {
        if (website == null) {
            website = new StringFilter();
        }
        return website;
    }

    public void setWebsite(StringFilter website) {
        this.website = website;
    }

    public StringFilter getGstinNumber() {
        return gstinNumber;
    }

    public StringFilter gstinNumber() {
        if (gstinNumber == null) {
            gstinNumber = new StringFilter();
        }
        return gstinNumber;
    }

    public void setGstinNumber(StringFilter gstinNumber) {
        this.gstinNumber = gstinNumber;
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

    public ClientTypeFilter getClientType() {
        return clientType;
    }

    public ClientTypeFilter clientType() {
        if (clientType == null) {
            clientType = new ClientTypeFilter();
        }
        return clientType;
    }

    public void setClientType(ClientTypeFilter clientType) {
        this.clientType = clientType;
    }

    public BooleanFilter getIsactivated() {
        return isactivated;
    }

    public BooleanFilter isactivated() {
        if (isactivated == null) {
            isactivated = new BooleanFilter();
        }
        return isactivated;
    }

    public void setIsactivated(BooleanFilter isactivated) {
        this.isactivated = isactivated;
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
        final ClientDetailsCriteria that = (ClientDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(clientName, that.clientName) &&
            Objects.equals(mobileNo, that.mobileNo) &&
            Objects.equals(email, that.email) &&
            Objects.equals(billingAddress, that.billingAddress) &&
            Objects.equals(companyName, that.companyName) &&
            Objects.equals(companyContactNo, that.companyContactNo) &&
            Objects.equals(website, that.website) &&
            Objects.equals(gstinNumber, that.gstinNumber) &&
            Objects.equals(description, that.description) &&
            Objects.equals(clientType, that.clientType) &&
            Objects.equals(isactivated, that.isactivated) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            clientName,
            mobileNo,
            email,
            billingAddress,
            companyName,
            companyContactNo,
            website,
            gstinNumber,
            description,
            clientType,
            isactivated,
            freeField1,
            lastModified,
            lastModifiedBy,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (clientName != null ? "clientName=" + clientName + ", " : "") +
            (mobileNo != null ? "mobileNo=" + mobileNo + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (billingAddress != null ? "billingAddress=" + billingAddress + ", " : "") +
            (companyName != null ? "companyName=" + companyName + ", " : "") +
            (companyContactNo != null ? "companyContactNo=" + companyContactNo + ", " : "") +
            (website != null ? "website=" + website + ", " : "") +
            (gstinNumber != null ? "gstinNumber=" + gstinNumber + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (clientType != null ? "clientType=" + clientType + ", " : "") +
            (isactivated != null ? "isactivated=" + isactivated + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
