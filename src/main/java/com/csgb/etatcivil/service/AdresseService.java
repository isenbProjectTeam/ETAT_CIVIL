package com.csgb.etatcivil.service;

import com.csgb.etatcivil.domain.Adresse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Adresse.
 */
public interface AdresseService {

    /**
     * Save a adresse.
     *
     * @param adresse the entity to save
     * @return the persisted entity
     */
    Adresse save(Adresse adresse);

    /**
     *  Get all the adresses.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Adresse> findAll(Pageable pageable);

    /**
     *  Get the "id" adresse.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Adresse findOne(Long id);

    /**
     *  Delete the "id" adresse.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the adresse corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Adresse> search(String query, Pageable pageable);
}
