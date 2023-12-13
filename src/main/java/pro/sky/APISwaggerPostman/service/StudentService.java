package pro.sky.APISwaggerPostman.service;



import pro.sky.APISwaggerPostman.model.Student;

import java.util.Collection;

public interface StudentService {
    Student createStudent(Student student);
   Student getStudent(long id);
    Student editStudent(Student student);
    Student removeStudent(long id);

    Collection<Student> getAllStudents();
    Collection<Student> findByAge(long age);
}
