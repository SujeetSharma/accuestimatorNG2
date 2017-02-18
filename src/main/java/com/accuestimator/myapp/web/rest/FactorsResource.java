package com.accuestimator.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.accuestimator.myapp.domain.Factors;

import com.accuestimator.myapp.repository.FactorsRepository;
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
 * REST controller for managing Factors.
 */
@RestController
@RequestMapping("/api")
public class FactorsResource {

    private final Logger log = LoggerFactory.getLogger(FactorsResource.class);

    private static final String ENTITY_NAME = "factors";
        
    private final FactorsRepository factorsRepository;

    public FactorsResource(FactorsRepository factorsRepository) {
        this.factorsRepository = factorsRepository;
    }

    /**
     * POST  /factors : Create a new factors.
     *
     * @param factors the factors to create
     * @return the ResponseEntity with status 201 (Created) and with body the new factors, or with status 400 (Bad Request) if the factors has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/factors")
    @Timed
    public ResponseEntity<Factors> createFactors(@Valid @RequestBody Factors factors) throws URISyntaxException {
        log.debug("REST request to save Factors : {}", factors);
        if (factors.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new factors cannot already have an ID")).body(null);
        }
        Factors result = factorsRepository.save(factors);
        return ResponseEntity.created(new URI("/api/factors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /factors : Updates an existing factors.
     *
     * @param factors the factors to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated factors,
     * or with status 400 (Bad Request) if the factors is not valid,
     * or with status 500 (Internal Server Error) if the factors couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/factors")
    @Timed
    public ResponseEntity<Factors> updateFactors(@Valid @RequestBody Factors factors) throws URISyntaxException {
        log.debug("REST request to update Factors : {}", factors);
        if (factors.getId() == null) {
            return createFactors(factors);
        }
        Factors result = factorsRepository.save(factors);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, factors.getId().toString()))
            .body(result);
    }

    /**
     * GET  /factors : get all the factors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of factors in body
     */
    @GetMapping("/factors")
    @Timed
    public List<Factors> getAllFactors() {
        log.debug("REST request to get all Factors");
        List<Factors> factors = factorsRepository.findAll();
        return factors;
    }

    /**
     * GET  /factors/:id : get the "id" factors.
     *
     * @param id the id of the factors to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the factors, or with status 404 (Not Found)
     */
    @GetMapping("/factors/{id}")
    @Timed
    public ResponseEntity<Factors> getFactors(@PathVariable String id) {
        log.debug("REST request to get Factors : {}", id);
        Factors factors = factorsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(factors));
    }

    /**
     * GET  /factors/ByCat/:catid : get the "id" factors.
     *
     * @param id the id of the factors to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the factors, or with status 404 (Not Found)
     */
    @GetMapping("/factors/ByCat/{catid}")
    @Timed
    public List<Factors> getFactorsByCat(@PathVariable String catid) {
        log.debug("REST request to get Factors : {}", catid);
        List<Factors> factors = factorsRepository.findOneByCategory(catid);
        return factors;
    }

    /**
     * DELETE  /factors/:id : delete the "id" factors.
     *
     * @param id the id of the factors to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/factors/{id}")
    @Timed
    public ResponseEntity<Void> deleteFactors(@PathVariable String id) {
        log.debug("REST request to delete Factors : {}", id);
        factorsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
