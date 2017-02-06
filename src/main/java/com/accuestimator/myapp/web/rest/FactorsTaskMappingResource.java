package com.accuestimator.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.accuestimator.myapp.domain.FactorsTaskMapping;

import com.accuestimator.myapp.repository.FactorsTaskMappingRepository;
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
 * REST controller for managing FactorsTaskMapping.
 */
@RestController
@RequestMapping("/api")
public class FactorsTaskMappingResource {

    private final Logger log = LoggerFactory.getLogger(FactorsTaskMappingResource.class);

    private static final String ENTITY_NAME = "factorsTaskMapping";
        
    private final FactorsTaskMappingRepository factorsTaskMappingRepository;

    public FactorsTaskMappingResource(FactorsTaskMappingRepository factorsTaskMappingRepository) {
        this.factorsTaskMappingRepository = factorsTaskMappingRepository;
    }

    /**
     * POST  /factors-task-mappings : Create a new factorsTaskMapping.
     *
     * @param factorsTaskMapping the factorsTaskMapping to create
     * @return the ResponseEntity with status 201 (Created) and with body the new factorsTaskMapping, or with status 400 (Bad Request) if the factorsTaskMapping has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/factors-task-mappings")
    @Timed
    public ResponseEntity<FactorsTaskMapping> createFactorsTaskMapping(@Valid @RequestBody FactorsTaskMapping factorsTaskMapping) throws URISyntaxException {
        log.debug("REST request to save FactorsTaskMapping : {}", factorsTaskMapping);
        if (factorsTaskMapping.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new factorsTaskMapping cannot already have an ID")).body(null);
        }
        FactorsTaskMapping result = factorsTaskMappingRepository.save(factorsTaskMapping);
        return ResponseEntity.created(new URI("/api/factors-task-mappings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /factors-task-mappings : Updates an existing factorsTaskMapping.
     *
     * @param factorsTaskMapping the factorsTaskMapping to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated factorsTaskMapping,
     * or with status 400 (Bad Request) if the factorsTaskMapping is not valid,
     * or with status 500 (Internal Server Error) if the factorsTaskMapping couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/factors-task-mappings")
    @Timed
    public ResponseEntity<FactorsTaskMapping> updateFactorsTaskMapping(@Valid @RequestBody FactorsTaskMapping factorsTaskMapping) throws URISyntaxException {
        log.debug("REST request to update FactorsTaskMapping : {}", factorsTaskMapping);
        if (factorsTaskMapping.getId() == null) {
            return createFactorsTaskMapping(factorsTaskMapping);
        }
        FactorsTaskMapping result = factorsTaskMappingRepository.save(factorsTaskMapping);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, factorsTaskMapping.getId().toString()))
            .body(result);
    }

    /**
     * GET  /factors-task-mappings : get all the factorsTaskMappings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of factorsTaskMappings in body
     */
    @GetMapping("/factors-task-mappings")
    @Timed
    public List<FactorsTaskMapping> getAllFactorsTaskMappings() {
        log.debug("REST request to get all FactorsTaskMappings");
        List<FactorsTaskMapping> factorsTaskMappings = factorsTaskMappingRepository.findAll();
        return factorsTaskMappings;
    }

    /**
     * GET  /factors-task-mappings/:id : get the "id" factorsTaskMapping.
     *
     * @param id the id of the factorsTaskMapping to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the factorsTaskMapping, or with status 404 (Not Found)
     */
    @GetMapping("/factors-task-mappings/{id}")
    @Timed
    public ResponseEntity<FactorsTaskMapping> getFactorsTaskMapping(@PathVariable String id) {
        log.debug("REST request to get FactorsTaskMapping : {}", id);
        FactorsTaskMapping factorsTaskMapping = factorsTaskMappingRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(factorsTaskMapping));
    }

    /**
     * DELETE  /factors-task-mappings/:id : delete the "id" factorsTaskMapping.
     *
     * @param id the id of the factorsTaskMapping to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/factors-task-mappings/{id}")
    @Timed
    public ResponseEntity<Void> deleteFactorsTaskMapping(@PathVariable String id) {
        log.debug("REST request to delete FactorsTaskMapping : {}", id);
        factorsTaskMappingRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
