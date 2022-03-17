package com.techvg.inventory.management.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.techvg.inventory.management.domain.enumeration.Status;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Transfer.
 */
@Entity
@Table(name = "transfer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Transfer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tranfer_date")
    private Instant tranferDate;

    @Column(name = "comment")
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "source_ware_house")
    private String sourceWareHouse;

    @Column(name = "destination_ware_house")
    private String destinationWareHouse;

    @Column(name = "free_field_1")
    private String freeField1;

    @Column(name = "last_modified")
    private String lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @OneToMany(mappedBy = "transfer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "product", "transfer" }, allowSetters = true)
    private Set<TransferDetails> transferDetails = new HashSet<>();

    @OneToMany(mappedBy = "transfer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "securityUser", "transfer" }, allowSetters = true)
    private Set<TransferRecieved> transferRecieveds = new HashSet<>();

    @OneToMany(mappedBy = "transfer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "securityUser", "transfer" }, allowSetters = true)
    private Set<TransferDetailsApprovals> transferDetailsApprovals = new HashSet<>();

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

    public Transfer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getTranferDate() {
        return this.tranferDate;
    }

    public Transfer tranferDate(Instant tranferDate) {
        this.setTranferDate(tranferDate);
        return this;
    }

    public void setTranferDate(Instant tranferDate) {
        this.tranferDate = tranferDate;
    }

    public String getComment() {
        return this.comment;
    }

    public Transfer comment(String comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Status getStatus() {
        return this.status;
    }

    public Transfer status(Status status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getSourceWareHouse() {
        return this.sourceWareHouse;
    }

    public Transfer sourceWareHouse(String sourceWareHouse) {
        this.setSourceWareHouse(sourceWareHouse);
        return this;
    }

    public void setSourceWareHouse(String sourceWareHouse) {
        this.sourceWareHouse = sourceWareHouse;
    }

    public String getDestinationWareHouse() {
        return this.destinationWareHouse;
    }

    public Transfer destinationWareHouse(String destinationWareHouse) {
        this.setDestinationWareHouse(destinationWareHouse);
        return this;
    }

    public void setDestinationWareHouse(String destinationWareHouse) {
        this.destinationWareHouse = destinationWareHouse;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public Transfer freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getLastModified() {
        return this.lastModified;
    }

    public Transfer lastModified(String lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Transfer lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Set<TransferDetails> getTransferDetails() {
        return this.transferDetails;
    }

    public void setTransferDetails(Set<TransferDetails> transferDetails) {
        if (this.transferDetails != null) {
            this.transferDetails.forEach(i -> i.setTransfer(null));
        }
        if (transferDetails != null) {
            transferDetails.forEach(i -> i.setTransfer(this));
        }
        this.transferDetails = transferDetails;
    }

    public Transfer transferDetails(Set<TransferDetails> transferDetails) {
        this.setTransferDetails(transferDetails);
        return this;
    }

    public Transfer addTransferDetails(TransferDetails transferDetails) {
        this.transferDetails.add(transferDetails);
        transferDetails.setTransfer(this);
        return this;
    }

    public Transfer removeTransferDetails(TransferDetails transferDetails) {
        this.transferDetails.remove(transferDetails);
        transferDetails.setTransfer(null);
        return this;
    }

    public Set<TransferRecieved> getTransferRecieveds() {
        return this.transferRecieveds;
    }

    public void setTransferRecieveds(Set<TransferRecieved> transferRecieveds) {
        if (this.transferRecieveds != null) {
            this.transferRecieveds.forEach(i -> i.setTransfer(null));
        }
        if (transferRecieveds != null) {
            transferRecieveds.forEach(i -> i.setTransfer(this));
        }
        this.transferRecieveds = transferRecieveds;
    }

    public Transfer transferRecieveds(Set<TransferRecieved> transferRecieveds) {
        this.setTransferRecieveds(transferRecieveds);
        return this;
    }

    public Transfer addTransferRecieved(TransferRecieved transferRecieved) {
        this.transferRecieveds.add(transferRecieved);
        transferRecieved.setTransfer(this);
        return this;
    }

    public Transfer removeTransferRecieved(TransferRecieved transferRecieved) {
        this.transferRecieveds.remove(transferRecieved);
        transferRecieved.setTransfer(null);
        return this;
    }

    public Set<TransferDetailsApprovals> getTransferDetailsApprovals() {
        return this.transferDetailsApprovals;
    }

    public void setTransferDetailsApprovals(Set<TransferDetailsApprovals> transferDetailsApprovals) {
        if (this.transferDetailsApprovals != null) {
            this.transferDetailsApprovals.forEach(i -> i.setTransfer(null));
        }
        if (transferDetailsApprovals != null) {
            transferDetailsApprovals.forEach(i -> i.setTransfer(this));
        }
        this.transferDetailsApprovals = transferDetailsApprovals;
    }

    public Transfer transferDetailsApprovals(Set<TransferDetailsApprovals> transferDetailsApprovals) {
        this.setTransferDetailsApprovals(transferDetailsApprovals);
        return this;
    }

    public Transfer addTransferDetailsApprovals(TransferDetailsApprovals transferDetailsApprovals) {
        this.transferDetailsApprovals.add(transferDetailsApprovals);
        transferDetailsApprovals.setTransfer(this);
        return this;
    }

    public Transfer removeTransferDetailsApprovals(TransferDetailsApprovals transferDetailsApprovals) {
        this.transferDetailsApprovals.remove(transferDetailsApprovals);
        transferDetailsApprovals.setTransfer(null);
        return this;
    }

    public SecurityUser getSecurityUser() {
        return this.securityUser;
    }

    public void setSecurityUser(SecurityUser securityUser) {
        this.securityUser = securityUser;
    }

    public Transfer securityUser(SecurityUser securityUser) {
        this.setSecurityUser(securityUser);
        return this;
    }

    public WareHouse getWareHouse() {
        return this.wareHouse;
    }

    public void setWareHouse(WareHouse wareHouse) {
        this.wareHouse = wareHouse;
    }

    public Transfer wareHouse(WareHouse wareHouse) {
        this.setWareHouse(wareHouse);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transfer)) {
            return false;
        }
        return id != null && id.equals(((Transfer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Transfer{" +
            "id=" + getId() +
            ", tranferDate='" + getTranferDate() + "'" +
            ", comment='" + getComment() + "'" +
            ", status='" + getStatus() + "'" +
            ", sourceWareHouse='" + getSourceWareHouse() + "'" +
            ", destinationWareHouse='" + getDestinationWareHouse() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
