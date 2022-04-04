package com.techvg.inventory.management.service.dto;

import com.techvg.inventory.management.domain.enumeration.ProductType;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.techvg.inventory.management.domain.Product} entity.
 */
public class ProductDTO implements Serializable {

    private Long id;

    private String shortName;

    private String chemicalFormula;

    private String hsnNo;

    @Lob
    private byte[] materialImage;

    private String materialImageContentType;
    private Boolean isDeleted;

    private Boolean isActive;

    private String productName;

    private String alertUnits;

    private String casNumber;

    private String catlogNumber;

    private Double molecularWt;

    private String molecularFormula;

    private String chemicalName;

    private String structureImg;

    private String description;

    private String qrCode;

    private String barCode;

    private Double gstPercentage;

    private ProductType productType;

    private String lastModified;

    private String lastModifiedBy;

    private String freeField1;

    private String freeField2;

    private CategoriesDTO categories;

    private UnitDTO unit;

    private SecurityUserDTO securityUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getChemicalFormula() {
        return chemicalFormula;
    }

    public void setChemicalFormula(String chemicalFormula) {
        this.chemicalFormula = chemicalFormula;
    }

    public String getHsnNo() {
        return hsnNo;
    }

    public void setHsnNo(String hsnNo) {
        this.hsnNo = hsnNo;
    }

    public byte[] getMaterialImage() {
        return materialImage;
    }

    public void setMaterialImage(byte[] materialImage) {
        this.materialImage = materialImage;
    }

    public String getMaterialImageContentType() {
        return materialImageContentType;
    }

    public void setMaterialImageContentType(String materialImageContentType) {
        this.materialImageContentType = materialImageContentType;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getAlertUnits() {
        return alertUnits;
    }

    public void setAlertUnits(String alertUnits) {
        this.alertUnits = alertUnits;
    }

    public String getCasNumber() {
        return casNumber;
    }

    public void setCasNumber(String casNumber) {
        this.casNumber = casNumber;
    }

    public String getCatlogNumber() {
        return catlogNumber;
    }

    public void setCatlogNumber(String catlogNumber) {
        this.catlogNumber = catlogNumber;
    }

    public Double getMolecularWt() {
        return molecularWt;
    }

    public void setMolecularWt(Double molecularWt) {
        this.molecularWt = molecularWt;
    }

    public String getMolecularFormula() {
        return molecularFormula;
    }

    public void setMolecularFormula(String molecularFormula) {
        this.molecularFormula = molecularFormula;
    }

    public String getChemicalName() {
        return chemicalName;
    }

    public void setChemicalName(String chemicalName) {
        this.chemicalName = chemicalName;
    }

    public String getStructureImg() {
        return structureImg;
    }

    public void setStructureImg(String structureImg) {
        this.structureImg = structureImg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Double getGstPercentage() {
        return gstPercentage;
    }

    public void setGstPercentage(Double gstPercentage) {
        this.gstPercentage = gstPercentage;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
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

    public String getFreeField1() {
        return freeField1;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return freeField2;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public CategoriesDTO getCategories() {
        return categories;
    }

    public void setCategories(CategoriesDTO categories) {
        this.categories = categories;
    }

    public UnitDTO getUnit() {
        return unit;
    }

    public void setUnit(UnitDTO unit) {
        this.unit = unit;
    }

    public SecurityUserDTO getSecurityUser() {
        return securityUser;
    }

    public void setSecurityUser(SecurityUserDTO securityUser) {
        this.securityUser = securityUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductDTO)) {
            return false;
        }

        ProductDTO productDTO = (ProductDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductDTO{" +
            "id=" + getId() +
            ", shortName='" + getShortName() + "'" +
            ", chemicalFormula='" + getChemicalFormula() + "'" +
            ", hsnNo='" + getHsnNo() + "'" +
            ", materialImage='" + getMaterialImage() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", productName='" + getProductName() + "'" +
            ", alertUnits='" + getAlertUnits() + "'" +
            ", casNumber='" + getCasNumber() + "'" +
            ", catlogNumber='" + getCatlogNumber() + "'" +
            ", molecularWt=" + getMolecularWt() +
            ", molecularFormula='" + getMolecularFormula() + "'" +
            ", chemicalName='" + getChemicalName() + "'" +
            ", structureImg='" + getStructureImg() + "'" +
            ", description='" + getDescription() + "'" +
            ", qrCode='" + getQrCode() + "'" +
            ", barCode='" + getBarCode() + "'" +
            ", gstPercentage=" + getGstPercentage() +
            ", productType='" + getProductType() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", categories=" + getCategories() +
            ", unit=" + getUnit() +
            ", securityUser=" + getSecurityUser() +
            "}";
    }
}
