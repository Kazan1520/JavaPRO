package com.jawapro.jawapro.DTO.Response;

import com.jawapro.jawapro.Entity.ProjectTopic;
import lombok.*;

import java.sql.Timestamp;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectTopicResponse {
    private Long id;
    private String name;
    private String description;
    private boolean isMultiple;
    private boolean isReserved;
    private int multipleMaxCount;
    private Timestamp deadline;

    public ProjectTopicResponse(ProjectTopic projectTopic){
        this.id = projectTopic.getId();
        this.name = projectTopic.getName();
        this.description = projectTopic.getDescription();
        this.isMultiple = projectTopic.isMultiple();
        this.isReserved = projectTopic.isReserved();
        this.multipleMaxCount = projectTopic.getMultipleMaxCount();
        this.deadline = projectTopic.getDeadline();
    }

}
