package com.accuestimator.myapp.repository;

import com.accuestimator.myapp.domain.Template;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Template entity.
 */
@SuppressWarnings("unused")
public interface TemplateRepository extends MongoRepository<Template,String> {

}
