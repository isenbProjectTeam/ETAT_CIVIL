package com.csgb.etatcivil.service;

import com.csgb.etatcivil.domain.Ville;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Ville.
 */
public interface VilleService {

    /**
     * Save a ville.
     *
     * @param ville the entity to save
     * @return the persisted entity
     */
    Ville save(Ville ville);

    /**
     *  Get all the villes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Ville> findAll(Pageable pageable);

    /**
     *  Get the "id" ville.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Ville findOne(Long id);

    /**
     *  Delete the "id" ville.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the ville corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Ville> search(String query, Pageable pageable);
}
