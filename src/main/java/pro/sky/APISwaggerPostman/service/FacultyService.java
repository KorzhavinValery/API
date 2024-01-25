package pro.sky.APISwaggerPostman.service;

import org.springframework.http.ResponseEntity;
import pro.sky.APISwaggerPostman.model.Faculty;

import java.util.Collection;

public interface FacultyService {

    Faculty createFaculty(Faculty faculty);
    Faculty getFaculty(long id);
    Faculty editFaculty(Faculty faculty);
  void deleteFaculty(long id);

    Collection<Faculty> getAllFaculties();
    Collection<Faculty> findAllByColor(String color);
    Collection<Faculty> findByNameContainsIgnoreCase(String name);

    ResponseEntity<String> getFacultyWithMaxLength();


}
