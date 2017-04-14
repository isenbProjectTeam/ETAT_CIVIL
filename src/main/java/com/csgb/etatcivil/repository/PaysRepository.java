package com.csgb.etatcivil.repository;

import com.csgb.etatcivil.domain.Pays;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Pays entity.
 */
@SuppressWarnings("unused")
public interface PaysRepository extends JpaRepository<Pays,Long> {

}
