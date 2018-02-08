package fr.istic.sit.repository;

import fr.istic.sit.domain.Obstacle;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Obstacle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ObstacleRepository extends MongoRepository<Obstacle, String> {

}
