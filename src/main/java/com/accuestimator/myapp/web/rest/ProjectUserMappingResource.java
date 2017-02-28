package com.accuestimator.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.accuestimator.myapp.domain.ProjectUserMapping;

import com.accuestimator.myapp.repository.ProjectUserMappingRepository;
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
 * REST controller for managing ProjectUserMapping.
 */
@RestController
@RequestMapping("/api")
public class ProjectUserMappingResource {

    private final Logger log = LoggerFactory.getLogger(ProjectUserMappingResource.class);

    private static final String ENTITY_NAME = "projectUserMapping";
        
    private final ProjectUserMappingRepository projectUserMappingRepository;

    public ProjectUserMappingResource(ProjectUserMappingRepository projectUserMappingRepository) {
        this.projectUserMappingRepository = projectUserMappingRepository;
    }

    /**
     * POST  /project-user-mappings : Create a new projectUserMapping.
     *
     * @param projectUserMapping the projectUserMapping to create
     * @return the ResponseEntity with status 201 (Created) and with body the new projectUserMapping, or with status 400 (Bad Request) if the projectUserMapping has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/project-user-mappings")
    @Timed
    public ResponseEntity<ProjectUserMapping> createProjectUserMapping(@Valid @RequestBody ProjectUserMapping projectUserMapping) throws URISyntaxException {
        log.debug("REST request to save ProjectUserMapping : {}", projectUserMapping);
        if (projectUserMapping.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new projectUserMapping cannot already have an ID")).body(null);
        }
        ProjectUserMapping result = projectUserMappingRepository.save(projectUserMapping);
        return ResponseEntity.created(new URI("/api/project-user-mappings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /project-user-mappings : Updates an existing projectUserMapping.
     *
     * @param projectUserMapping the projectUserMapping to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated projectUserMapping,
     * or with status 400 (Bad Request) if the projectUserMapping is not valid,
     * or with status 500 (Internal Server Error) if the projectUserMapping couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/project-user-mappings")
    @Timed
    public ResponseEntity<ProjectUserMapping> updateProjectUserMapping(@Valid @RequestBody ProjectUserMapping projectUserMapping) throws URISyntaxException {
        log.debug("REST request to update ProjectUserMapping : {}", projectUserMapping);
        if (projectUserMapping.getId() == null) {
            return createProjectUserMapping(projectUserMapping);
        }
        ProjectUserMapping result = projectUserMappingRepository.save(projectUserMapping);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, projectUserMapping.getId().toString()))
            .body(result);
    }

    /**
     * GET  /project-user-mappings : get all the projectUserMappings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of projectUserMappings in body
     */
    @GetMapping("/project-user-mappings")
    @Timed
    public List<ProjectUserMapping> getAllProjectUserMappings() {
        log.debug("REST request to get all ProjectUserMappings");
        List<ProjectUserMapping> projectUserMappings = projectUserMappingRepository.findAll();
        return projectUserMappings;
    }

    /**
     * GET  /project-user-mappings/:id : get the "id" projectUserMapping.
     *
     * @param id the id of the projectUserMapping to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the projectUserMapping, or with status 404 (Not Found)
     */
    @GetMapping("/project-user-mappings/{id}")
    @Timed
    public ResponseEntity<ProjectUserMapping> getProjectUserMapping(@PathVariable String id) {
        log.debug("REST request to get ProjectUserMapping : {}", id);
        ProjectUserMapping projectUserMapping = projectUserMappingRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(projectUserMapping));
    }

     /**
     * GET  /project-user-mappings/user/:id : get the "id" projectUserMapping.
     *
     * @param id the id of the projectUserMapping to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the projectUserMapping, or with status 404 (Not Found)
     */
    @GetMapping("/project-user-mappings/user/{login}")
    @Timed
    public List<ProjectUserMapping> getProjectUserMappingByUserId(@PathVariable String login) {
        log.debug("REST request to get ProjectUserMapping : {}", login);
        List<ProjectUserMapping> projectUserMapping = projectUserMappingRepository.findOneByUserid(login);
        return projectUserMapping;
    }

    /**
     * DELETE  /project-user-mappings/:id : delete the "id" projectUserMapping.
     *
     * @param id the id of the projectUserMapping to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/project-user-mappings/{id}")
    @Timed
    public ResponseEntity<Void> deleteProjectUserMapping(@PathVariable String id) {
        log.debug("REST request to delete ProjectUserMapping : {}", id);
        projectUserMappingRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
