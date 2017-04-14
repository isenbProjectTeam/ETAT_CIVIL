package com.csgb.etatcivil.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.csgb.etatcivil.domain.PieceJointe;
import com.csgb.etatcivil.service.PieceJointeService;
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
 * REST controller for managing PieceJointe.
 */
@RestController
@RequestMapping("/api")
public class PieceJointeResource {

    private final Logger log = LoggerFactory.getLogger(PieceJointeResource.class);

    private static final String ENTITY_NAME = "pieceJointe";
        
    private final PieceJointeService pieceJointeService;

    public PieceJointeResource(PieceJointeService pieceJointeService) {
        this.pieceJointeService = pieceJointeService;
    }

    /**
     * POST  /piece-jointes : Create a new pieceJointe.
     *
     * @param pieceJointe the pieceJointe to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pieceJointe, or with status 400 (Bad Request) if the pieceJointe has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/piece-jointes")
    @Timed
    public ResponseEntity<PieceJointe> createPieceJointe(@Valid @RequestBody PieceJointe pieceJointe) throws URISyntaxException {
        log.debug("REST request to save PieceJointe : {}", pieceJointe);
        if (pieceJointe.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pieceJointe cannot already have an ID")).body(null);
        }
        PieceJointe result = pieceJointeService.save(pieceJointe);
        return ResponseEntity.created(new URI("/api/piece-jointes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /piece-jointes : Updates an existing pieceJointe.
     *
     * @param pieceJointe the pieceJointe to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pieceJointe,
     * or with status 400 (Bad Request) if the pieceJointe is not valid,
     * or with status 500 (Internal Server Error) if the pieceJointe couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/piece-jointes")
    @Timed
    public ResponseEntity<PieceJointe> updatePieceJointe(@Valid @RequestBody PieceJointe pieceJointe) throws URISyntaxException {
        log.debug("REST request to update PieceJointe : {}", pieceJointe);
        if (pieceJointe.getId() == null) {
            return createPieceJointe(pieceJointe);
        }
        PieceJointe result = pieceJointeService.save(pieceJointe);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pieceJointe.getId().toString()))
            .body(result);
    }

    /**
     * GET  /piece-jointes : get all the pieceJointes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pieceJointes in body
     */
    @GetMapping("/piece-jointes")
    @Timed
    public ResponseEntity<List<PieceJointe>> getAllPieceJointes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of PieceJointes");
        Page<PieceJointe> page = pieceJointeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/piece-jointes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /piece-jointes/:id : get the "id" pieceJointe.
     *
     * @param id the id of the pieceJointe to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pieceJointe, or with status 404 (Not Found)
     */
    @GetMapping("/piece-jointes/{id}")
    @Timed
    public ResponseEntity<PieceJointe> getPieceJointe(@PathVariable Long id) {
        log.debug("REST request to get PieceJointe : {}", id);
        PieceJointe pieceJointe = pieceJointeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pieceJointe));
    }

    /**
     * DELETE  /piece-jointes/:id : delete the "id" pieceJointe.
     *
     * @param id the id of the pieceJointe to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/piece-jointes/{id}")
    @Timed
    public ResponseEntity<Void> deletePieceJointe(@PathVariable Long id) {
        log.debug("REST request to delete PieceJointe : {}", id);
        pieceJointeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/piece-jointes?query=:query : search for the pieceJointe corresponding
     * to the query.
     *
     * @param query the query of the pieceJointe search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/piece-jointes")
    @Timed
    public ResponseEntity<List<PieceJointe>> searchPieceJointes(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of PieceJointes for query {}", query);
        Page<PieceJointe> page = pieceJointeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/piece-jointes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
