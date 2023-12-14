package pro.sky.APISwaggerPostman;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.APISwaggerPostman.model.Student;
import pro.sky.APISwaggerPostman.repository.StudentRepository;
import pro.sky.APISwaggerPostman.service.impl.StudentServiceImpl;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static javax.management.Query.eq;

@ExtendWith(MockitoExtension.class)
public class StudentServiceImplTest {
    @Mock
    private StudentRepository studentRepository;
    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student = new Student(1, "Garik", 17);

    @Test
    public void shouldCreateStudent() {
        Mockito.when(studentRepository.save(student)).thenReturn(student);

        Student result = studentService.createStudent(student);
        Assertions.assertEquals(student, result);
    }

    @Test
    public void shouldGetStudentById() {
        Mockito.when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        Student result = studentService.getStudent(student.getId());
        Assertions.assertEquals(student, result);
    }

    @Test
    public void shouldRemoveStudentById() {
        Student student1 = new Student(2, "cloneGarik", 17);
        studentService.removeStudent(student1.getId());
        Mockito.verify(studentRepository, Mockito.times(1)).deleteById(student1.getId());
    }

    @Test
    public void shouldGetAllStudents() {
        List<Student> expected = List.of(
                new Student(1, "fggg", 44),
                new Student(2, "vitalya", 20),
                new Student(3, "fedor", 15)
        );
        Mockito.when(studentRepository.findAll()).thenReturn(expected);
        Assertions.assertEquals(expected, studentRepository.findAll());
    }

    @Test
    void shouldGetStudentsByAge() {
        List<Student> students = List.of((student),
                new Student(2, "Ron", 15),
                new Student(3, "Germiona", 20));
        int age = 15;

        Mockito.when(studentRepository.findAllByAge(age))
                .thenReturn(students);

        List<Student> studentList = students.stream().filter(st -> st.getAge() == age)
                .toList();

        Collection<Student> result = studentService.findAllByAge(age)
                .stream().filter(st -> st.getAge() == age).toList();

        Assertions.assertEquals(studentList, result);
    }
}
