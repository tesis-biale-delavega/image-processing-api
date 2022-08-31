package tesis.droneimageprocessingapi.dto;

import com.google.cloud.storage.BlobInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectSavedDto {
    private String url;
    private String name;
    private String fileName;

    public static ProjectSavedDto from(BlobInfo blobInfo, String originalName){
        return ProjectSavedDto.builder()
                .url(blobInfo.getMediaLink())
                .name(originalName)
                .fileName(blobInfo.getName())
                .build();
    }
}
