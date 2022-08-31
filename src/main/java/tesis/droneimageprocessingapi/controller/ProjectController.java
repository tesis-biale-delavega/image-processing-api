package tesis.droneimageprocessingapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tesis.droneimageprocessingapi.model.Project;
import tesis.droneimageprocessingapi.service.ProjectService;

import java.util.List;


@RestController
@RequestMapping("/api/project")
@AllArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Project uploadFile(@RequestPart(value = "file") MultipartFile file)  {
        return projectService.upload(file);
    }

    @GetMapping
    public List<Project> getAllUserProjects() {
        return projectService.getAllUserProjects();
    }

}
