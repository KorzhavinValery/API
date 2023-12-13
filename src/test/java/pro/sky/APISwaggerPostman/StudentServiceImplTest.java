package pro.sky.APISwaggerPostman;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.APISwaggerPostman.repository.StudentRepository;
import pro.sky.APISwaggerPostman.service.impl.StudentServiceImpl;

@ExtendWith(MockitoExtension.class)
public class StudentServiceImplTest {
    @Mock
    private StudentRepository  studentRepository;
    @InjectMocks
    private StudentServiceImpl studentService;

}
