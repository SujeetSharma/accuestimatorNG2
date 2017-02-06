package com.accuestimator.myapp.web.rest;

import com.accuestimator.myapp.AccuestimatorNg2App;

import com.accuestimator.myapp.domain.Estimates;
import com.accuestimator.myapp.repository.EstimatesRepository;

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

import com.accuestimator.myapp.domain.enumeration.TYPEENUM;
import com.accuestimator.myapp.domain.enumeration.STATEENUM;
/**
 * Test class for the EstimatesResource REST controller.
 *
 * @see EstimatesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccuestimatorNg2App.class)
public class EstimatesResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT_ID = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TEMPLATE_ID = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TASK_ID = "AAAAAAAAAA";
    private static final String UPDATED_TASK_ID = "BBBBBBBBBB";

    private static final String DEFAULT_FACTOR_ID = "AAAAAAAAAA";
    private static final String UPDATED_FACTOR_ID = "BBBBBBBBBB";

    private static final TYPEENUM DEFAULT_TYPE = TYPEENUM.TECHNICAL;
    private static final TYPEENUM UPDATED_TYPE = TYPEENUM.MANAGEMENT;

    private static final Float DEFAULT_VALUE = 1F;
    private static final Float UPDATED_VALUE = 2F;

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final STATEENUM DEFAULT_STATE = STATEENUM.DRAFT;
    private static final STATEENUM UPDATED_STATE = STATEENUM.INPROGRESS;

    private static final String DEFAULT_REFERENCED_FROM = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCED_FROM = "BBBBBBBBBB";

    private static final String DEFAULT_CREATEDBY = "AAAAAAAAAA";
    private static final String UPDATED_CREATEDBY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATEDON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATEDON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_MODIFIEDBY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIEDBY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_MODIFIEDON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MODIFIEDON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private EstimatesRepository estimatesRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEstimatesMockMvc;

    private Estimates estimates;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            EstimatesResource estimatesResource = new EstimatesResource(estimatesRepository);
        this.restEstimatesMockMvc = MockMvcBuilders.standaloneSetup(estimatesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Estimates createEntity() {
        Estimates estimates = new Estimates()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION)
                .projectId(DEFAULT_PROJECT_ID)
                .templateId(DEFAULT_TEMPLATE_ID)
                .taskId(DEFAULT_TASK_ID)
                .factorId(DEFAULT_FACTOR_ID)
                .type(DEFAULT_TYPE)
                .value(DEFAULT_VALUE)
                .version(DEFAULT_VERSION)
                .state(DEFAULT_STATE)
                .referencedFrom(DEFAULT_REFERENCED_FROM)
                .createdby(DEFAULT_CREATEDBY)
                .createdon(DEFAULT_CREATEDON)
                .modifiedby(DEFAULT_MODIFIEDBY)
                .modifiedon(DEFAULT_MODIFIEDON)
                .active(DEFAULT_ACTIVE);
        return estimates;
    }

    @Before
    public void initTest() {
        estimatesRepository.deleteAll();
        estimates = createEntity();
    }

    @Test
    public void createEstimates() throws Exception {
        int databaseSizeBeforeCreate = estimatesRepository.findAll().size();

        // Create the Estimates

        restEstimatesMockMvc.perform(post("/api/estimates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estimates)))
            .andExpect(status().isCreated());

        // Validate the Estimates in the database
        List<Estimates> estimatesList = estimatesRepository.findAll();
        assertThat(estimatesList).hasSize(databaseSizeBeforeCreate + 1);
        Estimates testEstimates = estimatesList.get(estimatesList.size() - 1);
        assertThat(testEstimates.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEstimates.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEstimates.getProjectId()).isEqualTo(DEFAULT_PROJECT_ID);
        assertThat(testEstimates.getTemplateId()).isEqualTo(DEFAULT_TEMPLATE_ID);
        assertThat(testEstimates.getTaskId()).isEqualTo(DEFAULT_TASK_ID);
        assertThat(testEstimates.getFactorId()).isEqualTo(DEFAULT_FACTOR_ID);
        assertThat(testEstimates.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testEstimates.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testEstimates.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testEstimates.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testEstimates.getReferencedFrom()).isEqualTo(DEFAULT_REFERENCED_FROM);
        assertThat(testEstimates.getCreatedby()).isEqualTo(DEFAULT_CREATEDBY);
        assertThat(testEstimates.getCreatedon()).isEqualTo(DEFAULT_CREATEDON);
        assertThat(testEstimates.getModifiedby()).isEqualTo(DEFAULT_MODIFIEDBY);
        assertThat(testEstimates.getModifiedon()).isEqualTo(DEFAULT_MODIFIEDON);
        assertThat(testEstimates.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    public void createEstimatesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = estimatesRepository.findAll().size();

        // Create the Estimates with an existing ID
        Estimates existingEstimates = new Estimates();
        existingEstimates.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstimatesMockMvc.perform(post("/api/estimates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingEstimates)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Estimates> estimatesList = estimatesRepository.findAll();
        assertThat(estimatesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = estimatesRepository.findAll().size();
        // set the field null
        estimates.setName(null);

        // Create the Estimates, which fails.

        restEstimatesMockMvc.perform(post("/api/estimates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estimates)))
            .andExpect(status().isBadRequest());

        List<Estimates> estimatesList = estimatesRepository.findAll();
        assertThat(estimatesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkProjectIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = estimatesRepository.findAll().size();
        // set the field null
        estimates.setProjectId(null);

        // Create the Estimates, which fails.

        restEstimatesMockMvc.perform(post("/api/estimates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estimates)))
            .andExpect(status().isBadRequest());

        List<Estimates> estimatesList = estimatesRepository.findAll();
        assertThat(estimatesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkTemplateIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = estimatesRepository.findAll().size();
        // set the field null
        estimates.setTemplateId(null);

        // Create the Estimates, which fails.

        restEstimatesMockMvc.perform(post("/api/estimates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estimates)))
            .andExpect(status().isBadRequest());

        List<Estimates> estimatesList = estimatesRepository.findAll();
        assertThat(estimatesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkTaskIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = estimatesRepository.findAll().size();
        // set the field null
        estimates.setTaskId(null);

        // Create the Estimates, which fails.

        restEstimatesMockMvc.perform(post("/api/estimates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estimates)))
            .andExpect(status().isBadRequest());

        List<Estimates> estimatesList = estimatesRepository.findAll();
        assertThat(estimatesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkFactorIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = estimatesRepository.findAll().size();
        // set the field null
        estimates.setFactorId(null);

        // Create the Estimates, which fails.

        restEstimatesMockMvc.perform(post("/api/estimates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estimates)))
            .andExpect(status().isBadRequest());

        List<Estimates> estimatesList = estimatesRepository.findAll();
        assertThat(estimatesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = estimatesRepository.findAll().size();
        // set the field null
        estimates.setType(null);

        // Create the Estimates, which fails.

        restEstimatesMockMvc.perform(post("/api/estimates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estimates)))
            .andExpect(status().isBadRequest());

        List<Estimates> estimatesList = estimatesRepository.findAll();
        assertThat(estimatesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = estimatesRepository.findAll().size();
        // set the field null
        estimates.setValue(null);

        // Create the Estimates, which fails.

        restEstimatesMockMvc.perform(post("/api/estimates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estimates)))
            .andExpect(status().isBadRequest());

        List<Estimates> estimatesList = estimatesRepository.findAll();
        assertThat(estimatesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = estimatesRepository.findAll().size();
        // set the field null
        estimates.setVersion(null);

        // Create the Estimates, which fails.

        restEstimatesMockMvc.perform(post("/api/estimates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estimates)))
            .andExpect(status().isBadRequest());

        List<Estimates> estimatesList = estimatesRepository.findAll();
        assertThat(estimatesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = estimatesRepository.findAll().size();
        // set the field null
        estimates.setState(null);

        // Create the Estimates, which fails.

        restEstimatesMockMvc.perform(post("/api/estimates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estimates)))
            .andExpect(status().isBadRequest());

        List<Estimates> estimatesList = estimatesRepository.findAll();
        assertThat(estimatesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkReferencedFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = estimatesRepository.findAll().size();
        // set the field null
        estimates.setReferencedFrom(null);

        // Create the Estimates, which fails.

        restEstimatesMockMvc.perform(post("/api/estimates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estimates)))
            .andExpect(status().isBadRequest());

        List<Estimates> estimatesList = estimatesRepository.findAll();
        assertThat(estimatesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = estimatesRepository.findAll().size();
        // set the field null
        estimates.setActive(null);

        // Create the Estimates, which fails.

        restEstimatesMockMvc.perform(post("/api/estimates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estimates)))
            .andExpect(status().isBadRequest());

        List<Estimates> estimatesList = estimatesRepository.findAll();
        assertThat(estimatesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllEstimates() throws Exception {
        // Initialize the database
        estimatesRepository.save(estimates);

        // Get all the estimatesList
        restEstimatesMockMvc.perform(get("/api/estimates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estimates.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].projectId").value(hasItem(DEFAULT_PROJECT_ID.toString())))
            .andExpect(jsonPath("$.[*].templateId").value(hasItem(DEFAULT_TEMPLATE_ID.toString())))
            .andExpect(jsonPath("$.[*].taskId").value(hasItem(DEFAULT_TASK_ID.toString())))
            .andExpect(jsonPath("$.[*].factorId").value(hasItem(DEFAULT_FACTOR_ID.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].referencedFrom").value(hasItem(DEFAULT_REFERENCED_FROM.toString())))
            .andExpect(jsonPath("$.[*].createdby").value(hasItem(DEFAULT_CREATEDBY.toString())))
            .andExpect(jsonPath("$.[*].createdon").value(hasItem(sameInstant(DEFAULT_CREATEDON))))
            .andExpect(jsonPath("$.[*].modifiedby").value(hasItem(DEFAULT_MODIFIEDBY.toString())))
            .andExpect(jsonPath("$.[*].modifiedon").value(hasItem(sameInstant(DEFAULT_MODIFIEDON))))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    public void getEstimates() throws Exception {
        // Initialize the database
        estimatesRepository.save(estimates);

        // Get the estimates
        restEstimatesMockMvc.perform(get("/api/estimates/{id}", estimates.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(estimates.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.projectId").value(DEFAULT_PROJECT_ID.toString()))
            .andExpect(jsonPath("$.templateId").value(DEFAULT_TEMPLATE_ID.toString()))
            .andExpect(jsonPath("$.taskId").value(DEFAULT_TASK_ID.toString()))
            .andExpect(jsonPath("$.factorId").value(DEFAULT_FACTOR_ID.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.referencedFrom").value(DEFAULT_REFERENCED_FROM.toString()))
            .andExpect(jsonPath("$.createdby").value(DEFAULT_CREATEDBY.toString()))
            .andExpect(jsonPath("$.createdon").value(sameInstant(DEFAULT_CREATEDON)))
            .andExpect(jsonPath("$.modifiedby").value(DEFAULT_MODIFIEDBY.toString()))
            .andExpect(jsonPath("$.modifiedon").value(sameInstant(DEFAULT_MODIFIEDON)))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    public void getNonExistingEstimates() throws Exception {
        // Get the estimates
        restEstimatesMockMvc.perform(get("/api/estimates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateEstimates() throws Exception {
        // Initialize the database
        estimatesRepository.save(estimates);
        int databaseSizeBeforeUpdate = estimatesRepository.findAll().size();

        // Update the estimates
        Estimates updatedEstimates = estimatesRepository.findOne(estimates.getId());
        updatedEstimates
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION)
                .projectId(UPDATED_PROJECT_ID)
                .templateId(UPDATED_TEMPLATE_ID)
                .taskId(UPDATED_TASK_ID)
                .factorId(UPDATED_FACTOR_ID)
                .type(UPDATED_TYPE)
                .value(UPDATED_VALUE)
                .version(UPDATED_VERSION)
                .state(UPDATED_STATE)
                .referencedFrom(UPDATED_REFERENCED_FROM)
                .createdby(UPDATED_CREATEDBY)
                .createdon(UPDATED_CREATEDON)
                .modifiedby(UPDATED_MODIFIEDBY)
                .modifiedon(UPDATED_MODIFIEDON)
                .active(UPDATED_ACTIVE);

        restEstimatesMockMvc.perform(put("/api/estimates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEstimates)))
            .andExpect(status().isOk());

        // Validate the Estimates in the database
        List<Estimates> estimatesList = estimatesRepository.findAll();
        assertThat(estimatesList).hasSize(databaseSizeBeforeUpdate);
        Estimates testEstimates = estimatesList.get(estimatesList.size() - 1);
        assertThat(testEstimates.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEstimates.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEstimates.getProjectId()).isEqualTo(UPDATED_PROJECT_ID);
        assertThat(testEstimates.getTemplateId()).isEqualTo(UPDATED_TEMPLATE_ID);
        assertThat(testEstimates.getTaskId()).isEqualTo(UPDATED_TASK_ID);
        assertThat(testEstimates.getFactorId()).isEqualTo(UPDATED_FACTOR_ID);
        assertThat(testEstimates.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testEstimates.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testEstimates.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testEstimates.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testEstimates.getReferencedFrom()).isEqualTo(UPDATED_REFERENCED_FROM);
        assertThat(testEstimates.getCreatedby()).isEqualTo(UPDATED_CREATEDBY);
        assertThat(testEstimates.getCreatedon()).isEqualTo(UPDATED_CREATEDON);
        assertThat(testEstimates.getModifiedby()).isEqualTo(UPDATED_MODIFIEDBY);
        assertThat(testEstimates.getModifiedon()).isEqualTo(UPDATED_MODIFIEDON);
        assertThat(testEstimates.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    public void updateNonExistingEstimates() throws Exception {
        int databaseSizeBeforeUpdate = estimatesRepository.findAll().size();

        // Create the Estimates

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEstimatesMockMvc.perform(put("/api/estimates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estimates)))
            .andExpect(status().isCreated());

        // Validate the Estimates in the database
        List<Estimates> estimatesList = estimatesRepository.findAll();
        assertThat(estimatesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteEstimates() throws Exception {
        // Initialize the database
        estimatesRepository.save(estimates);
        int databaseSizeBeforeDelete = estimatesRepository.findAll().size();

        // Get the estimates
        restEstimatesMockMvc.perform(delete("/api/estimates/{id}", estimates.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Estimates> estimatesList = estimatesRepository.findAll();
        assertThat(estimatesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
