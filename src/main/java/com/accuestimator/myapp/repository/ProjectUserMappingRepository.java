package com.accuestimator.myapp.repository;

import com.accuestimator.myapp.domain.ProjectUserMapping;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
/**
 * Spring Data MongoDB repository for the ProjectUserMapping entity.
 */
@SuppressWarnings("unused")
public interface ProjectUserMappingRepository extends MongoRepository<ProjectUserMapping,String> {

     List<ProjectUserMapping> findOneByUserid(String login);

}
