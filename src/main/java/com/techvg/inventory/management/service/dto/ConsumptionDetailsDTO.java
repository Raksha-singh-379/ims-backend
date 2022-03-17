package com.techvg.inventory.management.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.inventory.management.domain.ConsumptionDetails} entity.
 */
public class ConsumptionDetailsDTO implements Serializable {

    private Long id;

    private Instant comsumptionDate;

    private Double qtyConsumed;

    private String freeField1;

    private String freeField2;

    private String lastModified;

    private String lastModifiedBy;

    private SecurityUserDTO securityUser;

    private ProjectDTO project;

    private ProductInventoryDTO productInventory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getComsumptionDate() {
        return comsumptionDate;
    }

    public void setComsumptionDate(Instant comsumptionDate) {
        this.comsumptionDate = comsumptionDate;
    }

    public Double getQtyConsumed() {
        return qtyConsumed;
    }

    public void setQtyConsumed(Double qtyConsumed) {
        this.qtyConsumed = qtyConsumed;
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

    public SecurityUserDTO getSecurityUser() {
        return securityUser;
    }

    public void setSecurityUser(SecurityUserDTO securityUser) {
        this.securityUser = securityUser;
    }

    public ProjectDTO getProject() {
        return project;
    }

    public void setProject(ProjectDTO project) {
        this.project = project;
    }

    public ProductInventoryDTO getProductInventory() {
        return productInventory;
    }

    public void setProductInventory(ProductInventoryDTO productInventory) {
        this.productInventory = productInventory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConsumptionDetailsDTO)) {
            return false;
        }

        ConsumptionDetailsDTO consumptionDetailsDTO = (ConsumptionDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, consumptionDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConsumptionDetailsDTO{" +
            "id=" + getId() +
            ", comsumptionDate='" + getComsumptionDate() + "'" +
            ", qtyConsumed=" + getQtyConsumed() +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", securityUser=" + getSecurityUser() +
            ", project=" + getProject() +
            ", productInventory=" + getProductInventory() +
            "}";
    }
}
