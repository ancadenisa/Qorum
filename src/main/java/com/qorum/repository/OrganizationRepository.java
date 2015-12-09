package com.qorum.repository;

import com.qorum.domain.Organization;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Organization entity.
 */
public interface OrganizationRepository extends JpaRepository<Organization,Long> {

    @Query("select organization from Organization organization where organization.orgAdmin.login = ?#{principal.username}")
    List<Organization> findByOrgAdminIsCurrentUser();

    @Query("select distinct organization from Organization organization left join fetch organization.users")
    List<Organization> findAllWithEagerRelationships();

    @Query("select organization from Organization organization left join fetch organization.users where organization.id =:id")
    Organization findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select distinct organization from Organization organization, User user join organization.users user where user.id = :userId or organization.orgAdmin.id = :userId")
    List<Organization> getOrganizationsByUserLoggedId(@Param("userId") Long userId);
}
