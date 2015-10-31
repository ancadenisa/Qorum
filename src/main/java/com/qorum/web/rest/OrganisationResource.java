package com.qorum.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.qorum.domain.Organisation;
import com.qorum.repository.OrganisationRepository;
import com.qorum.web.rest.util.HeaderUtil;
import com.qorum.web.rest.util.PaginationUtil;
import com.qorum.web.rest.dto.OrganisationDTO;
import com.qorum.web.rest.mapper.OrganisationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Organisation.
 */
@RestController
@RequestMapping("/api")
public class OrganisationResource {

    private final Logger log = LoggerFactory.getLogger(OrganisationResource.class);

    @Inject
    private OrganisationRepository organisationRepository;

    @Inject
    private OrganisationMapper organisationMapper;

    /**
     * POST  /organisations -> Create a new organisation.
     */
    @RequestMapping(value = "/organisations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OrganisationDTO> createOrganisation(@RequestBody OrganisationDTO organisationDTO) throws URISyntaxException {
        log.debug("REST request to save Organisation : {}", organisationDTO);
        if (organisationDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new organisation cannot already have an ID").body(null);
        }
        Organisation organisation = organisationMapper.organisationDTOToOrganisation(organisationDTO);
        Organisation result = organisationRepository.save(organisation);
        return ResponseEntity.created(new URI("/api/organisations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("organisation", result.getId().toString()))
            .body(organisationMapper.organisationToOrganisationDTO(result));
    }

    /**
     * PUT  /organisations -> Updates an existing organisation.
     */
    @RequestMapping(value = "/organisations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OrganisationDTO> updateOrganisation(@RequestBody OrganisationDTO organisationDTO) throws URISyntaxException {
        log.debug("REST request to update Organisation : {}", organisationDTO);
        if (organisationDTO.getId() == null) {
            return createOrganisation(organisationDTO);
        }
        Organisation organisation = organisationMapper.organisationDTOToOrganisation(organisationDTO);
        Organisation result = organisationRepository.save(organisation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("organisation", organisationDTO.getId().toString()))
            .body(organisationMapper.organisationToOrganisationDTO(result));
    }

    /**
     * GET  /organisations -> get all the organisations.
     */
    @RequestMapping(value = "/organisations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<OrganisationDTO>> getAllOrganisations(Pageable pageable)
        throws URISyntaxException {
        Page<Organisation> page = organisationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/organisations");
        return new ResponseEntity<>(page.getContent().stream()
            .map(organisationMapper::organisationToOrganisationDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /organisations/:id -> get the "id" organisation.
     */
    @RequestMapping(value = "/organisations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OrganisationDTO> getOrganisation(@PathVariable Long id) {
        log.debug("REST request to get Organisation : {}", id);
        return Optional.ofNullable(organisationRepository.findOne(id))
            .map(organisationMapper::organisationToOrganisationDTO)
            .map(organisationDTO -> new ResponseEntity<>(
                organisationDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /organisations/:id -> delete the "id" organisation.
     */
    @RequestMapping(value = "/organisations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteOrganisation(@PathVariable Long id) {
        log.debug("REST request to delete Organisation : {}", id);
        organisationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("organisation", id.toString())).build();
    }
}
