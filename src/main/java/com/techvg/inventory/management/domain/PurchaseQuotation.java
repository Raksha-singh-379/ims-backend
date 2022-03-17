package com.techvg.inventory.management.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.techvg.inventory.management.domain.enumeration.OrderType;
import com.techvg.inventory.management.domain.enumeration.Status;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PurchaseQuotation.
 */
@Entity
@Table(name = "purchase_quotation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PurchaseQuotation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "refrence_number")
    private String refrenceNumber;

    @Column(name = "total_po_amount")
    private Double totalPOAmount;

    @Column(name = "total_gst_amount")
    private Double totalGSTAmount;

    @Column(name = "expected_delivery_date")
    private Instant expectedDeliveryDate;

    @Column(name = "po_date")
    private Instant poDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_type")
    private OrderType orderType;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private Status orderStatus;

    @Column(name = "terms_and_condition")
    private String termsAndCondition;

    @Column(name = "notes")
    private String notes;

    @Column(name = "last_modified")
    private String lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "free_field_1")
    private String freeField1;

    @Column(name = "free_field_2")
    private String freeField2;

    @OneToMany(mappedBy = "purchaseQuotation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "products", "purchaseQuotation" }, allowSetters = true)
    private Set<PurchaseQuotationDetails> purchaseQuotationDetails = new HashSet<>();

    @OneToMany(mappedBy = "purchaseQuotation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "purchaseQuotation" }, allowSetters = true)
    private Set<GoodsRecived> goodsReciveds = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "securityPermissions", "securityRoles", "wareHouses" }, allowSetters = true)
    private SecurityUser securityUser;

    @ManyToOne
    private Project project;

    @ManyToOne
    private ClientDetails clientDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PurchaseQuotation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRefrenceNumber() {
        return this.refrenceNumber;
    }

    public PurchaseQuotation refrenceNumber(String refrenceNumber) {
        this.setRefrenceNumber(refrenceNumber);
        return this;
    }

    public void setRefrenceNumber(String refrenceNumber) {
        this.refrenceNumber = refrenceNumber;
    }

    public Double getTotalPOAmount() {
        return this.totalPOAmount;
    }

    public PurchaseQuotation totalPOAmount(Double totalPOAmount) {
        this.setTotalPOAmount(totalPOAmount);
        return this;
    }

    public void setTotalPOAmount(Double totalPOAmount) {
        this.totalPOAmount = totalPOAmount;
    }

    public Double getTotalGSTAmount() {
        return this.totalGSTAmount;
    }

    public PurchaseQuotation totalGSTAmount(Double totalGSTAmount) {
        this.setTotalGSTAmount(totalGSTAmount);
        return this;
    }

    public void setTotalGSTAmount(Double totalGSTAmount) {
        this.totalGSTAmount = totalGSTAmount;
    }

    public Instant getExpectedDeliveryDate() {
        return this.expectedDeliveryDate;
    }

    public PurchaseQuotation expectedDeliveryDate(Instant expectedDeliveryDate) {
        this.setExpectedDeliveryDate(expectedDeliveryDate);
        return this;
    }

    public void setExpectedDeliveryDate(Instant expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public Instant getPoDate() {
        return this.poDate;
    }

    public PurchaseQuotation poDate(Instant poDate) {
        this.setPoDate(poDate);
        return this;
    }

    public void setPoDate(Instant poDate) {
        this.poDate = poDate;
    }

    public OrderType getOrderType() {
        return this.orderType;
    }

    public PurchaseQuotation orderType(OrderType orderType) {
        this.setOrderType(orderType);
        return this;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public Status getOrderStatus() {
        return this.orderStatus;
    }

    public PurchaseQuotation orderStatus(Status orderStatus) {
        this.setOrderStatus(orderStatus);
        return this;
    }

    public void setOrderStatus(Status orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getTermsAndCondition() {
        return this.termsAndCondition;
    }

    public PurchaseQuotation termsAndCondition(String termsAndCondition) {
        this.setTermsAndCondition(termsAndCondition);
        return this;
    }

    public void setTermsAndCondition(String termsAndCondition) {
        this.termsAndCondition = termsAndCondition;
    }

    public String getNotes() {
        return this.notes;
    }

    public PurchaseQuotation notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getLastModified() {
        return this.lastModified;
    }

    public PurchaseQuotation lastModified(String lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public PurchaseQuotation lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public PurchaseQuotation freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public PurchaseQuotation freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public Set<PurchaseQuotationDetails> getPurchaseQuotationDetails() {
        return this.purchaseQuotationDetails;
    }

    public void setPurchaseQuotationDetails(Set<PurchaseQuotationDetails> purchaseQuotationDetails) {
        if (this.purchaseQuotationDetails != null) {
            this.purchaseQuotationDetails.forEach(i -> i.setPurchaseQuotation(null));
        }
        if (purchaseQuotationDetails != null) {
            purchaseQuotationDetails.forEach(i -> i.setPurchaseQuotation(this));
        }
        this.purchaseQuotationDetails = purchaseQuotationDetails;
    }

    public PurchaseQuotation purchaseQuotationDetails(Set<PurchaseQuotationDetails> purchaseQuotationDetails) {
        this.setPurchaseQuotationDetails(purchaseQuotationDetails);
        return this;
    }

    public PurchaseQuotation addPurchaseQuotationDetails(PurchaseQuotationDetails purchaseQuotationDetails) {
        this.purchaseQuotationDetails.add(purchaseQuotationDetails);
        purchaseQuotationDetails.setPurchaseQuotation(this);
        return this;
    }

    public PurchaseQuotation removePurchaseQuotationDetails(PurchaseQuotationDetails purchaseQuotationDetails) {
        this.purchaseQuotationDetails.remove(purchaseQuotationDetails);
        purchaseQuotationDetails.setPurchaseQuotation(null);
        return this;
    }

    public Set<GoodsRecived> getGoodsReciveds() {
        return this.goodsReciveds;
    }

    public void setGoodsReciveds(Set<GoodsRecived> goodsReciveds) {
        if (this.goodsReciveds != null) {
            this.goodsReciveds.forEach(i -> i.setPurchaseQuotation(null));
        }
        if (goodsReciveds != null) {
            goodsReciveds.forEach(i -> i.setPurchaseQuotation(this));
        }
        this.goodsReciveds = goodsReciveds;
    }

    public PurchaseQuotation goodsReciveds(Set<GoodsRecived> goodsReciveds) {
        this.setGoodsReciveds(goodsReciveds);
        return this;
    }

    public PurchaseQuotation addGoodsRecived(GoodsRecived goodsRecived) {
        this.goodsReciveds.add(goodsRecived);
        goodsRecived.setPurchaseQuotation(this);
        return this;
    }

    public PurchaseQuotation removeGoodsRecived(GoodsRecived goodsRecived) {
        this.goodsReciveds.remove(goodsRecived);
        goodsRecived.setPurchaseQuotation(null);
        return this;
    }

    public SecurityUser getSecurityUser() {
        return this.securityUser;
    }

    public void setSecurityUser(SecurityUser securityUser) {
        this.securityUser = securityUser;
    }

    public PurchaseQuotation securityUser(SecurityUser securityUser) {
        this.setSecurityUser(securityUser);
        return this;
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public PurchaseQuotation project(Project project) {
        this.setProject(project);
        return this;
    }

    public ClientDetails getClientDetails() {
        return this.clientDetails;
    }

    public void setClientDetails(ClientDetails clientDetails) {
        this.clientDetails = clientDetails;
    }

    public PurchaseQuotation clientDetails(ClientDetails clientDetails) {
        this.setClientDetails(clientDetails);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PurchaseQuotation)) {
            return false;
        }
        return id != null && id.equals(((PurchaseQuotation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PurchaseQuotation{" +
            "id=" + getId() +
            ", refrenceNumber='" + getRefrenceNumber() + "'" +
            ", totalPOAmount=" + getTotalPOAmount() +
            ", totalGSTAmount=" + getTotalGSTAmount() +
            ", expectedDeliveryDate='" + getExpectedDeliveryDate() + "'" +
            ", poDate='" + getPoDate() + "'" +
            ", orderType='" + getOrderType() + "'" +
            ", orderStatus='" + getOrderStatus() + "'" +
            ", termsAndCondition='" + getTermsAndCondition() + "'" +
            ", notes='" + getNotes() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            "}";
    }
}
