package com.accuestimator.myapp.repository;

import com.accuestimator.myapp.domain.Project;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Project entity.
 */
@SuppressWarnings("unused")
public interface ProjectRepository extends MongoRepository<Project,String> {

}
