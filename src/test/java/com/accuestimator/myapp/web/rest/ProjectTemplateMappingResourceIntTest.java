package com.accuestimator.myapp.web.rest;

import com.accuestimator.myapp.AccuestimatorNg2App;

import com.accuestimator.myapp.domain.ProjectTemplateMapping;
import com.accuestimator.myapp.repository.ProjectTemplateMappingRepository;

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
 * Test class for the ProjectTemplateMappingResource REST controller.
 *
 * @see ProjectTemplateMappingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccuestimatorNg2App.class)
public class ProjectTemplateMappingResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT_ID = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TEMPLATE_ID = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE_ID = "BBBBBBBBBB";

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
    private ProjectTemplateMappingRepository projectTemplateMappingRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProjectTemplateMappingMockMvc;

    private ProjectTemplateMapping projectTemplateMapping;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            ProjectTemplateMappingResource projectTemplateMappingResource = new ProjectTemplateMappingResource(projectTemplateMappingRepository);
        this.restProjectTemplateMappingMockMvc = MockMvcBuilders.standaloneSetup(projectTemplateMappingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectTemplateMapping createEntity() {
        ProjectTemplateMapping projectTemplateMapping = new ProjectTemplateMapping()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION)
                .projectId(DEFAULT_PROJECT_ID)
                .templateId(DEFAULT_TEMPLATE_ID)
                .active(DEFAULT_ACTIVE)
                .version(DEFAULT_VERSION)
                .state(DEFAULT_STATE)
                .createdby(DEFAULT_CREATEDBY)
                .createdon(DEFAULT_CREATEDON)
                .modifiedby(DEFAULT_MODIFIEDBY)
                .modifiedon(DEFAULT_MODIFIEDON);
        return projectTemplateMapping;
    }

    @Before
    public void initTest() {
        projectTemplateMappingRepository.deleteAll();
        projectTemplateMapping = createEntity();
    }

    @Test
    public void createProjectTemplateMapping() throws Exception {
        int databaseSizeBeforeCreate = projectTemplateMappingRepository.findAll().size();

        // Create the ProjectTemplateMapping

        restProjectTemplateMappingMockMvc.perform(post("/api/project-template-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectTemplateMapping)))
            .andExpect(status().isCreated());

        // Validate the ProjectTemplateMapping in the database
        List<ProjectTemplateMapping> projectTemplateMappingList = projectTemplateMappingRepository.findAll();
        assertThat(projectTemplateMappingList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectTemplateMapping testProjectTemplateMapping = projectTemplateMappingList.get(projectTemplateMappingList.size() - 1);
        assertThat(testProjectTemplateMapping.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProjectTemplateMapping.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProjectTemplateMapping.getProjectId()).isEqualTo(DEFAULT_PROJECT_ID);
        assertThat(testProjectTemplateMapping.getTemplateId()).isEqualTo(DEFAULT_TEMPLATE_ID);
        assertThat(testProjectTemplateMapping.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testProjectTemplateMapping.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testProjectTemplateMapping.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testProjectTemplateMapping.getCreatedby()).isEqualTo(DEFAULT_CREATEDBY);
        assertThat(testProjectTemplateMapping.getCreatedon()).isEqualTo(DEFAULT_CREATEDON);
        assertThat(testProjectTemplateMapping.getModifiedby()).isEqualTo(DEFAULT_MODIFIEDBY);
        assertThat(testProjectTemplateMapping.getModifiedon()).isEqualTo(DEFAULT_MODIFIEDON);
    }

    @Test
    public void createProjectTemplateMappingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectTemplateMappingRepository.findAll().size();

        // Create the ProjectTemplateMapping with an existing ID
        ProjectTemplateMapping existingProjectTemplateMapping = new ProjectTemplateMapping();
        existingProjectTemplateMapping.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectTemplateMappingMockMvc.perform(post("/api/project-template-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingProjectTemplateMapping)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProjectTemplateMapping> projectTemplateMappingList = projectTemplateMappingRepository.findAll();
        assertThat(projectTemplateMappingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectTemplateMappingRepository.findAll().size();
        // set the field null
        projectTemplateMapping.setName(null);

        // Create the ProjectTemplateMapping, which fails.

        restProjectTemplateMappingMockMvc.perform(post("/api/project-template-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectTemplateMapping)))
            .andExpect(status().isBadRequest());

        List<ProjectTemplateMapping> projectTemplateMappingList = projectTemplateMappingRepository.findAll();
        assertThat(projectTemplateMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkProjectIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectTemplateMappingRepository.findAll().size();
        // set the field null
        projectTemplateMapping.setProjectId(null);

        // Create the ProjectTemplateMapping, which fails.

        restProjectTemplateMappingMockMvc.perform(post("/api/project-template-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectTemplateMapping)))
            .andExpect(status().isBadRequest());

        List<ProjectTemplateMapping> projectTemplateMappingList = projectTemplateMappingRepository.findAll();
        assertThat(projectTemplateMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkTemplateIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectTemplateMappingRepository.findAll().size();
        // set the field null
        projectTemplateMapping.setTemplateId(null);

        // Create the ProjectTemplateMapping, which fails.

        restProjectTemplateMappingMockMvc.perform(post("/api/project-template-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectTemplateMapping)))
            .andExpect(status().isBadRequest());

        List<ProjectTemplateMapping> projectTemplateMappingList = projectTemplateMappingRepository.findAll();
        assertThat(projectTemplateMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectTemplateMappingRepository.findAll().size();
        // set the field null
        projectTemplateMapping.setActive(null);

        // Create the ProjectTemplateMapping, which fails.

        restProjectTemplateMappingMockMvc.perform(post("/api/project-template-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectTemplateMapping)))
            .andExpect(status().isBadRequest());

        List<ProjectTemplateMapping> projectTemplateMappingList = projectTemplateMappingRepository.findAll();
        assertThat(projectTemplateMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectTemplateMappingRepository.findAll().size();
        // set the field null
        projectTemplateMapping.setVersion(null);

        // Create the ProjectTemplateMapping, which fails.

        restProjectTemplateMappingMockMvc.perform(post("/api/project-template-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectTemplateMapping)))
            .andExpect(status().isBadRequest());

        List<ProjectTemplateMapping> projectTemplateMappingList = projectTemplateMappingRepository.findAll();
        assertThat(projectTemplateMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectTemplateMappingRepository.findAll().size();
        // set the field null
        projectTemplateMapping.setState(null);

        // Create the ProjectTemplateMapping, which fails.

        restProjectTemplateMappingMockMvc.perform(post("/api/project-template-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectTemplateMapping)))
            .andExpect(status().isBadRequest());

        List<ProjectTemplateMapping> projectTemplateMappingList = projectTemplateMappingRepository.findAll();
        assertThat(projectTemplateMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllProjectTemplateMappings() throws Exception {
        // Initialize the database
        projectTemplateMappingRepository.save(projectTemplateMapping);

        // Get all the projectTemplateMappingList
        restProjectTemplateMappingMockMvc.perform(get("/api/project-template-mappings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectTemplateMapping.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].projectId").value(hasItem(DEFAULT_PROJECT_ID.toString())))
            .andExpect(jsonPath("$.[*].templateId").value(hasItem(DEFAULT_TEMPLATE_ID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].createdby").value(hasItem(DEFAULT_CREATEDBY.toString())))
            .andExpect(jsonPath("$.[*].createdon").value(hasItem(sameInstant(DEFAULT_CREATEDON))))
            .andExpect(jsonPath("$.[*].modifiedby").value(hasItem(DEFAULT_MODIFIEDBY.toString())))
            .andExpect(jsonPath("$.[*].modifiedon").value(hasItem(sameInstant(DEFAULT_MODIFIEDON))));
    }

    @Test
    public void getProjectTemplateMapping() throws Exception {
        // Initialize the database
        projectTemplateMappingRepository.save(projectTemplateMapping);

        // Get the projectTemplateMapping
        restProjectTemplateMappingMockMvc.perform(get("/api/project-template-mappings/{id}", projectTemplateMapping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projectTemplateMapping.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.projectId").value(DEFAULT_PROJECT_ID.toString()))
            .andExpect(jsonPath("$.templateId").value(DEFAULT_TEMPLATE_ID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.createdby").value(DEFAULT_CREATEDBY.toString()))
            .andExpect(jsonPath("$.createdon").value(sameInstant(DEFAULT_CREATEDON)))
            .andExpect(jsonPath("$.modifiedby").value(DEFAULT_MODIFIEDBY.toString()))
            .andExpect(jsonPath("$.modifiedon").value(sameInstant(DEFAULT_MODIFIEDON)));
    }

    @Test
    public void getNonExistingProjectTemplateMapping() throws Exception {
        // Get the projectTemplateMapping
        restProjectTemplateMappingMockMvc.perform(get("/api/project-template-mappings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateProjectTemplateMapping() throws Exception {
        // Initialize the database
        projectTemplateMappingRepository.save(projectTemplateMapping);
        int databaseSizeBeforeUpdate = projectTemplateMappingRepository.findAll().size();

        // Update the projectTemplateMapping
        ProjectTemplateMapping updatedProjectTemplateMapping = projectTemplateMappingRepository.findOne(projectTemplateMapping.getId());
        updatedProjectTemplateMapping
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION)
                .projectId(UPDATED_PROJECT_ID)
                .templateId(UPDATED_TEMPLATE_ID)
                .active(UPDATED_ACTIVE)
                .version(UPDATED_VERSION)
                .state(UPDATED_STATE)
                .createdby(UPDATED_CREATEDBY)
                .createdon(UPDATED_CREATEDON)
                .modifiedby(UPDATED_MODIFIEDBY)
                .modifiedon(UPDATED_MODIFIEDON);

        restProjectTemplateMappingMockMvc.perform(put("/api/project-template-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjectTemplateMapping)))
            .andExpect(status().isOk());

        // Validate the ProjectTemplateMapping in the database
        List<ProjectTemplateMapping> projectTemplateMappingList = projectTemplateMappingRepository.findAll();
        assertThat(projectTemplateMappingList).hasSize(databaseSizeBeforeUpdate);
        ProjectTemplateMapping testProjectTemplateMapping = projectTemplateMappingList.get(projectTemplateMappingList.size() - 1);
        assertThat(testProjectTemplateMapping.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProjectTemplateMapping.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProjectTemplateMapping.getProjectId()).isEqualTo(UPDATED_PROJECT_ID);
        assertThat(testProjectTemplateMapping.getTemplateId()).isEqualTo(UPDATED_TEMPLATE_ID);
        assertThat(testProjectTemplateMapping.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testProjectTemplateMapping.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testProjectTemplateMapping.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testProjectTemplateMapping.getCreatedby()).isEqualTo(UPDATED_CREATEDBY);
        assertThat(testProjectTemplateMapping.getCreatedon()).isEqualTo(UPDATED_CREATEDON);
        assertThat(testProjectTemplateMapping.getModifiedby()).isEqualTo(UPDATED_MODIFIEDBY);
        assertThat(testProjectTemplateMapping.getModifiedon()).isEqualTo(UPDATED_MODIFIEDON);
    }

    @Test
    public void updateNonExistingProjectTemplateMapping() throws Exception {
        int databaseSizeBeforeUpdate = projectTemplateMappingRepository.findAll().size();

        // Create the ProjectTemplateMapping

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProjectTemplateMappingMockMvc.perform(put("/api/project-template-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectTemplateMapping)))
            .andExpect(status().isCreated());

        // Validate the ProjectTemplateMapping in the database
        List<ProjectTemplateMapping> projectTemplateMappingList = projectTemplateMappingRepository.findAll();
        assertThat(projectTemplateMappingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteProjectTemplateMapping() throws Exception {
        // Initialize the database
        projectTemplateMappingRepository.save(projectTemplateMapping);
        int databaseSizeBeforeDelete = projectTemplateMappingRepository.findAll().size();

        // Get the projectTemplateMapping
        restProjectTemplateMappingMockMvc.perform(delete("/api/project-template-mappings/{id}", projectTemplateMapping.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProjectTemplateMapping> projectTemplateMappingList = projectTemplateMappingRepository.findAll();
        assertThat(projectTemplateMappingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
