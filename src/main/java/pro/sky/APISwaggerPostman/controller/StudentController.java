package pro.sky.APISwaggerPostman.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long id) {
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
    public ResponseEntity<Void> removeStudentInfo(@PathVariable Long id) {
        service.removeStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getAllStudents() {
        return ResponseEntity.ok(service.getAllStudents());
    }

    @GetMapping("age")
    public ResponseEntity<Collection<Student>> getByAgeStudent(@RequestParam(required = false) int age) {
        if (age > 0) {
            return ResponseEntity.ok(service.findAllByAge(age));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

}
