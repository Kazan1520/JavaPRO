package com.jawapro.jawapro.DTO.Response;

import com.jawapro.jawapro.Entity.File;
import com.jawapro.jawapro.Entity.ProjectTopic;
import com.jawapro.jawapro.Entity.Student;
import com.jawapro.jawapro.Entity.TakenProject;
import lombok.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.sql.Timestamp;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TakenProjectResponse {
    private Long id;
    private Timestamp deadline;
    private Student student;
    private ProjectTopic projectTopic;

    private String fileURL;
    private File file;

    public TakenProjectResponse(TakenProject takenProject) {
        this.id = takenProject.getId();
        this.deadline = takenProject.getDeadline();
        this.student = takenProject.getStudent();
        this.projectTopic = takenProject.getProjectTopic();
        if (takenProject.getFile() != null) {
            this.fileURL = ServletUriComponentsBuilder
                    .fromCurrentContextPath().path("/files/")
                    .path(takenProject.getFile().getId().toString()).toUriString();
        }
    }
}
