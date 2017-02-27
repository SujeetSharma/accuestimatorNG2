package com.accuestimator.myapp.web.rest;

import com.accuestimator.myapp.AccuestimatorNg2App;

import com.accuestimator.myapp.domain.ProjectUserMapping;
import com.accuestimator.myapp.repository.ProjectUserMappingRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.accuestimator.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.accuestimator.myapp.domain.enumeration.STATEENUM;
/**
 * Test class for the ProjectUserMappingResource REST controller.
 *
 * @see ProjectUserMappingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccuestimatorNg2App.class)
public class ProjectUserMappingResourceIntTest {

    private static final String DEFAULT_USERID = "AAAAAAAAAA";
    private static final String UPDATED_USERID = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECTID = "AAAAAAAAAA";
    private static final String UPDATED_PROJECTID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final STATEENUM DEFAULT_STATE = STATEENUM.DRAFT;
    private static final STATEENUM UPDATED_STATE = STATEENUM.INPROGRESS;

    private static final String DEFAULT_CREATEDBY = "AAAAAAAAAA";
    private static final String UPDATED_CREATEDBY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATEDON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATEDON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_MODIFIEDBY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIEDBY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_MODIFIEDON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MODIFIEDON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ProjectUserMappingRepository projectUserMappingRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProjectUserMappingMockMvc;

    private ProjectUserMapping projectUserMapping;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            ProjectUserMappingResource projectUserMappingResource = new ProjectUserMappingResource(projectUserMappingRepository);
        this.restProjectUserMappingMockMvc = MockMvcBuilders.standaloneSetup(projectUserMappingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectUserMapping createEntity() {
        ProjectUserMapping projectUserMapping = new ProjectUserMapping()
                .userid(DEFAULT_USERID)
                .projectid(DEFAULT_PROJECTID)
                .active(DEFAULT_ACTIVE)
                .version(DEFAULT_VERSION)
                .state(DEFAULT_STATE)
                .createdby(DEFAULT_CREATEDBY)
                .createdon(DEFAULT_CREATEDON)
                .modifiedby(DEFAULT_MODIFIEDBY)
                .modifiedon(DEFAULT_MODIFIEDON);
        return projectUserMapping;
    }

    @Before
    public void initTest() {
        projectUserMappingRepository.deleteAll();
        projectUserMapping = createEntity();
    }

    @Test
    public void createProjectUserMapping() throws Exception {
        int databaseSizeBeforeCreate = projectUserMappingRepository.findAll().size();

        // Create the ProjectUserMapping

        restProjectUserMappingMockMvc.perform(post("/api/project-user-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectUserMapping)))
            .andExpect(status().isCreated());

        // Validate the ProjectUserMapping in the database
        List<ProjectUserMapping> projectUserMappingList = projectUserMappingRepository.findAll();
        assertThat(projectUserMappingList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectUserMapping testProjectUserMapping = projectUserMappingList.get(projectUserMappingList.size() - 1);
        assertThat(testProjectUserMapping.getUserid()).isEqualTo(DEFAULT_USERID);
        assertThat(testProjectUserMapping.getProjectid()).isEqualTo(DEFAULT_PROJECTID);
        assertThat(testProjectUserMapping.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testProjectUserMapping.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testProjectUserMapping.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testProjectUserMapping.getCreatedby()).isEqualTo(DEFAULT_CREATEDBY);
        assertThat(testProjectUserMapping.getCreatedon()).isEqualTo(DEFAULT_CREATEDON);
        assertThat(testProjectUserMapping.getModifiedby()).isEqualTo(DEFAULT_MODIFIEDBY);
        assertThat(testProjectUserMapping.getModifiedon()).isEqualTo(DEFAULT_MODIFIEDON);
    }

    @Test
    public void createProjectUserMappingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectUserMappingRepository.findAll().size();

        // Create the ProjectUserMapping with an existing ID
        ProjectUserMapping existingProjectUserMapping = new ProjectUserMapping();
        existingProjectUserMapping.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectUserMappingMockMvc.perform(post("/api/project-user-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingProjectUserMapping)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProjectUserMapping> projectUserMappingList = projectUserMappingRepository.findAll();
        assertThat(projectUserMappingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkUseridIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectUserMappingRepository.findAll().size();
        // set the field null
        projectUserMapping.setUserid(null);

        // Create the ProjectUserMapping, which fails.

        restProjectUserMappingMockMvc.perform(post("/api/project-user-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectUserMapping)))
            .andExpect(status().isBadRequest());

        List<ProjectUserMapping> projectUserMappingList = projectUserMappingRepository.findAll();
        assertThat(projectUserMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkProjectidIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectUserMappingRepository.findAll().size();
        // set the field null
        projectUserMapping.setProjectid(null);

        // Create the ProjectUserMapping, which fails.

        restProjectUserMappingMockMvc.perform(post("/api/project-user-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectUserMapping)))
            .andExpect(status().isBadRequest());

        List<ProjectUserMapping> projectUserMappingList = projectUserMappingRepository.findAll();
        assertThat(projectUserMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectUserMappingRepository.findAll().size();
        // set the field null
        projectUserMapping.setActive(null);

        // Create the ProjectUserMapping, which fails.

        restProjectUserMappingMockMvc.perform(post("/api/project-user-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectUserMapping)))
            .andExpect(status().isBadRequest());

        List<ProjectUserMapping> projectUserMappingList = projectUserMappingRepository.findAll();
        assertThat(projectUserMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectUserMappingRepository.findAll().size();
        // set the field null
        projectUserMapping.setVersion(null);

        // Create the ProjectUserMapping, which fails.

        restProjectUserMappingMockMvc.perform(post("/api/project-user-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectUserMapping)))
            .andExpect(status().isBadRequest());

        List<ProjectUserMapping> projectUserMappingList = projectUserMappingRepository.findAll();
        assertThat(projectUserMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectUserMappingRepository.findAll().size();
        // set the field null
        projectUserMapping.setState(null);

        // Create the ProjectUserMapping, which fails.

        restProjectUserMappingMockMvc.perform(post("/api/project-user-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectUserMapping)))
            .andExpect(status().isBadRequest());

        List<ProjectUserMapping> projectUserMappingList = projectUserMappingRepository.findAll();
        assertThat(projectUserMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllProjectUserMappings() throws Exception {
        // Initialize the database
        projectUserMappingRepository.save(projectUserMapping);

        // Get all the projectUserMappingList
        restProjectUserMappingMockMvc.perform(get("/api/project-user-mappings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectUserMapping.getId())))
            .andExpect(jsonPath("$.[*].userid").value(hasItem(DEFAULT_USERID.toString())))
            .andExpect(jsonPath("$.[*].projectid").value(hasItem(DEFAULT_PROJECTID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].createdby").value(hasItem(DEFAULT_CREATEDBY.toString())))
            .andExpect(jsonPath("$.[*].createdon").value(hasItem(sameInstant(DEFAULT_CREATEDON))))
            .andExpect(jsonPath("$.[*].modifiedby").value(hasItem(DEFAULT_MODIFIEDBY.toString())))
            .andExpect(jsonPath("$.[*].modifiedon").value(hasItem(sameInstant(DEFAULT_MODIFIEDON))));
    }

    @Test
    public void getProjectUserMapping() throws Exception {
        // Initialize the database
        projectUserMappingRepository.save(projectUserMapping);

        // Get the projectUserMapping
        restProjectUserMappingMockMvc.perform(get("/api/project-user-mappings/{id}", projectUserMapping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projectUserMapping.getId()))
            .andExpect(jsonPath("$.userid").value(DEFAULT_USERID.toString()))
            .andExpect(jsonPath("$.projectid").value(DEFAULT_PROJECTID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.createdby").value(DEFAULT_CREATEDBY.toString()))
            .andExpect(jsonPath("$.createdon").value(sameInstant(DEFAULT_CREATEDON)))
            .andExpect(jsonPath("$.modifiedby").value(DEFAULT_MODIFIEDBY.toString()))
            .andExpect(jsonPath("$.modifiedon").value(sameInstant(DEFAULT_MODIFIEDON)));
    }

    @Test
    public void getNonExistingProjectUserMapping() throws Exception {
        // Get the projectUserMapping
        restProjectUserMappingMockMvc.perform(get("/api/project-user-mappings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateProjectUserMapping() throws Exception {
        // Initialize the database
        projectUserMappingRepository.save(projectUserMapping);
        int databaseSizeBeforeUpdate = projectUserMappingRepository.findAll().size();

        // Update the projectUserMapping
        ProjectUserMapping updatedProjectUserMapping = projectUserMappingRepository.findOne(projectUserMapping.getId());
        updatedProjectUserMapping
                .userid(UPDATED_USERID)
                .projectid(UPDATED_PROJECTID)
                .active(UPDATED_ACTIVE)
                .version(UPDATED_VERSION)
                .state(UPDATED_STATE)
                .createdby(UPDATED_CREATEDBY)
                .createdon(UPDATED_CREATEDON)
                .modifiedby(UPDATED_MODIFIEDBY)
                .modifiedon(UPDATED_MODIFIEDON);

        restProjectUserMappingMockMvc.perform(put("/api/project-user-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjectUserMapping)))
            .andExpect(status().isOk());

        // Validate the ProjectUserMapping in the database
        List<ProjectUserMapping> projectUserMappingList = projectUserMappingRepository.findAll();
        assertThat(projectUserMappingList).hasSize(databaseSizeBeforeUpdate);
        ProjectUserMapping testProjectUserMapping = projectUserMappingList.get(projectUserMappingList.size() - 1);
        assertThat(testProjectUserMapping.getUserid()).isEqualTo(UPDATED_USERID);
        assertThat(testProjectUserMapping.getProjectid()).isEqualTo(UPDATED_PROJECTID);
        assertThat(testProjectUserMapping.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testProjectUserMapping.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testProjectUserMapping.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testProjectUserMapping.getCreatedby()).isEqualTo(UPDATED_CREATEDBY);
        assertThat(testProjectUserMapping.getCreatedon()).isEqualTo(UPDATED_CREATEDON);
        assertThat(testProjectUserMapping.getModifiedby()).isEqualTo(UPDATED_MODIFIEDBY);
        assertThat(testProjectUserMapping.getModifiedon()).isEqualTo(UPDATED_MODIFIEDON);
    }

    @Test
    public void updateNonExistingProjectUserMapping() throws Exception {
        int databaseSizeBeforeUpdate = projectUserMappingRepository.findAll().size();

        // Create the ProjectUserMapping

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProjectUserMappingMockMvc.perform(put("/api/project-user-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectUserMapping)))
            .andExpect(status().isCreated());

        // Validate the ProjectUserMapping in the database
        List<ProjectUserMapping> projectUserMappingList = projectUserMappingRepository.findAll();
        assertThat(projectUserMappingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteProjectUserMapping() throws Exception {
        // Initialize the database
        projectUserMappingRepository.save(projectUserMapping);
        int databaseSizeBeforeDelete = projectUserMappingRepository.findAll().size();

        // Get the projectUserMapping
        restProjectUserMappingMockMvc.perform(delete("/api/project-user-mappings/{id}", projectUserMapping.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProjectUserMapping> projectUserMappingList = projectUserMappingRepository.findAll();
        assertThat(projectUserMappingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
