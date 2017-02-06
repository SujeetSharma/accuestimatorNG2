package com.accuestimator.myapp.repository;

import com.accuestimator.myapp.domain.FactorCategory;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the FactorCategory entity.
 */
@SuppressWarnings("unused")
public interface FactorCategoryRepository extends MongoRepository<FactorCategory,String> {

}
