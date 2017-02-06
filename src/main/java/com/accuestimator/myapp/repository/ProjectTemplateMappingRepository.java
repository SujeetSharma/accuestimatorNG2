package com.accuestimator.myapp.repository;

import com.accuestimator.myapp.domain.ProjectTemplateMapping;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the ProjectTemplateMapping entity.
 */
@SuppressWarnings("unused")
public interface ProjectTemplateMappingRepository extends MongoRepository<ProjectTemplateMapping,String> {

}
