package com.csgb.etatcivil.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.csgb.etatcivil.domain.Naissance;
import com.csgb.etatcivil.service.NaissanceService;
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
 * REST controller for managing Naissance.
 */
@RestController
@RequestMapping("/api")
public class NaissanceResource {

    private final Logger log = LoggerFactory.getLogger(NaissanceResource.class);

    private static final String ENTITY_NAME = "naissance";
        
    private final NaissanceService naissanceService;

    public NaissanceResource(NaissanceService naissanceService) {
        this.naissanceService = naissanceService;
    }

    /**
     * POST  /naissances : Create a new naissance.
     *
     * @param naissance the naissance to create
     * @return the ResponseEntity with status 201 (Created) and with body the new naissance, or with status 400 (Bad Request) if the naissance has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/naissances")
    @Timed
    public ResponseEntity<Naissance> createNaissance(@Valid @RequestBody Naissance naissance) throws URISyntaxException {
        log.debug("REST request to save Naissance : {}", naissance);
        if (naissance.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new naissance cannot already have an ID")).body(null);
        }
        Naissance result = naissanceService.save(naissance);
        return ResponseEntity.created(new URI("/api/naissances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /naissances : Updates an existing naissance.
     *
     * @param naissance the naissance to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated naissance,
     * or with status 400 (Bad Request) if the naissance is not valid,
     * or with status 500 (Internal Server Error) if the naissance couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/naissances")
    @Timed
    public ResponseEntity<Naissance> updateNaissance(@Valid @RequestBody Naissance naissance) throws URISyntaxException {
        log.debug("REST request to update Naissance : {}", naissance);
        if (naissance.getId() == null) {
            return createNaissance(naissance);
        }
        Naissance result = naissanceService.save(naissance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, naissance.getId().toString()))
            .body(result);
    }

    /**
     * GET  /naissances : get all the naissances.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of naissances in body
     */
    @GetMapping("/naissances")
    @Timed
    public ResponseEntity<List<Naissance>> getAllNaissances(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Naissances");
        Page<Naissance> page = naissanceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/naissances");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /naissances/:id : get the "id" naissance.
     *
     * @param id the id of the naissance to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the naissance, or with status 404 (Not Found)
     */
    @GetMapping("/naissances/{id}")
    @Timed
    public ResponseEntity<Naissance> getNaissance(@PathVariable Long id) {
        log.debug("REST request to get Naissance : {}", id);
        Naissance naissance = naissanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(naissance));
    }

    /**
     * DELETE  /naissances/:id : delete the "id" naissance.
     *
     * @param id the id of the naissance to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/naissances/{id}")
    @Timed
    public ResponseEntity<Void> deleteNaissance(@PathVariable Long id) {
        log.debug("REST request to delete Naissance : {}", id);
        naissanceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/naissances?query=:query : search for the naissance corresponding
     * to the query.
     *
     * @param query the query of the naissance search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/naissances")
    @Timed
    public ResponseEntity<List<Naissance>> searchNaissances(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Naissances for query {}", query);
        Page<Naissance> page = naissanceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/naissances");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
