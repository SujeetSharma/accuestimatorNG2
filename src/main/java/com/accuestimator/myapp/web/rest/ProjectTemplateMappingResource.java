package com.accuestimator.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.accuestimator.myapp.domain.ProjectTemplateMapping;

import com.accuestimator.myapp.repository.ProjectTemplateMappingRepository;
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
 * REST controller for managing ProjectTemplateMapping.
 */
@RestController
@RequestMapping("/api")
public class ProjectTemplateMappingResource {

    private final Logger log = LoggerFactory.getLogger(ProjectTemplateMappingResource.class);

    private static final String ENTITY_NAME = "projectTemplateMapping";
        
    private final ProjectTemplateMappingRepository projectTemplateMappingRepository;

    public ProjectTemplateMappingResource(ProjectTemplateMappingRepository projectTemplateMappingRepository) {
        this.projectTemplateMappingRepository = projectTemplateMappingRepository;
    }

    /**
     * POST  /project-template-mappings : Create a new projectTemplateMapping.
     *
     * @param projectTemplateMapping the projectTemplateMapping to create
     * @return the ResponseEntity with status 201 (Created) and with body the new projectTemplateMapping, or with status 400 (Bad Request) if the projectTemplateMapping has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/project-template-mappings")
    @Timed
    public ResponseEntity<ProjectTemplateMapping> createProjectTemplateMapping(@Valid @RequestBody ProjectTemplateMapping projectTemplateMapping) throws URISyntaxException {
        log.debug("REST request to save ProjectTemplateMapping : {}", projectTemplateMapping);
        if (projectTemplateMapping.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new projectTemplateMapping cannot already have an ID")).body(null);
        }
        ProjectTemplateMapping result = projectTemplateMappingRepository.save(projectTemplateMapping);
        return ResponseEntity.created(new URI("/api/project-template-mappings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /project-template-mappings : Updates an existing projectTemplateMapping.
     *
     * @param projectTemplateMapping the projectTemplateMapping to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated projectTemplateMapping,
     * or with status 400 (Bad Request) if the projectTemplateMapping is not valid,
     * or with status 500 (Internal Server Error) if the projectTemplateMapping couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/project-template-mappings")
    @Timed
    public ResponseEntity<ProjectTemplateMapping> updateProjectTemplateMapping(@Valid @RequestBody ProjectTemplateMapping projectTemplateMapping) throws URISyntaxException {
        log.debug("REST request to update ProjectTemplateMapping : {}", projectTemplateMapping);
        if (projectTemplateMapping.getId() == null) {
            return createProjectTemplateMapping(projectTemplateMapping);
        }
        ProjectTemplateMapping result = projectTemplateMappingRepository.save(projectTemplateMapping);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, projectTemplateMapping.getId().toString()))
            .body(result);
    }

    /**
     * GET  /project-template-mappings : get all the projectTemplateMappings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of projectTemplateMappings in body
     */
    @GetMapping("/project-template-mappings")
    @Timed
    public List<ProjectTemplateMapping> getAllProjectTemplateMappings() {
        log.debug("REST request to get all ProjectTemplateMappings");
        List<ProjectTemplateMapping> projectTemplateMappings = projectTemplateMappingRepository.findAll();
        return projectTemplateMappings;
    }

    /**
     * GET  /project-template-mappings/:id : get the "id" projectTemplateMapping.
     *
     * @param id the id of the projectTemplateMapping to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the projectTemplateMapping, or with status 404 (Not Found)
     */
    @GetMapping("/project-template-mappings/{id}")
    @Timed
    public ResponseEntity<ProjectTemplateMapping> getProjectTemplateMapping(@PathVariable String id) {
        log.debug("REST request to get ProjectTemplateMapping : {}", id);
        ProjectTemplateMapping projectTemplateMapping = projectTemplateMappingRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(projectTemplateMapping));
    }

    /**
     * DELETE  /project-template-mappings/:id : delete the "id" projectTemplateMapping.
     *
     * @param id the id of the projectTemplateMapping to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/project-template-mappings/{id}")
    @Timed
    public ResponseEntity<Void> deleteProjectTemplateMapping(@PathVariable String id) {
        log.debug("REST request to delete ProjectTemplateMapping : {}", id);
        projectTemplateMappingRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
