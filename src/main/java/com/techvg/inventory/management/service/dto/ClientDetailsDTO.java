package com.techvg.inventory.management.service.dto;

import com.techvg.inventory.management.domain.enumeration.ClientType;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.techvg.inventory.management.domain.ClientDetails} entity.
 */
public class ClientDetailsDTO implements Serializable {

    private Long id;

    @NotNull
    private String clientName;

    private String mobileNo;

    private String email;

    private String billingAddress;

    private String companyName;

    private String companyContactNo;

    private String website;

    private String gstinNumber;

    private String description;

    private ClientType clientType;

    private Boolean isactivated;

    private String freeField1;

    private String lastModified;

    private String lastModifiedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyContactNo() {
        return companyContactNo;
    }

    public void setCompanyContactNo(String companyContactNo) {
        this.companyContactNo = companyContactNo;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getGstinNumber() {
        return gstinNumber;
    }

    public void setGstinNumber(String gstinNumber) {
        this.gstinNumber = gstinNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public Boolean getIsactivated() {
        return isactivated;
    }

    public void setIsactivated(Boolean isactivated) {
        this.isactivated = isactivated;
    }

    public String getFreeField1() {
        return freeField1;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClientDetailsDTO)) {
            return false;
        }

        ClientDetailsDTO clientDetailsDTO = (ClientDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, clientDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientDetailsDTO{" +
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
