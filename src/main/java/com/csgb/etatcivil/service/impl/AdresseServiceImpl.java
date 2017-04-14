package com.csgb.etatcivil.service.impl;

import com.csgb.etatcivil.service.AdresseService;
import com.csgb.etatcivil.domain.Adresse;
import com.csgb.etatcivil.repository.AdresseRepository;
import com.csgb.etatcivil.repository.search.AdresseSearchRepository;
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
 * Service Implementation for managing Adresse.
 */
@Service
@Transactional
public class AdresseServiceImpl implements AdresseService{

    private final Logger log = LoggerFactory.getLogger(AdresseServiceImpl.class);
    
    private final AdresseRepository adresseRepository;

    private final AdresseSearchRepository adresseSearchRepository;

    public AdresseServiceImpl(AdresseRepository adresseRepository, AdresseSearchRepository adresseSearchRepository) {
        this.adresseRepository = adresseRepository;
        this.adresseSearchRepository = adresseSearchRepository;
    }

    /**
     * Save a adresse.
     *
     * @param adresse the entity to save
     * @return the persisted entity
     */
    @Override
    public Adresse save(Adresse adresse) {
        log.debug("Request to save Adresse : {}", adresse);
        Adresse result = adresseRepository.save(adresse);
        adresseSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the adresses.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Adresse> findAll(Pageable pageable) {
        log.debug("Request to get all Adresses");
        Page<Adresse> result = adresseRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one adresse by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Adresse findOne(Long id) {
        log.debug("Request to get Adresse : {}", id);
        Adresse adresse = adresseRepository.findOne(id);
        return adresse;
    }

    /**
     *  Delete the  adresse by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Adresse : {}", id);
        adresseRepository.delete(id);
        adresseSearchRepository.delete(id);
    }

    /**
     * Search for the adresse corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Adresse> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Adresses for query {}", query);
        Page<Adresse> result = adresseSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
