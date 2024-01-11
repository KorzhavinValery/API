package pro.sky.APISwaggerPostman.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.APISwaggerPostman.model.Avatar;
import pro.sky.APISwaggerPostman.service.impl.AvatarServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

@RestController
@RequestMapping("avatar")
public class AvatarController {
    private final AvatarServiceImpl service;

    public AvatarController(AvatarServiceImpl service) {
        this.service = service;
    }

    @PostMapping(value = "{id}" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long id,
                                               @RequestParam MultipartFile avatar) throws IOException {
        if (avatar.getSize() > 1024 * 100) {
            return ResponseEntity.badRequest().body("The file is too big");
        }
        service.uploadAvatar(id, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}/from-db")
    public ResponseEntity<byte[]> downloadAvatarFromDB(@PathVariable Long id) {
        Avatar avatar = service.findAvatar(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);

        return ResponseEntity.ok().headers(headers).body(avatar.getData());
    }
    @GetMapping("{id}/from-file")
    public void downloadAvatarFromFile(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Avatar avatar = service.findAvatar(id);

        Path path = Path.of(avatar.getFilePath());

        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream()) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());

            is.transferTo(os);
        }
    }
    @GetMapping(value = "/all")
    public Collection<Avatar> getAllAvatars(@RequestParam("page") int pageNumber,
                                            @RequestParam("size") int pageSize) {
        return service.getAllAvatars(pageNumber, pageSize);
    }
}
