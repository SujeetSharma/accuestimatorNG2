package com.accuestimator.myapp.repository;

import com.accuestimator.myapp.domain.TaskCategory;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the TaskCategory entity.
 */
@SuppressWarnings("unused")
public interface TaskCategoryRepository extends MongoRepository<TaskCategory,String> {

}
