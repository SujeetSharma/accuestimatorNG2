package com.accuestimator.myapp.repository;

import com.accuestimator.myapp.domain.Values;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Values entity.
 */
@SuppressWarnings("unused")
public interface ValuesRepository extends MongoRepository<Values,String> {

}
