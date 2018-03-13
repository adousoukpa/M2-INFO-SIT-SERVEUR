package fr.istic.sit.web.rest;

import fr.istic.sit.ServeurApp;

import fr.istic.sit.domain.Obstacle;
import fr.istic.sit.repository.ObstacleRepository;
import fr.istic.sit.web.rest.errors.ExceptionTranslator;

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

import java.util.List;

import static fr.istic.sit.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ObstacleResource REST controller.
 *
 * @see ObstacleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServeurApp.class)
public class ObstacleResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ObstacleRepository obstacleRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restObstacleMockMvc;

    private Obstacle obstacle;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ObstacleResource obstacleResource = new ObstacleResource(obstacleRepository);
        this.restObstacleMockMvc = MockMvcBuilders.standaloneSetup(obstacleResource)
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
    public static Obstacle createEntity() {
        Obstacle obstacle = new Obstacle()
            .name(DEFAULT_NAME);
        return obstacle;
    }

    @Before
    public void initTest() {
        obstacleRepository.deleteAll();
        obstacle = createEntity();
    }

    @Test
    public void createObstacle() throws Exception {
        int databaseSizeBeforeCreate = obstacleRepository.findAll().size();

        // Create the Obstacle
        restObstacleMockMvc.perform(post("/api/obstacles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obstacle)))
            .andExpect(status().isCreated());

        // Validate the Obstacle in the database
        List<Obstacle> obstacleList = obstacleRepository.findAll();
        assertThat(obstacleList).hasSize(databaseSizeBeforeCreate + 1);
        Obstacle testObstacle = obstacleList.get(obstacleList.size() - 1);
        assertThat(testObstacle.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    public void createObstacleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = obstacleRepository.findAll().size();

        // Create the Obstacle with an existing ID
        obstacle.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restObstacleMockMvc.perform(post("/api/obstacles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obstacle)))
            .andExpect(status().isBadRequest());

        // Validate the Obstacle in the database
        List<Obstacle> obstacleList = obstacleRepository.findAll();
        assertThat(obstacleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getObstacle() throws Exception {
        // Initialize the database
        obstacleRepository.save(obstacle);

        // Get the obstacle
        restObstacleMockMvc.perform(get("/api/obstacles/{id}", obstacle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(obstacle.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    public void getNonExistingObstacle() throws Exception {
        // Get the obstacle
        restObstacleMockMvc.perform(get("/api/obstacles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateObstacle() throws Exception {
        // Initialize the database
        obstacleRepository.save(obstacle);
        int databaseSizeBeforeUpdate = obstacleRepository.findAll().size();

        // Update the obstacle
        Obstacle updatedObstacle = obstacleRepository.findOne(obstacle.getId());
        updatedObstacle
            .name(UPDATED_NAME);

        restObstacleMockMvc.perform(put("/api/obstacles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedObstacle)))
            .andExpect(status().isOk());

        // Validate the Obstacle in the database
        List<Obstacle> obstacleList = obstacleRepository.findAll();
        assertThat(obstacleList).hasSize(databaseSizeBeforeUpdate);
        Obstacle testObstacle = obstacleList.get(obstacleList.size() - 1);
        assertThat(testObstacle.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    public void updateNonExistingObstacle() throws Exception {
        int databaseSizeBeforeUpdate = obstacleRepository.findAll().size();

        // Create the Obstacle

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restObstacleMockMvc.perform(put("/api/obstacles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obstacle)))
            .andExpect(status().isCreated());

        // Validate the Obstacle in the database
        List<Obstacle> obstacleList = obstacleRepository.findAll();
        assertThat(obstacleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Obstacle.class);
        Obstacle obstacle1 = new Obstacle();
        obstacle1.setId("id1");
        Obstacle obstacle2 = new Obstacle();
        obstacle2.setId(obstacle1.getId());
        assertThat(obstacle1).isEqualTo(obstacle2);
        obstacle2.setId("id2");
        assertThat(obstacle1).isNotEqualTo(obstacle2);
        obstacle1.setId(null);
        assertThat(obstacle1).isNotEqualTo(obstacle2);
    }
}
