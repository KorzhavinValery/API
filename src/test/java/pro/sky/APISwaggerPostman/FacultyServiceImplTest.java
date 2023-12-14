package pro.sky.APISwaggerPostman;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.APISwaggerPostman.model.Faculty;
import pro.sky.APISwaggerPostman.model.Student;
import pro.sky.APISwaggerPostman.repository.FacultyRepository;
import pro.sky.APISwaggerPostman.service.impl.FacultyServiceImpl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class FacultyServiceImplTest {
    @Mock
    private FacultyRepository facultyRepository;
    @InjectMocks
    private FacultyServiceImpl facultyService;

    private Faculty faculty = new Faculty(1, "Griffindor", "green");

    @Test
    void shouldCreateFaculty() {
        Mockito.when(facultyRepository.save(faculty)).thenReturn(faculty);
        Faculty result = facultyRepository.save(faculty);
        Assertions.assertEquals(faculty, result);
    }

    @Test
    void shouldGetFacultyById() {

        Mockito.when(facultyRepository.findById(faculty.getId()))
                .thenReturn(Optional.of(faculty));

        Faculty result = facultyService.getFaculty(faculty.getId());

        Assertions.assertEquals(faculty, result);
    }

    @Test
    void shouldUpdateFaculty() {
        Mockito.when(facultyRepository.findById(faculty.getId()))
                .thenReturn(Optional.of(faculty));

        Faculty result = facultyService.getFaculty(faculty.getId());

        Assertions.assertEquals(faculty, result);
    }

    @Test
    void shouldDeleteFacultyById() {
        Faculty faculty1 = new Faculty(2, "kogtevran", "white");
        facultyService.deleteFaculty(faculty1.getId());
        Mockito.verify(facultyRepository, Mockito.times(1)).deleteById(faculty1.getId());
    }

    @Test
    void shouldFindByColor() {
        List<Faculty> faculties = List.of((faculty),
                new Faculty(2, "kogtevran", "white"),
                new Faculty(3, "pyfindyi", "yellow"));
        String color = "yellow";

        Mockito.when(facultyRepository.findByNameContainsIgnoreCase(color))
                .thenReturn(faculties);

        List<Faculty> facultyList = faculties.stream().filter(fc -> fc.getColor().equals(color))
                .toList();

        Collection<Faculty> result = facultyService.findAllByColor(color)
                .stream().filter(fc -> fc.getColor().equals(color)).toList();

        Assertions.assertEquals(facultyList, result);

    }

}
