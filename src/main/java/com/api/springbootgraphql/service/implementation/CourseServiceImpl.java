package com.api.springbootgraphql.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.springbootgraphql.entities.Course;
import com.api.springbootgraphql.persistence.CourseDao;
import com.api.springbootgraphql.service.ICourseService;

@Service
public class CourseServiceImpl implements ICourseService {

    @Autowired
    private CourseDao courseDao;

    @Override
    @Transactional(readOnly = true) // transação somente de leitura
    public Course findById(Long id) {
        return courseDao.findById(id).orElseThrow(() -> new RuntimeException("Id: " + id + " não encontrado!"));
    }

    @Override
    @Transactional(readOnly = true) // transação somente de leitura
    public List<Course> findAll() {
        return (List<Course>) courseDao.findAll();
    }

    @Override
    @Transactional
    public void createCourse(Course course) {
        courseDao.save(course);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        
        if (courseDao.existsById(id)) {
            courseDao.deleteById(id);
        } else {
            throw new RuntimeException("Id: " + id + " não encontrado, impossível excluir!");
        }

    }

    @Override
    @Transactional
    public Course updateCourse(Long id, Course updateCourse){

        Course existingCourse = findById(id);

        existingCourse.setName(updateCourse.getName());
        existingCourse.setCategory(updateCourse.getCategory());
        existingCourse.setTeacher(updateCourse.getTeacher());

        return courseDao.save(existingCourse);
    }

}
