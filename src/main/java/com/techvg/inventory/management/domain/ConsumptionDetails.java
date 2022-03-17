package com.techvg.inventory.management.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ConsumptionDetails.
 */
@Entity
@Table(name = "consumption_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ConsumptionDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "comsumption_date")
    private Instant comsumptionDate;

    @Column(name = "qty_consumed")
    private Double qtyConsumed;

    @Column(name = "free_field_1")
    private String freeField1;

    @Column(name = "free_field_2")
    private String freeField2;

    @Column(name = "last_modified")
    private String lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = { "securityPermissions", "securityRoles", "wareHouses" }, allowSetters = true)
    private SecurityUser securityUser;

    @ManyToOne
    private Project project;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "consumptionDetails", "product", "productTransaction", "securityUser", "wareHouse" },
        allowSetters = true
    )
    private ProductInventory productInventory;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ConsumptionDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getComsumptionDate() {
        return this.comsumptionDate;
    }

    public ConsumptionDetails comsumptionDate(Instant comsumptionDate) {
        this.setComsumptionDate(comsumptionDate);
        return this;
    }

    public void setComsumptionDate(Instant comsumptionDate) {
        this.comsumptionDate = comsumptionDate;
    }

    public Double getQtyConsumed() {
        return this.qtyConsumed;
    }

    public ConsumptionDetails qtyConsumed(Double qtyConsumed) {
        this.setQtyConsumed(qtyConsumed);
        return this;
    }

    public void setQtyConsumed(Double qtyConsumed) {
        this.qtyConsumed = qtyConsumed;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public ConsumptionDetails freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public ConsumptionDetails freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getLastModified() {
        return this.lastModified;
    }

    public ConsumptionDetails lastModified(String lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public ConsumptionDetails lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public SecurityUser getSecurityUser() {
        return this.securityUser;
    }

    public void setSecurityUser(SecurityUser securityUser) {
        this.securityUser = securityUser;
    }

    public ConsumptionDetails securityUser(SecurityUser securityUser) {
        this.setSecurityUser(securityUser);
        return this;
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public ConsumptionDetails project(Project project) {
        this.setProject(project);
        return this;
    }

    public ProductInventory getProductInventory() {
        return this.productInventory;
    }

    public void setProductInventory(ProductInventory productInventory) {
        this.productInventory = productInventory;
    }

    public ConsumptionDetails productInventory(ProductInventory productInventory) {
        this.setProductInventory(productInventory);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConsumptionDetails)) {
            return false;
        }
        return id != null && id.equals(((ConsumptionDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConsumptionDetails{" +
            "id=" + getId() +
            ", comsumptionDate='" + getComsumptionDate() + "'" +
            ", qtyConsumed=" + getQtyConsumed() +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
