package pro.sky.APISwaggerPostman.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pro.sky.APISwaggerPostman.model.Faculty;
import pro.sky.APISwaggerPostman.repository.FacultyRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerRestTemplateTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private FacultyRepository facultyRepository;

    private String baseUrl;
    private Faculty faculty = new Faculty(1L, "kogtevran", "black");

    @BeforeEach
    void beforeEach() {
        facultyRepository.deleteAll();
        baseUrl = "http://localhost:" + port + "/faculty";

    }

    @Test
    public void shouldCreateFaculty() {
        Faculty faculty1 = facultyRepository.save(faculty);
        ResponseEntity<Faculty> result = testRestTemplate.postForEntity(
                baseUrl,
                faculty1,
                Faculty.class
        );
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(faculty1, result.getBody());
    }

    @Test
    public void shouldGetFaculty() {
        Faculty getFaculty = facultyRepository.save(faculty);

        ResponseEntity<Faculty> result = testRestTemplate.getForEntity(
                baseUrl + "/" + getFaculty.getId(),
                Faculty.class
        );
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(getFaculty, result.getBody());
    }

    @Test
    public void shouldUpdateFaculty() {
        Faculty updateFaculty = facultyRepository.save(faculty);

        ResponseEntity<Faculty> result = testRestTemplate.exchange(
                baseUrl,
                HttpMethod.PUT,
                new HttpEntity<>(updateFaculty),
                Faculty.class
        );
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(updateFaculty, result.getBody());
    }

    @Test
    public void shouldDeleteFaculty() {
        Faculty deleteFaculty = facultyRepository.save(faculty);

        ResponseEntity<Faculty> result = testRestTemplate.exchange(
                baseUrl + "/" + deleteFaculty.getId(),
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Faculty.class
        );

        assertThat(facultyRepository.findById(1L)).isEmpty();
    }

    @Test
    public void shouldGetByColor() {
        Faculty faculty1 = new Faculty(2L, "puffendyi", "red");
        Faculty faculty2 = new Faculty(3L, "dragonborn", "red");

        Faculty saveFaculty = facultyRepository.save(faculty1);
        Faculty saveFaculty1 = facultyRepository.save(faculty2);
        List<Faculty> faculties = List.of(saveFaculty, saveFaculty1);

        ResponseEntity<List<Faculty>> result = testRestTemplate.exchange(
                baseUrl + "/color?color=" + saveFaculty.getColor(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(faculties, result.getBody());
    }


}
