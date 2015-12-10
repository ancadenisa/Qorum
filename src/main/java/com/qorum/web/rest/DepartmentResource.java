package com.qorum.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.qorum.domain.Department;
import com.qorum.repository.DepartmentRepository;
import com.qorum.service.DepartmentService;
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
 * REST controller for managing Department.
 */
@RestController
@RequestMapping("/api")
public class DepartmentResource {

    private final Logger log = LoggerFactory.getLogger(DepartmentResource.class);

    @Inject
    private DepartmentRepository departmentRepository;

    @Inject
    private DepartmentService departmentService;

    /**
     * POST  /departments -> Create a new department.
     */
    @RequestMapping(value = "/departments",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) throws URISyntaxException {
        log.debug("REST request to save Department : {}", department);
        if (department.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new department cannot already have an ID").body(null);
        }
        Department result = departmentRepository.save(department);
        return ResponseEntity.created(new URI("/api/departments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("department", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /departments -> Updates an existing department.
     */
    @RequestMapping(value = "/departments",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Department> updateDepartment(@RequestBody Department department) throws URISyntaxException {
        log.debug("REST request to update Department : {}", department);
        if (department.getId() == null) {
            return createDepartment(department);
        }
        Department result = departmentRepository.save(department);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("department", department.getId().toString()))
            .body(result);
    }

    /**
     * GET  /departments -> get all the departments.
     */
    @RequestMapping(value = "/departments",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Department>> getAllDepartments(Pageable pageable)
        throws URISyntaxException {
        Page<Department> page = departmentRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/departments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /departments/:id -> get the "id" department.
     */
    @RequestMapping(value = "/departments/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Department> getDepartment(@PathVariable Long id) {
        log.debug("REST request to get Department : {}", id);
        return Optional.ofNullable(departmentRepository.findOneWithEagerRelationships(id))
            .map(department -> new ResponseEntity<>(
                department,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /departments/:id -> delete the "id" department.
     */
    @RequestMapping(value = "/departments/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        log.debug("REST request to delete Department : {}", id);
        departmentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("department", id.toString())).build();
    }

    @RequestMapping(value = "/organizations-section/getDepartments/{orgId}/{userId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Department>> getDepartmentsByUserIdAnByOrgId(@PathVariable Long orgId, @PathVariable Long userId) {
        log.debug("REST request to get Departments of User with the id ", userId, "and wich belong to Organization with the id ", orgId);
        List<Department> departmentList = departmentService.getDepartmentsByOrgAndByUserLogged(orgId, userId);
        return new ResponseEntity<>(departmentList, HttpStatus.OK);
    }

    @RequestMapping(value = "/getDepartmentsByOrgId/{orgId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Department>> getDepartmentsByOrgId(@PathVariable Long orgId) {
        log.debug("REST request to get Departments wich belong to Organization with the id ", orgId);
        List<Department> departmentList = departmentRepository.getDepartmentsByOrgId(orgId);
        return new ResponseEntity<>(departmentList, HttpStatus.OK);
    }

    @RequestMapping(value = "/departments/getByProjectId/{projectId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Department>> getByProjectId(@PathVariable Long projectId) {
        List<Department> departmentList = departmentRepository.getByProjectId(projectId);
        return new ResponseEntity<>(departmentList, HttpStatus.OK);
    }
}
