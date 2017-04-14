package com.csgb.etatcivil.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.csgb.etatcivil.domain.Pays;
import com.csgb.etatcivil.service.PaysService;
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
 * REST controller for managing Pays.
 */
@RestController
@RequestMapping("/api")
public class PaysResource {

    private final Logger log = LoggerFactory.getLogger(PaysResource.class);

    private static final String ENTITY_NAME = "pays";
        
    private final PaysService paysService;

    public PaysResource(PaysService paysService) {
        this.paysService = paysService;
    }

    /**
     * POST  /pays : Create a new pays.
     *
     * @param pays the pays to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pays, or with status 400 (Bad Request) if the pays has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pays")
    @Timed
    public ResponseEntity<Pays> createPays(@Valid @RequestBody Pays pays) throws URISyntaxException {
        log.debug("REST request to save Pays : {}", pays);
        if (pays.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pays cannot already have an ID")).body(null);
        }
        Pays result = paysService.save(pays);
        return ResponseEntity.created(new URI("/api/pays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pays : Updates an existing pays.
     *
     * @param pays the pays to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pays,
     * or with status 400 (Bad Request) if the pays is not valid,
     * or with status 500 (Internal Server Error) if the pays couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pays")
    @Timed
    public ResponseEntity<Pays> updatePays(@Valid @RequestBody Pays pays) throws URISyntaxException {
        log.debug("REST request to update Pays : {}", pays);
        if (pays.getId() == null) {
            return createPays(pays);
        }
        Pays result = paysService.save(pays);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pays.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pays : get all the pays.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pays in body
     */
    @GetMapping("/pays")
    @Timed
    public ResponseEntity<List<Pays>> getAllPays(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Pays");
        Page<Pays> page = paysService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pays");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pays/:id : get the "id" pays.
     *
     * @param id the id of the pays to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pays, or with status 404 (Not Found)
     */
    @GetMapping("/pays/{id}")
    @Timed
    public ResponseEntity<Pays> getPays(@PathVariable Long id) {
        log.debug("REST request to get Pays : {}", id);
        Pays pays = paysService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pays));
    }

    /**
     * DELETE  /pays/:id : delete the "id" pays.
     *
     * @param id the id of the pays to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pays/{id}")
    @Timed
    public ResponseEntity<Void> deletePays(@PathVariable Long id) {
        log.debug("REST request to delete Pays : {}", id);
        paysService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/pays?query=:query : search for the pays corresponding
     * to the query.
     *
     * @param query the query of the pays search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/pays")
    @Timed
    public ResponseEntity<List<Pays>> searchPays(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Pays for query {}", query);
        Page<Pays> page = paysService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/pays");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
