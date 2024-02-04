package com.api.springbootgraphql.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.api.springbootgraphql.dto.StudentRequest;
import com.api.springbootgraphql.entities.Course;
import com.api.springbootgraphql.entities.Student;
import com.api.springbootgraphql.graphiql.InputStudent;
import com.api.springbootgraphql.service.ICourseService;
import com.api.springbootgraphql.service.IStudentService;

@Controller
public class GraphqlStudentController {

    @Autowired
    private IStudentService iStudentService;

    @Autowired
    private ICourseService iCourseService;

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

    /*  // Neste método, o Student é criado já associado ao Course (courseId).
    @MutationMapping
    public Student createStudent(@Argument InputStudent inputStudent) {
        
        Course course = iCourseService.findById(Long.parseLong(inputStudent.getCourseId()));

        Student student = new Student();
        student.setName(inputStudent.getName());
        student.setLastName(inputStudent.getLastName());
        student.setAge(inputStudent.getAge());
        student.setCourse(course);

        iStudentService.createStudent(student);
        return student;
    }
    */

    // Refatorando CreateStudent, neste método o Student pode ser criado associando ao Course ou não associando ao Course (courseId).
    @MutationMapping
    public Student createStudent(@Argument InputStudent inputStudent) {

        Student student = new Student();
        student.setName(inputStudent.getName());
        student.setLastName(inputStudent.getLastName());
        student.setAge(inputStudent.getAge());

        // Verifica se courseId não é nulo antes de associar o curso
        if (inputStudent.getCourseId() != null) {
            Course course = iCourseService.findById(Long.parseLong(inputStudent.getCourseId()));
            student.setCourse(course);
        }

        iStudentService.createStudent(student);
        return student;
    }

    // Cria a associação entre Student e Course
    @MutationMapping
    public String createStudentAssociationAndCourse(@Argument String studentId, @Argument String courseId){

        // Convertendo Student String para Long 
        Long student = Long.parseLong(studentId);

        // Convertendo Course String para Long 
        Long course = Long.parseLong(courseId);

        try {
            iStudentService.createAssociationStudentIncourse(student, course);
            // ou pode chamar esse método também: // iStudentService.associateStudentWithCourse(student, course);
            return "Student associated with Course successfully!";
        } catch (Exception e) {
            return "Failed to associate Student with Course. Reason: " + e.getMessage();
        }
        
    }

    @MutationMapping(name = "deleteStudentById")
    public String deleteById(@Argument(name = "studentId") String id) {
        iStudentService.deleteById(Long.parseLong(id));
        return "student with id " + id + " successfully deleted!";
    }

    @MutationMapping
    public Student updateStudent(@Argument(name = "studentId") String id,
            @Argument(name = "inputStudent") InputStudent inputStudent) {

        // Convertendo o id de String para Long do Student
        Long studentId = Long.parseLong(id);

        // Convertendo o id de String para Long do Course
        Course courseId = iCourseService.findById(Long.parseLong(inputStudent.getCourseId()));

        // Convertendo o InputString para Student
        Student student = new Student();
        student.setId(studentId);
        student.setName(inputStudent.getName());
        student.setLastName(inputStudent.getLastName());
        student.setAge(inputStudent.getAge());
        student.setCourse(courseId);

        // Chama o serviço para realizar a atualização do estudante
        Student updateStudent = iStudentService.updateStudent(studentId, student);

        // Retorna o estudante atualizado
        return updateStudent;
    }


    /* Exemplo Extra - para criar um estudante sem está associado ao curso (cursoId) */
    @MutationMapping
    public Student createStudentWithoutCourse(@Argument StudentRequest studentRequest){

        Student student = new Student();
        student.setName(studentRequest.name());
        student.setLastName(studentRequest.lastName());
        student.setAge(studentRequest.age());

        iStudentService.createStudentWithoutCourse(student);

        return student;
    }

}
