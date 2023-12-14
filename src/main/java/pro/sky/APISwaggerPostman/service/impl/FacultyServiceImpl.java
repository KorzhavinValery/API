package pro.sky.APISwaggerPostman.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.APISwaggerPostman.model.Faculty;
import pro.sky.APISwaggerPostman.repository.FacultyRepository;
import pro.sky.APISwaggerPostman.service.FacultyService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
public class FacultyServiceImpl implements FacultyService {
    @Autowired
   private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    @Override
    public Faculty createFaculty(Faculty faculty) {

        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty getFaculty(long id) {
        return facultyRepository.findById(id).get();
    }

    @Override
    public Faculty editFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);
    }

    @Override
    public Collection<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }


    public Collection<Faculty> findAllByColor(String color){
        return facultyRepository.findAllByColor(color);
    }
}
