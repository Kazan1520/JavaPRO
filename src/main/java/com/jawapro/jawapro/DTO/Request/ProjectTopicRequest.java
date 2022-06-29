package com.jawapro.jawapro.DTO.Request;
import lombok.*;
import java.sql.Timestamp;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectTopicRequest {
        private String name;
        private String description;
        private int multipleMaxCount = 1;
        private Timestamp deadline;
}
