package pro.sky.APISwaggerPostman.service;

import pro.sky.APISwaggerPostman.model.Faculty;

import java.util.Collection;

public interface FacultyService {

    Faculty createFaculty(Faculty faculty);
    Faculty getFaculty(long id);
    Faculty editFaculty(Faculty faculty);
    Faculty deleteFaculty(long id);

    Collection<Faculty> getAllFaculties();
    Collection<Faculty> findByColor(String color);

}
