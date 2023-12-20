package pro.sky.APISwaggerPostman;

import nonapi.io.github.classgraph.utils.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.APISwaggerPostman.model.Avatar;
import pro.sky.APISwaggerPostman.model.Student;
import pro.sky.APISwaggerPostman.repository.AvatarRepository;
import pro.sky.APISwaggerPostman.service.StudentService;
import pro.sky.APISwaggerPostman.service.impl.AvatarServiceImpl;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

public class AvatarServiceImplTest {
    StudentService studentService = mock(StudentService.class);

    AvatarRepository avatarRepository = mock(AvatarRepository.class);



    AvatarServiceImpl avatarService = new AvatarServiceImpl(
             studentService, avatarRepository);

    Student student = new Student(50, "Newbee", 17);

    @Test
    void shouldUploadAvatar() throws IOException {
    }

    @Test
    void shouldFindAndReturnAvatarByStudentId() {
        Avatar avatar = new Avatar();
        avatar.setStudent(student);

        Mockito.when(avatarRepository.findByStudent_Id(student.getId()))
                .thenReturn(Optional.of(avatar));

        Avatar result = avatarService.findAvatar(student.getId());
        Assertions.assertEquals(avatar, result);
    }

}
