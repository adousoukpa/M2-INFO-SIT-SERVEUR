package fr.istic.sit.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.istic.sit.domain.Obstacle;

import fr.istic.sit.repository.ObstacleRepository;
import fr.istic.sit.web.rest.errors.BadRequestAlertException;
import fr.istic.sit.web.rest.util.HeaderUtil;
import fr.istic.sit.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Obstacle.
 */
@RestController
@RequestMapping("/api")
public class ObstacleResource {

    private final Logger log = LoggerFactory.getLogger(ObstacleResource.class);

    private static final String ENTITY_NAME = "obstacle";

    private final ObstacleRepository obstacleRepository;

    public ObstacleResource(ObstacleRepository obstacleRepository) {
        this.obstacleRepository = obstacleRepository;
    }

    /**
     * POST  /obstacles : Create a new obstacle.
     *
     * @param obstacle the obstacle to create
     * @return the ResponseEntity with status 201 (Created) and with body the new obstacle, or with status 400 (Bad Request) if the obstacle has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/obstacles")
    @Timed
    public ResponseEntity<Obstacle> createObstacle(@RequestBody Obstacle obstacle) throws URISyntaxException {
        log.debug("REST request to save Obstacle : {}", obstacle);
        if (obstacle.getId() != null) {
            throw new BadRequestAlertException("A new obstacle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Obstacle result = obstacleRepository.save(obstacle);
        return ResponseEntity.created(new URI("/api/obstacles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /obstacles : Updates an existing obstacle.
     *
     * @param obstacle the obstacle to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated obstacle,
     * or with status 400 (Bad Request) if the obstacle is not valid,
     * or with status 500 (Internal Server Error) if the obstacle couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/obstacles")
    @Timed
    public ResponseEntity<Obstacle> updateObstacle(@RequestBody Obstacle obstacle) throws URISyntaxException {
        log.debug("REST request to update Obstacle : {}", obstacle);
        if (obstacle.getId() == null) {
            return createObstacle(obstacle);
        }
        Obstacle result = obstacleRepository.save(obstacle);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, obstacle.getId().toString()))
            .body(result);
    }

    /**
     * GET  /obstacles : get all the obstacles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of obstacles in body
     */
    @GetMapping("/obstacles")
    @Timed
    public ResponseEntity<List<Obstacle>> getAllObstacles(Pageable pageable) {
        log.debug("REST request to get a page of Obstacles");
        Page<Obstacle> page = obstacleRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/obstacles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /obstacles/:id : get the "id" obstacle.
     *
     * @param id the id of the obstacle to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the obstacle, or with status 404 (Not Found)
     */
    @GetMapping("/obstacles/{id}")
    @Timed
    public ResponseEntity<Obstacle> getObstacle(@PathVariable String id) {
        log.debug("REST request to get Obstacle : {}", id);
        Obstacle obstacle = obstacleRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(obstacle));
    }

}
