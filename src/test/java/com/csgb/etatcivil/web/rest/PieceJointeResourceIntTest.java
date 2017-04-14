package com.csgb.etatcivil.web.rest;

import com.csgb.etatcivil.EtatCivilApp;

import com.csgb.etatcivil.domain.PieceJointe;
import com.csgb.etatcivil.domain.Naissance;
import com.csgb.etatcivil.repository.PieceJointeRepository;
import com.csgb.etatcivil.service.PieceJointeService;
import com.csgb.etatcivil.repository.search.PieceJointeSearchRepository;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PieceJointeResource REST controller.
 *
 * @see PieceJointeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EtatCivilApp.class)
public class PieceJointeResourceIntTest {

    private static final byte[] DEFAULT_NOM = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_NOM = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_NOM_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_NOM_CONTENT_TYPE = "image/png";

    @Autowired
    private PieceJointeRepository pieceJointeRepository;

    @Autowired
    private PieceJointeService pieceJointeService;

    @Autowired
    private PieceJointeSearchRepository pieceJointeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPieceJointeMockMvc;

    private PieceJointe pieceJointe;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PieceJointeResource pieceJointeResource = new PieceJointeResource(pieceJointeService);
        this.restPieceJointeMockMvc = MockMvcBuilders.standaloneSetup(pieceJointeResource)
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
    public static PieceJointe createEntity(EntityManager em) {
        PieceJointe pieceJointe = new PieceJointe()
            .nom(DEFAULT_NOM)
            .nomContentType(DEFAULT_NOM_CONTENT_TYPE);
        // Add required entity
        Naissance naissance = NaissanceResourceIntTest.createEntity(em);
        em.persist(naissance);
        em.flush();
        pieceJointe.setNaissance(naissance);
        return pieceJointe;
    }

    @Before
    public void initTest() {
        pieceJointeSearchRepository.deleteAll();
        pieceJointe = createEntity(em);
    }

    @Test
    @Transactional
    public void createPieceJointe() throws Exception {
        int databaseSizeBeforeCreate = pieceJointeRepository.findAll().size();

        // Create the PieceJointe
        restPieceJointeMockMvc.perform(post("/api/piece-jointes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieceJointe)))
            .andExpect(status().isCreated());

        // Validate the PieceJointe in the database
        List<PieceJointe> pieceJointeList = pieceJointeRepository.findAll();
        assertThat(pieceJointeList).hasSize(databaseSizeBeforeCreate + 1);
        PieceJointe testPieceJointe = pieceJointeList.get(pieceJointeList.size() - 1);
        assertThat(testPieceJointe.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testPieceJointe.getNomContentType()).isEqualTo(DEFAULT_NOM_CONTENT_TYPE);

        // Validate the PieceJointe in Elasticsearch
        PieceJointe pieceJointeEs = pieceJointeSearchRepository.findOne(testPieceJointe.getId());
        assertThat(pieceJointeEs).isEqualToComparingFieldByField(testPieceJointe);
    }

    @Test
    @Transactional
    public void createPieceJointeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pieceJointeRepository.findAll().size();

        // Create the PieceJointe with an existing ID
        pieceJointe.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPieceJointeMockMvc.perform(post("/api/piece-jointes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieceJointe)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PieceJointe> pieceJointeList = pieceJointeRepository.findAll();
        assertThat(pieceJointeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = pieceJointeRepository.findAll().size();
        // set the field null
        pieceJointe.setNom(null);

        // Create the PieceJointe, which fails.

        restPieceJointeMockMvc.perform(post("/api/piece-jointes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieceJointe)))
            .andExpect(status().isBadRequest());

        List<PieceJointe> pieceJointeList = pieceJointeRepository.findAll();
        assertThat(pieceJointeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPieceJointes() throws Exception {
        // Initialize the database
        pieceJointeRepository.saveAndFlush(pieceJointe);

        // Get all the pieceJointeList
        restPieceJointeMockMvc.perform(get("/api/piece-jointes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pieceJointe.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomContentType").value(hasItem(DEFAULT_NOM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(Base64Utils.encodeToString(DEFAULT_NOM))));
    }

    @Test
    @Transactional
    public void getPieceJointe() throws Exception {
        // Initialize the database
        pieceJointeRepository.saveAndFlush(pieceJointe);

        // Get the pieceJointe
        restPieceJointeMockMvc.perform(get("/api/piece-jointes/{id}", pieceJointe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pieceJointe.getId().intValue()))
            .andExpect(jsonPath("$.nomContentType").value(DEFAULT_NOM_CONTENT_TYPE))
            .andExpect(jsonPath("$.nom").value(Base64Utils.encodeToString(DEFAULT_NOM)));
    }

    @Test
    @Transactional
    public void getNonExistingPieceJointe() throws Exception {
        // Get the pieceJointe
        restPieceJointeMockMvc.perform(get("/api/piece-jointes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePieceJointe() throws Exception {
        // Initialize the database
        pieceJointeService.save(pieceJointe);

        int databaseSizeBeforeUpdate = pieceJointeRepository.findAll().size();

        // Update the pieceJointe
        PieceJointe updatedPieceJointe = pieceJointeRepository.findOne(pieceJointe.getId());
        updatedPieceJointe
            .nom(UPDATED_NOM)
            .nomContentType(UPDATED_NOM_CONTENT_TYPE);

        restPieceJointeMockMvc.perform(put("/api/piece-jointes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPieceJointe)))
            .andExpect(status().isOk());

        // Validate the PieceJointe in the database
        List<PieceJointe> pieceJointeList = pieceJointeRepository.findAll();
        assertThat(pieceJointeList).hasSize(databaseSizeBeforeUpdate);
        PieceJointe testPieceJointe = pieceJointeList.get(pieceJointeList.size() - 1);
        assertThat(testPieceJointe.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testPieceJointe.getNomContentType()).isEqualTo(UPDATED_NOM_CONTENT_TYPE);

        // Validate the PieceJointe in Elasticsearch
        PieceJointe pieceJointeEs = pieceJointeSearchRepository.findOne(testPieceJointe.getId());
        assertThat(pieceJointeEs).isEqualToComparingFieldByField(testPieceJointe);
    }

    @Test
    @Transactional
    public void updateNonExistingPieceJointe() throws Exception {
        int databaseSizeBeforeUpdate = pieceJointeRepository.findAll().size();

        // Create the PieceJointe

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPieceJointeMockMvc.perform(put("/api/piece-jointes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieceJointe)))
            .andExpect(status().isCreated());

        // Validate the PieceJointe in the database
        List<PieceJointe> pieceJointeList = pieceJointeRepository.findAll();
        assertThat(pieceJointeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePieceJointe() throws Exception {
        // Initialize the database
        pieceJointeService.save(pieceJointe);

        int databaseSizeBeforeDelete = pieceJointeRepository.findAll().size();

        // Get the pieceJointe
        restPieceJointeMockMvc.perform(delete("/api/piece-jointes/{id}", pieceJointe.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean pieceJointeExistsInEs = pieceJointeSearchRepository.exists(pieceJointe.getId());
        assertThat(pieceJointeExistsInEs).isFalse();

        // Validate the database is empty
        List<PieceJointe> pieceJointeList = pieceJointeRepository.findAll();
        assertThat(pieceJointeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPieceJointe() throws Exception {
        // Initialize the database
        pieceJointeService.save(pieceJointe);

        // Search the pieceJointe
        restPieceJointeMockMvc.perform(get("/api/_search/piece-jointes?query=id:" + pieceJointe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pieceJointe.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomContentType").value(hasItem(DEFAULT_NOM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(Base64Utils.encodeToString(DEFAULT_NOM))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PieceJointe.class);
    }
}
