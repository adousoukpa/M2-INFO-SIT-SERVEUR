package fr.istic.sit.repository;

import fr.istic.sit.domain.Mission;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Mission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MissionRepository extends MongoRepository<Mission, String> {

}
