package com.accuestimator.myapp.repository;

import com.accuestimator.myapp.domain.Projects;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Projects entity.
 */
@SuppressWarnings("unused")
public interface ProjectsRepository extends MongoRepository<Projects,String> {

}
