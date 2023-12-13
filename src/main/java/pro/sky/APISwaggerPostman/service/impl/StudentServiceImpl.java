package pro.sky.APISwaggerPostman.service.impl;


import org.springframework.stereotype.Service;
import pro.sky.APISwaggerPostman.model.Student;
import pro.sky.APISwaggerPostman.service.StudentService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


@Service
public class StudentServiceImpl implements StudentService {
    private final HashMap<Long, Student> students = new HashMap<>();
    private long count = 0;

    @Override
    public Student createStudent(Student student) {
        student.setId(++count);
        students.put(count, student);
        return student;
    }

    @Override
    public Student getStudent(long id) {
        return students.get(id);
    }

    @Override
    public Student editStudent(Student student) {
        if (!students.containsKey(student.getId())) {
            return null;
        }
        students.put(student.getId(), student);
        return student;
    }

    @Override
    public Student removeStudent(long id) {
        return students.remove(id);
    }

    @Override
    public Collection<Student> getAllStudents() {
        return students.values();
    }

    @Override
    public Collection<Student> findByAge(long age) {
        ArrayList<Student> result = new ArrayList<>();
        for (Student student : students.values()) {
            if (student.getAge() == age) {
                result.add(student);
            }
        }
        return result;
    }
}
