package com.csgb.etatcivil.service.impl;

import com.csgb.etatcivil.service.PieceJointeService;
import com.csgb.etatcivil.domain.PieceJointe;
import com.csgb.etatcivil.repository.PieceJointeRepository;
import com.csgb.etatcivil.repository.search.PieceJointeSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing PieceJointe.
 */
@Service
@Transactional
public class PieceJointeServiceImpl implements PieceJointeService{

    private final Logger log = LoggerFactory.getLogger(PieceJointeServiceImpl.class);
    
    private final PieceJointeRepository pieceJointeRepository;

    private final PieceJointeSearchRepository pieceJointeSearchRepository;

    public PieceJointeServiceImpl(PieceJointeRepository pieceJointeRepository, PieceJointeSearchRepository pieceJointeSearchRepository) {
        this.pieceJointeRepository = pieceJointeRepository;
        this.pieceJointeSearchRepository = pieceJointeSearchRepository;
    }

    /**
     * Save a pieceJointe.
     *
     * @param pieceJointe the entity to save
     * @return the persisted entity
     */
    @Override
    public PieceJointe save(PieceJointe pieceJointe) {
        log.debug("Request to save PieceJointe : {}", pieceJointe);
        PieceJointe result = pieceJointeRepository.save(pieceJointe);
        pieceJointeSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the pieceJointes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PieceJointe> findAll(Pageable pageable) {
        log.debug("Request to get all PieceJointes");
        Page<PieceJointe> result = pieceJointeRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one pieceJointe by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PieceJointe findOne(Long id) {
        log.debug("Request to get PieceJointe : {}", id);
        PieceJointe pieceJointe = pieceJointeRepository.findOne(id);
        return pieceJointe;
    }

    /**
     *  Delete the  pieceJointe by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PieceJointe : {}", id);
        pieceJointeRepository.delete(id);
        pieceJointeSearchRepository.delete(id);
    }

    /**
     * Search for the pieceJointe corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PieceJointe> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PieceJointes for query {}", query);
        Page<PieceJointe> result = pieceJointeSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
