package com.accuestimator.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.accuestimator.myapp.domain.Tasks;

import com.accuestimator.myapp.repository.TasksRepository;
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
 * REST controller for managing Tasks.
 */
@RestController
@RequestMapping("/api")
public class TasksResource {

    private final Logger log = LoggerFactory.getLogger(TasksResource.class);

    private static final String ENTITY_NAME = "tasks";
        
    private final TasksRepository tasksRepository;

    public TasksResource(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    /**
     * POST  /tasks : Create a new tasks.
     *
     * @param tasks the tasks to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tasks, or with status 400 (Bad Request) if the tasks has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tasks")
    @Timed
    public ResponseEntity<Tasks> createTasks(@Valid @RequestBody Tasks tasks) throws URISyntaxException {
        log.debug("REST request to save Tasks : {}", tasks);
        if (tasks.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tasks cannot already have an ID")).body(null);
        }
        Tasks result = tasksRepository.save(tasks);
        return ResponseEntity.created(new URI("/api/tasks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tasks : Updates an existing tasks.
     *
     * @param tasks the tasks to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tasks,
     * or with status 400 (Bad Request) if the tasks is not valid,
     * or with status 500 (Internal Server Error) if the tasks couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tasks")
    @Timed
    public ResponseEntity<Tasks> updateTasks(@Valid @RequestBody Tasks tasks) throws URISyntaxException {
        log.debug("REST request to update Tasks : {}", tasks);
        if (tasks.getId() == null) {
            return createTasks(tasks);
        }
        Tasks result = tasksRepository.save(tasks);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tasks.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tasks : get all the tasks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tasks in body
     */
    @GetMapping("/tasks")
    @Timed
    public List<Tasks> getAllTasks() {
        log.debug("REST request to get all Tasks");
        List<Tasks> tasks = tasksRepository.findAll();
        return tasks;
    }

    /**
     * GET  /tasks/:id : get the "id" tasks.
     *
     * @param id the id of the tasks to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tasks, or with status 404 (Not Found)
     */
    @GetMapping("/tasks/{id}")
    @Timed
    public ResponseEntity<Tasks> getTasks(@PathVariable String id) {
        log.debug("REST request to get Tasks : {}", id);
        Tasks tasks = tasksRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tasks));
    }

    /**
     * DELETE  /tasks/:id : delete the "id" tasks.
     *
     * @param id the id of the tasks to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tasks/{id}")
    @Timed
    public ResponseEntity<Void> deleteTasks(@PathVariable String id) {
        log.debug("REST request to delete Tasks : {}", id);
        tasksRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
