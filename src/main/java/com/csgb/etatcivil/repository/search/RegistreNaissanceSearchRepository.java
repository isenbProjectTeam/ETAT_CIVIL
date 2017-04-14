package com.csgb.etatcivil.repository.search;

import com.csgb.etatcivil.domain.RegistreNaissance;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RegistreNaissance entity.
 */
public interface RegistreNaissanceSearchRepository extends ElasticsearchRepository<RegistreNaissance, Long> {
}
