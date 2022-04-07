package com.techvg.inventory.management.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PurchaseQuotationDetails.
 */
@Entity
@Table(name = "purchase_quotation_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PurchaseQuotationDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "qtyordered")
    private Double qtyordered;

    @Column(name = "gst_tax_percentage")
    private Integer gstTaxPercentage;

    @Column(name = "price_per_unit")
    private Double pricePerUnit;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "last_modified")
    private String lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "free_field_1")
    private String freeField1;

    @Column(name = "free_field_2")
    private String freeField2;

    @ManyToOne
    @JsonIgnoreProperties(value = { "transferDetails", "categories", "unit", "securityUser" }, allowSetters = true)
    private Product product;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "purchaseQuotationDetails", "goodsReciveds", "securityUser", "project", "clientDetails" },
        allowSetters = true
    )
    private PurchaseQuotation purchaseQuotation;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PurchaseQuotationDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQtyordered() {
        return this.qtyordered;
    }

    public PurchaseQuotationDetails qtyordered(Double qtyordered) {
        this.setQtyordered(qtyordered);
        return this;
    }

    public void setQtyordered(Double qtyordered) {
        this.qtyordered = qtyordered;
    }

    public Integer getGstTaxPercentage() {
        return this.gstTaxPercentage;
    }

    public PurchaseQuotationDetails gstTaxPercentage(Integer gstTaxPercentage) {
        this.setGstTaxPercentage(gstTaxPercentage);
        return this;
    }

    public void setGstTaxPercentage(Integer gstTaxPercentage) {
        this.gstTaxPercentage = gstTaxPercentage;
    }

    public Double getPricePerUnit() {
        return this.pricePerUnit;
    }

    public PurchaseQuotationDetails pricePerUnit(Double pricePerUnit) {
        this.setPricePerUnit(pricePerUnit);
        return this;
    }

    public void setPricePerUnit(Double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public Double getTotalPrice() {
        return this.totalPrice;
    }

    public PurchaseQuotationDetails totalPrice(Double totalPrice) {
        this.setTotalPrice(totalPrice);
        return this;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getDiscount() {
        return this.discount;
    }

    public PurchaseQuotationDetails discount(Double discount) {
        this.setDiscount(discount);
        return this;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getLastModified() {
        return this.lastModified;
    }

    public PurchaseQuotationDetails lastModified(String lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public PurchaseQuotationDetails lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public PurchaseQuotationDetails freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public PurchaseQuotationDetails freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public PurchaseQuotationDetails product(Product product) {
        this.setProduct(product);
        return this;
    }

    public PurchaseQuotation getPurchaseQuotation() {
        return this.purchaseQuotation;
    }

    public void setPurchaseQuotation(PurchaseQuotation purchaseQuotation) {
        this.purchaseQuotation = purchaseQuotation;
    }

    public PurchaseQuotationDetails purchaseQuotation(PurchaseQuotation purchaseQuotation) {
        this.setPurchaseQuotation(purchaseQuotation);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PurchaseQuotationDetails)) {
            return false;
        }
        return id != null && id.equals(((PurchaseQuotationDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PurchaseQuotationDetails{" +
            "id=" + getId() +
            ", qtyordered=" + getQtyordered() +
            ", gstTaxPercentage=" + getGstTaxPercentage() +
            ", pricePerUnit=" + getPricePerUnit() +
            ", totalPrice=" + getTotalPrice() +
            ", discount=" + getDiscount() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            "}";
    }
}
