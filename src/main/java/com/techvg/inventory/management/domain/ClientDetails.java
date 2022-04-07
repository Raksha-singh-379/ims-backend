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

    @NotNull
    @Column(name = "gstin_number", nullable = false, unique = true)
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

    @Column(name = "is_approved")
    private Boolean isApproved;

    @Column(name = "name_of_beneficiary")
    private String nameOfBeneficiary;

    @NotNull
    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "ifsc_code")
    private String ifscCode;

    @Lob
    @Column(name = "gst_certificate_image")
    private byte[] gstCertificateImage;

    @Column(name = "gst_certificate_image_content_type")
    private String gstCertificateImageContentType;

    @Lob
    @Column(name = "pan_card_image")
    private byte[] panCardImage;

    @Column(name = "pan_card_image_content_type")
    private String panCardImageContentType;

    @Lob
    @Column(name = "cancelled_cheque_image")
    private byte[] cancelledChequeImage;

    @Column(name = "cancelled_cheque_image_content_type")
    private String cancelledChequeImageContentType;

    @Lob
    @Column(name = "ud_yog_aadhar_image")
    private byte[] udYogAadharImage;

    @Column(name = "ud_yog_aadhar_image_content_type")
    private String udYogAadharImageContentType;

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

    public Boolean getIsApproved() {
        return this.isApproved;
    }

    public ClientDetails isApproved(Boolean isApproved) {
        this.setIsApproved(isApproved);
        return this;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public String getNameOfBeneficiary() {
        return this.nameOfBeneficiary;
    }

    public ClientDetails nameOfBeneficiary(String nameOfBeneficiary) {
        this.setNameOfBeneficiary(nameOfBeneficiary);
        return this;
    }

    public void setNameOfBeneficiary(String nameOfBeneficiary) {
        this.nameOfBeneficiary = nameOfBeneficiary;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public ClientDetails accountNumber(String accountNumber) {
        this.setAccountNumber(accountNumber);
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return this.bankName;
    }

    public ClientDetails bankName(String bankName) {
        this.setBankName(bankName);
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountType() {
        return this.accountType;
    }

    public ClientDetails accountType(String accountType) {
        this.setAccountType(accountType);
        return this;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getIfscCode() {
        return this.ifscCode;
    }

    public ClientDetails ifscCode(String ifscCode) {
        this.setIfscCode(ifscCode);
        return this;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public byte[] getGstCertificateImage() {
        return this.gstCertificateImage;
    }

    public ClientDetails gstCertificateImage(byte[] gstCertificateImage) {
        this.setGstCertificateImage(gstCertificateImage);
        return this;
    }

    public void setGstCertificateImage(byte[] gstCertificateImage) {
        this.gstCertificateImage = gstCertificateImage;
    }

    public String getGstCertificateImageContentType() {
        return this.gstCertificateImageContentType;
    }

    public ClientDetails gstCertificateImageContentType(String gstCertificateImageContentType) {
        this.gstCertificateImageContentType = gstCertificateImageContentType;
        return this;
    }

    public void setGstCertificateImageContentType(String gstCertificateImageContentType) {
        this.gstCertificateImageContentType = gstCertificateImageContentType;
    }

    public byte[] getPanCardImage() {
        return this.panCardImage;
    }

    public ClientDetails panCardImage(byte[] panCardImage) {
        this.setPanCardImage(panCardImage);
        return this;
    }

    public void setPanCardImage(byte[] panCardImage) {
        this.panCardImage = panCardImage;
    }

    public String getPanCardImageContentType() {
        return this.panCardImageContentType;
    }

    public ClientDetails panCardImageContentType(String panCardImageContentType) {
        this.panCardImageContentType = panCardImageContentType;
        return this;
    }

    public void setPanCardImageContentType(String panCardImageContentType) {
        this.panCardImageContentType = panCardImageContentType;
    }

    public byte[] getCancelledChequeImage() {
        return this.cancelledChequeImage;
    }

    public ClientDetails cancelledChequeImage(byte[] cancelledChequeImage) {
        this.setCancelledChequeImage(cancelledChequeImage);
        return this;
    }

    public void setCancelledChequeImage(byte[] cancelledChequeImage) {
        this.cancelledChequeImage = cancelledChequeImage;
    }

    public String getCancelledChequeImageContentType() {
        return this.cancelledChequeImageContentType;
    }

    public ClientDetails cancelledChequeImageContentType(String cancelledChequeImageContentType) {
        this.cancelledChequeImageContentType = cancelledChequeImageContentType;
        return this;
    }

    public void setCancelledChequeImageContentType(String cancelledChequeImageContentType) {
        this.cancelledChequeImageContentType = cancelledChequeImageContentType;
    }

    public byte[] getUdYogAadharImage() {
        return this.udYogAadharImage;
    }

    public ClientDetails udYogAadharImage(byte[] udYogAadharImage) {
        this.setUdYogAadharImage(udYogAadharImage);
        return this;
    }

    public void setUdYogAadharImage(byte[] udYogAadharImage) {
        this.udYogAadharImage = udYogAadharImage;
    }

    public String getUdYogAadharImageContentType() {
        return this.udYogAadharImageContentType;
    }

    public ClientDetails udYogAadharImageContentType(String udYogAadharImageContentType) {
        this.udYogAadharImageContentType = udYogAadharImageContentType;
        return this;
    }

    public void setUdYogAadharImageContentType(String udYogAadharImageContentType) {
        this.udYogAadharImageContentType = udYogAadharImageContentType;
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
            ", isApproved='" + getIsApproved() + "'" +
            ", nameOfBeneficiary='" + getNameOfBeneficiary() + "'" +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", bankName='" + getBankName() + "'" +
            ", accountType='" + getAccountType() + "'" +
            ", ifscCode='" + getIfscCode() + "'" +
            ", gstCertificateImage='" + getGstCertificateImage() + "'" +
            ", gstCertificateImageContentType='" + getGstCertificateImageContentType() + "'" +
            ", panCardImage='" + getPanCardImage() + "'" +
            ", panCardImageContentType='" + getPanCardImageContentType() + "'" +
            ", cancelledChequeImage='" + getCancelledChequeImage() + "'" +
            ", cancelledChequeImageContentType='" + getCancelledChequeImageContentType() + "'" +
            ", udYogAadharImage='" + getUdYogAadharImage() + "'" +
            ", udYogAadharImageContentType='" + getUdYogAadharImageContentType() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
