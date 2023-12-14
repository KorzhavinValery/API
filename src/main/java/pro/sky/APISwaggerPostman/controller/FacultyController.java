package pro.sky.APISwaggerPostman.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.APISwaggerPostman.model.Faculty;
import pro.sky.APISwaggerPostman.service.impl.FacultyServiceImpl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyServiceImpl service;

    public FacultyController(FacultyServiceImpl service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFacultyInfo(@PathVariable long id) {
        Faculty faculty = service.getFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return service.createFaculty(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> editFacultyInfo(@RequestBody Faculty faculty) {
        Faculty editFaculty = service.editFaculty(faculty);
        if (editFaculty == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(editFaculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeFacultyInfo(@PathVariable long id) {
        service.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> getAllFaculties() {
        return ResponseEntity.ok(service.getAllFaculties());
    }

    @GetMapping ("color")
    public ResponseEntity<Collection<Faculty>> findByColorFaculty(@RequestParam(required = false)  String color) {
        if (color != null && !color.isBlank()) {
            return ResponseEntity.ok(service.findAllByColor(color));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }
}
