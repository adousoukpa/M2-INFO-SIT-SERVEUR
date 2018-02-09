package fr.istic.sit.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.istic.sit.domain.Obstacle;
import fr.istic.sit.domain.Utils;
import fr.istic.sit.repository.ObstacleRepository;
import fr.istic.sit.web.rest.errors.BadRequestAlertException;
import fr.istic.sit.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
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
    @ApiOperation(value = "Mise à jour d'un obstacle",
        notes = "Mise à jour d'un obstacle",
        response = Obstacle.class,
        responseContainer = "ResponseEntity")
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
     * GET  /obstaclesNearLocation : Récupère la liste des obstacles dans un cercle défini
     * par une localisation et un rayon
     *
     * @param latitude : Latitude du centre du cercle de recherche
     * @param longitude : Longitude du centre du cercle de recherche
     * @param radius : rayon du cercle de recherche
     * @return the ResponseEntity with status 200 (OK) and the list of obstacles in body
     */
    @ApiOperation(value = "Recherche des obstacles avec une localisation",
        notes = "Récupère la liste des obstacles dans un cercle défini",
        response = List.class,
        responseContainer = "ResponseEntity")
    @GetMapping("/obstaclesNearLocation")
    @Timed
    public ResponseEntity<List<Obstacle>> getObstacleNearLocation(double latitude,double longitude, double radius) {

        log.info("Requete REST de recherche d'obstacle. lat:{},long:{}, rayon:{}",latitude,longitude,radius);
        List<Obstacle> obstacleListA = obstacleRepository.findByPointANear(
            new Point(longitude,latitude),
            new Distance(radius/1000, Metrics.KILOMETERS));

        List<Obstacle> obstacleListB = obstacleRepository.findByPointANear(
            new Point(longitude,latitude),
            new Distance(radius/1000, Metrics.KILOMETERS));

        List<Obstacle> obstacleListC = obstacleRepository.findByPointANear(
            new Point(longitude,latitude),
            new Distance(radius/1000, Metrics.KILOMETERS));

        List<Obstacle> obstacleListD = obstacleRepository.findByPointANear(
            new Point(longitude,latitude),
            new Distance(radius/1000, Metrics.KILOMETERS));

        List list1 = Utils.intersection(obstacleListA,obstacleListB);
        List list2 = Utils.intersection(obstacleListC,obstacleListD);
        List list = Utils.intersection(list1,list2);

        return new ResponseEntity<>(list, HttpStatus.OK);
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
