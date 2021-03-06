package com.csgb.etatcivil.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.csgb.etatcivil.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.csgb.etatcivil.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.csgb.etatcivil.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.csgb.etatcivil.domain.PersistentToken.class.getName(), jcacheConfiguration);
            cm.createCache(com.csgb.etatcivil.domain.User.class.getName() + ".persistentTokens", jcacheConfiguration);
            cm.createCache(com.csgb.etatcivil.domain.Pays.class.getName(), jcacheConfiguration);
            cm.createCache(com.csgb.etatcivil.domain.Ville.class.getName(), jcacheConfiguration);
            cm.createCache(com.csgb.etatcivil.domain.Adresse.class.getName(), jcacheConfiguration);
            cm.createCache(com.csgb.etatcivil.domain.Adresse.class.getName() + ".pays", jcacheConfiguration);
            cm.createCache(com.csgb.etatcivil.domain.Personne.class.getName(), jcacheConfiguration);
            cm.createCache(com.csgb.etatcivil.domain.Naissance.class.getName(), jcacheConfiguration);
            cm.createCache(com.csgb.etatcivil.domain.PieceJointe.class.getName(), jcacheConfiguration);
            cm.createCache(com.csgb.etatcivil.domain.RegistreNaissance.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
