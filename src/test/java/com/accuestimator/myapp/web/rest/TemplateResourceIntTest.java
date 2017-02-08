package com.accuestimator.myapp.web.rest;

import com.accuestimator.myapp.AccuestimatorNg2App;

import com.accuestimator.myapp.domain.Template;
import com.accuestimator.myapp.repository.TemplateRepository;

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
 * Test class for the TemplateResource REST controller.
 *
 * @see TemplateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccuestimatorNg2App.class)
public class TemplateResourceIntTest {

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String[] DEFAULT_FACTOR_TASK_ID = {"A","B","C"};
    private static final String[] UPDATED_FACTOR_TASK_ID = {"1","2","3"};

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final STATEENUM DEFAULT_STATE = STATEENUM.DRAFT;
    private static final STATEENUM UPDATED_STATE = STATEENUM.INPROGRESS;

    private static final String DEFAULT_CREATEDBY = "AAAAAAAAAA";
    private static final String UPDATED_CREATEDBY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATEDON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATEDON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_REFERENCED_FROM = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCED_FROM = "BBBBBBBBBB";

    private static final String DEFAULT_MODIFIEDBY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIEDBY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_MODIFIEDON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MODIFIEDON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTemplateMockMvc;

    private Template template;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            TemplateResource templateResource = new TemplateResource(templateRepository);
        this.restTemplateMockMvc = MockMvcBuilders.standaloneSetup(templateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Template createEntity() {
        Template template = new Template()
                .active(DEFAULT_ACTIVE)
                .factorTaskId(DEFAULT_FACTOR_TASK_ID)
                .version(DEFAULT_VERSION)
                .state(DEFAULT_STATE)
                .createdby(DEFAULT_CREATEDBY)
                .createdon(DEFAULT_CREATEDON)
                .referencedFrom(DEFAULT_REFERENCED_FROM)
                .modifiedby(DEFAULT_MODIFIEDBY)
                .modifiedon(DEFAULT_MODIFIEDON)
                .description(DEFAULT_DESCRIPTION);
        return template;
    }

    @Before
    public void initTest() {
        templateRepository.deleteAll();
        template = createEntity();
    }

    @Test
    public void createTemplate() throws Exception {
        int databaseSizeBeforeCreate = templateRepository.findAll().size();

        // Create the Template

        restTemplateMockMvc.perform(post("/api/templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(template)))
            .andExpect(status().isCreated());

        // Validate the Template in the database
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeCreate + 1);
        Template testTemplate = templateList.get(templateList.size() - 1);
        assertThat(testTemplate.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testTemplate.getFactorTaskId()).isEqualTo(DEFAULT_FACTOR_TASK_ID);
        assertThat(testTemplate.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testTemplate.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testTemplate.getCreatedby()).isEqualTo(DEFAULT_CREATEDBY);
        assertThat(testTemplate.getCreatedon()).isEqualTo(DEFAULT_CREATEDON);
        assertThat(testTemplate.getReferencedFrom()).isEqualTo(DEFAULT_REFERENCED_FROM);
        assertThat(testTemplate.getModifiedby()).isEqualTo(DEFAULT_MODIFIEDBY);
        assertThat(testTemplate.getModifiedon()).isEqualTo(DEFAULT_MODIFIEDON);
        assertThat(testTemplate.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    public void createTemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = templateRepository.findAll().size();

        // Create the Template with an existing ID
        Template existingTemplate = new Template();
        existingTemplate.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restTemplateMockMvc.perform(post("/api/templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTemplate)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = templateRepository.findAll().size();
        // set the field null
        template.setActive(null);

        // Create the Template, which fails.

        restTemplateMockMvc.perform(post("/api/templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(template)))
            .andExpect(status().isBadRequest());

        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkFactorTaskIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = templateRepository.findAll().size();
        // set the field null
        template.setFactorTaskId(null);

        // Create the Template, which fails.

        restTemplateMockMvc.perform(post("/api/templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(template)))
            .andExpect(status().isBadRequest());

        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = templateRepository.findAll().size();
        // set the field null
        template.setVersion(null);

        // Create the Template, which fails.

        restTemplateMockMvc.perform(post("/api/templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(template)))
            .andExpect(status().isBadRequest());

        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = templateRepository.findAll().size();
        // set the field null
        template.setState(null);

        // Create the Template, which fails.

        restTemplateMockMvc.perform(post("/api/templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(template)))
            .andExpect(status().isBadRequest());

        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllTemplates() throws Exception {
        // Initialize the database
        templateRepository.save(template);

        // Get all the templateList
        restTemplateMockMvc.perform(get("/api/templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(template.getId())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].factorTaskId").value(hasItem(DEFAULT_FACTOR_TASK_ID.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].createdby").value(hasItem(DEFAULT_CREATEDBY.toString())))
            .andExpect(jsonPath("$.[*].createdon").value(hasItem(sameInstant(DEFAULT_CREATEDON))))
            .andExpect(jsonPath("$.[*].referencedFrom").value(hasItem(DEFAULT_REFERENCED_FROM.toString())))
            .andExpect(jsonPath("$.[*].modifiedby").value(hasItem(DEFAULT_MODIFIEDBY.toString())))
            .andExpect(jsonPath("$.[*].modifiedon").value(hasItem(sameInstant(DEFAULT_MODIFIEDON))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    public void getTemplate() throws Exception {
        // Initialize the database
        templateRepository.save(template);

        // Get the template
        restTemplateMockMvc.perform(get("/api/templates/{id}", template.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(template.getId()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.factorTaskId").value(DEFAULT_FACTOR_TASK_ID.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.createdby").value(DEFAULT_CREATEDBY.toString()))
            .andExpect(jsonPath("$.createdon").value(sameInstant(DEFAULT_CREATEDON)))
            .andExpect(jsonPath("$.referencedFrom").value(DEFAULT_REFERENCED_FROM.toString()))
            .andExpect(jsonPath("$.modifiedby").value(DEFAULT_MODIFIEDBY.toString()))
            .andExpect(jsonPath("$.modifiedon").value(sameInstant(DEFAULT_MODIFIEDON)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    public void getNonExistingTemplate() throws Exception {
        // Get the template
        restTemplateMockMvc.perform(get("/api/templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTemplate() throws Exception {
        // Initialize the database
        templateRepository.save(template);
        int databaseSizeBeforeUpdate = templateRepository.findAll().size();

        // Update the template
        Template updatedTemplate = templateRepository.findOne(template.getId());
        updatedTemplate
                .active(UPDATED_ACTIVE)
                .factorTaskId(UPDATED_FACTOR_TASK_ID)
                .version(UPDATED_VERSION)
                .state(UPDATED_STATE)
                .createdby(UPDATED_CREATEDBY)
                .createdon(UPDATED_CREATEDON)
                .referencedFrom(UPDATED_REFERENCED_FROM)
                .modifiedby(UPDATED_MODIFIEDBY)
                .modifiedon(UPDATED_MODIFIEDON)
                .description(UPDATED_DESCRIPTION);

        restTemplateMockMvc.perform(put("/api/templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTemplate)))
            .andExpect(status().isOk());

        // Validate the Template in the database
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeUpdate);
        Template testTemplate = templateList.get(templateList.size() - 1);
        assertThat(testTemplate.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testTemplate.getFactorTaskId()).isEqualTo(UPDATED_FACTOR_TASK_ID);
        assertThat(testTemplate.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testTemplate.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testTemplate.getCreatedby()).isEqualTo(UPDATED_CREATEDBY);
        assertThat(testTemplate.getCreatedon()).isEqualTo(UPDATED_CREATEDON);
        assertThat(testTemplate.getReferencedFrom()).isEqualTo(UPDATED_REFERENCED_FROM);
        assertThat(testTemplate.getModifiedby()).isEqualTo(UPDATED_MODIFIEDBY);
        assertThat(testTemplate.getModifiedon()).isEqualTo(UPDATED_MODIFIEDON);
        assertThat(testTemplate.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    public void updateNonExistingTemplate() throws Exception {
        int databaseSizeBeforeUpdate = templateRepository.findAll().size();

        // Create the Template

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTemplateMockMvc.perform(put("/api/templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(template)))
            .andExpect(status().isCreated());

        // Validate the Template in the database
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteTemplate() throws Exception {
        // Initialize the database
        templateRepository.save(template);
        int databaseSizeBeforeDelete = templateRepository.findAll().size();

        // Get the template
        restTemplateMockMvc.perform(delete("/api/templates/{id}", template.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
