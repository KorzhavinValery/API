package pro.sky.APISwaggerPostman.controller;

import org.apache.el.stream.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pro.sky.APISwaggerPostman.model.Faculty;
import pro.sky.APISwaggerPostman.model.Student;
import pro.sky.APISwaggerPostman.repository.FacultyRepository;
import pro.sky.APISwaggerPostman.repository.StudentRepository;

import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerRestTemplateTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private FacultyRepository facultyRepository;

    private String baseUrl;
    private Student student = new Student(1L, "Garik", 20);
    private Faculty faculty = new Faculty(1L, "griffindor", "yellow");

    @BeforeEach
    public void beforeEach() {
        studentRepository.deleteAll();

        baseUrl = "http://localhost:" + port + "/student";

    }

    @Test
    public void shouldCreateStudent() {
        ResponseEntity<Student> result = testRestTemplate.postForEntity(
                baseUrl,
                student,
                Student.class
        );
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(student, result.getBody());
    }

    @Test
    public void shouldGetStudent() {
        Student getStudent = studentRepository.save(student);
        ResponseEntity<Student> result = testRestTemplate.getForEntity(
                baseUrl + "/" + getStudent.getId(),
                Student.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(getStudent, result.getBody());
    }

    @Test
    public void shouldUpdateStudent() {
        Student getStudent = studentRepository.save(student);
        ResponseEntity<Student> result = testRestTemplate.exchange(
                baseUrl,
                HttpMethod.PUT,
                new HttpEntity<>(getStudent),
                Student.class
        );
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(getStudent, result.getBody());
    }

    @Test
    public void shouldDeleteStudent() {
        Student getStudent = studentRepository.save(student);

        ResponseEntity<Student> result = testRestTemplate.exchange(
                baseUrl + "/1",
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Student.class
        );

                assertThat(studentRepository.findById(1L)).isEmpty();

    }

    @Test
    public void shouldFindByAgeStudent() {
        Student student1 = new Student(2L, "Germiona", 15);
        Student student2 = new Student(2L, "Ron", 15);
        Student getStudent = studentRepository.save(student1);
        Student getStudent1 = studentRepository.save(student2);

        ResponseEntity<List<Student>> result = testRestTemplate.exchange(
                baseUrl + "/age?age=" + getStudent.getAge(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(List.of(getStudent, getStudent1), result.getBody());
    }

    @Test
    public void shouldFindByAgeBetweenStudents() {

        Student student1 = new Student(1L, "newbee", 10);
        Student student2 = new Student(2L, "newbee1", 14);
        Student student3 = new Student(3L, "newbee2", 19);
        Student student4 = new Student(4L, "newbee3", 9);

        Student getStudent1 = studentRepository.save(student1);
        Student getStudent2 = studentRepository.save(student2);
        Student getStudent3 = studentRepository.save(student3);
        Student getStudent4 = studentRepository.save(student4);


        List<Student> students = List.of(getStudent2, getStudent3);


        ResponseEntity<List<Student>> result = testRestTemplate.exchange(
                baseUrl + "/age/between?min=" + getStudent2.getAge() + "&max=" + getStudent3.getAge(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(students, result.getBody());
    }

    @Test
    public void shouldGetFacultyByStudentId() {
        Faculty getFaculty = facultyRepository.save(faculty);
        student.setFaculty(getFaculty);

        Student saveStudent = studentRepository.save(student);


        ResponseEntity<Faculty> result = testRestTemplate.getForEntity(
                baseUrl + "/getFacultyByStudentId?studentId=" + saveStudent.getId(),
                Faculty.class
        );
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(getFaculty, result.getBody());

    }

    @Test
   public void shouldFindAllStudentsInFacultyById() {
        Faculty getFaculty =facultyRepository.save(faculty);
        Student student1 = new Student(2L, "Newbee", 20);
        Student student2 = new Student(3L, "Newbee1", 17);
        Student student3 = new Student(4L, "Newbee2", 18);


        student2.setFaculty(faculty);
        student3.setFaculty(faculty);


        Student getStudent2 = studentRepository.save(student2);
        Student getStudent3 = studentRepository.save(student3);

        List<Student> students =List.of(getStudent2, getStudent3);


        ResponseEntity<List<Student>> result = testRestTemplate.exchange(
                baseUrl+"/facultyId?facultyId=" +getStudent2.getId(),
                HttpMethod.GET,
                null,
                new  ParameterizedTypeReference<>() {}
        );

        assertEquals(HttpStatus.OK,result.getStatusCode());
        assertEquals(students,result.getBody());
    }

}
