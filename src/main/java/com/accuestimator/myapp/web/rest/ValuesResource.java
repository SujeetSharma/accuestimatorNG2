package com.accuestimator.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.accuestimator.myapp.domain.Values;

import com.accuestimator.myapp.repository.ValuesRepository;
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
 * REST controller for managing Values.
 */
@RestController
@RequestMapping("/api")
public class ValuesResource {

    private final Logger log = LoggerFactory.getLogger(ValuesResource.class);

    private static final String ENTITY_NAME = "values";
        
    private final ValuesRepository valuesRepository;

    public ValuesResource(ValuesRepository valuesRepository) {
        this.valuesRepository = valuesRepository;
    }

    /**
     * POST  /values : Create a new values.
     *
     * @param values the values to create
     * @return the ResponseEntity with status 201 (Created) and with body the new values, or with status 400 (Bad Request) if the values has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/values")
    @Timed
    public ResponseEntity<Values> createValues(@Valid @RequestBody Values values) throws URISyntaxException {
        log.debug("REST request to save Values : {}", values);
        if (values.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new values cannot already have an ID")).body(null);
        }
        Values result = valuesRepository.save(values);
        return ResponseEntity.created(new URI("/api/values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /values : Updates an existing values.
     *
     * @param values the values to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated values,
     * or with status 400 (Bad Request) if the values is not valid,
     * or with status 500 (Internal Server Error) if the values couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/values")
    @Timed
    public ResponseEntity<Values> updateValues(@Valid @RequestBody Values values) throws URISyntaxException {
        log.debug("REST request to update Values : {}", values);
        if (values.getId() == null) {
            return createValues(values);
        }
        Values result = valuesRepository.save(values);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, values.getId().toString()))
            .body(result);
    }

    /**
     * GET  /values : get all the values.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of values in body
     */
    @GetMapping("/values")
    @Timed
    public List<Values> getAllValues() {
        log.debug("REST request to get all Values");
        List<Values> values = valuesRepository.findAll();
        return values;
    }

    /**
     * GET  /values/:id : get the "id" values.
     *
     * @param id the id of the values to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the values, or with status 404 (Not Found)
     */
    @GetMapping("/values/{id}")
    @Timed
    public ResponseEntity<Values> getValues(@PathVariable String id) {
        log.debug("REST request to get Values : {}", id);
        Values values = valuesRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(values));
    }

    /**
     * DELETE  /values/:id : delete the "id" values.
     *
     * @param id the id of the values to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/values/{id}")
    @Timed
    public ResponseEntity<Void> deleteValues(@PathVariable String id) {
        log.debug("REST request to delete Values : {}", id);
        valuesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
