package fr.istic.sit.repository;

import fr.istic.sit.domain.Obstacle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data MongoDB repository for the Obstacle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ObstacleRepository extends MongoRepository<Obstacle, String> {

    List<Obstacle> findByPointANear(Point p, Distance d);

    List<Obstacle> findByPointBNear(Point p, Distance d);

    List<Obstacle> findByPointCNear(Point p, Distance d);

    List<Obstacle> findByPointDNear(Point p, Distance d);

}
