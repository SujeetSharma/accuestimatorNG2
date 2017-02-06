package com.accuestimator.myapp.web.rest;

import com.accuestimator.myapp.AccuestimatorNg2App;

import com.accuestimator.myapp.domain.TaskCategory;
import com.accuestimator.myapp.repository.TaskCategoryRepository;

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
 * Test class for the TaskCategoryResource REST controller.
 *
 * @see TaskCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccuestimatorNg2App.class)
public class TaskCategoryResourceIntTest {

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
    private TaskCategoryRepository taskCategoryRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTaskCategoryMockMvc;

    private TaskCategory taskCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            TaskCategoryResource taskCategoryResource = new TaskCategoryResource(taskCategoryRepository);
        this.restTaskCategoryMockMvc = MockMvcBuilders.standaloneSetup(taskCategoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskCategory createEntity() {
        TaskCategory taskCategory = new TaskCategory()
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
        return taskCategory;
    }

    @Before
    public void initTest() {
        taskCategoryRepository.deleteAll();
        taskCategory = createEntity();
    }

    @Test
    public void createTaskCategory() throws Exception {
        int databaseSizeBeforeCreate = taskCategoryRepository.findAll().size();

        // Create the TaskCategory

        restTaskCategoryMockMvc.perform(post("/api/task-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskCategory)))
            .andExpect(status().isCreated());

        // Validate the TaskCategory in the database
        List<TaskCategory> taskCategoryList = taskCategoryRepository.findAll();
        assertThat(taskCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        TaskCategory testTaskCategory = taskCategoryList.get(taskCategoryList.size() - 1);
        assertThat(testTaskCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTaskCategory.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTaskCategory.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testTaskCategory.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testTaskCategory.getCreatedby()).isEqualTo(DEFAULT_CREATEDBY);
        assertThat(testTaskCategory.getCreatedon()).isEqualTo(DEFAULT_CREATEDON);
        assertThat(testTaskCategory.getModifiedby()).isEqualTo(DEFAULT_MODIFIEDBY);
        assertThat(testTaskCategory.getModifiedon()).isEqualTo(DEFAULT_MODIFIEDON);
        assertThat(testTaskCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTaskCategory.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    public void createTaskCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taskCategoryRepository.findAll().size();

        // Create the TaskCategory with an existing ID
        TaskCategory existingTaskCategory = new TaskCategory();
        existingTaskCategory.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskCategoryMockMvc.perform(post("/api/task-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTaskCategory)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TaskCategory> taskCategoryList = taskCategoryRepository.findAll();
        assertThat(taskCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskCategoryRepository.findAll().size();
        // set the field null
        taskCategory.setName(null);

        // Create the TaskCategory, which fails.

        restTaskCategoryMockMvc.perform(post("/api/task-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskCategory)))
            .andExpect(status().isBadRequest());

        List<TaskCategory> taskCategoryList = taskCategoryRepository.findAll();
        assertThat(taskCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskCategoryRepository.findAll().size();
        // set the field null
        taskCategory.setType(null);

        // Create the TaskCategory, which fails.

        restTaskCategoryMockMvc.perform(post("/api/task-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskCategory)))
            .andExpect(status().isBadRequest());

        List<TaskCategory> taskCategoryList = taskCategoryRepository.findAll();
        assertThat(taskCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskCategoryRepository.findAll().size();
        // set the field null
        taskCategory.setVersion(null);

        // Create the TaskCategory, which fails.

        restTaskCategoryMockMvc.perform(post("/api/task-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskCategory)))
            .andExpect(status().isBadRequest());

        List<TaskCategory> taskCategoryList = taskCategoryRepository.findAll();
        assertThat(taskCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskCategoryRepository.findAll().size();
        // set the field null
        taskCategory.setState(null);

        // Create the TaskCategory, which fails.

        restTaskCategoryMockMvc.perform(post("/api/task-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskCategory)))
            .andExpect(status().isBadRequest());

        List<TaskCategory> taskCategoryList = taskCategoryRepository.findAll();
        assertThat(taskCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskCategoryRepository.findAll().size();
        // set the field null
        taskCategory.setActive(null);

        // Create the TaskCategory, which fails.

        restTaskCategoryMockMvc.perform(post("/api/task-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskCategory)))
            .andExpect(status().isBadRequest());

        List<TaskCategory> taskCategoryList = taskCategoryRepository.findAll();
        assertThat(taskCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllTaskCategories() throws Exception {
        // Initialize the database
        taskCategoryRepository.save(taskCategory);

        // Get all the taskCategoryList
        restTaskCategoryMockMvc.perform(get("/api/task-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskCategory.getId())))
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
    public void getTaskCategory() throws Exception {
        // Initialize the database
        taskCategoryRepository.save(taskCategory);

        // Get the taskCategory
        restTaskCategoryMockMvc.perform(get("/api/task-categories/{id}", taskCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(taskCategory.getId()))
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
    public void getNonExistingTaskCategory() throws Exception {
        // Get the taskCategory
        restTaskCategoryMockMvc.perform(get("/api/task-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTaskCategory() throws Exception {
        // Initialize the database
        taskCategoryRepository.save(taskCategory);
        int databaseSizeBeforeUpdate = taskCategoryRepository.findAll().size();

        // Update the taskCategory
        TaskCategory updatedTaskCategory = taskCategoryRepository.findOne(taskCategory.getId());
        updatedTaskCategory
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

        restTaskCategoryMockMvc.perform(put("/api/task-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTaskCategory)))
            .andExpect(status().isOk());

        // Validate the TaskCategory in the database
        List<TaskCategory> taskCategoryList = taskCategoryRepository.findAll();
        assertThat(taskCategoryList).hasSize(databaseSizeBeforeUpdate);
        TaskCategory testTaskCategory = taskCategoryList.get(taskCategoryList.size() - 1);
        assertThat(testTaskCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaskCategory.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTaskCategory.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testTaskCategory.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testTaskCategory.getCreatedby()).isEqualTo(UPDATED_CREATEDBY);
        assertThat(testTaskCategory.getCreatedon()).isEqualTo(UPDATED_CREATEDON);
        assertThat(testTaskCategory.getModifiedby()).isEqualTo(UPDATED_MODIFIEDBY);
        assertThat(testTaskCategory.getModifiedon()).isEqualTo(UPDATED_MODIFIEDON);
        assertThat(testTaskCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTaskCategory.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    public void updateNonExistingTaskCategory() throws Exception {
        int databaseSizeBeforeUpdate = taskCategoryRepository.findAll().size();

        // Create the TaskCategory

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTaskCategoryMockMvc.perform(put("/api/task-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskCategory)))
            .andExpect(status().isCreated());

        // Validate the TaskCategory in the database
        List<TaskCategory> taskCategoryList = taskCategoryRepository.findAll();
        assertThat(taskCategoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteTaskCategory() throws Exception {
        // Initialize the database
        taskCategoryRepository.save(taskCategory);
        int databaseSizeBeforeDelete = taskCategoryRepository.findAll().size();

        // Get the taskCategory
        restTaskCategoryMockMvc.perform(delete("/api/task-categories/{id}", taskCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TaskCategory> taskCategoryList = taskCategoryRepository.findAll();
        assertThat(taskCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
