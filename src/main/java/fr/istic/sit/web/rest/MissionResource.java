package fr.istic.sit.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.istic.sit.domain.Location;
import fr.istic.sit.domain.Mission;

import fr.istic.sit.domain.Order;
import fr.istic.sit.repository.MissionRepository;
import fr.istic.sit.web.rest.errors.BadRequestAlertException;
import fr.istic.sit.web.rest.util.HeaderUtil;
import fr.istic.sit.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Mission.
 */
@RestController
@RequestMapping("/api")
public class MissionResource {

    private final Logger log = LoggerFactory.getLogger(MissionResource.class);

    private static final String ENTITY_NAME = "mission";

    private final MissionRepository missionRepository;

    public MissionResource(MissionRepository missionRepository) {
        this.missionRepository = missionRepository;
    }

    /**
     * POST  /missions : Create a new mission.
     *
     * @param mission the mission to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mission, or with status 400 (Bad Request) if the mission has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @ApiOperation(value = "Création d'une nouvelle mission",
        notes = "Création d'une nouvelle mission avec en retour d'opération la mission ainsi créée",
        response = Mission.class,
        responseContainer = "ResponseEntity")
    @PostMapping("/missions")
    @Timed
    public ResponseEntity<Mission> createMission(@RequestBody Mission mission) throws URISyntaxException {
        log.debug("REST request to save Mission : {}", mission);
        if (mission.getId() != null) {
            throw new BadRequestAlertException("A new mission cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Mission result = missionRepository.save(mission);
        return ResponseEntity.created(new URI("/api/missions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /missions : Updates an existing mission.
     *
     * @param mission the mission to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mission,
     * or with status 400 (Bad Request) if the mission is not valid,
     * or with status 500 (Internal Server Error) if the mission couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @ApiOperation(value = "Mise à jour d'une mission",
        notes = "Mise à jour d'une mission avec en retour d'opération la mission mise à jour",
        response = Mission.class,
        responseContainer = "ResponseEntity")
    @PutMapping("/missions")
    @Timed
    public ResponseEntity<Mission> updateMission(@RequestBody Mission mission) throws URISyntaxException {
        log.debug("REST request to update Mission : {}", mission);
        if (mission.getId() == null) {
            return createMission(mission);
        }
        Mission result = missionRepository.save(mission);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mission.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /missions/:id/orderMove : Crée un ordre de mission pour déplacer le drone sans photo
     *
     * @param id : id de la mission a modifier
     * @param location : localisation a ajouter
     * @return the ResponseEntity with status 200 (OK) and with body the updated mission,
     * or with status 400 (Bad Request) if the mission is not valid,
     * or with status 500 (Internal Server Error) if the mission couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @ApiOperation(value = "Crée un ordre de mission pour déplacer le drone sans photo",
        notes = "Crée un ordre de mission pour déplacer le drone sans photo. " +
        "La localisation cible est donné dans le corps de la requête. " +
        "Retourne la mission mise à jour en paramètre",
        response = Mission.class,
        responseContainer = "ResponseEntity")
    @PutMapping("/missions/{id}/orderMove")
    @Timed
        public ResponseEntity<Mission> orderMove(@PathVariable String id,@RequestBody Location location) throws URISyntaxException {
        log.debug("REST request : Crée un ordre de mission à la mission d'id {}. Déplacement de la mission {} ", location, id);
        log.trace("Récupération de la mission");
        Mission mission = missionRepository.findOne(id);
        if(mission == null){
            throw new BadRequestAlertException("La mission est introuvable. L'id donné en paramètre ne permet pas de récupérer une mission",ENTITY_NAME,"idnoref");
        }

        mission.addOrder(constructOrderWithLocation(location,false));

        Mission result = missionRepository.save(mission);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mission.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /missions/:id/orderMovePicture : Crée un ordre de mission pour déplacer le drone avec photo
     *
     * @param id : id de la mission a modifier
     * @param location : localisation a ajouter
     * @return the ResponseEntity with status 200 (OK) and with body the updated mission,
     * or with status 400 (Bad Request) if the mission is not valid,
     * or with status 500 (Internal Server Error) if the mission couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @ApiOperation(value = "Crée un ordre de mission pour déplacer le drone avec photo",
        notes = "Crée un ordre de mission pour déplacer le drone avec photo. " +
            "La localisation cible est donné dans le corps de la requête. " +
            "Retourne la mission mise à jour en paramètre",
        response = Mission.class,
        responseContainer = "ResponseEntity")
    @PutMapping("/missions/{id}/orderMovePicture")
    @Timed
    public ResponseEntity<Mission> orderMovePicture(@PathVariable String id,@RequestBody Location location) throws URISyntaxException {
        log.debug("REST request : Crée un ordre de mission à la mission d'id {}. Déplacement de la mission {} ", location, id);
        log.trace("Récupération de la mission");
        Mission mission = missionRepository.findOne(id);
        if(mission == null){
            throw new BadRequestAlertException("La mission est introuvable. L'id donné en paramètre ne permet pas de récupérer une mission",ENTITY_NAME,"idnoref");
        }

        mission.addOrder(constructOrderWithLocation(location,true));

        Mission result = missionRepository.save(mission);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mission.getId().toString()))
            .body(result);
    }

    /**
     * Construit un order avec une localisation avec les infos necessaires
     * @param location
     * @param withPicture
     * @return
     */
    private Order constructOrderWithLocation(Location location, boolean withPicture){
        if(location==null){
            throw new BadRequestAlertException("La localisation transmise à la requête est null",ENTITY_NAME,"location-not-found");
        }

        //Construction de l'ordre avec la localisation transmise
        Order order = new Order(location,withPicture, LocalDateTime.now());
        return order;
    }

    /**
     * GET  /missions : get all the missions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of missions in body
     */
    @GetMapping("/missions")
    @Timed
    @ApiOperation(value = "Retourne l'intégralité des missions",
        notes = "Les missions retournées par la requête ne comporte pas de localisation pour pas" +
            "surcharger le corps de la réponse",
        response = Mission.class,
        responseContainer = "ResponseEntity")
    public ResponseEntity<List<Mission>> getAllMissions(Pageable pageable) {
        // TODO : Enlever les localisations du retour
        log.debug("REST request to get a page of Missions");
        Page<Mission> page = missionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/missions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /missions/:id : get the "id" mission.
     *
     * @param id the id of the mission to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mission, or with status 404 (Not Found)
     */
    @ApiOperation(value = "Retourne la mission avec l'ID spécifié",
        notes = "La mission retournée comporte les localisations demandées.",
        response = Mission.class,
        responseContainer = "ResponseEntity")
    @GetMapping("/missions/{id}")
    @Timed
    public ResponseEntity<Mission> getMission(@PathVariable String id) {
        log.debug("REST request to get Mission : {}", id);
        Mission mission = missionRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mission));
    }

    /**
     * DELETE  /missions/:id : delete the "id" mission.
     *
     * @param id the id of the mission to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @ApiOperation(value = "Supprime la mission avec l'ID de mission spécifié",
        notes = "La requête ne retourne rien, le code de statut de réponse permet de " +
            "déterminer si la mission a bien été supprimée.",
        response = Mission.class,
        responseContainer = "ResponseEntity")
    @DeleteMapping("/missions/{id}")
    @Timed
    public ResponseEntity<Void> deleteMission(@PathVariable String id) {
        log.debug("REST request to delete Mission : {}", id);
        missionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
