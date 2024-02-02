package com.api.springbootgraphql.service;

import java.util.List;

import com.api.springbootgraphql.entities.Student;

public interface IStudentService {

    Student findById(Long id);

    List<Student> findAll();

    void createStudent(Student student);

    void deleteById(long id);

    Student updateStudent(Long id, Student updateStudent);

    void createAssociationStudentIncourse(Long idStudent, Long idCourse);

    boolean associateStudentWithCourse(Long idStudent, Long idCourse);

}
