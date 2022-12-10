package tesis.droneimageprocessingapi.service;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tesis.droneimageprocessingapi.dto.ProjectSavedDto;
import tesis.droneimageprocessingapi.exception.BadRequestException;
import tesis.droneimageprocessingapi.model.Project;
import tesis.droneimageprocessingapi.model.User;
import tesis.droneimageprocessingapi.repository.ProjectRepository;
import tesis.droneimageprocessingapi.util.SessionUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProjectService {

    private static final Storage storage = StorageOptions.getDefaultInstance().getService();
    @Value("${gcp.bucket}")
    private String bucket;
    private final SessionUtils sessionUtils;
    private final ProjectRepository projectRepository;

    public Project upload(MultipartFile file) {
        final User user = sessionUtils.getLoggedUserInfo();
        Optional<Project> existingProject = projectRepository.findFirstByUserIdAndFileName(user.getId(), file.getOriginalFilename());

        existingProject.ifPresent(project -> {
            try {
                storage.delete(BlobId.of(bucket, project.getFileName()));
                projectRepository.delete(project);
            } catch (Exception e) {
                log.info(e.getMessage());
                throw new BadRequestException("Error deleting old file");
            }
        });

        try {
            final String[] parts = file.getOriginalFilename().split("/");
            final String name = parts[parts.length - 1];
            BlobInfo blobInfo = storage.createFrom(
                    BlobInfo.newBuilder(bucket, name).build(),
                    file.getInputStream()
            );
            return projectRepository.save(Project.from(user, name, blobInfo.getMediaLink()));
        }catch(Exception e){
            log.info(e.getMessage());
            throw new BadRequestException("Error creating new file");
        }
    }

    public List<Project> getAllUserProjects() {
        return projectRepository.findAllByUserId(sessionUtils.getLoggedUserInfo().getId());
    }
}
