package com.csgb.etatcivil.repository.search;

import com.csgb.etatcivil.domain.Pays;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Pays entity.
 */
public interface PaysSearchRepository extends ElasticsearchRepository<Pays, Long> {
}
