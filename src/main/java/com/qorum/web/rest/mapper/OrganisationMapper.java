package com.qorum.web.rest.mapper;

import com.qorum.domain.*;
import com.qorum.web.rest.dto.OrganisationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Organisation and its DTO OrganisationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrganisationMapper {

    @Mapping(source = "fk_org_user.id", target = "fk_org_userId")
    @Mapping(source = "fk_org_user.login", target = "fk_org_userLogin")
    OrganisationDTO organisationToOrganisationDTO(Organisation organisation);

    @Mapping(source = "fk_org_userId", target = "fk_org_user")
    Organisation organisationDTOToOrganisation(OrganisationDTO organisationDTO);

    default User userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}
