package com.csgb.etatcivil.repository;

import com.csgb.etatcivil.domain.Ville;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Ville entity.
 */
@SuppressWarnings("unused")
public interface VilleRepository extends JpaRepository<Ville,Long> {

}
