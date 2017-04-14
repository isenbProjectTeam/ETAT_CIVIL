package com.csgb.etatcivil.service;

import com.csgb.etatcivil.domain.RegistreNaissance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing RegistreNaissance.
 */
public interface RegistreNaissanceService {

    /**
     * Save a registreNaissance.
     *
     * @param registreNaissance the entity to save
     * @return the persisted entity
     */
    RegistreNaissance save(RegistreNaissance registreNaissance);

    /**
     *  Get all the registreNaissances.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RegistreNaissance> findAll(Pageable pageable);

    /**
     *  Get the "id" registreNaissance.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RegistreNaissance findOne(Long id);

    /**
     *  Delete the "id" registreNaissance.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the registreNaissance corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RegistreNaissance> search(String query, Pageable pageable);
}
