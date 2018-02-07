package fr.istic.sit.repository;

import fr.istic.sit.domain.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Mission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MissionRepository extends MongoRepository<Mission, String> {

    // FIXME : Ne marche pas pour le moment
    @Query(fields="{ 'title' : 1, 'dateBegin' : 1,'dateEnd' : 1}")
    Page<Mission> findAllOrderByDateBegin(Pageable pageable);
}
