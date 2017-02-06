package com.accuestimator.myapp.repository;

import com.accuestimator.myapp.domain.Factors;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Factors entity.
 */
@SuppressWarnings("unused")
public interface FactorsRepository extends MongoRepository<Factors,String> {

}
