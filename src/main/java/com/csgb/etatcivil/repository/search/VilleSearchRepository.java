package com.csgb.etatcivil.repository.search;

import com.csgb.etatcivil.domain.Ville;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Ville entity.
 */
public interface VilleSearchRepository extends ElasticsearchRepository<Ville, Long> {
}
