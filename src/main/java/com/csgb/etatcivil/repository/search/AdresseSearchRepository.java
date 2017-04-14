package com.csgb.etatcivil.repository.search;

import com.csgb.etatcivil.domain.Adresse;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Adresse entity.
 */
public interface AdresseSearchRepository extends ElasticsearchRepository<Adresse, Long> {
}
