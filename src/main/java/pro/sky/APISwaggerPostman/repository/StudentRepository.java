package pro.sky.APISwaggerPostman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pro.sky.APISwaggerPostman.model.Student;

import java.util.Collection;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findAllByAge(int age);

    Collection<Student> findByAgeBetween(int min, int max);

    Collection<Student> findAllByFaculty_id(long facultyId);

    @Query(value = "SELECT COUNT(*) FROM student", nativeQuery = true)
    Integer getCountAllStudents();

    @Query(value = "SELECT AVG(age) FROM student", nativeQuery = true)
    Double getAverageAgeOfStudents();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    Collection<Student> getLastFiveStudents();
}
