package com.api.springbootgraphql.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.springbootgraphql.entities.Student;

@Repository
public interface StudentDao extends CrudRepository<Student, Long> {

}
