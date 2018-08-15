package org.tinywind.webtemplate.service;

import org.tinywind.webtemplate.jooq.tables.pojos.FileEntity;
import org.tinywind.webtemplate.model.form.FileUploadForm;
import org.tinywind.webtemplate.repository.FileRepository;
import org.tinywind.webtemplate.util.UrlUtils;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author tinywind
 * @since 2018-01-14
 */
@Service
@PropertySource("classpath:application.properties")
public class FileService {
    public static final String FILE_PATH = "files/download";
    public static final String FILE_REQUEST_PARAM_KEY = "file";
    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    @Autowired
    private FileRepository fileRepository;

    @Value("${application.file.location}")
    private String fileLocation;

    public String url(Long fileId) {
        if (fileId == null)
            return null;

        return url(fileRepository.fileEntity(fileId));
    }

    public String url(FileEntity fileEntity) {
        if (fileEntity == null)
            return null;

        return url(fileEntity.getPath());
    }

    public String url(String path) {
        if (path == null)
            return null;

        return baseUrl() + "?" + UrlUtils.encodeQueryParams(FILE_REQUEST_PARAM_KEY, path);
    }

    public String baseUrl() {
        return ("/" + FILE_PATH + "/").replaceAll("[/]+", "/");
    }

    public Long save(String fileName, byte[] bytes) throws IOException {
        final String filePath = System.currentTimeMillis() + "_" + System.nanoTime() + "_" + fileName;
        final File file = new File(fileLocation, filePath);
        try (final FileOutputStream writer = new FileOutputStream(file)) {
            writer.write(bytes);
            writer.flush();

            return fileRepository.save(bytes.length, fileName, filePath);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            boolean deleted = file.delete();
            if (!deleted) logger.error("Failed: delete file: " + file.getAbsolutePath());

            throw e;
        }
    }

    public Long save(FileUploadForm form) throws IOException {
        return save(form.getFileName(), Base64.decodeBase64(form.getData()));
    }

    public Long save(MultipartFile file) throws IOException {
        return save(file.getOriginalFilename(), file.getBytes());
    }
}
