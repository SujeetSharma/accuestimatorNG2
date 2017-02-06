package com.accuestimator.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.accuestimator.myapp.domain.Template;

import com.accuestimator.myapp.repository.TemplateRepository;
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
 * REST controller for managing Template.
 */
@RestController
@RequestMapping("/api")
public class TemplateResource {

    private final Logger log = LoggerFactory.getLogger(TemplateResource.class);

    private static final String ENTITY_NAME = "template";
        
    private final TemplateRepository templateRepository;

    public TemplateResource(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    /**
     * POST  /templates : Create a new template.
     *
     * @param template the template to create
     * @return the ResponseEntity with status 201 (Created) and with body the new template, or with status 400 (Bad Request) if the template has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/templates")
    @Timed
    public ResponseEntity<Template> createTemplate(@Valid @RequestBody Template template) throws URISyntaxException {
        log.debug("REST request to save Template : {}", template);
        if (template.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new template cannot already have an ID")).body(null);
        }
        Template result = templateRepository.save(template);
        return ResponseEntity.created(new URI("/api/templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /templates : Updates an existing template.
     *
     * @param template the template to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated template,
     * or with status 400 (Bad Request) if the template is not valid,
     * or with status 500 (Internal Server Error) if the template couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/templates")
    @Timed
    public ResponseEntity<Template> updateTemplate(@Valid @RequestBody Template template) throws URISyntaxException {
        log.debug("REST request to update Template : {}", template);
        if (template.getId() == null) {
            return createTemplate(template);
        }
        Template result = templateRepository.save(template);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, template.getId().toString()))
            .body(result);
    }

    /**
     * GET  /templates : get all the templates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of templates in body
     */
    @GetMapping("/templates")
    @Timed
    public List<Template> getAllTemplates() {
        log.debug("REST request to get all Templates");
        List<Template> templates = templateRepository.findAll();
        return templates;
    }

    /**
     * GET  /templates/:id : get the "id" template.
     *
     * @param id the id of the template to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the template, or with status 404 (Not Found)
     */
    @GetMapping("/templates/{id}")
    @Timed
    public ResponseEntity<Template> getTemplate(@PathVariable String id) {
        log.debug("REST request to get Template : {}", id);
        Template template = templateRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(template));
    }

    /**
     * DELETE  /templates/:id : delete the "id" template.
     *
     * @param id the id of the template to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/templates/{id}")
    @Timed
    public ResponseEntity<Void> deleteTemplate(@PathVariable String id) {
        log.debug("REST request to delete Template : {}", id);
        templateRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
