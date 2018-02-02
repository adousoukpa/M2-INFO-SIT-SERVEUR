package fr.istic.sit.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.istic.sit.domain.Localisation;
import fr.istic.sit.domain.Mission;

import fr.istic.sit.repository.MissionRepository;
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
     * PUT  /missions/:id/addLocalisation : Ajoute une localisation à la mission passée en paramètre
     *
     * @param id : id de la mission a modifier
     * @param localisation : localisation a ajouter
     * @return the ResponseEntity with status 200 (OK) and with body the updated mission,
     * or with status 400 (Bad Request) if the mission is not valid,
     * or with status 500 (Internal Server Error) if the mission couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/missions/{id}/addLocalisation")
    @Timed
    public ResponseEntity<Mission> addLocalisation(@PathVariable String id,@RequestBody Localisation localisation) throws URISyntaxException {
        log.debug("REST request : Ajout de la localisation {} à la mission d'id {}", localisation, id);
        log.trace("Récupération de la mission");
        Mission mission = missionRepository.findOne(id);
        if(mission == null){
            throw new BadRequestAlertException("La mission est introuvable. L'id donné en paramètre ne permet pas de récupérer une mission",ENTITY_NAME,"idnoref");
        }
        if(localisation.getId()!=null){
            throw new BadRequestAlertException("Une localisation avec un id non null ne peut être ajoutée à une mission",ENTITY_NAME,"idexists");
        }
        mission.addLocalisation(localisation);
        Mission result = missionRepository.save(mission);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mission.getId().toString()))
            .body(result);
    }

    /**
     * GET  /missions : get all the missions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of missions in body
     */
    @GetMapping("/missions")
    @Timed
    public ResponseEntity<List<Mission>> getAllMissions(Pageable pageable) {
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
    @DeleteMapping("/missions/{id}")
    @Timed
    public ResponseEntity<Void> deleteMission(@PathVariable String id) {
        log.debug("REST request to delete Mission : {}", id);
        missionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
