package pro.sky.APISwaggerPostman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.APISwaggerPostman.model.Student;

import java.util.Collection;
import java.util.List;
@Repository
public interface StudentRepository extends JpaRepository <Student, Long> {
    Collection<Student> findAllByAge(int age);
}
