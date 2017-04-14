package com.csgb.etatcivil.web.rest;

import com.csgb.etatcivil.EtatCivilApp;

import com.csgb.etatcivil.domain.RegistreNaissance;
import com.csgb.etatcivil.domain.Naissance;
import com.csgb.etatcivil.repository.RegistreNaissanceRepository;
import com.csgb.etatcivil.service.RegistreNaissanceService;
import com.csgb.etatcivil.repository.search.RegistreNaissanceSearchRepository;
import com.csgb.etatcivil.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RegistreNaissanceResource REST controller.
 *
 * @see RegistreNaissanceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EtatCivilApp.class)
public class RegistreNaissanceResourceIntTest {

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    private static final LocalDate DEFAULT_ANNEE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ANNEE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private RegistreNaissanceRepository registreNaissanceRepository;

    @Autowired
    private RegistreNaissanceService registreNaissanceService;

    @Autowired
    private RegistreNaissanceSearchRepository registreNaissanceSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRegistreNaissanceMockMvc;

    private RegistreNaissance registreNaissance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RegistreNaissanceResource registreNaissanceResource = new RegistreNaissanceResource(registreNaissanceService);
        this.restRegistreNaissanceMockMvc = MockMvcBuilders.standaloneSetup(registreNaissanceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegistreNaissance createEntity(EntityManager em) {
        RegistreNaissance registreNaissance = new RegistreNaissance()
            .numero(DEFAULT_NUMERO)
            .annee(DEFAULT_ANNEE);
        // Add required entity
        Naissance naissance = NaissanceResourceIntTest.createEntity(em);
        em.persist(naissance);
        em.flush();
        registreNaissance.setNaissance(naissance);
        return registreNaissance;
    }

    @Before
    public void initTest() {
        registreNaissanceSearchRepository.deleteAll();
        registreNaissance = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegistreNaissance() throws Exception {
        int databaseSizeBeforeCreate = registreNaissanceRepository.findAll().size();

        // Create the RegistreNaissance
        restRegistreNaissanceMockMvc.perform(post("/api/registre-naissances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registreNaissance)))
            .andExpect(status().isCreated());

        // Validate the RegistreNaissance in the database
        List<RegistreNaissance> registreNaissanceList = registreNaissanceRepository.findAll();
        assertThat(registreNaissanceList).hasSize(databaseSizeBeforeCreate + 1);
        RegistreNaissance testRegistreNaissance = registreNaissanceList.get(registreNaissanceList.size() - 1);
        assertThat(testRegistreNaissance.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testRegistreNaissance.getAnnee()).isEqualTo(DEFAULT_ANNEE);

        // Validate the RegistreNaissance in Elasticsearch
        RegistreNaissance registreNaissanceEs = registreNaissanceSearchRepository.findOne(testRegistreNaissance.getId());
        assertThat(registreNaissanceEs).isEqualToComparingFieldByField(testRegistreNaissance);
    }

    @Test
    @Transactional
    public void createRegistreNaissanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = registreNaissanceRepository.findAll().size();

        // Create the RegistreNaissance with an existing ID
        registreNaissance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegistreNaissanceMockMvc.perform(post("/api/registre-naissances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registreNaissance)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RegistreNaissance> registreNaissanceList = registreNaissanceRepository.findAll();
        assertThat(registreNaissanceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = registreNaissanceRepository.findAll().size();
        // set the field null
        registreNaissance.setNumero(null);

        // Create the RegistreNaissance, which fails.

        restRegistreNaissanceMockMvc.perform(post("/api/registre-naissances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registreNaissance)))
            .andExpect(status().isBadRequest());

        List<RegistreNaissance> registreNaissanceList = registreNaissanceRepository.findAll();
        assertThat(registreNaissanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnneeIsRequired() throws Exception {
        int databaseSizeBeforeTest = registreNaissanceRepository.findAll().size();
        // set the field null
        registreNaissance.setAnnee(null);

        // Create the RegistreNaissance, which fails.

        restRegistreNaissanceMockMvc.perform(post("/api/registre-naissances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registreNaissance)))
            .andExpect(status().isBadRequest());

        List<RegistreNaissance> registreNaissanceList = registreNaissanceRepository.findAll();
        assertThat(registreNaissanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRegistreNaissances() throws Exception {
        // Initialize the database
        registreNaissanceRepository.saveAndFlush(registreNaissance);

        // Get all the registreNaissanceList
        restRegistreNaissanceMockMvc.perform(get("/api/registre-naissances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(registreNaissance.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].annee").value(hasItem(DEFAULT_ANNEE.toString())));
    }

    @Test
    @Transactional
    public void getRegistreNaissance() throws Exception {
        // Initialize the database
        registreNaissanceRepository.saveAndFlush(registreNaissance);

        // Get the registreNaissance
        restRegistreNaissanceMockMvc.perform(get("/api/registre-naissances/{id}", registreNaissance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(registreNaissance.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.annee").value(DEFAULT_ANNEE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRegistreNaissance() throws Exception {
        // Get the registreNaissance
        restRegistreNaissanceMockMvc.perform(get("/api/registre-naissances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegistreNaissance() throws Exception {
        // Initialize the database
        registreNaissanceService.save(registreNaissance);

        int databaseSizeBeforeUpdate = registreNaissanceRepository.findAll().size();

        // Update the registreNaissance
        RegistreNaissance updatedRegistreNaissance = registreNaissanceRepository.findOne(registreNaissance.getId());
        updatedRegistreNaissance
            .numero(UPDATED_NUMERO)
            .annee(UPDATED_ANNEE);

        restRegistreNaissanceMockMvc.perform(put("/api/registre-naissances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRegistreNaissance)))
            .andExpect(status().isOk());

        // Validate the RegistreNaissance in the database
        List<RegistreNaissance> registreNaissanceList = registreNaissanceRepository.findAll();
        assertThat(registreNaissanceList).hasSize(databaseSizeBeforeUpdate);
        RegistreNaissance testRegistreNaissance = registreNaissanceList.get(registreNaissanceList.size() - 1);
        assertThat(testRegistreNaissance.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testRegistreNaissance.getAnnee()).isEqualTo(UPDATED_ANNEE);

        // Validate the RegistreNaissance in Elasticsearch
        RegistreNaissance registreNaissanceEs = registreNaissanceSearchRepository.findOne(testRegistreNaissance.getId());
        assertThat(registreNaissanceEs).isEqualToComparingFieldByField(testRegistreNaissance);
    }

    @Test
    @Transactional
    public void updateNonExistingRegistreNaissance() throws Exception {
        int databaseSizeBeforeUpdate = registreNaissanceRepository.findAll().size();

        // Create the RegistreNaissance

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRegistreNaissanceMockMvc.perform(put("/api/registre-naissances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registreNaissance)))
            .andExpect(status().isCreated());

        // Validate the RegistreNaissance in the database
        List<RegistreNaissance> registreNaissanceList = registreNaissanceRepository.findAll();
        assertThat(registreNaissanceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRegistreNaissance() throws Exception {
        // Initialize the database
        registreNaissanceService.save(registreNaissance);

        int databaseSizeBeforeDelete = registreNaissanceRepository.findAll().size();

        // Get the registreNaissance
        restRegistreNaissanceMockMvc.perform(delete("/api/registre-naissances/{id}", registreNaissance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean registreNaissanceExistsInEs = registreNaissanceSearchRepository.exists(registreNaissance.getId());
        assertThat(registreNaissanceExistsInEs).isFalse();

        // Validate the database is empty
        List<RegistreNaissance> registreNaissanceList = registreNaissanceRepository.findAll();
        assertThat(registreNaissanceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRegistreNaissance() throws Exception {
        // Initialize the database
        registreNaissanceService.save(registreNaissance);

        // Search the registreNaissance
        restRegistreNaissanceMockMvc.perform(get("/api/_search/registre-naissances?query=id:" + registreNaissance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(registreNaissance.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].annee").value(hasItem(DEFAULT_ANNEE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegistreNaissance.class);
    }
}
