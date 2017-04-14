package com.csgb.etatcivil.repository.search;

import com.csgb.etatcivil.domain.PieceJointe;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PieceJointe entity.
 */
public interface PieceJointeSearchRepository extends ElasticsearchRepository<PieceJointe, Long> {
}
