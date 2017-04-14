package com.csgb.etatcivil.repository;

import com.csgb.etatcivil.domain.Personne;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Personne entity.
 */
@SuppressWarnings("unused")
public interface PersonneRepository extends JpaRepository<Personne,Long> {

}
