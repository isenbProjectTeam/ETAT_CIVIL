package com.csgb.etatcivil.service.impl;

import com.csgb.etatcivil.service.PersonneService;
import com.csgb.etatcivil.domain.Personne;
import com.csgb.etatcivil.repository.PersonneRepository;
import com.csgb.etatcivil.repository.search.PersonneSearchRepository;
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
 * Service Implementation for managing Personne.
 */
@Service
@Transactional
public class PersonneServiceImpl implements PersonneService{

    private final Logger log = LoggerFactory.getLogger(PersonneServiceImpl.class);
    
    private final PersonneRepository personneRepository;

    private final PersonneSearchRepository personneSearchRepository;

    public PersonneServiceImpl(PersonneRepository personneRepository, PersonneSearchRepository personneSearchRepository) {
        this.personneRepository = personneRepository;
        this.personneSearchRepository = personneSearchRepository;
    }

    /**
     * Save a personne.
     *
     * @param personne the entity to save
     * @return the persisted entity
     */
    @Override
    public Personne save(Personne personne) {
        log.debug("Request to save Personne : {}", personne);
        Personne result = personneRepository.save(personne);
        personneSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the personnes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Personne> findAll(Pageable pageable) {
        log.debug("Request to get all Personnes");
        Page<Personne> result = personneRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one personne by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Personne findOne(Long id) {
        log.debug("Request to get Personne : {}", id);
        Personne personne = personneRepository.findOne(id);
        return personne;
    }

    /**
     *  Delete the  personne by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Personne : {}", id);
        personneRepository.delete(id);
        personneSearchRepository.delete(id);
    }

    /**
     * Search for the personne corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Personne> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Personnes for query {}", query);
        Page<Personne> result = personneSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
