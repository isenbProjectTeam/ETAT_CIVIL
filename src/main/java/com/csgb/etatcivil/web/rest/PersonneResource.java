package com.csgb.etatcivil.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.csgb.etatcivil.domain.Personne;
import com.csgb.etatcivil.service.PersonneService;
import com.csgb.etatcivil.web.rest.util.HeaderUtil;
import com.csgb.etatcivil.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Personne.
 */
@RestController
@RequestMapping("/api")
public class PersonneResource {

    private final Logger log = LoggerFactory.getLogger(PersonneResource.class);

    private static final String ENTITY_NAME = "personne";
        
    private final PersonneService personneService;

    public PersonneResource(PersonneService personneService) {
        this.personneService = personneService;
    }

    /**
     * POST  /personnes : Create a new personne.
     *
     * @param personne the personne to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personne, or with status 400 (Bad Request) if the personne has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/personnes")
    @Timed
    public ResponseEntity<Personne> createPersonne(@Valid @RequestBody Personne personne) throws URISyntaxException {
        log.debug("REST request to save Personne : {}", personne);
        if (personne.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new personne cannot already have an ID")).body(null);
        }
        Personne result = personneService.save(personne);
        return ResponseEntity.created(new URI("/api/personnes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /personnes : Updates an existing personne.
     *
     * @param personne the personne to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personne,
     * or with status 400 (Bad Request) if the personne is not valid,
     * or with status 500 (Internal Server Error) if the personne couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/personnes")
    @Timed
    public ResponseEntity<Personne> updatePersonne(@Valid @RequestBody Personne personne) throws URISyntaxException {
        log.debug("REST request to update Personne : {}", personne);
        if (personne.getId() == null) {
            return createPersonne(personne);
        }
        Personne result = personneService.save(personne);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, personne.getId().toString()))
            .body(result);
    }

    /**
     * GET  /personnes : get all the personnes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personnes in body
     */
    @GetMapping("/personnes")
    @Timed
    public ResponseEntity<List<Personne>> getAllPersonnes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Personnes");
        Page<Personne> page = personneService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/personnes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /personnes/:id : get the "id" personne.
     *
     * @param id the id of the personne to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personne, or with status 404 (Not Found)
     */
    @GetMapping("/personnes/{id}")
    @Timed
    public ResponseEntity<Personne> getPersonne(@PathVariable Long id) {
        log.debug("REST request to get Personne : {}", id);
        Personne personne = personneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(personne));
    }

    /**
     * DELETE  /personnes/:id : delete the "id" personne.
     *
     * @param id the id of the personne to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/personnes/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonne(@PathVariable Long id) {
        log.debug("REST request to delete Personne : {}", id);
        personneService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/personnes?query=:query : search for the personne corresponding
     * to the query.
     *
     * @param query the query of the personne search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/personnes")
    @Timed
    public ResponseEntity<List<Personne>> searchPersonnes(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Personnes for query {}", query);
        Page<Personne> page = personneService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/personnes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
