package com.api.springbootgraphql.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.springbootgraphql.entities.Course;

@Repository
public interface CourseDao extends CrudRepository<Course, Long>{
    
}
