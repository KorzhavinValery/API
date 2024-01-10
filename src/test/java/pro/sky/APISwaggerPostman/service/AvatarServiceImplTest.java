package pro.sky.APISwaggerPostman.service;

import nonapi.io.github.classgraph.utils.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AvatarServiceImplTest {
     private StudentService studentService = mock(StudentService.class);

    private AvatarRepository avatarRepository = mock(AvatarRepository.class);
    private String avatarsDir = "./src/test/resources/avatar";



    AvatarServiceImpl avatarService = new AvatarServiceImpl(avatarsDir, studentService, avatarRepository);

    Student student = new Student(50, "Newbee", 17);

    @Test
    void shouldUploadAvatar() throws IOException {
        String fileName = "1.jpeg";
        MockMultipartFile file = new MockMultipartFile(
                "uploaded-file",
                fileName,
                "image/jpeg",
                new byte[]{});
        Mockito.when(studentService.getStudent(student.getId())).thenReturn(student);
        Mockito.when(avatarRepository.findByStudent_Id((student.getId())))
                .thenReturn(Optional.empty());

        avatarService.uploadAvatar(student.getId(), file);

        Mockito.verify(avatarRepository, times(1)).save(any());
        Assertions.assertTrue(FileUtils.canRead(new File(avatarsDir + "/" + student.getId() +
                "." + fileName.substring(fileName.lastIndexOf(".") + 1))));
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
