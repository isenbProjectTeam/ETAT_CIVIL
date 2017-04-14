package com.csgb.etatcivil.service;

import com.csgb.etatcivil.domain.Naissance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Naissance.
 */
public interface NaissanceService {

    /**
     * Save a naissance.
     *
     * @param naissance the entity to save
     * @return the persisted entity
     */
    Naissance save(Naissance naissance);

    /**
     *  Get all the naissances.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Naissance> findAll(Pageable pageable);

    /**
     *  Get the "id" naissance.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Naissance findOne(Long id);

    /**
     *  Delete the "id" naissance.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the naissance corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Naissance> search(String query, Pageable pageable);
}
