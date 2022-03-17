package com.techvg.inventory.management.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A WareHouse.
 */
@Entity
@Table(name = "ware_house")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WareHouse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "wh_name")
    private String whName;

    @Column(name = "address")
    private String address;

    @Column(name = "pincode")
    private Integer pincode;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "g_st_details")
    private String gSTDetails;

    @Column(name = "manager_name")
    private String managerName;

    @Column(name = "manager_email")
    private String managerEmail;

    @Column(name = "manager_contact")
    private String managerContact;

    @Column(name = "contact")
    private String contact;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "ware_house_id")
    private Long wareHouseId;

    @Column(name = "last_modified")
    private String lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @ManyToMany(mappedBy = "wareHouses")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "securityPermissions", "securityRoles", "wareHouses" }, allowSetters = true)
    private Set<SecurityUser> securityUsers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public WareHouse id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWhName() {
        return this.whName;
    }

    public WareHouse whName(String whName) {
        this.setWhName(whName);
        return this;
    }

    public void setWhName(String whName) {
        this.whName = whName;
    }

    public String getAddress() {
        return this.address;
    }

    public WareHouse address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPincode() {
        return this.pincode;
    }

    public WareHouse pincode(Integer pincode) {
        this.setPincode(pincode);
        return this;
    }

    public void setPincode(Integer pincode) {
        this.pincode = pincode;
    }

    public String getCity() {
        return this.city;
    }

    public WareHouse city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return this.state;
    }

    public WareHouse state(String state) {
        this.setState(state);
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return this.country;
    }

    public WareHouse country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getgSTDetails() {
        return this.gSTDetails;
    }

    public WareHouse gSTDetails(String gSTDetails) {
        this.setgSTDetails(gSTDetails);
        return this;
    }

    public void setgSTDetails(String gSTDetails) {
        this.gSTDetails = gSTDetails;
    }

    public String getManagerName() {
        return this.managerName;
    }

    public WareHouse managerName(String managerName) {
        this.setManagerName(managerName);
        return this;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerEmail() {
        return this.managerEmail;
    }

    public WareHouse managerEmail(String managerEmail) {
        this.setManagerEmail(managerEmail);
        return this;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    public String getManagerContact() {
        return this.managerContact;
    }

    public WareHouse managerContact(String managerContact) {
        this.setManagerContact(managerContact);
        return this;
    }

    public void setManagerContact(String managerContact) {
        this.managerContact = managerContact;
    }

    public String getContact() {
        return this.contact;
    }

    public WareHouse contact(String contact) {
        this.setContact(contact);
        return this;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public WareHouse isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public WareHouse isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Long getWareHouseId() {
        return this.wareHouseId;
    }

    public WareHouse wareHouseId(Long wareHouseId) {
        this.setWareHouseId(wareHouseId);
        return this;
    }

    public void setWareHouseId(Long wareHouseId) {
        this.wareHouseId = wareHouseId;
    }

    public String getLastModified() {
        return this.lastModified;
    }

    public WareHouse lastModified(String lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public WareHouse lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Set<SecurityUser> getSecurityUsers() {
        return this.securityUsers;
    }

    public void setSecurityUsers(Set<SecurityUser> securityUsers) {
        if (this.securityUsers != null) {
            this.securityUsers.forEach(i -> i.removeWareHouse(this));
        }
        if (securityUsers != null) {
            securityUsers.forEach(i -> i.addWareHouse(this));
        }
        this.securityUsers = securityUsers;
    }

    public WareHouse securityUsers(Set<SecurityUser> securityUsers) {
        this.setSecurityUsers(securityUsers);
        return this;
    }

    public WareHouse addSecurityUser(SecurityUser securityUser) {
        this.securityUsers.add(securityUser);
        securityUser.getWareHouses().add(this);
        return this;
    }

    public WareHouse removeSecurityUser(SecurityUser securityUser) {
        this.securityUsers.remove(securityUser);
        securityUser.getWareHouses().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WareHouse)) {
            return false;
        }
        return id != null && id.equals(((WareHouse) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WareHouse{" +
            "id=" + getId() +
            ", whName='" + getWhName() + "'" +
            ", address='" + getAddress() + "'" +
            ", pincode=" + getPincode() +
            ", city='" + getCity() + "'" +
            ", state='" + getState() + "'" +
            ", country='" + getCountry() + "'" +
            ", gSTDetails='" + getgSTDetails() + "'" +
            ", managerName='" + getManagerName() + "'" +
            ", managerEmail='" + getManagerEmail() + "'" +
            ", managerContact='" + getManagerContact() + "'" +
            ", contact='" + getContact() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", wareHouseId=" + getWareHouseId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
