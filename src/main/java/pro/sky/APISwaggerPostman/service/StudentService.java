package pro.sky.APISwaggerPostman.service;



import pro.sky.APISwaggerPostman.model.Faculty;
import pro.sky.APISwaggerPostman.model.Student;

import java.util.Collection;

public interface StudentService {
    Student createStudent(Student student);
   Student getStudent(long id);
    Student editStudent(Student student);
    void removeStudent(long id);

    Collection<Student> getAllStudents();
    Collection<Student> findAllByAge(int age);
    Collection<Student> findByAgeBetween(int min, int max);
    Collection<Student> findAllByFaculty_id(long facultyId);
    Faculty getFacultyByStudentId(long studentId);

}
