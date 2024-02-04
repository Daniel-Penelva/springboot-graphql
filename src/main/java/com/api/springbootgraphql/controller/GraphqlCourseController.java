package com.api.springbootgraphql.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.api.springbootgraphql.entities.Course;
import com.api.springbootgraphql.graphiql.InputCourse;
import com.api.springbootgraphql.service.ICourseService;

@Controller
public class GraphqlCourseController {

    @Autowired
    private ICourseService iCourseService;

    /* Acessar a interface gráfica do Graphiql: http://localhost:8080/graphiql */
    @QueryMapping(name = "findCourseById")
    public Course findById(@Argument(name = "courseId") String id) {
        Long courseId = Long.parseLong(id);
        return iCourseService.findById(courseId);
    }

    @QueryMapping(name = "findAllCourses")
    public List<Course> findall() {
        return iCourseService.findAll();
    }

    @MutationMapping
    public Course createCourse(@Argument InputCourse inputCourse) {

        Course course = new Course();
        course.setName(inputCourse.getName());
        course.setCategory(inputCourse.getCategory());
        course.setTeacher(inputCourse.getTeacher());

        iCourseService.createCourse(course);
        return course;

    }

    @MutationMapping(name = "deleteCourseById")
    public String deleteById(@Argument(name = "courseId") String id) {

        iCourseService.deleteById(Long.parseLong(id));
        return "course with id " + id + " successfully deleted!";
    }

    @MutationMapping
    public Course updateCourse(@Argument(name = "courseId") String id, @Argument InputCourse inputCourse) {

        // Converter o id String para Long
        Long courseId = Long.parseLong(id);

        // Convertendo InputCourse para Course
        Course course = new Course();
        course.setId(courseId);
        course.setName(inputCourse.getName());
        course.setCategory(inputCourse.getCategory());
        course.setTeacher(inputCourse.getTeacher());

        iCourseService.createCourse(course);

        return course;
    }
}
