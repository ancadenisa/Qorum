package com.qorum.repository;

import com.qorum.domain.Organisation;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Organisation entity.
 */
public interface OrganisationRepository extends JpaRepository<Organisation,Long> {

    @Query("select organisation from Organisation organisation where organisation.fk_org_user.login = ?#{principal.username}")
    List<Organisation> findByFk_org_userIsCurrentUser();

}
