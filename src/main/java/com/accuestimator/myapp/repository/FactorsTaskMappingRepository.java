package com.accuestimator.myapp.repository;

import com.accuestimator.myapp.domain.FactorsTaskMapping;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the FactorsTaskMapping entity.
 */
@SuppressWarnings("unused")
public interface FactorsTaskMappingRepository extends MongoRepository<FactorsTaskMapping,String> {

}
