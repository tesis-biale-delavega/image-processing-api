package tesis.droneimageprocessingapi.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "project")
public class Project extends UUIDEntity{
    private String name;

    private String fileName;

    @CreationTimestamp
    private LocalDateTime creationDate;

    private String projectUrl;

    @ManyToOne
    private User user;

    public static Project from(User user, String originalName, String mediaLink) {
        String[] parts = originalName.split("_");
        String[] nameParts = new String[parts.length - 1];
        System.arraycopy(parts, 0, nameParts, 0, parts.length - 1);
        return Project.builder()
                .user(user)
                .name(String.join("_", nameParts))
                .fileName(originalName)
                .projectUrl(mediaLink)
                .build();
    }
}
