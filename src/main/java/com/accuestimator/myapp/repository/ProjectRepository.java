package com.accuestimator.myapp.repository;

import com.accuestimator.myapp.domain.Project;
import com.accuestimator.myapp.domain.ProjectUserMapping;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Project entity.
 */
@SuppressWarnings("unused")
public interface ProjectRepository extends MongoRepository<Project,String> {
	
	 List<Project> findByid(String id);

}
