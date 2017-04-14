package com.csgb.etatcivil.web.rest;

import com.csgb.etatcivil.EtatCivilApp;

import com.csgb.etatcivil.domain.Naissance;
import com.csgb.etatcivil.domain.Personne;
import com.csgb.etatcivil.domain.Personne;
import com.csgb.etatcivil.domain.Personne;
import com.csgb.etatcivil.domain.User;
import com.csgb.etatcivil.domain.Ville;
import com.csgb.etatcivil.repository.NaissanceRepository;
import com.csgb.etatcivil.service.NaissanceService;
import com.csgb.etatcivil.repository.search.NaissanceSearchRepository;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the NaissanceResource REST controller.
 *
 * @see NaissanceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EtatCivilApp.class)
public class NaissanceResourceIntTest {

    private static final String DEFAULT_NUMERO_REGISTRE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_REGISTRE = "BBBBBBBBBB";

    private static final String DEFAULT_MENTION_MARGINALE = "AAAAAAAAAA";
    private static final String UPDATED_MENTION_MARGINALE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_DECLARATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DECLARATION = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private NaissanceRepository naissanceRepository;

    @Autowired
    private NaissanceService naissanceService;

    @Autowired
    private NaissanceSearchRepository naissanceSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNaissanceMockMvc;

    private Naissance naissance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        NaissanceResource naissanceResource = new NaissanceResource(naissanceService);
        this.restNaissanceMockMvc = MockMvcBuilders.standaloneSetup(naissanceResource)
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
    public static Naissance createEntity(EntityManager em) {
        Naissance naissance = new Naissance()
            .numeroRegistre(DEFAULT_NUMERO_REGISTRE)
            .mentionMarginale(DEFAULT_MENTION_MARGINALE)
            .dateDeclaration(DEFAULT_DATE_DECLARATION);
        // Add required entity
        Personne pere = PersonneResourceIntTest.createEntity(em);
        em.persist(pere);
        em.flush();
        naissance.setPere(pere);
        // Add required entity
        Personne mere = PersonneResourceIntTest.createEntity(em);
        em.persist(mere);
        em.flush();
        naissance.setMere(mere);
        // Add required entity
        Personne enfant = PersonneResourceIntTest.createEntity(em);
        em.persist(enfant);
        em.flush();
        naissance.setEnfant(enfant);
        // Add required entity
        User agentDeclarant = UserResourceIntTest.createEntity(em);
        em.persist(agentDeclarant);
        em.flush();
        naissance.setAgentDeclarant(agentDeclarant);
        // Add required entity
        Ville lieuDeclaration = VilleResourceIntTest.createEntity(em);
        em.persist(lieuDeclaration);
        em.flush();
        naissance.setLieuDeclaration(lieuDeclaration);
        return naissance;
    }

    @Before
    public void initTest() {
        naissanceSearchRepository.deleteAll();
        naissance = createEntity(em);
    }

    @Test
    @Transactional
    public void createNaissance() throws Exception {
        int databaseSizeBeforeCreate = naissanceRepository.findAll().size();

        // Create the Naissance
        restNaissanceMockMvc.perform(post("/api/naissances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(naissance)))
            .andExpect(status().isCreated());

        // Validate the Naissance in the database
        List<Naissance> naissanceList = naissanceRepository.findAll();
        assertThat(naissanceList).hasSize(databaseSizeBeforeCreate + 1);
        Naissance testNaissance = naissanceList.get(naissanceList.size() - 1);
        assertThat(testNaissance.getNumeroRegistre()).isEqualTo(DEFAULT_NUMERO_REGISTRE);
        assertThat(testNaissance.getMentionMarginale()).isEqualTo(DEFAULT_MENTION_MARGINALE);
        assertThat(testNaissance.getDateDeclaration()).isEqualTo(DEFAULT_DATE_DECLARATION);

        // Validate the Naissance in Elasticsearch
        Naissance naissanceEs = naissanceSearchRepository.findOne(testNaissance.getId());
        assertThat(naissanceEs).isEqualToComparingFieldByField(testNaissance);
    }

    @Test
    @Transactional
    public void createNaissanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = naissanceRepository.findAll().size();

        // Create the Naissance with an existing ID
        naissance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNaissanceMockMvc.perform(post("/api/naissances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(naissance)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Naissance> naissanceList = naissanceRepository.findAll();
        assertThat(naissanceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumeroRegistreIsRequired() throws Exception {
        int databaseSizeBeforeTest = naissanceRepository.findAll().size();
        // set the field null
        naissance.setNumeroRegistre(null);

        // Create the Naissance, which fails.

        restNaissanceMockMvc.perform(post("/api/naissances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(naissance)))
            .andExpect(status().isBadRequest());

        List<Naissance> naissanceList = naissanceRepository.findAll();
        assertThat(naissanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMentionMarginaleIsRequired() throws Exception {
        int databaseSizeBeforeTest = naissanceRepository.findAll().size();
        // set the field null
        naissance.setMentionMarginale(null);

        // Create the Naissance, which fails.

        restNaissanceMockMvc.perform(post("/api/naissances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(naissance)))
            .andExpect(status().isBadRequest());

        List<Naissance> naissanceList = naissanceRepository.findAll();
        assertThat(naissanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateDeclarationIsRequired() throws Exception {
        int databaseSizeBeforeTest = naissanceRepository.findAll().size();
        // set the field null
        naissance.setDateDeclaration(null);

        // Create the Naissance, which fails.

        restNaissanceMockMvc.perform(post("/api/naissances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(naissance)))
            .andExpect(status().isBadRequest());

        List<Naissance> naissanceList = naissanceRepository.findAll();
        assertThat(naissanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNaissances() throws Exception {
        // Initialize the database
        naissanceRepository.saveAndFlush(naissance);

        // Get all the naissanceList
        restNaissanceMockMvc.perform(get("/api/naissances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(naissance.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroRegistre").value(hasItem(DEFAULT_NUMERO_REGISTRE.toString())))
            .andExpect(jsonPath("$.[*].mentionMarginale").value(hasItem(DEFAULT_MENTION_MARGINALE.toString())))
            .andExpect(jsonPath("$.[*].dateDeclaration").value(hasItem(DEFAULT_DATE_DECLARATION.toString())));
    }

    @Test
    @Transactional
    public void getNaissance() throws Exception {
        // Initialize the database
        naissanceRepository.saveAndFlush(naissance);

        // Get the naissance
        restNaissanceMockMvc.perform(get("/api/naissances/{id}", naissance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(naissance.getId().intValue()))
            .andExpect(jsonPath("$.numeroRegistre").value(DEFAULT_NUMERO_REGISTRE.toString()))
            .andExpect(jsonPath("$.mentionMarginale").value(DEFAULT_MENTION_MARGINALE.toString()))
            .andExpect(jsonPath("$.dateDeclaration").value(DEFAULT_DATE_DECLARATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNaissance() throws Exception {
        // Get the naissance
        restNaissanceMockMvc.perform(get("/api/naissances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNaissance() throws Exception {
        // Initialize the database
        naissanceService.save(naissance);

        int databaseSizeBeforeUpdate = naissanceRepository.findAll().size();

        // Update the naissance
        Naissance updatedNaissance = naissanceRepository.findOne(naissance.getId());
        updatedNaissance
            .numeroRegistre(UPDATED_NUMERO_REGISTRE)
            .mentionMarginale(UPDATED_MENTION_MARGINALE)
            .dateDeclaration(UPDATED_DATE_DECLARATION);

        restNaissanceMockMvc.perform(put("/api/naissances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNaissance)))
            .andExpect(status().isOk());

        // Validate the Naissance in the database
        List<Naissance> naissanceList = naissanceRepository.findAll();
        assertThat(naissanceList).hasSize(databaseSizeBeforeUpdate);
        Naissance testNaissance = naissanceList.get(naissanceList.size() - 1);
        assertThat(testNaissance.getNumeroRegistre()).isEqualTo(UPDATED_NUMERO_REGISTRE);
        assertThat(testNaissance.getMentionMarginale()).isEqualTo(UPDATED_MENTION_MARGINALE);
        assertThat(testNaissance.getDateDeclaration()).isEqualTo(UPDATED_DATE_DECLARATION);

        // Validate the Naissance in Elasticsearch
        Naissance naissanceEs = naissanceSearchRepository.findOne(testNaissance.getId());
        assertThat(naissanceEs).isEqualToComparingFieldByField(testNaissance);
    }

    @Test
    @Transactional
    public void updateNonExistingNaissance() throws Exception {
        int databaseSizeBeforeUpdate = naissanceRepository.findAll().size();

        // Create the Naissance

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNaissanceMockMvc.perform(put("/api/naissances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(naissance)))
            .andExpect(status().isCreated());

        // Validate the Naissance in the database
        List<Naissance> naissanceList = naissanceRepository.findAll();
        assertThat(naissanceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNaissance() throws Exception {
        // Initialize the database
        naissanceService.save(naissance);

        int databaseSizeBeforeDelete = naissanceRepository.findAll().size();

        // Get the naissance
        restNaissanceMockMvc.perform(delete("/api/naissances/{id}", naissance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean naissanceExistsInEs = naissanceSearchRepository.exists(naissance.getId());
        assertThat(naissanceExistsInEs).isFalse();

        // Validate the database is empty
        List<Naissance> naissanceList = naissanceRepository.findAll();
        assertThat(naissanceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchNaissance() throws Exception {
        // Initialize the database
        naissanceService.save(naissance);

        // Search the naissance
        restNaissanceMockMvc.perform(get("/api/_search/naissances?query=id:" + naissance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(naissance.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroRegistre").value(hasItem(DEFAULT_NUMERO_REGISTRE.toString())))
            .andExpect(jsonPath("$.[*].mentionMarginale").value(hasItem(DEFAULT_MENTION_MARGINALE.toString())))
            .andExpect(jsonPath("$.[*].dateDeclaration").value(hasItem(DEFAULT_DATE_DECLARATION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Naissance.class);
    }
}
