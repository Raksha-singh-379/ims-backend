package com.techvg.inventory.management.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.techvg.inventory.management.domain.enumeration.ProductType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "chemical_formula")
    private String chemicalFormula;

    @Column(name = "hsn_no")
    private String hsnNo;

    @Lob
    @Column(name = "material_image")
    private byte[] materialImage;

    @Column(name = "material_image_content_type")
    private String materialImageContentType;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "alert_units")
    private String alertUnits;

    @Column(name = "cas_number")
    private String casNumber;

    @Column(name = "catlog_number")
    private String catlogNumber;

    @Column(name = "molecular_wt")
    private Double molecularWt;

    @Column(name = "molecular_formula")
    private String molecularFormula;

    @Column(name = "chemical_name")
    private String chemicalName;

    @Column(name = "structure_img")
    private String structureImg;

    @Column(name = "description")
    private String description;

    @Column(name = "qr_code")
    private String qrCode;

    @Column(name = "bar_code")
    private String barCode;

    @Column(name = "gst_percentage")
    private Double gstPercentage;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type")
    private ProductType productType;

    @Column(name = "last_modified")
    private String lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "free_field_1")
    private String freeField1;

    @Column(name = "free_field_2")
    private String freeField2;

    @OneToMany(mappedBy = "product")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "product", "transfer" }, allowSetters = true)
    private Set<TransferDetails> transferDetails = new HashSet<>();

    @ManyToOne
    private Categories categories;

    @ManyToOne
    private Unit unit;

    @ManyToOne
    @JsonIgnoreProperties(value = { "securityPermissions", "securityRoles", "wareHouses" }, allowSetters = true)
    private SecurityUser securityUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Product id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortName() {
        return this.shortName;
    }

    public Product shortName(String shortName) {
        this.setShortName(shortName);
        return this;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getChemicalFormula() {
        return this.chemicalFormula;
    }

    public Product chemicalFormula(String chemicalFormula) {
        this.setChemicalFormula(chemicalFormula);
        return this;
    }

    public void setChemicalFormula(String chemicalFormula) {
        this.chemicalFormula = chemicalFormula;
    }

    public String getHsnNo() {
        return this.hsnNo;
    }

    public Product hsnNo(String hsnNo) {
        this.setHsnNo(hsnNo);
        return this;
    }

    public void setHsnNo(String hsnNo) {
        this.hsnNo = hsnNo;
    }

    public byte[] getMaterialImage() {
        return this.materialImage;
    }

    public Product materialImage(byte[] materialImage) {
        this.setMaterialImage(materialImage);
        return this;
    }

    public void setMaterialImage(byte[] materialImage) {
        this.materialImage = materialImage;
    }

    public String getMaterialImageContentType() {
        return this.materialImageContentType;
    }

    public Product materialImageContentType(String materialImageContentType) {
        this.materialImageContentType = materialImageContentType;
        return this;
    }

    public void setMaterialImageContentType(String materialImageContentType) {
        this.materialImageContentType = materialImageContentType;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public Product isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Product isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getProductName() {
        return this.productName;
    }

    public Product productName(String productName) {
        this.setProductName(productName);
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getAlertUnits() {
        return this.alertUnits;
    }

    public Product alertUnits(String alertUnits) {
        this.setAlertUnits(alertUnits);
        return this;
    }

    public void setAlertUnits(String alertUnits) {
        this.alertUnits = alertUnits;
    }

    public String getCasNumber() {
        return this.casNumber;
    }

    public Product casNumber(String casNumber) {
        this.setCasNumber(casNumber);
        return this;
    }

    public void setCasNumber(String casNumber) {
        this.casNumber = casNumber;
    }

    public String getCatlogNumber() {
        return this.catlogNumber;
    }

    public Product catlogNumber(String catlogNumber) {
        this.setCatlogNumber(catlogNumber);
        return this;
    }

    public void setCatlogNumber(String catlogNumber) {
        this.catlogNumber = catlogNumber;
    }

    public Double getMolecularWt() {
        return this.molecularWt;
    }

    public Product molecularWt(Double molecularWt) {
        this.setMolecularWt(molecularWt);
        return this;
    }

    public void setMolecularWt(Double molecularWt) {
        this.molecularWt = molecularWt;
    }

    public String getMolecularFormula() {
        return this.molecularFormula;
    }

    public Product molecularFormula(String molecularFormula) {
        this.setMolecularFormula(molecularFormula);
        return this;
    }

    public void setMolecularFormula(String molecularFormula) {
        this.molecularFormula = molecularFormula;
    }

    public String getChemicalName() {
        return this.chemicalName;
    }

    public Product chemicalName(String chemicalName) {
        this.setChemicalName(chemicalName);
        return this;
    }

    public void setChemicalName(String chemicalName) {
        this.chemicalName = chemicalName;
    }

    public String getStructureImg() {
        return this.structureImg;
    }

    public Product structureImg(String structureImg) {
        this.setStructureImg(structureImg);
        return this;
    }

    public void setStructureImg(String structureImg) {
        this.structureImg = structureImg;
    }

    public String getDescription() {
        return this.description;
    }

    public Product description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQrCode() {
        return this.qrCode;
    }

    public Product qrCode(String qrCode) {
        this.setQrCode(qrCode);
        return this;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getBarCode() {
        return this.barCode;
    }

    public Product barCode(String barCode) {
        this.setBarCode(barCode);
        return this;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Double getGstPercentage() {
        return this.gstPercentage;
    }

    public Product gstPercentage(Double gstPercentage) {
        this.setGstPercentage(gstPercentage);
        return this;
    }

    public void setGstPercentage(Double gstPercentage) {
        this.gstPercentage = gstPercentage;
    }

    public ProductType getProductType() {
        return this.productType;
    }

    public Product productType(ProductType productType) {
        this.setProductType(productType);
        return this;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public String getLastModified() {
        return this.lastModified;
    }

    public Product lastModified(String lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Product lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public Product freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public Product freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public Set<TransferDetails> getTransferDetails() {
        return this.transferDetails;
    }

    public void setTransferDetails(Set<TransferDetails> transferDetails) {
        if (this.transferDetails != null) {
            this.transferDetails.forEach(i -> i.setProduct(null));
        }
        if (transferDetails != null) {
            transferDetails.forEach(i -> i.setProduct(this));
        }
        this.transferDetails = transferDetails;
    }

    public Product transferDetails(Set<TransferDetails> transferDetails) {
        this.setTransferDetails(transferDetails);
        return this;
    }

    public Product addTransferDetails(TransferDetails transferDetails) {
        this.transferDetails.add(transferDetails);
        transferDetails.setProduct(this);
        return this;
    }

    public Product removeTransferDetails(TransferDetails transferDetails) {
        this.transferDetails.remove(transferDetails);
        transferDetails.setProduct(null);
        return this;
    }

    public Categories getCategories() {
        return this.categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    public Product categories(Categories categories) {
        this.setCategories(categories);
        return this;
    }

    public Unit getUnit() {
        return this.unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Product unit(Unit unit) {
        this.setUnit(unit);
        return this;
    }

    public SecurityUser getSecurityUser() {
        return this.securityUser;
    }

    public void setSecurityUser(SecurityUser securityUser) {
        this.securityUser = securityUser;
    }

    public Product securityUser(SecurityUser securityUser) {
        this.setSecurityUser(securityUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", shortName='" + getShortName() + "'" +
            ", chemicalFormula='" + getChemicalFormula() + "'" +
            ", hsnNo='" + getHsnNo() + "'" +
            ", materialImage='" + getMaterialImage() + "'" +
            ", materialImageContentType='" + getMaterialImageContentType() + "'" +
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
            "}";
    }
}
