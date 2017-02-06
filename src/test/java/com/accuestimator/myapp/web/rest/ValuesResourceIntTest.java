package com.accuestimator.myapp.web.rest;

import com.accuestimator.myapp.AccuestimatorNg2App;

import com.accuestimator.myapp.domain.Values;
import com.accuestimator.myapp.repository.ValuesRepository;

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
 * Test class for the ValuesResource REST controller.
 *
 * @see ValuesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccuestimatorNg2App.class)
public class ValuesResourceIntTest {

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
    private ValuesRepository valuesRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restValuesMockMvc;

    private Values values;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            ValuesResource valuesResource = new ValuesResource(valuesRepository);
        this.restValuesMockMvc = MockMvcBuilders.standaloneSetup(valuesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Values createEntity() {
        Values values = new Values()
                .value(DEFAULT_VALUE)
                .version(DEFAULT_VERSION)
                .state(DEFAULT_STATE)
                .createdby(DEFAULT_CREATEDBY)
                .createdon(DEFAULT_CREATEDON)
                .modifiedby(DEFAULT_MODIFIEDBY)
                .modifiedon(DEFAULT_MODIFIEDON)
                .description(DEFAULT_DESCRIPTION)
                .active(DEFAULT_ACTIVE);
        return values;
    }

    @Before
    public void initTest() {
        valuesRepository.deleteAll();
        values = createEntity();
    }

    @Test
    public void createValues() throws Exception {
        int databaseSizeBeforeCreate = valuesRepository.findAll().size();

        // Create the Values

        restValuesMockMvc.perform(post("/api/values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(values)))
            .andExpect(status().isCreated());

        // Validate the Values in the database
        List<Values> valuesList = valuesRepository.findAll();
        assertThat(valuesList).hasSize(databaseSizeBeforeCreate + 1);
        Values testValues = valuesList.get(valuesList.size() - 1);
        assertThat(testValues.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testValues.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testValues.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testValues.getCreatedby()).isEqualTo(DEFAULT_CREATEDBY);
        assertThat(testValues.getCreatedon()).isEqualTo(DEFAULT_CREATEDON);
        assertThat(testValues.getModifiedby()).isEqualTo(DEFAULT_MODIFIEDBY);
        assertThat(testValues.getModifiedon()).isEqualTo(DEFAULT_MODIFIEDON);
        assertThat(testValues.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testValues.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    public void createValuesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = valuesRepository.findAll().size();

        // Create the Values with an existing ID
        Values existingValues = new Values();
        existingValues.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restValuesMockMvc.perform(post("/api/values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingValues)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Values> valuesList = valuesRepository.findAll();
        assertThat(valuesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = valuesRepository.findAll().size();
        // set the field null
        values.setValue(null);

        // Create the Values, which fails.

        restValuesMockMvc.perform(post("/api/values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(values)))
            .andExpect(status().isBadRequest());

        List<Values> valuesList = valuesRepository.findAll();
        assertThat(valuesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = valuesRepository.findAll().size();
        // set the field null
        values.setVersion(null);

        // Create the Values, which fails.

        restValuesMockMvc.perform(post("/api/values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(values)))
            .andExpect(status().isBadRequest());

        List<Values> valuesList = valuesRepository.findAll();
        assertThat(valuesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = valuesRepository.findAll().size();
        // set the field null
        values.setState(null);

        // Create the Values, which fails.

        restValuesMockMvc.perform(post("/api/values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(values)))
            .andExpect(status().isBadRequest());

        List<Values> valuesList = valuesRepository.findAll();
        assertThat(valuesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = valuesRepository.findAll().size();
        // set the field null
        values.setActive(null);

        // Create the Values, which fails.

        restValuesMockMvc.perform(post("/api/values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(values)))
            .andExpect(status().isBadRequest());

        List<Values> valuesList = valuesRepository.findAll();
        assertThat(valuesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllValues() throws Exception {
        // Initialize the database
        valuesRepository.save(values);

        // Get all the valuesList
        restValuesMockMvc.perform(get("/api/values?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(values.getId())))
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
    public void getValues() throws Exception {
        // Initialize the database
        valuesRepository.save(values);

        // Get the values
        restValuesMockMvc.perform(get("/api/values/{id}", values.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(values.getId()))
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
    public void getNonExistingValues() throws Exception {
        // Get the values
        restValuesMockMvc.perform(get("/api/values/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateValues() throws Exception {
        // Initialize the database
        valuesRepository.save(values);
        int databaseSizeBeforeUpdate = valuesRepository.findAll().size();

        // Update the values
        Values updatedValues = valuesRepository.findOne(values.getId());
        updatedValues
                .value(UPDATED_VALUE)
                .version(UPDATED_VERSION)
                .state(UPDATED_STATE)
                .createdby(UPDATED_CREATEDBY)
                .createdon(UPDATED_CREATEDON)
                .modifiedby(UPDATED_MODIFIEDBY)
                .modifiedon(UPDATED_MODIFIEDON)
                .description(UPDATED_DESCRIPTION)
                .active(UPDATED_ACTIVE);

        restValuesMockMvc.perform(put("/api/values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedValues)))
            .andExpect(status().isOk());

        // Validate the Values in the database
        List<Values> valuesList = valuesRepository.findAll();
        assertThat(valuesList).hasSize(databaseSizeBeforeUpdate);
        Values testValues = valuesList.get(valuesList.size() - 1);
        assertThat(testValues.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testValues.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testValues.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testValues.getCreatedby()).isEqualTo(UPDATED_CREATEDBY);
        assertThat(testValues.getCreatedon()).isEqualTo(UPDATED_CREATEDON);
        assertThat(testValues.getModifiedby()).isEqualTo(UPDATED_MODIFIEDBY);
        assertThat(testValues.getModifiedon()).isEqualTo(UPDATED_MODIFIEDON);
        assertThat(testValues.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testValues.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    public void updateNonExistingValues() throws Exception {
        int databaseSizeBeforeUpdate = valuesRepository.findAll().size();

        // Create the Values

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restValuesMockMvc.perform(put("/api/values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(values)))
            .andExpect(status().isCreated());

        // Validate the Values in the database
        List<Values> valuesList = valuesRepository.findAll();
        assertThat(valuesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteValues() throws Exception {
        // Initialize the database
        valuesRepository.save(values);
        int databaseSizeBeforeDelete = valuesRepository.findAll().size();

        // Get the values
        restValuesMockMvc.perform(delete("/api/values/{id}", values.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Values> valuesList = valuesRepository.findAll();
        assertThat(valuesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
