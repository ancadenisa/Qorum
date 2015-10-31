package com.qorum.web.rest.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Organisation entity.
 */
public class OrganisationDTO implements Serializable {

    private Long id;

    private String name;

    private String address;

    private Long fk_org_userId;

    private String fk_org_userLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getFk_org_userId() {
        return fk_org_userId;
    }

    public void setFk_org_userId(Long userId) {
        this.fk_org_userId = userId;
    }

    public String getFk_org_userLogin() {
        return fk_org_userLogin;
    }

    public void setFk_org_userLogin(String userLogin) {
        this.fk_org_userLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrganisationDTO organisationDTO = (OrganisationDTO) o;

        if ( ! Objects.equals(id, organisationDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OrganisationDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", address='" + address + "'" +
            '}';
    }
}
