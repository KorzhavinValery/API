package pro.sky.APISwaggerPostman.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pro.sky.APISwaggerPostman.model.Faculty;
import pro.sky.APISwaggerPostman.repository.FacultyRepository;
import pro.sky.APISwaggerPostman.service.FacultyService;

import java.util.*;

@Service
public class FacultyServiceImpl implements FacultyService {

    Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);
    @Autowired
    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    @Override
    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was invoked method for creating faculty");
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty getFaculty(long id) {
        logger.info("Was invoked method for get faculty by id: {}", id);
        return facultyRepository.findById(id).get();
    }

    @Override
    public Faculty editFaculty(Faculty faculty) {
        logger.info("Was invoked method for update faculty by id {}",faculty.getId());
        return facultyRepository.save(faculty);
    }

    @Override
    public void deleteFaculty(long id) {
        logger.warn("Was invoked method for remove Faculty by id {}", id);
        facultyRepository.deleteById(id);
    }

    @Override
    public Collection<Faculty> getAllFaculties() {
        logger.info("Was invoked method for searching all faculties");
        return facultyRepository.findAll();
    }

    @Override
    public Collection<Faculty> findAllByColor(String color) {
        logger.info("Was invoked method for finding faculty by color {}",color);
        return facultyRepository.findAllByColorIgnoreCase(color);
    }

    @Override
    public Collection<Faculty> findByNameContainsIgnoreCase(String name) {
        logger.info("Was invoked method for finding faculty by name {} ",name);
        return facultyRepository.findByNameContainsIgnoreCase(name);
    }

    @Override
    public ResponseEntity<String> getFacultyWithMaxLength() {
        logger.info("Was invoked method for finding faculty name with maximum length");

        Optional<String> facultyNameWithMaxLength =
                facultyRepository.findAll().stream()
                        .map(Faculty::getName)
                        .max(Comparator.comparing(String::length));
        return ResponseEntity.ok(facultyNameWithMaxLength.get());
    }
}
