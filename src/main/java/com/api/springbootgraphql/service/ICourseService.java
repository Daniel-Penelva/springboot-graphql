package com.api.springbootgraphql.service;

import java.util.List;

import com.api.springbootgraphql.entities.Course;

public interface ICourseService {

    Course findById(Long id);

    List<Course> findAll();

    void createCourse(Course course);

    void deleteById(long id);

    Course updateCourse(Long id, Course updateCourse);
}
