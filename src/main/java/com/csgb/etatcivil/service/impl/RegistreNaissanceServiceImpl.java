package com.csgb.etatcivil.service.impl;

import com.csgb.etatcivil.service.RegistreNaissanceService;
import com.csgb.etatcivil.domain.RegistreNaissance;
import com.csgb.etatcivil.repository.RegistreNaissanceRepository;
import com.csgb.etatcivil.repository.search.RegistreNaissanceSearchRepository;
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
 * Service Implementation for managing RegistreNaissance.
 */
@Service
@Transactional
public class RegistreNaissanceServiceImpl implements RegistreNaissanceService{

    private final Logger log = LoggerFactory.getLogger(RegistreNaissanceServiceImpl.class);
    
    private final RegistreNaissanceRepository registreNaissanceRepository;

    private final RegistreNaissanceSearchRepository registreNaissanceSearchRepository;

    public RegistreNaissanceServiceImpl(RegistreNaissanceRepository registreNaissanceRepository, RegistreNaissanceSearchRepository registreNaissanceSearchRepository) {
        this.registreNaissanceRepository = registreNaissanceRepository;
        this.registreNaissanceSearchRepository = registreNaissanceSearchRepository;
    }

    /**
     * Save a registreNaissance.
     *
     * @param registreNaissance the entity to save
     * @return the persisted entity
     */
    @Override
    public RegistreNaissance save(RegistreNaissance registreNaissance) {
        log.debug("Request to save RegistreNaissance : {}", registreNaissance);
        RegistreNaissance result = registreNaissanceRepository.save(registreNaissance);
        registreNaissanceSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the registreNaissances.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RegistreNaissance> findAll(Pageable pageable) {
        log.debug("Request to get all RegistreNaissances");
        Page<RegistreNaissance> result = registreNaissanceRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one registreNaissance by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RegistreNaissance findOne(Long id) {
        log.debug("Request to get RegistreNaissance : {}", id);
        RegistreNaissance registreNaissance = registreNaissanceRepository.findOne(id);
        return registreNaissance;
    }

    /**
     *  Delete the  registreNaissance by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RegistreNaissance : {}", id);
        registreNaissanceRepository.delete(id);
        registreNaissanceSearchRepository.delete(id);
    }

    /**
     * Search for the registreNaissance corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RegistreNaissance> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RegistreNaissances for query {}", query);
        Page<RegistreNaissance> result = registreNaissanceSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
