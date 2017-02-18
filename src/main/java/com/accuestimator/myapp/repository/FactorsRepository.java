package com.accuestimator.myapp.repository;

import com.accuestimator.myapp.domain.Factors;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
/**
 * Spring Data MongoDB repository for the Factors entity.
 */
@SuppressWarnings("unused")
public interface FactorsRepository extends MongoRepository<Factors,String> {
     
     List<Factors> findOneByCategory(String catId);

}
