package fr.istic.sit.web.rest;

import fr.istic.sit.ServeurApp;

import fr.istic.sit.domain.Location;
import fr.istic.sit.domain.Mission;
import fr.istic.sit.repository.MissionRepository;
import fr.istic.sit.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static fr.istic.sit.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MissionResource REST controller.
 *
 * @see MissionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServeurApp.class)
public class MissionResourceIntTest {

    private final Logger log = LoggerFactory.getLogger(MissionResourceIntTest.class);

    private static final String DEFAULT_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE = "BBBBBBBBBB";

    private static final LocalDateTime DEFAULT_DATE_DEBUT = LocalDateTime.of(2018,01,01,01,01,01);
    private static final LocalDateTime UPDATED_DATE_DEBUT = LocalDateTime.now(ZoneId.systemDefault());

    private static final LocalDateTime DEFAULT_DATE_FIN = LocalDateTime.of(2018,01,01,01,01,01);
    private static final LocalDateTime UPDATED_DATE_FIN = LocalDateTime.now(ZoneId.systemDefault());

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restMissionMockMvc;

    private Mission mission;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MissionResource missionResource = new MissionResource(missionRepository);
        this.restMissionMockMvc = MockMvcBuilders.standaloneSetup(missionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mission createEntity() {
        Mission mission = new Mission()
            .setTitle(DEFAULT_TITRE)
            .setDateBegin(DEFAULT_DATE_DEBUT)
            .setDateEnd(DEFAULT_DATE_FIN);
        return mission;
    }

    @Before
    public void initTest() {
        missionRepository.deleteAll();
        mission = createEntity();
    }

    @Test
    public void createMission() throws Exception {
        int databaseSizeBeforeCreate = missionRepository.findAll().size();

        // Create the Mission
        restMissionMockMvc.perform(post("/api/missions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mission)))
            .andExpect(status().isCreated());

        // Validate the Mission in the database
        List<Mission> missionList = missionRepository.findAll();
        assertThat(missionList).hasSize(databaseSizeBeforeCreate + 1);
        Mission testMission = missionList.get(missionList.size() - 1);
        assertThat(testMission.getTitle()).isEqualTo(DEFAULT_TITRE);
        assertThat(testMission.getDateBegin()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testMission.getDateEnd()).isEqualTo(DEFAULT_DATE_FIN);
    }

    @Test
    public void createMissionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = missionRepository.findAll().size();

        // Create the Mission with an existing ID
        mission.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restMissionMockMvc.perform(post("/api/missions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mission)))
            .andExpect(status().isBadRequest());

        // Validate the Mission in the database
        List<Mission> missionList = missionRepository.findAll();
        assertThat(missionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllMissions() throws Exception {
        // Initialize the database
        missionRepository.save(mission);

        // Get all the missionList
        restMissionMockMvc.perform(get("/api/missions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mission.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITRE.toString())))
            .andExpect(jsonPath("$.[*].dateBegin").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateEnd").value(hasItem(DEFAULT_DATE_FIN.toString())));
    }

    @Test
    public void getMission() throws Exception {
        // Initialize the database
        missionRepository.save(mission);

        // Get the mission
        restMissionMockMvc.perform(get("/api/missions/{id}", mission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mission.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITRE.toString()))
            .andExpect(jsonPath("$.dateBegin").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateEnd").value(DEFAULT_DATE_FIN.toString()));
    }

    @Test
    public void getNonExistingMission() throws Exception {
        // Get the mission
        restMissionMockMvc.perform(get("/api/missions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateMission() throws Exception {
        // Initialize the database
        missionRepository.save(mission);
        int databaseSizeBeforeUpdate = missionRepository.findAll().size();

        // Update the mission
        Mission updatedMission = missionRepository.findOne(mission.getId());
        updatedMission
            .setTitle(UPDATED_TITRE)
            .setDateBegin(UPDATED_DATE_DEBUT)
            .setDateEnd(UPDATED_DATE_FIN);

        restMissionMockMvc.perform(put("/api/missions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMission)))
            .andExpect(status().isOk());

        // Validate the Mission in the database
        List<Mission> missionList = missionRepository.findAll();
        assertThat(missionList).hasSize(databaseSizeBeforeUpdate);
        Mission testMission = missionList.get(missionList.size() - 1);
        assertThat(testMission.getTitle()).isEqualTo(UPDATED_TITRE);
        assertThat(testMission.getDateBegin()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testMission.getDateEnd()).isEqualTo(UPDATED_DATE_FIN);
    }

    @Test
    public void addOrder() throws Exception {
        // Initialize the database
        missionRepository.save(mission);
        int databaseSizeBeforeUpdate = missionRepository.findAll().size();

        // Update the mission
        Mission updatedMission = missionRepository.findOne(mission.getId());
        updatedMission.setOrderList(new ArrayList<>());
        missionRepository.save(updatedMission);

        //On crée une nouvelle location pour ajouter un order via le REST
        Location l = new Location();
        l.setAltitude(13.0);
        l.setLatitude(13.5165181);
        l.setLongitude(-61.651984913);
        int oldLocalisationSize = updatedMission.getOrderList().size();

        restMissionMockMvc.perform(put("/api/missions/"+updatedMission.getId()+"/orderMove")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(l)))
            .andExpect(status().isOk());

        // Validate the Mission in the database
        List<Mission> missionList = missionRepository.findAll();
        assertThat(missionList).hasSize(databaseSizeBeforeUpdate);
        Mission testMission = missionList.get(missionList.size() - 1);
        assertThat(testMission.getOrderList().size()).isEqualTo(oldLocalisationSize+1);
        assertThat(testMission.getOrderList().get(0).isTakePicture()).isFalse();
    }

    @Test
    public void addOrderWithPicture() throws Exception {
        // Initialize the database
        missionRepository.save(mission);
        int databaseSizeBeforeUpdate = missionRepository.findAll().size();

        // Update the mission
        Mission updatedMission = missionRepository.findOne(mission.getId());
        updatedMission.setOrderList(new ArrayList<>());
        missionRepository.save(updatedMission);

        //On crée une nouvelle location pour ajouter un order via le REST
        Location l = new Location();
        l.setAltitude(13.0);
        l.setLatitude(13.5165181);
        l.setLongitude(-61.651984913);
        int oldLocalisationSize = updatedMission.getOrderList().size();

        restMissionMockMvc.perform(put("/api/missions/"+updatedMission.getId()+"/orderMovePicture")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(l)))
            .andExpect(status().isOk());

        // Validate the Mission in the database
        List<Mission> missionList = missionRepository.findAll();
        assertThat(missionList).hasSize(databaseSizeBeforeUpdate);
        Mission testMission = missionList.get(missionList.size() - 1);
        assertThat(testMission.getOrderList().size()).isEqualTo(oldLocalisationSize+1);
        assertThat(testMission.getOrderList().get(0).isTakePicture()).isTrue();
    }

    @Test
    public void addOrderNonExistingMission() throws Exception {

        String idNotExisting = "EVNZNVIEVE11861481C";

        Location l = new Location();
        l.setAltitude(13.0);
        l.setLatitude(13.5165181);
        l.setLongitude(-61.651984913);

        restMissionMockMvc.perform(put("/api/missions/"+idNotExisting+"/orderMove")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(l)))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void addOrderPictureNonExistingMission() throws Exception {

        String idNotExisting = "EVNZNVIEVE11861481C";

        Location l = new Location();
        l.setAltitude(13.0);
        l.setLatitude(13.5165181);
        l.setLongitude(-61.651984913);

        restMissionMockMvc.perform(put("/api/missions/"+idNotExisting+"/orderMovePicture")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(l)))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void updateNonExistingMission() throws Exception {
        int databaseSizeBeforeUpdate = missionRepository.findAll().size();

        // Create the Mission

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMissionMockMvc.perform(put("/api/missions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mission)))
            .andExpect(status().isCreated());

        // Validate the Mission in the database
        List<Mission> missionList = missionRepository.findAll();
        assertThat(missionList).hasSize(databaseSizeBeforeUpdate + 1);
    }
    
    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mission.class);
        Mission mission1 = new Mission();
        mission1.setId("id1");
        Mission mission2 = new Mission();
        mission2.setId(mission1.getId());
        assertThat(mission1).isEqualTo(mission2);
        mission2.setId("id2");
        assertThat(mission1).isNotEqualTo(mission2);
        mission1.setId(null);
        assertThat(mission1).isNotEqualTo(mission2);
    }
}
