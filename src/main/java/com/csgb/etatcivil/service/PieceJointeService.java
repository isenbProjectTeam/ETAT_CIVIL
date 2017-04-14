package com.csgb.etatcivil.service;

import com.csgb.etatcivil.domain.PieceJointe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PieceJointe.
 */
public interface PieceJointeService {

    /**
     * Save a pieceJointe.
     *
     * @param pieceJointe the entity to save
     * @return the persisted entity
     */
    PieceJointe save(PieceJointe pieceJointe);

    /**
     *  Get all the pieceJointes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PieceJointe> findAll(Pageable pageable);

    /**
     *  Get the "id" pieceJointe.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PieceJointe findOne(Long id);

    /**
     *  Delete the "id" pieceJointe.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the pieceJointe corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PieceJointe> search(String query, Pageable pageable);
}
