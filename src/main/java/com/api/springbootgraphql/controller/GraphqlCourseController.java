package com.api.springbootgraphql.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.api.springbootgraphql.entities.Course;
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
}
