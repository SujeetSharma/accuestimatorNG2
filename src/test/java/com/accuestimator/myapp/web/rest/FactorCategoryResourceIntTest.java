package com.accuestimator.myapp.web.rest;

import com.accuestimator.myapp.AccuestimatorNg2App;

import com.accuestimator.myapp.domain.FactorCategory;
import com.accuestimator.myapp.repository.FactorCategoryRepository;

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
 * Test class for the FactorCategoryResource REST controller.
 *
 * @see FactorCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccuestimatorNg2App.class)
public class FactorCategoryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final TYPEENUM DEFAULT_TYPE = TYPEENUM.TECHNICAL;
    private static final TYPEENUM UPDATED_TYPE = TYPEENUM.MANAGEMENT;

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
    private FactorCategoryRepository factorCategoryRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFactorCategoryMockMvc;

    private FactorCategory factorCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            FactorCategoryResource factorCategoryResource = new FactorCategoryResource(factorCategoryRepository);
        this.restFactorCategoryMockMvc = MockMvcBuilders.standaloneSetup(factorCategoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FactorCategory createEntity() {
        FactorCategory factorCategory = new FactorCategory()
                .name(DEFAULT_NAME)
                .type(DEFAULT_TYPE)
                .version(DEFAULT_VERSION)
                .state(DEFAULT_STATE)
                .createdby(DEFAULT_CREATEDBY)
                .createdon(DEFAULT_CREATEDON)
                .modifiedby(DEFAULT_MODIFIEDBY)
                .modifiedon(DEFAULT_MODIFIEDON)
                .description(DEFAULT_DESCRIPTION)
                .active(DEFAULT_ACTIVE);
        return factorCategory;
    }

    @Before
    public void initTest() {
        factorCategoryRepository.deleteAll();
        factorCategory = createEntity();
    }

    @Test
    public void createFactorCategory() throws Exception {
        int databaseSizeBeforeCreate = factorCategoryRepository.findAll().size();

        // Create the FactorCategory

        restFactorCategoryMockMvc.perform(post("/api/factor-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(factorCategory)))
            .andExpect(status().isCreated());

        // Validate the FactorCategory in the database
        List<FactorCategory> factorCategoryList = factorCategoryRepository.findAll();
        assertThat(factorCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        FactorCategory testFactorCategory = factorCategoryList.get(factorCategoryList.size() - 1);
        assertThat(testFactorCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFactorCategory.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testFactorCategory.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testFactorCategory.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testFactorCategory.getCreatedby()).isEqualTo(DEFAULT_CREATEDBY);
        assertThat(testFactorCategory.getCreatedon()).isEqualTo(DEFAULT_CREATEDON);
        assertThat(testFactorCategory.getModifiedby()).isEqualTo(DEFAULT_MODIFIEDBY);
        assertThat(testFactorCategory.getModifiedon()).isEqualTo(DEFAULT_MODIFIEDON);
        assertThat(testFactorCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFactorCategory.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    public void createFactorCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = factorCategoryRepository.findAll().size();

        // Create the FactorCategory with an existing ID
        FactorCategory existingFactorCategory = new FactorCategory();
        existingFactorCategory.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restFactorCategoryMockMvc.perform(post("/api/factor-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingFactorCategory)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<FactorCategory> factorCategoryList = factorCategoryRepository.findAll();
        assertThat(factorCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = factorCategoryRepository.findAll().size();
        // set the field null
        factorCategory.setName(null);

        // Create the FactorCategory, which fails.

        restFactorCategoryMockMvc.perform(post("/api/factor-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(factorCategory)))
            .andExpect(status().isBadRequest());

        List<FactorCategory> factorCategoryList = factorCategoryRepository.findAll();
        assertThat(factorCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = factorCategoryRepository.findAll().size();
        // set the field null
        factorCategory.setType(null);

        // Create the FactorCategory, which fails.

        restFactorCategoryMockMvc.perform(post("/api/factor-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(factorCategory)))
            .andExpect(status().isBadRequest());

        List<FactorCategory> factorCategoryList = factorCategoryRepository.findAll();
        assertThat(factorCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = factorCategoryRepository.findAll().size();
        // set the field null
        factorCategory.setVersion(null);

        // Create the FactorCategory, which fails.

        restFactorCategoryMockMvc.perform(post("/api/factor-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(factorCategory)))
            .andExpect(status().isBadRequest());

        List<FactorCategory> factorCategoryList = factorCategoryRepository.findAll();
        assertThat(factorCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = factorCategoryRepository.findAll().size();
        // set the field null
        factorCategory.setState(null);

        // Create the FactorCategory, which fails.

        restFactorCategoryMockMvc.perform(post("/api/factor-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(factorCategory)))
            .andExpect(status().isBadRequest());

        List<FactorCategory> factorCategoryList = factorCategoryRepository.findAll();
        assertThat(factorCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = factorCategoryRepository.findAll().size();
        // set the field null
        factorCategory.setActive(null);

        // Create the FactorCategory, which fails.

        restFactorCategoryMockMvc.perform(post("/api/factor-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(factorCategory)))
            .andExpect(status().isBadRequest());

        List<FactorCategory> factorCategoryList = factorCategoryRepository.findAll();
        assertThat(factorCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllFactorCategories() throws Exception {
        // Initialize the database
        factorCategoryRepository.save(factorCategory);

        // Get all the factorCategoryList
        restFactorCategoryMockMvc.perform(get("/api/factor-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(factorCategory.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
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
    public void getFactorCategory() throws Exception {
        // Initialize the database
        factorCategoryRepository.save(factorCategory);

        // Get the factorCategory
        restFactorCategoryMockMvc.perform(get("/api/factor-categories/{id}", factorCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(factorCategory.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
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
    public void getNonExistingFactorCategory() throws Exception {
        // Get the factorCategory
        restFactorCategoryMockMvc.perform(get("/api/factor-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateFactorCategory() throws Exception {
        // Initialize the database
        factorCategoryRepository.save(factorCategory);
        int databaseSizeBeforeUpdate = factorCategoryRepository.findAll().size();

        // Update the factorCategory
        FactorCategory updatedFactorCategory = factorCategoryRepository.findOne(factorCategory.getId());
        updatedFactorCategory
                .name(UPDATED_NAME)
                .type(UPDATED_TYPE)
                .version(UPDATED_VERSION)
                .state(UPDATED_STATE)
                .createdby(UPDATED_CREATEDBY)
                .createdon(UPDATED_CREATEDON)
                .modifiedby(UPDATED_MODIFIEDBY)
                .modifiedon(UPDATED_MODIFIEDON)
                .description(UPDATED_DESCRIPTION)
                .active(UPDATED_ACTIVE);

        restFactorCategoryMockMvc.perform(put("/api/factor-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFactorCategory)))
            .andExpect(status().isOk());

        // Validate the FactorCategory in the database
        List<FactorCategory> factorCategoryList = factorCategoryRepository.findAll();
        assertThat(factorCategoryList).hasSize(databaseSizeBeforeUpdate);
        FactorCategory testFactorCategory = factorCategoryList.get(factorCategoryList.size() - 1);
        assertThat(testFactorCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFactorCategory.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testFactorCategory.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testFactorCategory.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testFactorCategory.getCreatedby()).isEqualTo(UPDATED_CREATEDBY);
        assertThat(testFactorCategory.getCreatedon()).isEqualTo(UPDATED_CREATEDON);
        assertThat(testFactorCategory.getModifiedby()).isEqualTo(UPDATED_MODIFIEDBY);
        assertThat(testFactorCategory.getModifiedon()).isEqualTo(UPDATED_MODIFIEDON);
        assertThat(testFactorCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFactorCategory.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    public void updateNonExistingFactorCategory() throws Exception {
        int databaseSizeBeforeUpdate = factorCategoryRepository.findAll().size();

        // Create the FactorCategory

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFactorCategoryMockMvc.perform(put("/api/factor-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(factorCategory)))
            .andExpect(status().isCreated());

        // Validate the FactorCategory in the database
        List<FactorCategory> factorCategoryList = factorCategoryRepository.findAll();
        assertThat(factorCategoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteFactorCategory() throws Exception {
        // Initialize the database
        factorCategoryRepository.save(factorCategory);
        int databaseSizeBeforeDelete = factorCategoryRepository.findAll().size();

        // Get the factorCategory
        restFactorCategoryMockMvc.perform(delete("/api/factor-categories/{id}", factorCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FactorCategory> factorCategoryList = factorCategoryRepository.findAll();
        assertThat(factorCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
