package tesis.droneimageprocessingapi.repository;

import org.springframework.data.repository.CrudRepository;
import tesis.droneimageprocessingapi.model.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends CrudRepository<Project, String> {
    List<Project> findAllByUserIdOrderByCreationDateDesc(String userId);
    Optional<Project> findFirstByUserIdAndFileName(String userId, String projectUrl);
}
