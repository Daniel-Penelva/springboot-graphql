package com.api.springbootgraphql.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.api.springbootgraphql.entities.Student;
import com.api.springbootgraphql.service.IStudentService;

@Controller
public class GraphqlStudentController {

    @Autowired
    private IStudentService iStudentService;

    /* Acessar a interface gráfica do Graphiql: http://localhost:8080/graphiql */
    @QueryMapping(name = "findStudentById")
    public Student findById(@Argument(name = "studentId") String id) {
        Long studentId = Long.parseLong(id);
        return iStudentService.findById(studentId);
    }

    @QueryMapping(name = "findAllStudents")
    public List<Student> findAll() {
        return iStudentService.findAll();
    }

}
