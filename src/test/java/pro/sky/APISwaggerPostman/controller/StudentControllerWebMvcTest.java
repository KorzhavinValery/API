package pro.sky.APISwaggerPostman.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pro.sky.APISwaggerPostman.model.Faculty;
import pro.sky.APISwaggerPostman.model.Student;
import pro.sky.APISwaggerPostman.repository.StudentRepository;
import pro.sky.APISwaggerPostman.service.impl.StudentServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentRepository studentRepository;
    @SpyBean
    private StudentServiceImpl studentService;
    @InjectMocks
    private StudentController studentController;
    @Autowired
    private ObjectMapper objectMapper;
    private Student student = new Student(1L, "Cross Crissy", 30);
    private Faculty faculty = new Faculty(1L, "radio", "red");

    @Test
    public void shouldCreateStudent() throws Exception {


        when(studentRepository.save(student)).thenReturn(student);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(objectMapper.writeValueAsString(student))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()));

    }

    @Test
    public void shouldGetStudent() throws Exception {
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/" + student.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()));

    }

    @Test
    public void shouldUpdateStudent() throws  Exception {
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        when(studentRepository.save(student)).thenReturn(student);

        mockMvc.perform(put("/student")
                .content(objectMapper.writeValueAsString(student))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()));
    }
    @Test
    public void shouldDeleteStudent() throws Exception {

        when(studentRepository.findById(any())).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders.delete("/student/" + student.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldFindStudentByAge() throws Exception {
        Student studentNew = new Student(2L, "Jeremy Clarkson", 30);
        int age = student.getAge();

        when(studentRepository.findAllByAge(age)).thenReturn(List.of(student, studentNew));

        mockMvc.perform(get("/student/age?age=" + student.getAge()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].age").value(student.getAge()))
                .andExpect(jsonPath("$.[1].age").value(studentNew.getAge()));
    }
    @Test
    public void shouldFindStudentsBetweenAge() throws Exception {
        Student studentNew = new Student(2L, "Jeremy Clarkson", 35);
        Student studentNew1 = new Student(2L, "Hamond", 37);
        Student studentNew2 = new Student(2L, "Cptn Jelly", 36);
        int min = student.getAge();
        int max = studentNew1.getAge();
        when(studentRepository.findByAgeBetween(min, max)).thenReturn(List.of(studentNew, studentNew2));

        mockMvc.perform(get("/student/age/between?min=" + min + "&max=" + max))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]age").value(studentNew.getAge()))
                .andExpect(jsonPath("$.[1]age").value(studentNew2.getAge()));
    }
    @Test
    public void shouldGetFacultyByStudentId() throws Exception {
        student.setFaculty(faculty);
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));

        mockMvc.perform(get("/student/getFacultyByStudentId?studentId=" + student.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()));
    }
}



