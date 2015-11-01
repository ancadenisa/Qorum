package com.qorum.web.rest;

import com.qorum.Application;
import com.qorum.domain.Organization;
import com.qorum.repository.OrganizationRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the OrganizationResource REST controller.
 *
 * @see OrganizationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OrganizationResourceTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private OrganizationRepository organizationRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restOrganizationMockMvc;

    private Organization organization;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OrganizationResource organizationResource = new OrganizationResource();
        ReflectionTestUtils.setField(organizationResource, "organizationRepository", organizationRepository);
        this.restOrganizationMockMvc = MockMvcBuilders.standaloneSetup(organizationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        organization = new Organization();
        organization.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createOrganization() throws Exception {
        int databaseSizeBeforeCreate = organizationRepository.findAll().size();

        // Create the Organization

        restOrganizationMockMvc.perform(post("/api/organizations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(organization)))
                .andExpect(status().isCreated());

        // Validate the Organization in the database
        List<Organization> organizations = organizationRepository.findAll();
        assertThat(organizations).hasSize(databaseSizeBeforeCreate + 1);
        Organization testOrganization = organizations.get(organizations.size() - 1);
        assertThat(testOrganization.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void getAllOrganizations() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizations
        restOrganizationMockMvc.perform(get("/api/organizations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(organization.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getOrganization() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get the organization
        restOrganizationMockMvc.perform(get("/api/organizations/{id}", organization.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(organization.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrganization() throws Exception {
        // Get the organization
        restOrganizationMockMvc.perform(get("/api/organizations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrganization() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

		int databaseSizeBeforeUpdate = organizationRepository.findAll().size();

        // Update the organization
        organization.setName(UPDATED_NAME);

        restOrganizationMockMvc.perform(put("/api/organizations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(organization)))
                .andExpect(status().isOk());

        // Validate the Organization in the database
        List<Organization> organizations = organizationRepository.findAll();
        assertThat(organizations).hasSize(databaseSizeBeforeUpdate);
        Organization testOrganization = organizations.get(organizations.size() - 1);
        assertThat(testOrganization.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteOrganization() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

		int databaseSizeBeforeDelete = organizationRepository.findAll().size();

        // Get the organization
        restOrganizationMockMvc.perform(delete("/api/organizations/{id}", organization.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Organization> organizations = organizationRepository.findAll();
        assertThat(organizations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
