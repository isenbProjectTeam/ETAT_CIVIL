package com.csgb.etatcivil.repository;

import com.csgb.etatcivil.domain.Naissance;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Naissance entity.
 */
@SuppressWarnings("unused")
public interface NaissanceRepository extends JpaRepository<Naissance,Long> {

    @Query("select naissance from Naissance naissance where naissance.agentDeclarant.login = ?#{principal.username}")
    List<Naissance> findByAgentDeclarantIsCurrentUser();

}
