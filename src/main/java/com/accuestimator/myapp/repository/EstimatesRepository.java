package com.accuestimator.myapp.repository;

import com.accuestimator.myapp.domain.Estimates;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Estimates entity.
 */
@SuppressWarnings("unused")
public interface EstimatesRepository extends MongoRepository<Estimates,String> {

}
