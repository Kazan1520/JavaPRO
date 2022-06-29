package com.jawapro.jawapro.DTO.Request;

import com.jawapro.jawapro.Entity.File;
import com.jawapro.jawapro.Entity.ProjectTopic;
import com.jawapro.jawapro.Entity.Student;
import lombok.*;

import java.sql.Timestamp;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TakenProjectRequest {
    private Long id;
    private Timestamp deadline;
    private ProjectTopic projectTopic;
    private Student student;
    private File file;

}
