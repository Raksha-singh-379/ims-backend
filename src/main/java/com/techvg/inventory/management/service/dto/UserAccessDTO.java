package com.techvg.inventory.management.service.dto;

import com.techvg.inventory.management.domain.enumeration.AccessLevel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.inventory.management.domain.UserAccess} entity.
 */
public class UserAccessDTO implements Serializable {

    private Long id;

    private AccessLevel level;

    private Long accessId;

    private String lastModified;

    private String lastModifiedBy;

    private SecurityUserDTO securityUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccessLevel getLevel() {
        return level;
    }

    public void setLevel(AccessLevel level) {
        this.level = level;
    }

    public Long getAccessId() {
        return accessId;
    }

    public void setAccessId(Long accessId) {
        this.accessId = accessId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserAccessDTO)) {
            return false;
        }

        UserAccessDTO userAccessDTO = (UserAccessDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, userAccessDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserAccessDTO{" +
            "id=" + getId() +
            ", level='" + getLevel() + "'" +
            ", accessId=" + getAccessId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", securityUser=" + getSecurityUser() +
            "}";
    }
}
