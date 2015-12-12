package com.qorum.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.qorum.domain.Organization;
import com.qorum.repository.OrganizationRepository;
import com.qorum.security.SecurityUtils;
import com.qorum.service.OrganizationService;
import com.qorum.service.UserService;
import com.qorum.web.rest.util.HeaderUtil;
import com.qorum.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Organization.
 */
@RestController
@RequestMapping("/api")
public class OrganizationResource {

    private final Logger log = LoggerFactory.getLogger(OrganizationResource.class);

    @Inject
    private OrganizationRepository organizationRepository;
    @Inject
    private UserService userService;

    @Inject
    OrganizationService organizationService;
    /**
     * POST  /organizations -> Create a new organization.
     */
    @RequestMapping(value = "/organizations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Organization> createOrganization(@RequestBody Organization organization) throws URISyntaxException {
        log.debug("REST request to save Organization : {}", organization);
        if (organization.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new organization cannot already have an ID").body(null);
        }
        Organization result = organizationRepository.save(organization);
        return ResponseEntity.created(new URI("/api/organizations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("organization", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /organizations -> Updates an existing organization.
     */
    @RequestMapping(value = "/organizations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Organization> updateOrganization(@RequestBody Organization organization) throws URISyntaxException {
        log.debug("REST request to update Organization : {}", organization);
        if (organization.getId() == null) {
            return createOrganization(organization);
        }
        Organization result = organizationRepository.save(organization);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("organization", organization.getId().toString()))
            .body(result);
    }

    /**
     * GET  /organizations -> get all the organizations.
     */
    @RequestMapping(value = "/organizations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Organization>> getAllOrganizations(Pageable pageable)
        throws URISyntaxException {
        Page<Organization> page = organizationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/organizations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /organizations/:id -> get the "id" organization.
     */
    @RequestMapping(value = "/organizations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Organization> getOrganization(@PathVariable Long id) {
        log.debug("REST request to get Organization : {}", id);
        return Optional.ofNullable(organizationRepository.findOneWithEagerRelationships(id))
            .map(organization -> new ResponseEntity<>(
                organization,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /organizations/:id -> delete the "id" organization.
     */
    //TO DO ANCa - stergerea nu functioneaza ...
    @RequestMapping(value = "/organizations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteOrganization(@PathVariable Long id) {
        log.debug("REST request to delete Organization : {}", id);
        organizationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("organization", id.toString())).build();
    }

    @RequestMapping(value = "/organizations-section/getOrganizations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Organization>> getOrganizationsByUserId(@PathVariable Long id) {
        log.debug("REST request to get Organizations of User with the id : {}", id);
        List<Organization> organizationList = organizationService.getOrganizationsByUserLoggedId(id);
        return new ResponseEntity<>(organizationList, HttpStatus.OK);
    }
    @RequestMapping(value = "/organtizations/filteredByLoggedUserAdmin",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Organization>> getOrganizationsFilteredByLoggedUserAdmin() {
        log.debug("REST request to get Organizations of current user admin");

        List<Organization> organizationList = organizationRepository.getOrganizationsByOrgAdmin(userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin()).get().getId());
        return new ResponseEntity<>(organizationList, HttpStatus.OK);
    }
}
