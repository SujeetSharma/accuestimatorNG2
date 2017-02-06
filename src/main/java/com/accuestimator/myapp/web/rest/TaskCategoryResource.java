package com.accuestimator.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.accuestimator.myapp.domain.TaskCategory;

import com.accuestimator.myapp.repository.TaskCategoryRepository;
import com.accuestimator.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TaskCategory.
 */
@RestController
@RequestMapping("/api")
public class TaskCategoryResource {

    private final Logger log = LoggerFactory.getLogger(TaskCategoryResource.class);

    private static final String ENTITY_NAME = "taskCategory";
        
    private final TaskCategoryRepository taskCategoryRepository;

    public TaskCategoryResource(TaskCategoryRepository taskCategoryRepository) {
        this.taskCategoryRepository = taskCategoryRepository;
    }

    /**
     * POST  /task-categories : Create a new taskCategory.
     *
     * @param taskCategory the taskCategory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new taskCategory, or with status 400 (Bad Request) if the taskCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/task-categories")
    @Timed
    public ResponseEntity<TaskCategory> createTaskCategory(@Valid @RequestBody TaskCategory taskCategory) throws URISyntaxException {
        log.debug("REST request to save TaskCategory : {}", taskCategory);
        if (taskCategory.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new taskCategory cannot already have an ID")).body(null);
        }
        TaskCategory result = taskCategoryRepository.save(taskCategory);
        return ResponseEntity.created(new URI("/api/task-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /task-categories : Updates an existing taskCategory.
     *
     * @param taskCategory the taskCategory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated taskCategory,
     * or with status 400 (Bad Request) if the taskCategory is not valid,
     * or with status 500 (Internal Server Error) if the taskCategory couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/task-categories")
    @Timed
    public ResponseEntity<TaskCategory> updateTaskCategory(@Valid @RequestBody TaskCategory taskCategory) throws URISyntaxException {
        log.debug("REST request to update TaskCategory : {}", taskCategory);
        if (taskCategory.getId() == null) {
            return createTaskCategory(taskCategory);
        }
        TaskCategory result = taskCategoryRepository.save(taskCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, taskCategory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /task-categories : get all the taskCategories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of taskCategories in body
     */
    @GetMapping("/task-categories")
    @Timed
    public List<TaskCategory> getAllTaskCategories() {
        log.debug("REST request to get all TaskCategories");
        List<TaskCategory> taskCategories = taskCategoryRepository.findAll();
        return taskCategories;
    }

    /**
     * GET  /task-categories/:id : get the "id" taskCategory.
     *
     * @param id the id of the taskCategory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taskCategory, or with status 404 (Not Found)
     */
    @GetMapping("/task-categories/{id}")
    @Timed
    public ResponseEntity<TaskCategory> getTaskCategory(@PathVariable String id) {
        log.debug("REST request to get TaskCategory : {}", id);
        TaskCategory taskCategory = taskCategoryRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(taskCategory));
    }

    /**
     * DELETE  /task-categories/:id : delete the "id" taskCategory.
     *
     * @param id the id of the taskCategory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/task-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteTaskCategory(@PathVariable String id) {
        log.debug("REST request to delete TaskCategory : {}", id);
        taskCategoryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
