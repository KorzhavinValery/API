package pro.sky.APISwaggerPostman.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.APISwaggerPostman.model.Faculty;
import pro.sky.APISwaggerPostman.model.Student;
import pro.sky.APISwaggerPostman.service.impl.StudentServiceImpl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentServiceImpl service;

    public StudentController(StudentServiceImpl service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentInfo(@PathVariable long id) {
        Student student = service.getStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return service.createStudent(student);
    }

    @PutMapping
    public ResponseEntity<Student> editStudentInfo(@RequestBody Student student) {
        Student editStudent = service.editStudent(student);
        if (editStudent == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(editStudent);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeStudentInfo(@PathVariable long id) {
        service.removeStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getAllStudents() {
        return ResponseEntity.ok(service.getAllStudents());
    }

    @GetMapping("facultyId")
    public ResponseEntity<Collection<Student>> getAllStudentsByFacultyId(@RequestParam(required = false) long facultyId) {
        if (facultyId > 0) {
            return ResponseEntity.ok(service.findAllByFaculty_id(facultyId));
        }

        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("age")
    public ResponseEntity<Collection<Student>> getByAgeStudent(@RequestParam(required = false) int age) {
        if (age > 0) {
            return ResponseEntity.ok(service.findAllByAge(age));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("age/between")
    public ResponseEntity<Collection<Student>> getByAgeBetweenStudent(@RequestParam(required = false) int min,
                                                                      @RequestParam(required = false) int max) {
        if (min > 0 && max < Integer.MAX_VALUE) {
            return ResponseEntity.ok(service.findByAgeBetween(min, max));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/getFacultyByStudentId")
    public ResponseEntity<Faculty> getFacultyByIdStudent(@RequestParam long studentId) {
        Faculty faculty = service.getFacultyByStudentId(studentId);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }
    @GetMapping("/getCountAllStudents")
    Integer getCountAllStudents() {
        return service.getCountAllStudents();
    }

    @GetMapping("/getAverageAgeOfStudents")
    Double getAverageAgeOfStudents() {
        return service.getAverageAgeOfStudents();
    }

    @GetMapping("/getLastFiveStudents")
    Collection<Student> getLastFiveStudents() {
        return service.getLastFiveStudents();
    }

    @GetMapping ("/studentsNameStartsWithA")
    Collection<Student> getStudentsNamesStartWithA() {
        return service.getAllStudentsNameStartsWithA();
    }

    @GetMapping("/averageAge")
    Double getAverageAge() {
        return service.averageAgeOfStudents();
    }
}
