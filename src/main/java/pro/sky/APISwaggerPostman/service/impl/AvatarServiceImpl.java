package pro.sky.APISwaggerPostman.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.APISwaggerPostman.model.Avatar;
import pro.sky.APISwaggerPostman.model.Student;
import pro.sky.APISwaggerPostman.repository.AvatarRepository;
import pro.sky.APISwaggerPostman.service.AvatarService;
import pro.sky.APISwaggerPostman.service.StudentService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;


import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarServiceImpl implements AvatarService {
    Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);
    @Value("${path.to.avatars.folder}")
    private final String avatarsDir;
    private final StudentService studentService;
    private final AvatarRepository avatarRepository;

    public AvatarServiceImpl(@Value("${path.to.avatars.folder}")String avatarsDir, StudentService studentService, AvatarRepository avatarRepository) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
        this.avatarsDir = avatarsDir;
    }

    @Override
    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        logger.info("Was invoked method for saving avatar to filePath");
        Student student = studentService.getStudent(studentId);
        Path filePath = Path.of(avatarsDir,
                studentId + "." + getExtensions((avatarFile.getOriginalFilename())));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = avatarFile.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }
        logger.info("Was invoked method for saving avatar to DataBase");
        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setData(avatarFile.getBytes());
        avatar.setMediaType(avatarFile.getContentType());

        avatarRepository.save(avatar);
    }

    @Override
    public Avatar findAvatar(Long studentId) {
        logger.info("Was invoked method for reading avatar ");
        return avatarRepository.findByStudent_Id(studentId).orElse(new Avatar());
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);

    }

    @Override
    public Collection<Avatar> getAllAvatars(Integer pageNumber, Integer pageSize) {
        logger.info("Was invoked method for getting Collection of all avatars ");
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return avatarRepository.findAll(pageRequest).getContent();
    }
}
