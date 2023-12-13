package pro.sky.APISwaggerPostman.service.impl;

import org.springframework.stereotype.Service;
import pro.sky.APISwaggerPostman.model.Faculty;
import pro.sky.APISwaggerPostman.service.FacultyService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final HashMap<Long, Faculty> faculties = new HashMap<>();
    private long count = 0;

    @Override
    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(++count);
        faculties.put(count, faculty);
        return faculty;
    }

    @Override
    public Faculty getFaculty(long id) {
        return faculties.get(id);
    }

    @Override
    public Faculty editFaculty(Faculty faculty) {
        if (!faculties.containsKey(faculty.getId())) {
            return null;
        }
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    @Override
    public Faculty deleteFaculty(long id) {
        return faculties.remove(id);
    }

    @Override
    public Collection<Faculty> getAllFaculties() {
        return faculties.values();
    }

    @Override
    public Collection<Faculty> findByColor(String color) {
        ArrayList<Faculty> result = new ArrayList<>();
        for (Faculty faculty : faculties.values()) {
            if (Objects.equals(faculty.getColor(), color)) {
                result.add(faculty);
            }

        }
        return result;
    }
}
