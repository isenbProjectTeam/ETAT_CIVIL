package com.csgb.etatcivil.repository;

import com.csgb.etatcivil.domain.RegistreNaissance;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the RegistreNaissance entity.
 */
@SuppressWarnings("unused")
public interface RegistreNaissanceRepository extends JpaRepository<RegistreNaissance,Long> {

}
