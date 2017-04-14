package com.csgb.etatcivil.repository;

import com.csgb.etatcivil.domain.PieceJointe;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PieceJointe entity.
 */
@SuppressWarnings("unused")
public interface PieceJointeRepository extends JpaRepository<PieceJointe,Long> {

}
