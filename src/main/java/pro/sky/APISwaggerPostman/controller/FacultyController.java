package pro.sky.APISwaggerPostman.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.APISwaggerPostman.model.Faculty;
import pro.sky.APISwaggerPostman.service.FacultyService;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService service;

    public FacultyController(FacultyService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFacultyInfo(@PathVariable Long id) {
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
    public ResponseEntity<Void> removeFacultyInfo(@PathVariable Long id) {
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
            return ResponseEntity.ok(service.findByColor(color));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }
}
