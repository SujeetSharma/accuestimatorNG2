package com.accuestimator.myapp.web.rest;

import com.accuestimator.myapp.AccuestimatorNg2App;

import com.accuestimator.myapp.domain.FactorsTaskMapping;
import com.accuestimator.myapp.repository.FactorsTaskMappingRepository;

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
 * Test class for the FactorsTaskMappingResource REST controller.
 *
 * @see FactorsTaskMappingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccuestimatorNg2App.class)
public class FactorsTaskMappingResourceIntTest {

    private static final String DEFAULT_TASK_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_TASK_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_TASK = "AAAAAAAAAA";
    private static final String UPDATED_TASK = "BBBBBBBBBB";

    private static final String DEFAULT_FACTOR = "AAAAAAAAAA";
    private static final String UPDATED_FACTOR = "BBBBBBBBBB";

    private static final String DEFAULT_FACTOR_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_FACTOR_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_FORMULA = "AAAAAAAAAA";
    private static final String UPDATED_FORMULA = "BBBBBBBBBB";

    private static final Float DEFAULT_VALUE = 1F;
    private static final Float UPDATED_VALUE = 2F;

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

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private FactorsTaskMappingRepository factorsTaskMappingRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFactorsTaskMappingMockMvc;

    private FactorsTaskMapping factorsTaskMapping;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            FactorsTaskMappingResource factorsTaskMappingResource = new FactorsTaskMappingResource(factorsTaskMappingRepository);
        this.restFactorsTaskMappingMockMvc = MockMvcBuilders.standaloneSetup(factorsTaskMappingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FactorsTaskMapping createEntity() {
        FactorsTaskMapping factorsTaskMapping = new FactorsTaskMapping()
                .taskCategory(DEFAULT_TASK_CATEGORY)
                .task(DEFAULT_TASK)
                .factor(DEFAULT_FACTOR)
                .factorCategory(DEFAULT_FACTOR_CATEGORY)
                .formula(DEFAULT_FORMULA)
                .value(DEFAULT_VALUE)
                .version(DEFAULT_VERSION)
                .state(DEFAULT_STATE)
                .createdby(DEFAULT_CREATEDBY)
                .createdon(DEFAULT_CREATEDON)
                .modifiedby(DEFAULT_MODIFIEDBY)
                .modifiedon(DEFAULT_MODIFIEDON)
                .description(DEFAULT_DESCRIPTION)
                .active(DEFAULT_ACTIVE);
        return factorsTaskMapping;
    }

    @Before
    public void initTest() {
        factorsTaskMappingRepository.deleteAll();
        factorsTaskMapping = createEntity();
    }

    @Test
    public void createFactorsTaskMapping() throws Exception {
        int databaseSizeBeforeCreate = factorsTaskMappingRepository.findAll().size();

        // Create the FactorsTaskMapping

        restFactorsTaskMappingMockMvc.perform(post("/api/factors-task-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(factorsTaskMapping)))
            .andExpect(status().isCreated());

        // Validate the FactorsTaskMapping in the database
        List<FactorsTaskMapping> factorsTaskMappingList = factorsTaskMappingRepository.findAll();
        assertThat(factorsTaskMappingList).hasSize(databaseSizeBeforeCreate + 1);
        FactorsTaskMapping testFactorsTaskMapping = factorsTaskMappingList.get(factorsTaskMappingList.size() - 1);
        assertThat(testFactorsTaskMapping.getTaskCategory()).isEqualTo(DEFAULT_TASK_CATEGORY);
        assertThat(testFactorsTaskMapping.getTask()).isEqualTo(DEFAULT_TASK);
        assertThat(testFactorsTaskMapping.getFactor()).isEqualTo(DEFAULT_FACTOR);
        assertThat(testFactorsTaskMapping.getFactorCategory()).isEqualTo(DEFAULT_FACTOR_CATEGORY);
        assertThat(testFactorsTaskMapping.getFormula()).isEqualTo(DEFAULT_FORMULA);
        assertThat(testFactorsTaskMapping.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testFactorsTaskMapping.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testFactorsTaskMapping.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testFactorsTaskMapping.getCreatedby()).isEqualTo(DEFAULT_CREATEDBY);
        assertThat(testFactorsTaskMapping.getCreatedon()).isEqualTo(DEFAULT_CREATEDON);
        assertThat(testFactorsTaskMapping.getModifiedby()).isEqualTo(DEFAULT_MODIFIEDBY);
        assertThat(testFactorsTaskMapping.getModifiedon()).isEqualTo(DEFAULT_MODIFIEDON);
        assertThat(testFactorsTaskMapping.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFactorsTaskMapping.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    public void createFactorsTaskMappingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = factorsTaskMappingRepository.findAll().size();

        // Create the FactorsTaskMapping with an existing ID
        FactorsTaskMapping existingFactorsTaskMapping = new FactorsTaskMapping();
        existingFactorsTaskMapping.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restFactorsTaskMappingMockMvc.perform(post("/api/factors-task-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingFactorsTaskMapping)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<FactorsTaskMapping> factorsTaskMappingList = factorsTaskMappingRepository.findAll();
        assertThat(factorsTaskMappingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkTaskCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = factorsTaskMappingRepository.findAll().size();
        // set the field null
        factorsTaskMapping.setTaskCategory(null);

        // Create the FactorsTaskMapping, which fails.

        restFactorsTaskMappingMockMvc.perform(post("/api/factors-task-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(factorsTaskMapping)))
            .andExpect(status().isBadRequest());

        List<FactorsTaskMapping> factorsTaskMappingList = factorsTaskMappingRepository.findAll();
        assertThat(factorsTaskMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkTaskIsRequired() throws Exception {
        int databaseSizeBeforeTest = factorsTaskMappingRepository.findAll().size();
        // set the field null
        factorsTaskMapping.setTask(null);

        // Create the FactorsTaskMapping, which fails.

        restFactorsTaskMappingMockMvc.perform(post("/api/factors-task-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(factorsTaskMapping)))
            .andExpect(status().isBadRequest());

        List<FactorsTaskMapping> factorsTaskMappingList = factorsTaskMappingRepository.findAll();
        assertThat(factorsTaskMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkFactorIsRequired() throws Exception {
        int databaseSizeBeforeTest = factorsTaskMappingRepository.findAll().size();
        // set the field null
        factorsTaskMapping.setFactor(null);

        // Create the FactorsTaskMapping, which fails.

        restFactorsTaskMappingMockMvc.perform(post("/api/factors-task-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(factorsTaskMapping)))
            .andExpect(status().isBadRequest());

        List<FactorsTaskMapping> factorsTaskMappingList = factorsTaskMappingRepository.findAll();
        assertThat(factorsTaskMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkFactorCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = factorsTaskMappingRepository.findAll().size();
        // set the field null
        factorsTaskMapping.setFactorCategory(null);

        // Create the FactorsTaskMapping, which fails.

        restFactorsTaskMappingMockMvc.perform(post("/api/factors-task-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(factorsTaskMapping)))
            .andExpect(status().isBadRequest());

        List<FactorsTaskMapping> factorsTaskMappingList = factorsTaskMappingRepository.findAll();
        assertThat(factorsTaskMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkFormulaIsRequired() throws Exception {
        int databaseSizeBeforeTest = factorsTaskMappingRepository.findAll().size();
        // set the field null
        factorsTaskMapping.setFormula(null);

        // Create the FactorsTaskMapping, which fails.

        restFactorsTaskMappingMockMvc.perform(post("/api/factors-task-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(factorsTaskMapping)))
            .andExpect(status().isBadRequest());

        List<FactorsTaskMapping> factorsTaskMappingList = factorsTaskMappingRepository.findAll();
        assertThat(factorsTaskMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = factorsTaskMappingRepository.findAll().size();
        // set the field null
        factorsTaskMapping.setValue(null);

        // Create the FactorsTaskMapping, which fails.

        restFactorsTaskMappingMockMvc.perform(post("/api/factors-task-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(factorsTaskMapping)))
            .andExpect(status().isBadRequest());

        List<FactorsTaskMapping> factorsTaskMappingList = factorsTaskMappingRepository.findAll();
        assertThat(factorsTaskMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = factorsTaskMappingRepository.findAll().size();
        // set the field null
        factorsTaskMapping.setVersion(null);

        // Create the FactorsTaskMapping, which fails.

        restFactorsTaskMappingMockMvc.perform(post("/api/factors-task-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(factorsTaskMapping)))
            .andExpect(status().isBadRequest());

        List<FactorsTaskMapping> factorsTaskMappingList = factorsTaskMappingRepository.findAll();
        assertThat(factorsTaskMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = factorsTaskMappingRepository.findAll().size();
        // set the field null
        factorsTaskMapping.setState(null);

        // Create the FactorsTaskMapping, which fails.

        restFactorsTaskMappingMockMvc.perform(post("/api/factors-task-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(factorsTaskMapping)))
            .andExpect(status().isBadRequest());

        List<FactorsTaskMapping> factorsTaskMappingList = factorsTaskMappingRepository.findAll();
        assertThat(factorsTaskMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = factorsTaskMappingRepository.findAll().size();
        // set the field null
        factorsTaskMapping.setActive(null);

        // Create the FactorsTaskMapping, which fails.

        restFactorsTaskMappingMockMvc.perform(post("/api/factors-task-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(factorsTaskMapping)))
            .andExpect(status().isBadRequest());

        List<FactorsTaskMapping> factorsTaskMappingList = factorsTaskMappingRepository.findAll();
        assertThat(factorsTaskMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllFactorsTaskMappings() throws Exception {
        // Initialize the database
        factorsTaskMappingRepository.save(factorsTaskMapping);

        // Get all the factorsTaskMappingList
        restFactorsTaskMappingMockMvc.perform(get("/api/factors-task-mappings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(factorsTaskMapping.getId())))
            .andExpect(jsonPath("$.[*].taskCategory").value(hasItem(DEFAULT_TASK_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].task").value(hasItem(DEFAULT_TASK.toString())))
            .andExpect(jsonPath("$.[*].factor").value(hasItem(DEFAULT_FACTOR.toString())))
            .andExpect(jsonPath("$.[*].factorCategory").value(hasItem(DEFAULT_FACTOR_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].formula").value(hasItem(DEFAULT_FORMULA.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].createdby").value(hasItem(DEFAULT_CREATEDBY.toString())))
            .andExpect(jsonPath("$.[*].createdon").value(hasItem(sameInstant(DEFAULT_CREATEDON))))
            .andExpect(jsonPath("$.[*].modifiedby").value(hasItem(DEFAULT_MODIFIEDBY.toString())))
            .andExpect(jsonPath("$.[*].modifiedon").value(hasItem(sameInstant(DEFAULT_MODIFIEDON))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    public void getFactorsTaskMapping() throws Exception {
        // Initialize the database
        factorsTaskMappingRepository.save(factorsTaskMapping);

        // Get the factorsTaskMapping
        restFactorsTaskMappingMockMvc.perform(get("/api/factors-task-mappings/{id}", factorsTaskMapping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(factorsTaskMapping.getId()))
            .andExpect(jsonPath("$.taskCategory").value(DEFAULT_TASK_CATEGORY.toString()))
            .andExpect(jsonPath("$.task").value(DEFAULT_TASK.toString()))
            .andExpect(jsonPath("$.factor").value(DEFAULT_FACTOR.toString()))
            .andExpect(jsonPath("$.factorCategory").value(DEFAULT_FACTOR_CATEGORY.toString()))
            .andExpect(jsonPath("$.formula").value(DEFAULT_FORMULA.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.createdby").value(DEFAULT_CREATEDBY.toString()))
            .andExpect(jsonPath("$.createdon").value(sameInstant(DEFAULT_CREATEDON)))
            .andExpect(jsonPath("$.modifiedby").value(DEFAULT_MODIFIEDBY.toString()))
            .andExpect(jsonPath("$.modifiedon").value(sameInstant(DEFAULT_MODIFIEDON)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    public void getNonExistingFactorsTaskMapping() throws Exception {
        // Get the factorsTaskMapping
        restFactorsTaskMappingMockMvc.perform(get("/api/factors-task-mappings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateFactorsTaskMapping() throws Exception {
        // Initialize the database
        factorsTaskMappingRepository.save(factorsTaskMapping);
        int databaseSizeBeforeUpdate = factorsTaskMappingRepository.findAll().size();

        // Update the factorsTaskMapping
        FactorsTaskMapping updatedFactorsTaskMapping = factorsTaskMappingRepository.findOne(factorsTaskMapping.getId());
        updatedFactorsTaskMapping
                .taskCategory(UPDATED_TASK_CATEGORY)
                .task(UPDATED_TASK)
                .factor(UPDATED_FACTOR)
                .factorCategory(UPDATED_FACTOR_CATEGORY)
                .formula(UPDATED_FORMULA)
                .value(UPDATED_VALUE)
                .version(UPDATED_VERSION)
                .state(UPDATED_STATE)
                .createdby(UPDATED_CREATEDBY)
                .createdon(UPDATED_CREATEDON)
                .modifiedby(UPDATED_MODIFIEDBY)
                .modifiedon(UPDATED_MODIFIEDON)
                .description(UPDATED_DESCRIPTION)
                .active(UPDATED_ACTIVE);

        restFactorsTaskMappingMockMvc.perform(put("/api/factors-task-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFactorsTaskMapping)))
            .andExpect(status().isOk());

        // Validate the FactorsTaskMapping in the database
        List<FactorsTaskMapping> factorsTaskMappingList = factorsTaskMappingRepository.findAll();
        assertThat(factorsTaskMappingList).hasSize(databaseSizeBeforeUpdate);
        FactorsTaskMapping testFactorsTaskMapping = factorsTaskMappingList.get(factorsTaskMappingList.size() - 1);
        assertThat(testFactorsTaskMapping.getTaskCategory()).isEqualTo(UPDATED_TASK_CATEGORY);
        assertThat(testFactorsTaskMapping.getTask()).isEqualTo(UPDATED_TASK);
        assertThat(testFactorsTaskMapping.getFactor()).isEqualTo(UPDATED_FACTOR);
        assertThat(testFactorsTaskMapping.getFactorCategory()).isEqualTo(UPDATED_FACTOR_CATEGORY);
        assertThat(testFactorsTaskMapping.getFormula()).isEqualTo(UPDATED_FORMULA);
        assertThat(testFactorsTaskMapping.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testFactorsTaskMapping.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testFactorsTaskMapping.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testFactorsTaskMapping.getCreatedby()).isEqualTo(UPDATED_CREATEDBY);
        assertThat(testFactorsTaskMapping.getCreatedon()).isEqualTo(UPDATED_CREATEDON);
        assertThat(testFactorsTaskMapping.getModifiedby()).isEqualTo(UPDATED_MODIFIEDBY);
        assertThat(testFactorsTaskMapping.getModifiedon()).isEqualTo(UPDATED_MODIFIEDON);
        assertThat(testFactorsTaskMapping.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFactorsTaskMapping.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    public void updateNonExistingFactorsTaskMapping() throws Exception {
        int databaseSizeBeforeUpdate = factorsTaskMappingRepository.findAll().size();

        // Create the FactorsTaskMapping

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFactorsTaskMappingMockMvc.perform(put("/api/factors-task-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(factorsTaskMapping)))
            .andExpect(status().isCreated());

        // Validate the FactorsTaskMapping in the database
        List<FactorsTaskMapping> factorsTaskMappingList = factorsTaskMappingRepository.findAll();
        assertThat(factorsTaskMappingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteFactorsTaskMapping() throws Exception {
        // Initialize the database
        factorsTaskMappingRepository.save(factorsTaskMapping);
        int databaseSizeBeforeDelete = factorsTaskMappingRepository.findAll().size();

        // Get the factorsTaskMapping
        restFactorsTaskMappingMockMvc.perform(delete("/api/factors-task-mappings/{id}", factorsTaskMapping.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FactorsTaskMapping> factorsTaskMappingList = factorsTaskMappingRepository.findAll();
        assertThat(factorsTaskMappingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
