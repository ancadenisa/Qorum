package com.qorum.web.rest;

import com.qorum.Application;
import com.qorum.domain.Issue;
import com.qorum.repository.IssueRepository;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the IssueResource REST controller.
 *
 * @see IssueResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class IssueResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_CONTENT = "AAAAA";
    private static final String UPDATED_CONTENT = "BBBBB";

    private static final ZonedDateTime DEFAULT_LAST_UPDATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_LAST_UPDATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_LAST_UPDATED_STR = dateTimeFormatter.format(DEFAULT_LAST_UPDATED);

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATED_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATED_DATE);

    private static final Long DEFAULT_RATING = 1L;
    private static final Long UPDATED_RATING = 2L;

    private static final Boolean DEFAULT_IS_PUBLIC = false;
    private static final Boolean UPDATED_IS_PUBLIC = true;

    @Inject
    private IssueRepository issueRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restIssueMockMvc;

    private Issue issue;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        IssueResource issueResource = new IssueResource();
        ReflectionTestUtils.setField(issueResource, "issueRepository", issueRepository);
        this.restIssueMockMvc = MockMvcBuilders.standaloneSetup(issueResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        issue = new Issue();
        issue.setName(DEFAULT_NAME);
        issue.setContent(DEFAULT_CONTENT);
        issue.setLast_updated(DEFAULT_LAST_UPDATED);
        issue.setCreated_date(DEFAULT_CREATED_DATE);
        issue.setRating(DEFAULT_RATING);
        issue.setIs_public(DEFAULT_IS_PUBLIC);
    }

    @Test
    @Transactional
    public void createIssue() throws Exception {
        int databaseSizeBeforeCreate = issueRepository.findAll().size();

        // Create the Issue

        restIssueMockMvc.perform(post("/api/issues")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(issue)))
                .andExpect(status().isCreated());

        // Validate the Issue in the database
        List<Issue> issues = issueRepository.findAll();
        assertThat(issues).hasSize(databaseSizeBeforeCreate + 1);
        Issue testIssue = issues.get(issues.size() - 1);
        assertThat(testIssue.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testIssue.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testIssue.getLast_updated()).isEqualTo(DEFAULT_LAST_UPDATED);
        assertThat(testIssue.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testIssue.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testIssue.getIs_public()).isEqualTo(DEFAULT_IS_PUBLIC);
    }

    @Test
    @Transactional
    public void getAllIssues() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);

        // Get all the issues
        restIssueMockMvc.perform(get("/api/issues"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(issue.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
                .andExpect(jsonPath("$.[*].last_updated").value(hasItem(DEFAULT_LAST_UPDATED_STR)))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE_STR)))
                .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING.intValue())))
                .andExpect(jsonPath("$.[*].is_public").value(hasItem(DEFAULT_IS_PUBLIC.booleanValue())));
    }

    @Test
    @Transactional
    public void getIssue() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);

        // Get the issue
        restIssueMockMvc.perform(get("/api/issues/{id}", issue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(issue.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.last_updated").value(DEFAULT_LAST_UPDATED_STR))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE_STR))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING.intValue()))
            .andExpect(jsonPath("$.is_public").value(DEFAULT_IS_PUBLIC.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingIssue() throws Exception {
        // Get the issue
        restIssueMockMvc.perform(get("/api/issues/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIssue() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);

		int databaseSizeBeforeUpdate = issueRepository.findAll().size();

        // Update the issue
        issue.setName(UPDATED_NAME);
        issue.setContent(UPDATED_CONTENT);
        issue.setLast_updated(UPDATED_LAST_UPDATED);
        issue.setCreated_date(UPDATED_CREATED_DATE);
        issue.setRating(UPDATED_RATING);
        issue.setIs_public(UPDATED_IS_PUBLIC);

        restIssueMockMvc.perform(put("/api/issues")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(issue)))
                .andExpect(status().isOk());

        // Validate the Issue in the database
        List<Issue> issues = issueRepository.findAll();
        assertThat(issues).hasSize(databaseSizeBeforeUpdate);
        Issue testIssue = issues.get(issues.size() - 1);
        assertThat(testIssue.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIssue.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testIssue.getLast_updated()).isEqualTo(UPDATED_LAST_UPDATED);
        assertThat(testIssue.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testIssue.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testIssue.getIs_public()).isEqualTo(UPDATED_IS_PUBLIC);
    }

    @Test
    @Transactional
    public void deleteIssue() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);

		int databaseSizeBeforeDelete = issueRepository.findAll().size();

        // Get the issue
        restIssueMockMvc.perform(delete("/api/issues/{id}", issue.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Issue> issues = issueRepository.findAll();
        assertThat(issues).hasSize(databaseSizeBeforeDelete - 1);
    }
}
