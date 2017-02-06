package com.accuestimator.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.accuestimator.myapp.domain.Estimates;

import com.accuestimator.myapp.repository.EstimatesRepository;
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
 * REST controller for managing Estimates.
 */
@RestController
@RequestMapping("/api")
public class EstimatesResource {

    private final Logger log = LoggerFactory.getLogger(EstimatesResource.class);

    private static final String ENTITY_NAME = "estimates";
        
    private final EstimatesRepository estimatesRepository;

    public EstimatesResource(EstimatesRepository estimatesRepository) {
        this.estimatesRepository = estimatesRepository;
    }

    /**
     * POST  /estimates : Create a new estimates.
     *
     * @param estimates the estimates to create
     * @return the ResponseEntity with status 201 (Created) and with body the new estimates, or with status 400 (Bad Request) if the estimates has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/estimates")
    @Timed
    public ResponseEntity<Estimates> createEstimates(@Valid @RequestBody Estimates estimates) throws URISyntaxException {
        log.debug("REST request to save Estimates : {}", estimates);
        if (estimates.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new estimates cannot already have an ID")).body(null);
        }
        Estimates result = estimatesRepository.save(estimates);
        return ResponseEntity.created(new URI("/api/estimates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /estimates : Updates an existing estimates.
     *
     * @param estimates the estimates to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated estimates,
     * or with status 400 (Bad Request) if the estimates is not valid,
     * or with status 500 (Internal Server Error) if the estimates couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/estimates")
    @Timed
    public ResponseEntity<Estimates> updateEstimates(@Valid @RequestBody Estimates estimates) throws URISyntaxException {
        log.debug("REST request to update Estimates : {}", estimates);
        if (estimates.getId() == null) {
            return createEstimates(estimates);
        }
        Estimates result = estimatesRepository.save(estimates);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, estimates.getId().toString()))
            .body(result);
    }

    /**
     * GET  /estimates : get all the estimates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of estimates in body
     */
    @GetMapping("/estimates")
    @Timed
    public List<Estimates> getAllEstimates() {
        log.debug("REST request to get all Estimates");
        List<Estimates> estimates = estimatesRepository.findAll();
        return estimates;
    }

    /**
     * GET  /estimates/:id : get the "id" estimates.
     *
     * @param id the id of the estimates to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the estimates, or with status 404 (Not Found)
     */
    @GetMapping("/estimates/{id}")
    @Timed
    public ResponseEntity<Estimates> getEstimates(@PathVariable String id) {
        log.debug("REST request to get Estimates : {}", id);
        Estimates estimates = estimatesRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(estimates));
    }

    /**
     * DELETE  /estimates/:id : delete the "id" estimates.
     *
     * @param id the id of the estimates to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/estimates/{id}")
    @Timed
    public ResponseEntity<Void> deleteEstimates(@PathVariable String id) {
        log.debug("REST request to delete Estimates : {}", id);
        estimatesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
