package pro.sky.APISwaggerPostman.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pro.sky.APISwaggerPostman.model.Faculty;
import pro.sky.APISwaggerPostman.repository.FacultyRepository;
import pro.sky.APISwaggerPostman.service.impl.FacultyServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FacultyController.class)
public class FacultyControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private FacultyRepository facultyRepository;
    @SpyBean
    private FacultyServiceImpl facultyService;

    Faculty faculty = new Faculty(1L, "BraveHeart", "red");
    Faculty faculty1 = new Faculty(2L, "BrainDamaged", "green");

    @Test
    public void shouldCreateFaculty() throws Exception {
        when(facultyRepository.save(faculty)).thenReturn(faculty);
        mockMvc.perform(post("/faculty")
                        .content(objectMapper.writeValueAsString(faculty))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));

    }

    @Test
    public void shouldGetFaculty() throws Exception {
        when(facultyRepository.findById(faculty.getId()))
                .thenReturn(Optional.of(faculty));
        mockMvc.perform(get("/faculty/" + faculty.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()));
    }

    @Test
    public void shouldUpdateFaculty() throws Exception {
        Faculty faculty1 = new Faculty(1L, "NotBrave", "yellow");

        when(facultyRepository.findById(faculty.getId()))
                .thenReturn(Optional.of(faculty1));

        when(facultyRepository.save(faculty1)).thenReturn(faculty1);

        mockMvc.perform(put("/faculty")
                        .content(objectMapper.writeValueAsString(faculty1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty1.getId()))
                .andExpect(jsonPath("$.name").value(faculty1.getName()))
                .andExpect(jsonPath("$.color").value(faculty1.getColor()));
    }

    @Test
    public void shouldDeleteFaculty() throws Exception {
        when(facultyRepository.findById(faculty.getId())).thenReturn(Optional.of(faculty));

        mockMvc.perform(delete("/faculty/" + faculty.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetFacultyByColor() throws Exception {
        when(facultyRepository.findAllByColorIgnoreCase(faculty.getColor()))
                .thenReturn(List.of(faculty, faculty1));

        mockMvc.perform(get("/faculty/color?color=" + faculty.getColor()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].color").value(faculty.getColor()))
                .andExpect(jsonPath("$.[1].color").value(faculty1.getColor()));

    }
}
