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
 * Criteria class for the {@link com.techvg.inventory.management.domain.WareHouse} entity. This class is used
 * in {@link com.techvg.inventory.management.web.rest.WareHouseResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ware-houses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class WareHouseCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter whName;

    private StringFilter address;

    private IntegerFilter pincode;

    private StringFilter city;

    private StringFilter state;

    private StringFilter country;

    private StringFilter gSTDetails;

    private StringFilter managerName;

    private StringFilter managerEmail;

    private StringFilter managerContact;

    private StringFilter contact;

    private BooleanFilter isDeleted;

    private BooleanFilter isActive;

    private LongFilter wareHouseId;

    private StringFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter securityUserId;

    private Boolean distinct;

    public WareHouseCriteria() {}

    public WareHouseCriteria(WareHouseCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.whName = other.whName == null ? null : other.whName.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.pincode = other.pincode == null ? null : other.pincode.copy();
        this.city = other.city == null ? null : other.city.copy();
        this.state = other.state == null ? null : other.state.copy();
        this.country = other.country == null ? null : other.country.copy();
        this.gSTDetails = other.gSTDetails == null ? null : other.gSTDetails.copy();
        this.managerName = other.managerName == null ? null : other.managerName.copy();
        this.managerEmail = other.managerEmail == null ? null : other.managerEmail.copy();
        this.managerContact = other.managerContact == null ? null : other.managerContact.copy();
        this.contact = other.contact == null ? null : other.contact.copy();
        this.isDeleted = other.isDeleted == null ? null : other.isDeleted.copy();
        this.isActive = other.isActive == null ? null : other.isActive.copy();
        this.wareHouseId = other.wareHouseId == null ? null : other.wareHouseId.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.securityUserId = other.securityUserId == null ? null : other.securityUserId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public WareHouseCriteria copy() {
        return new WareHouseCriteria(this);
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

    public StringFilter getWhName() {
        return whName;
    }

    public StringFilter whName() {
        if (whName == null) {
            whName = new StringFilter();
        }
        return whName;
    }

    public void setWhName(StringFilter whName) {
        this.whName = whName;
    }

    public StringFilter getAddress() {
        return address;
    }

    public StringFilter address() {
        if (address == null) {
            address = new StringFilter();
        }
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public IntegerFilter getPincode() {
        return pincode;
    }

    public IntegerFilter pincode() {
        if (pincode == null) {
            pincode = new IntegerFilter();
        }
        return pincode;
    }

    public void setPincode(IntegerFilter pincode) {
        this.pincode = pincode;
    }

    public StringFilter getCity() {
        return city;
    }

    public StringFilter city() {
        if (city == null) {
            city = new StringFilter();
        }
        return city;
    }

    public void setCity(StringFilter city) {
        this.city = city;
    }

    public StringFilter getState() {
        return state;
    }

    public StringFilter state() {
        if (state == null) {
            state = new StringFilter();
        }
        return state;
    }

    public void setState(StringFilter state) {
        this.state = state;
    }

    public StringFilter getCountry() {
        return country;
    }

    public StringFilter country() {
        if (country == null) {
            country = new StringFilter();
        }
        return country;
    }

    public void setCountry(StringFilter country) {
        this.country = country;
    }

    public StringFilter getgSTDetails() {
        return gSTDetails;
    }

    public StringFilter gSTDetails() {
        if (gSTDetails == null) {
            gSTDetails = new StringFilter();
        }
        return gSTDetails;
    }

    public void setgSTDetails(StringFilter gSTDetails) {
        this.gSTDetails = gSTDetails;
    }

    public StringFilter getManagerName() {
        return managerName;
    }

    public StringFilter managerName() {
        if (managerName == null) {
            managerName = new StringFilter();
        }
        return managerName;
    }

    public void setManagerName(StringFilter managerName) {
        this.managerName = managerName;
    }

    public StringFilter getManagerEmail() {
        return managerEmail;
    }

    public StringFilter managerEmail() {
        if (managerEmail == null) {
            managerEmail = new StringFilter();
        }
        return managerEmail;
    }

    public void setManagerEmail(StringFilter managerEmail) {
        this.managerEmail = managerEmail;
    }

    public StringFilter getManagerContact() {
        return managerContact;
    }

    public StringFilter managerContact() {
        if (managerContact == null) {
            managerContact = new StringFilter();
        }
        return managerContact;
    }

    public void setManagerContact(StringFilter managerContact) {
        this.managerContact = managerContact;
    }

    public StringFilter getContact() {
        return contact;
    }

    public StringFilter contact() {
        if (contact == null) {
            contact = new StringFilter();
        }
        return contact;
    }

    public void setContact(StringFilter contact) {
        this.contact = contact;
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
        final WareHouseCriteria that = (WareHouseCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(whName, that.whName) &&
            Objects.equals(address, that.address) &&
            Objects.equals(pincode, that.pincode) &&
            Objects.equals(city, that.city) &&
            Objects.equals(state, that.state) &&
            Objects.equals(country, that.country) &&
            Objects.equals(gSTDetails, that.gSTDetails) &&
            Objects.equals(managerName, that.managerName) &&
            Objects.equals(managerEmail, that.managerEmail) &&
            Objects.equals(managerContact, that.managerContact) &&
            Objects.equals(contact, that.contact) &&
            Objects.equals(isDeleted, that.isDeleted) &&
            Objects.equals(isActive, that.isActive) &&
            Objects.equals(wareHouseId, that.wareHouseId) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(securityUserId, that.securityUserId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            whName,
            address,
            pincode,
            city,
            state,
            country,
            gSTDetails,
            managerName,
            managerEmail,
            managerContact,
            contact,
            isDeleted,
            isActive,
            wareHouseId,
            lastModified,
            lastModifiedBy,
            securityUserId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WareHouseCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (whName != null ? "whName=" + whName + ", " : "") +
            (address != null ? "address=" + address + ", " : "") +
            (pincode != null ? "pincode=" + pincode + ", " : "") +
            (city != null ? "city=" + city + ", " : "") +
            (state != null ? "state=" + state + ", " : "") +
            (country != null ? "country=" + country + ", " : "") +
            (gSTDetails != null ? "gSTDetails=" + gSTDetails + ", " : "") +
            (managerName != null ? "managerName=" + managerName + ", " : "") +
            (managerEmail != null ? "managerEmail=" + managerEmail + ", " : "") +
            (managerContact != null ? "managerContact=" + managerContact + ", " : "") +
            (contact != null ? "contact=" + contact + ", " : "") +
            (isDeleted != null ? "isDeleted=" + isDeleted + ", " : "") +
            (isActive != null ? "isActive=" + isActive + ", " : "") +
            (wareHouseId != null ? "wareHouseId=" + wareHouseId + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (securityUserId != null ? "securityUserId=" + securityUserId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
