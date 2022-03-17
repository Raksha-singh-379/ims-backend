package com.techvg.inventory.management.domain;

import com.techvg.inventory.management.domain.enumeration.ClientType;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ClientDetails.
 */
@Entity
@Table(name = "client_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ClientDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "client_name", nullable = false)
    private String clientName;

    @Column(name = "mobile_no")
    private String mobileNo;

    @Column(name = "email")
    private String email;

    @Column(name = "billing_address")
    private String billingAddress;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_contact_no")
    private String companyContactNo;

    @Column(name = "website")
    private String website;

    @Column(name = "gstin_number", unique = true)
    private String gstinNumber;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "client_type")
    private ClientType clientType;

    @Column(name = "isactivated")
    private Boolean isactivated;

    @Column(name = "free_field_1")
    private String freeField1;

    @Column(name = "last_modified")
    private String lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ClientDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return this.clientName;
    }

    public ClientDetails clientName(String clientName) {
        this.setClientName(clientName);
        return this;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getMobileNo() {
        return this.mobileNo;
    }

    public ClientDetails mobileNo(String mobileNo) {
        this.setMobileNo(mobileNo);
        return this;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return this.email;
    }

    public ClientDetails email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBillingAddress() {
        return this.billingAddress;
    }

    public ClientDetails billingAddress(String billingAddress) {
        this.setBillingAddress(billingAddress);
        return this;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public ClientDetails companyName(String companyName) {
        this.setCompanyName(companyName);
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyContactNo() {
        return this.companyContactNo;
    }

    public ClientDetails companyContactNo(String companyContactNo) {
        this.setCompanyContactNo(companyContactNo);
        return this;
    }

    public void setCompanyContactNo(String companyContactNo) {
        this.companyContactNo = companyContactNo;
    }

    public String getWebsite() {
        return this.website;
    }

    public ClientDetails website(String website) {
        this.setWebsite(website);
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getGstinNumber() {
        return this.gstinNumber;
    }

    public ClientDetails gstinNumber(String gstinNumber) {
        this.setGstinNumber(gstinNumber);
        return this;
    }

    public void setGstinNumber(String gstinNumber) {
        this.gstinNumber = gstinNumber;
    }

    public String getDescription() {
        return this.description;
    }

    public ClientDetails description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ClientType getClientType() {
        return this.clientType;
    }

    public ClientDetails clientType(ClientType clientType) {
        this.setClientType(clientType);
        return this;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public Boolean getIsactivated() {
        return this.isactivated;
    }

    public ClientDetails isactivated(Boolean isactivated) {
        this.setIsactivated(isactivated);
        return this;
    }

    public void setIsactivated(Boolean isactivated) {
        this.isactivated = isactivated;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public ClientDetails freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getLastModified() {
        return this.lastModified;
    }

    public ClientDetails lastModified(String lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public ClientDetails lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClientDetails)) {
            return false;
        }
        return id != null && id.equals(((ClientDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientDetails{" +
            "id=" + getId() +
            ", clientName='" + getClientName() + "'" +
            ", mobileNo='" + getMobileNo() + "'" +
            ", email='" + getEmail() + "'" +
            ", billingAddress='" + getBillingAddress() + "'" +
            ", companyName='" + getCompanyName() + "'" +
            ", companyContactNo='" + getCompanyContactNo() + "'" +
            ", website='" + getWebsite() + "'" +
            ", gstinNumber='" + getGstinNumber() + "'" +
            ", description='" + getDescription() + "'" +
            ", clientType='" + getClientType() + "'" +
            ", isactivated='" + getIsactivated() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
