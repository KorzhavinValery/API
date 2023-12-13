package pro.sky.APISwaggerPostman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.APISwaggerPostman.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository <Student, Long> {
    List<Student> findByName(Long age);
}
