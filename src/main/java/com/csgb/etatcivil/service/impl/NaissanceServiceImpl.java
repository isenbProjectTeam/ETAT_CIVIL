package com.csgb.etatcivil.service.impl;

import com.csgb.etatcivil.service.NaissanceService;
import com.csgb.etatcivil.domain.Naissance;
import com.csgb.etatcivil.repository.NaissanceRepository;
import com.csgb.etatcivil.repository.search.NaissanceSearchRepository;
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
 * Service Implementation for managing Naissance.
 */
@Service
@Transactional
public class NaissanceServiceImpl implements NaissanceService{

    private final Logger log = LoggerFactory.getLogger(NaissanceServiceImpl.class);
    
    private final NaissanceRepository naissanceRepository;

    private final NaissanceSearchRepository naissanceSearchRepository;

    public NaissanceServiceImpl(NaissanceRepository naissanceRepository, NaissanceSearchRepository naissanceSearchRepository) {
        this.naissanceRepository = naissanceRepository;
        this.naissanceSearchRepository = naissanceSearchRepository;
    }

    /**
     * Save a naissance.
     *
     * @param naissance the entity to save
     * @return the persisted entity
     */
    @Override
    public Naissance save(Naissance naissance) {
        log.debug("Request to save Naissance : {}", naissance);
        Naissance result = naissanceRepository.save(naissance);
        naissanceSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the naissances.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Naissance> findAll(Pageable pageable) {
        log.debug("Request to get all Naissances");
        Page<Naissance> result = naissanceRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one naissance by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Naissance findOne(Long id) {
        log.debug("Request to get Naissance : {}", id);
        Naissance naissance = naissanceRepository.findOne(id);
        return naissance;
    }

    /**
     *  Delete the  naissance by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Naissance : {}", id);
        naissanceRepository.delete(id);
        naissanceSearchRepository.delete(id);
    }

    /**
     * Search for the naissance corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Naissance> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Naissances for query {}", query);
        Page<Naissance> result = naissanceSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
