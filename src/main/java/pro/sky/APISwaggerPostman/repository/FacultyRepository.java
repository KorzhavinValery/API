package pro.sky.APISwaggerPostman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.APISwaggerPostman.model.Faculty;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findByName(String color);
}
