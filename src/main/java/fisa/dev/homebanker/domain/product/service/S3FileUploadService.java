package fisa.dev.homebanker.domain.product.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class S3FileUploadService {

  private final AmazonS3Client amazonS3;

  @Value("${cloud.aws.s3.bucket}")
  private String bucketName;

  private String dir = "";
  private String defaultUrl = "";

  public void uploadFile(MultipartFile file) throws IOException {

    String bucketDir = bucketName + dir;
    String dirUrl = defaultUrl + dir + "/";
    String fileName = generateFileName(file);

    amazonS3.putObject(bucketDir, fileName, file.getInputStream(), getObjectMetadata(file));
//    return dirUrl + fileName;

  }

  private ObjectMetadata getObjectMetadata(MultipartFile file) {
    ObjectMetadata objectMetadata = new ObjectMetadata();
    objectMetadata.setContentType(file.getContentType());
    objectMetadata.setContentLength(file.getSize());
    return objectMetadata;
  }

  private String generateFileName(MultipartFile file) {
    return UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
  }
}