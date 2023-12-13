package pro.sky.APISwaggerPostman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.APISwaggerPostman.model.Faculty;

import java.util.Collection;
import java.util.List;
@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Collection<Faculty> findAllByColor(String color);
}
