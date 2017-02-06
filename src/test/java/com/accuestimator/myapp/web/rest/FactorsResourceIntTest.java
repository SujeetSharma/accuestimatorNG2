package com.accuestimator.myapp.web.rest;

import com.accuestimator.myapp.AccuestimatorNg2App;

import com.accuestimator.myapp.domain.Factors;
import com.accuestimator.myapp.repository.FactorsRepository;

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
 * Test class for the FactorsResource REST controller.
 *
 * @see FactorsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccuestimatorNg2App.class)
public class FactorsResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final TYPEENUM DEFAULT_TYPE = TYPEENUM.TECHNICAL;
    private static final TYPEENUM UPDATED_TYPE = TYPEENUM.MANAGEMENT;

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
    private FactorsRepository factorsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFactorsMockMvc;

    private Factors factors;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            FactorsResource factorsResource = new FactorsResource(factorsRepository);
        this.restFactorsMockMvc = MockMvcBuilders.standaloneSetup(factorsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Factors createEntity() {
        Factors factors = new Factors()
                .name(DEFAULT_NAME)
                .category(DEFAULT_CATEGORY)
                .type(DEFAULT_TYPE)
                .value(DEFAULT_VALUE)
                .version(DEFAULT_VERSION)
                .state(DEFAULT_STATE)
                .createdby(DEFAULT_CREATEDBY)
                .createdon(DEFAULT_CREATEDON)
                .modifiedby(DEFAULT_MODIFIEDBY)
                .modifiedon(DEFAULT_MODIFIEDON)
                .description(DEFAULT_DESCRIPTION)
                .active(DEFAULT_ACTIVE);
        return factors;
    }

    @Before
    public void initTest() {
        factorsRepository.deleteAll();
        factors = createEntity();
    }

    @Test
    public void createFactors() throws Exception {
        int databaseSizeBeforeCreate = factorsRepository.findAll().size();

        // Create the Factors

        restFactorsMockMvc.perform(post("/api/factors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(factors)))
            .andExpect(status().isCreated());

        // Validate the Factors in the database
        List<Factors> factorsList = factorsRepository.findAll();
        assertThat(factorsList).hasSize(databaseSizeBeforeCreate + 1);
        Factors testFactors = factorsList.get(factorsList.size() - 1);
        assertThat(testFactors.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFactors.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testFactors.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testFactors.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testFactors.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testFactors.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testFactors.getCreatedby()).isEqualTo(DEFAULT_CREATEDBY);
        assertThat(testFactors.getCreatedon()).isEqualTo(DEFAULT_CREATEDON);
        assertThat(testFactors.getModifiedby()).isEqualTo(DEFAULT_MODIFIEDBY);
        assertThat(testFactors.getModifiedon()).isEqualTo(DEFAULT_MODIFIEDON);
        assertThat(testFactors.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFactors.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    public void createFactorsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = factorsRepository.findAll().size();

        // Create the Factors with an existing ID
        Factors existingFactors = new Factors();
        existingFactors.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restFactorsMockMvc.perform(post("/api/factors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingFactors)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Factors> factorsList = factorsRepository.findAll();
        assertThat(factorsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = factorsRepository.findAll().size();
        // set the field null
        factors.setName(null);

        // Create the Factors, which fails.

        restFactorsMockMvc.perform(post("/api/factors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(factors)))
            .andExpect(status().isBadRequest());

        List<Factors> factorsList = factorsRepository.findAll();
        assertThat(factorsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = factorsRepository.findAll().size();
        // set the field null
        factors.setCategory(null);

        // Create the Factors, which fails.

        restFactorsMockMvc.perform(post("/api/factors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(factors)))
            .andExpect(status().isBadRequest());

        List<Factors> factorsList = factorsRepository.findAll();
        assertThat(factorsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = factorsRepository.findAll().size();
        // set the field null
        factors.setType(null);

        // Create the Factors, which fails.

        restFactorsMockMvc.perform(post("/api/factors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(factors)))
            .andExpect(status().isBadRequest());

        List<Factors> factorsList = factorsRepository.findAll();
        assertThat(factorsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = factorsRepository.findAll().size();
        // set the field null
        factors.setValue(null);

        // Create the Factors, which fails.

        restFactorsMockMvc.perform(post("/api/factors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(factors)))
            .andExpect(status().isBadRequest());

        List<Factors> factorsList = factorsRepository.findAll();
        assertThat(factorsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = factorsRepository.findAll().size();
        // set the field null
        factors.setVersion(null);

        // Create the Factors, which fails.

        restFactorsMockMvc.perform(post("/api/factors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(factors)))
            .andExpect(status().isBadRequest());

        List<Factors> factorsList = factorsRepository.findAll();
        assertThat(factorsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = factorsRepository.findAll().size();
        // set the field null
        factors.setState(null);

        // Create the Factors, which fails.

        restFactorsMockMvc.perform(post("/api/factors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(factors)))
            .andExpect(status().isBadRequest());

        List<Factors> factorsList = factorsRepository.findAll();
        assertThat(factorsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = factorsRepository.findAll().size();
        // set the field null
        factors.setActive(null);

        // Create the Factors, which fails.

        restFactorsMockMvc.perform(post("/api/factors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(factors)))
            .andExpect(status().isBadRequest());

        List<Factors> factorsList = factorsRepository.findAll();
        assertThat(factorsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllFactors() throws Exception {
        // Initialize the database
        factorsRepository.save(factors);

        // Get all the factorsList
        restFactorsMockMvc.perform(get("/api/factors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(factors.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
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
    public void getFactors() throws Exception {
        // Initialize the database
        factorsRepository.save(factors);

        // Get the factors
        restFactorsMockMvc.perform(get("/api/factors/{id}", factors.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(factors.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
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
    public void getNonExistingFactors() throws Exception {
        // Get the factors
        restFactorsMockMvc.perform(get("/api/factors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateFactors() throws Exception {
        // Initialize the database
        factorsRepository.save(factors);
        int databaseSizeBeforeUpdate = factorsRepository.findAll().size();

        // Update the factors
        Factors updatedFactors = factorsRepository.findOne(factors.getId());
        updatedFactors
                .name(UPDATED_NAME)
                .category(UPDATED_CATEGORY)
                .type(UPDATED_TYPE)
                .value(UPDATED_VALUE)
                .version(UPDATED_VERSION)
                .state(UPDATED_STATE)
                .createdby(UPDATED_CREATEDBY)
                .createdon(UPDATED_CREATEDON)
                .modifiedby(UPDATED_MODIFIEDBY)
                .modifiedon(UPDATED_MODIFIEDON)
                .description(UPDATED_DESCRIPTION)
                .active(UPDATED_ACTIVE);

        restFactorsMockMvc.perform(put("/api/factors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFactors)))
            .andExpect(status().isOk());

        // Validate the Factors in the database
        List<Factors> factorsList = factorsRepository.findAll();
        assertThat(factorsList).hasSize(databaseSizeBeforeUpdate);
        Factors testFactors = factorsList.get(factorsList.size() - 1);
        assertThat(testFactors.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFactors.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testFactors.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testFactors.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testFactors.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testFactors.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testFactors.getCreatedby()).isEqualTo(UPDATED_CREATEDBY);
        assertThat(testFactors.getCreatedon()).isEqualTo(UPDATED_CREATEDON);
        assertThat(testFactors.getModifiedby()).isEqualTo(UPDATED_MODIFIEDBY);
        assertThat(testFactors.getModifiedon()).isEqualTo(UPDATED_MODIFIEDON);
        assertThat(testFactors.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFactors.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    public void updateNonExistingFactors() throws Exception {
        int databaseSizeBeforeUpdate = factorsRepository.findAll().size();

        // Create the Factors

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFactorsMockMvc.perform(put("/api/factors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(factors)))
            .andExpect(status().isCreated());

        // Validate the Factors in the database
        List<Factors> factorsList = factorsRepository.findAll();
        assertThat(factorsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteFactors() throws Exception {
        // Initialize the database
        factorsRepository.save(factors);
        int databaseSizeBeforeDelete = factorsRepository.findAll().size();

        // Get the factors
        restFactorsMockMvc.perform(delete("/api/factors/{id}", factors.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Factors> factorsList = factorsRepository.findAll();
        assertThat(factorsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
