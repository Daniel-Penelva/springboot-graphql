package com.api.springbootgraphql.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.springbootgraphql.entities.Course;
import com.api.springbootgraphql.entities.Student;
import com.api.springbootgraphql.persistence.CourseDao;
import com.api.springbootgraphql.persistence.StudentDao;
import com.api.springbootgraphql.service.IStudentService;

@Service
public class StudentServiceImpl implements IStudentService {

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private CourseDao courseDao;

    @Override
    @Transactional(readOnly = true) // usada para otimizar consultas de leitura, indicando que a transação envolve apenas operações de leitura e não faz modificações no banco de dados.
    public Student findById(Long id) {
        return studentDao.findById(id).orElseThrow(() -> new RuntimeException("Id: " + id + " não encontrado!"));
    }

    @Override
    @Transactional(readOnly = true) // transação somente de leitura
    public List<Student> findAll() {
        return (List<Student>) studentDao.findAll();
    }

    @Override
    @Transactional // A anotação @Transactional sem readOnly assume que a transação pode envolver operações de leitura e escrita.
    public void createStudent(Student student) {
        studentDao.save(student);
    }

    @Override
    @Transactional
    public void deleteById(long id) {

        if (studentDao.existsById(id)) {
            studentDao.deleteById(id);
        } else {
            throw new RuntimeException("Id: " + id + " não encontrado, impossível excluir!");
        }
    }

    @Override
    @Transactional
    public Student updateStudent(Long id, Student updateStudent) {

        /*
         * Pode fazer assim ou chamando pelo método findById(): 
         * Student existingStudent = studentDao.findById(id).orElseThrow(() -> new RuntimeException("Id: " + id + " não encontrado!"));
         */
        Student existingStudent = findById(id);

        // Atualiza os valores do estudante existente com os valores do objeto 'updatedStudent'
        existingStudent.setName(updateStudent.getName());
        existingStudent.setLastname(updateStudent.getLastname());
        existingStudent.setAge(updateStudent.getAge());

        // Salva o estudante existente no banco de dados
        return studentDao.save(updateStudent);
    }

    @Override
    @Transactional
    public void createAssociationStudentIncourse(Long idStudent, Long idCourse) {

        // Busca o estudante e curso pelo ID
        Student existingStudent = findById(idStudent);
        Course existingCourse = courseDao.findById(idCourse)
                .orElseThrow(() -> new RuntimeException("Id: " + idCourse + " não encontrado!"));

        existingStudent.setCourse(existingCourse); // Associa o estudante ao curso

        studentDao.save(existingStudent); // Salva as alterações no banco de dados

    }

    /* Outra forma de fazer utilizando um tipo boolean, fiz somente para adquirir mais aprendizagem: */
    @Override
    @Transactional
    public boolean associateStudentWithCourse(Long idStudent, Long idCourse) {
        try {
            // Busca o estudante pelo ID
            Student existingStudent = findById(idStudent);

            // Busca o curso pelo ID
            Course existingCourse = courseDao.findById(idCourse)
                    .orElseThrow(() -> new RuntimeException("Id: " + idCourse + " não encontrado!"));

            // Associa o estudante ao curso
            existingStudent.setCourse(existingCourse);

            // Salva as alterações no banco de dados
            studentDao.save(existingStudent);

            return true; // Indica que a associação foi bem-sucedida
        } catch (Exception e) {
            e.printStackTrace(); // Lida com a exceção (pode ser melhorar o tratamento dependendo do caso)
            return false; // Indica que a associação falhou
        }
    }

}

/* Bom saber:
Vale ressaltar que em termos de transações em bancos de dados e do ponto de vista do Hibernate/JPA, as operações de leitura e escrita têm 
significados específicos:

1. Operações de Leitura:
    - Métodos que leem dados do banco de dados, mas não modificam nada.
    - Exemplos: `findById`, `findBy...`, consultas de leitura.

2. Operações de Escrita:
    - Métodos que modificam dados no banco de dados.
    - Exemplos: `save`, `update`, `delete`, qualquer operação que modifica o estado persistente.

Então, para esclarecer:

- Criar (Insert): É considerada uma operação de escrita, pois está inserindo novos dados no banco de dados.

- Deletar (Delete): É uma operação de escrita, pois está removendo dados do banco de dados.

- Alterar (Update): Também é uma operação de escrita, pois está modificando dados existentes no banco de dados.

- Listar e Buscar: São operações de leitura, pois está consultando dados existentes, mas não os modificando.

A anotação `@Transactional(readOnly = true)` é geralmente usada para otimizar consultas de leitura, indicando que a transação envolve apenas 
operações de leitura e não faz modificações no banco de dados. No entanto, listar uma lista ou buscar um valor são geralmente considerados 
operações de leitura, não de escrita. Isso pode variar um pouco dependendo do contexto e das implementações específicas, mas a distinção geral 
entre leitura e escrita permanece.

Portanto, a anotação @Transactional sem readOnly assume que a transação pode envolver operações de leitura e escrita. Já a anotação @Transaction
com o "readOnly=true" é usada para otimizar consultas de leitura, indicando que a transação envolve apenas operações de leitura e não faz 
modificações no banco de dados.

*/