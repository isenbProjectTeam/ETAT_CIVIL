package com.csgb.etatcivil.service.impl;

import com.csgb.etatcivil.service.VilleService;
import com.csgb.etatcivil.domain.Ville;
import com.csgb.etatcivil.repository.VilleRepository;
import com.csgb.etatcivil.repository.search.VilleSearchRepository;
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
 * Service Implementation for managing Ville.
 */
@Service
@Transactional
public class VilleServiceImpl implements VilleService{

    private final Logger log = LoggerFactory.getLogger(VilleServiceImpl.class);
    
    private final VilleRepository villeRepository;

    private final VilleSearchRepository villeSearchRepository;

    public VilleServiceImpl(VilleRepository villeRepository, VilleSearchRepository villeSearchRepository) {
        this.villeRepository = villeRepository;
        this.villeSearchRepository = villeSearchRepository;
    }

    /**
     * Save a ville.
     *
     * @param ville the entity to save
     * @return the persisted entity
     */
    @Override
    public Ville save(Ville ville) {
        log.debug("Request to save Ville : {}", ville);
        Ville result = villeRepository.save(ville);
        villeSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the villes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Ville> findAll(Pageable pageable) {
        log.debug("Request to get all Villes");
        Page<Ville> result = villeRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one ville by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Ville findOne(Long id) {
        log.debug("Request to get Ville : {}", id);
        Ville ville = villeRepository.findOne(id);
        return ville;
    }

    /**
     *  Delete the  ville by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ville : {}", id);
        villeRepository.delete(id);
        villeSearchRepository.delete(id);
    }

    /**
     * Search for the ville corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Ville> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Villes for query {}", query);
        Page<Ville> result = villeSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
