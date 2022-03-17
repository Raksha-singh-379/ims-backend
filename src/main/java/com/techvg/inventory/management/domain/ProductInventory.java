package com.techvg.inventory.management.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ProductInventory.
 */
@Entity
@Table(name = "product_inventory")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductInventory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "inward_outward_date")
    private Instant inwardOutwardDate;

    @Column(name = "inward_qty")
    private String inwardQty;

    @Column(name = "outward_qty")
    private String outwardQty;

    @Column(name = "total_quanity")
    private String totalQuanity;

    @Column(name = "price_per_unit")
    private Long pricePerUnit;

    @Column(name = "lot_no")
    private String lotNo;

    @Column(name = "expiry_date")
    private Instant expiryDate;

    @Column(name = "inventory_type_id")
    private String inventoryTypeId;

    @Column(name = "free_field_1")
    private String freeField1;

    @Column(name = "free_field_2")
    private String freeField2;

    @Column(name = "last_modified")
    private String lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy = "productInventory")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "securityUser", "project", "productInventory" }, allowSetters = true)
    private Set<ConsumptionDetails> consumptionDetails = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "transferDetails", "categories", "unit", "securityUser", "purchaseQuotationDetails" },
        allowSetters = true
    )
    private Product product;

    @ManyToOne
    @JsonIgnoreProperties(value = { "securityUser", "wareHouse" }, allowSetters = true)
    private ProductTransaction productTransaction;

    @ManyToOne
    @JsonIgnoreProperties(value = { "securityPermissions", "securityRoles", "wareHouses" }, allowSetters = true)
    private SecurityUser securityUser;

    @ManyToOne
    @JsonIgnoreProperties(value = { "securityUsers" }, allowSetters = true)
    private WareHouse wareHouse;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProductInventory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getInwardOutwardDate() {
        return this.inwardOutwardDate;
    }

    public ProductInventory inwardOutwardDate(Instant inwardOutwardDate) {
        this.setInwardOutwardDate(inwardOutwardDate);
        return this;
    }

    public void setInwardOutwardDate(Instant inwardOutwardDate) {
        this.inwardOutwardDate = inwardOutwardDate;
    }

    public String getInwardQty() {
        return this.inwardQty;
    }

    public ProductInventory inwardQty(String inwardQty) {
        this.setInwardQty(inwardQty);
        return this;
    }

    public void setInwardQty(String inwardQty) {
        this.inwardQty = inwardQty;
    }

    public String getOutwardQty() {
        return this.outwardQty;
    }

    public ProductInventory outwardQty(String outwardQty) {
        this.setOutwardQty(outwardQty);
        return this;
    }

    public void setOutwardQty(String outwardQty) {
        this.outwardQty = outwardQty;
    }

    public String getTotalQuanity() {
        return this.totalQuanity;
    }

    public ProductInventory totalQuanity(String totalQuanity) {
        this.setTotalQuanity(totalQuanity);
        return this;
    }

    public void setTotalQuanity(String totalQuanity) {
        this.totalQuanity = totalQuanity;
    }

    public Long getPricePerUnit() {
        return this.pricePerUnit;
    }

    public ProductInventory pricePerUnit(Long pricePerUnit) {
        this.setPricePerUnit(pricePerUnit);
        return this;
    }

    public void setPricePerUnit(Long pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public String getLotNo() {
        return this.lotNo;
    }

    public ProductInventory lotNo(String lotNo) {
        this.setLotNo(lotNo);
        return this;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    public Instant getExpiryDate() {
        return this.expiryDate;
    }

    public ProductInventory expiryDate(Instant expiryDate) {
        this.setExpiryDate(expiryDate);
        return this;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getInventoryTypeId() {
        return this.inventoryTypeId;
    }

    public ProductInventory inventoryTypeId(String inventoryTypeId) {
        this.setInventoryTypeId(inventoryTypeId);
        return this;
    }

    public void setInventoryTypeId(String inventoryTypeId) {
        this.inventoryTypeId = inventoryTypeId;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public ProductInventory freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public ProductInventory freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getLastModified() {
        return this.lastModified;
    }

    public ProductInventory lastModified(String lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public ProductInventory lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public ProductInventory isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public ProductInventory isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Set<ConsumptionDetails> getConsumptionDetails() {
        return this.consumptionDetails;
    }

    public void setConsumptionDetails(Set<ConsumptionDetails> consumptionDetails) {
        if (this.consumptionDetails != null) {
            this.consumptionDetails.forEach(i -> i.setProductInventory(null));
        }
        if (consumptionDetails != null) {
            consumptionDetails.forEach(i -> i.setProductInventory(this));
        }
        this.consumptionDetails = consumptionDetails;
    }

    public ProductInventory consumptionDetails(Set<ConsumptionDetails> consumptionDetails) {
        this.setConsumptionDetails(consumptionDetails);
        return this;
    }

    public ProductInventory addConsumptionDetails(ConsumptionDetails consumptionDetails) {
        this.consumptionDetails.add(consumptionDetails);
        consumptionDetails.setProductInventory(this);
        return this;
    }

    public ProductInventory removeConsumptionDetails(ConsumptionDetails consumptionDetails) {
        this.consumptionDetails.remove(consumptionDetails);
        consumptionDetails.setProductInventory(null);
        return this;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductInventory product(Product product) {
        this.setProduct(product);
        return this;
    }

    public ProductTransaction getProductTransaction() {
        return this.productTransaction;
    }

    public void setProductTransaction(ProductTransaction productTransaction) {
        this.productTransaction = productTransaction;
    }

    public ProductInventory productTransaction(ProductTransaction productTransaction) {
        this.setProductTransaction(productTransaction);
        return this;
    }

    public SecurityUser getSecurityUser() {
        return this.securityUser;
    }

    public void setSecurityUser(SecurityUser securityUser) {
        this.securityUser = securityUser;
    }

    public ProductInventory securityUser(SecurityUser securityUser) {
        this.setSecurityUser(securityUser);
        return this;
    }

    public WareHouse getWareHouse() {
        return this.wareHouse;
    }

    public void setWareHouse(WareHouse wareHouse) {
        this.wareHouse = wareHouse;
    }

    public ProductInventory wareHouse(WareHouse wareHouse) {
        this.setWareHouse(wareHouse);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductInventory)) {
            return false;
        }
        return id != null && id.equals(((ProductInventory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductInventory{" +
            "id=" + getId() +
            ", inwardOutwardDate='" + getInwardOutwardDate() + "'" +
            ", inwardQty='" + getInwardQty() + "'" +
            ", outwardQty='" + getOutwardQty() + "'" +
            ", totalQuanity='" + getTotalQuanity() + "'" +
            ", pricePerUnit=" + getPricePerUnit() +
            ", lotNo='" + getLotNo() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", inventoryTypeId='" + getInventoryTypeId() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", isActive='" + getIsActive() + "'" +
            "}";
    }
}
