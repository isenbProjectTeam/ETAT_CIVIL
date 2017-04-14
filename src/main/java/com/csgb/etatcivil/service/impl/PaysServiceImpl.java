package com.csgb.etatcivil.service.impl;

import com.csgb.etatcivil.service.PaysService;
import com.csgb.etatcivil.domain.Pays;
import com.csgb.etatcivil.repository.PaysRepository;
import com.csgb.etatcivil.repository.search.PaysSearchRepository;
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
 * Service Implementation for managing Pays.
 */
@Service
@Transactional
public class PaysServiceImpl implements PaysService{

    private final Logger log = LoggerFactory.getLogger(PaysServiceImpl.class);
    
    private final PaysRepository paysRepository;

    private final PaysSearchRepository paysSearchRepository;

    public PaysServiceImpl(PaysRepository paysRepository, PaysSearchRepository paysSearchRepository) {
        this.paysRepository = paysRepository;
        this.paysSearchRepository = paysSearchRepository;
    }

    /**
     * Save a pays.
     *
     * @param pays the entity to save
     * @return the persisted entity
     */
    @Override
    public Pays save(Pays pays) {
        log.debug("Request to save Pays : {}", pays);
        Pays result = paysRepository.save(pays);
        paysSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the pays.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Pays> findAll(Pageable pageable) {
        log.debug("Request to get all Pays");
        Page<Pays> result = paysRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one pays by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Pays findOne(Long id) {
        log.debug("Request to get Pays : {}", id);
        Pays pays = paysRepository.findOne(id);
        return pays;
    }

    /**
     *  Delete the  pays by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pays : {}", id);
        paysRepository.delete(id);
        paysSearchRepository.delete(id);
    }

    /**
     * Search for the pays corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Pays> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Pays for query {}", query);
        Page<Pays> result = paysSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
