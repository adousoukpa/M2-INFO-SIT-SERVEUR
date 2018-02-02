package fr.istic.sit.repository;

import fr.istic.sit.domain.Localisation;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Localisation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocalisationRepository extends MongoRepository<Localisation, String> {

}
