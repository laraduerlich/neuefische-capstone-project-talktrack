package org.example.backend.repo;

import org.example.backend.model.Summary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummaryRepo extends MongoRepository<Summary, String> {

}
