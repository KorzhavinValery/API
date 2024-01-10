package pro.sky.APISwaggerPostman.service;

import org.springframework.web.multipart.MultipartFile;
import pro.sky.APISwaggerPostman.model.Avatar;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public interface AvatarService {
    void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException;

    Avatar findAvatar(Long studentId);
    Collection<Avatar> getAllAvatars(Integer pageNumber, Integer pageSize);
}
