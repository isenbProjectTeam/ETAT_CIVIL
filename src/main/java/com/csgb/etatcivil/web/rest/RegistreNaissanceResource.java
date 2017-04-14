package com.csgb.etatcivil.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.csgb.etatcivil.domain.RegistreNaissance;
import com.csgb.etatcivil.service.RegistreNaissanceService;
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
 * REST controller for managing RegistreNaissance.
 */
@RestController
@RequestMapping("/api")
public class RegistreNaissanceResource {

    private final Logger log = LoggerFactory.getLogger(RegistreNaissanceResource.class);

    private static final String ENTITY_NAME = "registreNaissance";
        
    private final RegistreNaissanceService registreNaissanceService;

    public RegistreNaissanceResource(RegistreNaissanceService registreNaissanceService) {
        this.registreNaissanceService = registreNaissanceService;
    }

    /**
     * POST  /registre-naissances : Create a new registreNaissance.
     *
     * @param registreNaissance the registreNaissance to create
     * @return the ResponseEntity with status 201 (Created) and with body the new registreNaissance, or with status 400 (Bad Request) if the registreNaissance has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/registre-naissances")
    @Timed
    public ResponseEntity<RegistreNaissance> createRegistreNaissance(@Valid @RequestBody RegistreNaissance registreNaissance) throws URISyntaxException {
        log.debug("REST request to save RegistreNaissance : {}", registreNaissance);
        if (registreNaissance.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new registreNaissance cannot already have an ID")).body(null);
        }
        RegistreNaissance result = registreNaissanceService.save(registreNaissance);
        return ResponseEntity.created(new URI("/api/registre-naissances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /registre-naissances : Updates an existing registreNaissance.
     *
     * @param registreNaissance the registreNaissance to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated registreNaissance,
     * or with status 400 (Bad Request) if the registreNaissance is not valid,
     * or with status 500 (Internal Server Error) if the registreNaissance couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/registre-naissances")
    @Timed
    public ResponseEntity<RegistreNaissance> updateRegistreNaissance(@Valid @RequestBody RegistreNaissance registreNaissance) throws URISyntaxException {
        log.debug("REST request to update RegistreNaissance : {}", registreNaissance);
        if (registreNaissance.getId() == null) {
            return createRegistreNaissance(registreNaissance);
        }
        RegistreNaissance result = registreNaissanceService.save(registreNaissance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, registreNaissance.getId().toString()))
            .body(result);
    }

    /**
     * GET  /registre-naissances : get all the registreNaissances.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of registreNaissances in body
     */
    @GetMapping("/registre-naissances")
    @Timed
    public ResponseEntity<List<RegistreNaissance>> getAllRegistreNaissances(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of RegistreNaissances");
        Page<RegistreNaissance> page = registreNaissanceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/registre-naissances");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /registre-naissances/:id : get the "id" registreNaissance.
     *
     * @param id the id of the registreNaissance to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the registreNaissance, or with status 404 (Not Found)
     */
    @GetMapping("/registre-naissances/{id}")
    @Timed
    public ResponseEntity<RegistreNaissance> getRegistreNaissance(@PathVariable Long id) {
        log.debug("REST request to get RegistreNaissance : {}", id);
        RegistreNaissance registreNaissance = registreNaissanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(registreNaissance));
    }

    /**
     * DELETE  /registre-naissances/:id : delete the "id" registreNaissance.
     *
     * @param id the id of the registreNaissance to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/registre-naissances/{id}")
    @Timed
    public ResponseEntity<Void> deleteRegistreNaissance(@PathVariable Long id) {
        log.debug("REST request to delete RegistreNaissance : {}", id);
        registreNaissanceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/registre-naissances?query=:query : search for the registreNaissance corresponding
     * to the query.
     *
     * @param query the query of the registreNaissance search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/registre-naissances")
    @Timed
    public ResponseEntity<List<RegistreNaissance>> searchRegistreNaissances(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of RegistreNaissances for query {}", query);
        Page<RegistreNaissance> page = registreNaissanceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/registre-naissances");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
