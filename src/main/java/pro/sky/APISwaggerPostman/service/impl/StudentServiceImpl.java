package pro.sky.APISwaggerPostman.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.APISwaggerPostman.model.Faculty;
import pro.sky.APISwaggerPostman.model.Student;
import pro.sky.APISwaggerPostman.repository.StudentRepository;
import pro.sky.APISwaggerPostman.service.StudentService;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;


@Service
public class StudentServiceImpl implements StudentService {
    Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    @Autowired
    private final StudentRepository studentRepository;


    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    @Override
    public Student createStudent(Student student) {

        logger.info("Was invoked method for create student");
        return studentRepository.save(student);
    }

    @Override
    public Student getStudent(long id) {
        logger.info("Was invoked method for get student by id: {}", id);
        return studentRepository.findById(id).get();
    }

    @Override
    public Student editStudent(Student student) {
        logger.info("Was invoked method for update student by id {}", student.getId());
        return studentRepository.save(student);
    }

    @Override
    public void removeStudent(long id) {
        logger.warn("Was invoked method for remove student by id {}", id);
        studentRepository.deleteById(id);
    }

    @Override
    public Collection<Student> getAllStudents() {
        logger.info("Was invoked method for searching students");
        return studentRepository.findAll();
    }

    @Override
    public Collection<Student> findAllByAge(int age) {
        logger.info("Was invoked method for searching student by age: {}", age);
        return studentRepository.findAllByAge(age);
    }

    @Override
    public Collection<Student> findByAgeBetween(int min, int max) {
        logger.info("Was invoked method for searching student between min {} and max {}", min, max);
        return studentRepository.findByAgeBetween(min, max);
    }

    @Override
    public Collection<Student> findAllByFaculty_id(long facultyId) {
        logger.info("Was invoked method for getting all students by faculty id {}", facultyId);
        return studentRepository.findAllByFaculty_id(facultyId);
    }

    @Override
    public Faculty getFacultyByStudentId(long studentId) {
        logger.info("Was invoked method for getting faculty by student id {}", studentId);
        return getStudent(studentId).getFaculty();
    }

    @Override
    public Integer getCountAllStudents() {
        logger.info("Was invoked method for counting all students");
        return studentRepository.getCountAllStudents();
    }

    @Override
    public Double getAverageAgeOfStudents() {
        logger.info("Was invoked method for counting average age of students");
        return studentRepository.getAverageAgeOfStudents();
    }

    @Override
    public Collection<Student> getLastFiveStudents() {
        logger.info("Was invoked method for getting only last five students");
        return studentRepository.getLastFiveStudents();
    }

    @Override
    public Collection<Student> getAllStudentsNameStartsWithA() {
        logger.info("Was invoked method for getting all students whose name starts with A");
        return studentRepository.findAll().stream()
                .sorted(Comparator.comparing(Student::getName)).
                filter(student -> student.getName().startsWith("A"))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Double averageAgeOfStudents() {
        logger.info("Was invoked method for getting the average age of students");
        return studentRepository.findAll().stream()
                .mapToDouble(Student::getAge).average().getAsDouble();
    }

    @Override
    public void getStudentNamesParallel() {
        printNames(1);
        printNames(2);

        new Thread(() -> {
            printNames(3);
            printNames(4);
        }).start();

        new Thread(() -> {
            printNames(5);
            printNames(6);
        }).start();

    }

    private void printNames(long id) {
        String studentName = getStudent(id).getName();
        System.out.println(studentName + " id= " + id);
    }

    @Override
    public void getStudentNamesSync() {

    }


}
