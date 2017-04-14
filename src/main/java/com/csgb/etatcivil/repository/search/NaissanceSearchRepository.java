package com.csgb.etatcivil.repository.search;

import com.csgb.etatcivil.domain.Naissance;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Naissance entity.
 */
public interface NaissanceSearchRepository extends ElasticsearchRepository<Naissance, Long> {
}
