package com.csgb.etatcivil.repository.search;

import com.csgb.etatcivil.domain.Personne;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Personne entity.
 */
public interface PersonneSearchRepository extends ElasticsearchRepository<Personne, Long> {
}
