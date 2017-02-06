package com.accuestimator.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.accuestimator.myapp.domain.FactorCategory;

import com.accuestimator.myapp.repository.FactorCategoryRepository;
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
 * REST controller for managing FactorCategory.
 */
@RestController
@RequestMapping("/api")
public class FactorCategoryResource {

    private final Logger log = LoggerFactory.getLogger(FactorCategoryResource.class);

    private static final String ENTITY_NAME = "factorCategory";
        
    private final FactorCategoryRepository factorCategoryRepository;

    public FactorCategoryResource(FactorCategoryRepository factorCategoryRepository) {
        this.factorCategoryRepository = factorCategoryRepository;
    }

    /**
     * POST  /factor-categories : Create a new factorCategory.
     *
     * @param factorCategory the factorCategory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new factorCategory, or with status 400 (Bad Request) if the factorCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/factor-categories")
    @Timed
    public ResponseEntity<FactorCategory> createFactorCategory(@Valid @RequestBody FactorCategory factorCategory) throws URISyntaxException {
        log.debug("REST request to save FactorCategory : {}", factorCategory);
        if (factorCategory.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new factorCategory cannot already have an ID")).body(null);
        }
        FactorCategory result = factorCategoryRepository.save(factorCategory);
        return ResponseEntity.created(new URI("/api/factor-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /factor-categories : Updates an existing factorCategory.
     *
     * @param factorCategory the factorCategory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated factorCategory,
     * or with status 400 (Bad Request) if the factorCategory is not valid,
     * or with status 500 (Internal Server Error) if the factorCategory couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/factor-categories")
    @Timed
    public ResponseEntity<FactorCategory> updateFactorCategory(@Valid @RequestBody FactorCategory factorCategory) throws URISyntaxException {
        log.debug("REST request to update FactorCategory : {}", factorCategory);
        if (factorCategory.getId() == null) {
            return createFactorCategory(factorCategory);
        }
        FactorCategory result = factorCategoryRepository.save(factorCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, factorCategory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /factor-categories : get all the factorCategories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of factorCategories in body
     */
    @GetMapping("/factor-categories")
    @Timed
    public List<FactorCategory> getAllFactorCategories() {
        log.debug("REST request to get all FactorCategories");
        List<FactorCategory> factorCategories = factorCategoryRepository.findAll();
        return factorCategories;
    }

    /**
     * GET  /factor-categories/:id : get the "id" factorCategory.
     *
     * @param id the id of the factorCategory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the factorCategory, or with status 404 (Not Found)
     */
    @GetMapping("/factor-categories/{id}")
    @Timed
    public ResponseEntity<FactorCategory> getFactorCategory(@PathVariable String id) {
        log.debug("REST request to get FactorCategory : {}", id);
        FactorCategory factorCategory = factorCategoryRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(factorCategory));
    }

    /**
     * DELETE  /factor-categories/:id : delete the "id" factorCategory.
     *
     * @param id the id of the factorCategory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/factor-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteFactorCategory(@PathVariable String id) {
        log.debug("REST request to delete FactorCategory : {}", id);
        factorCategoryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
