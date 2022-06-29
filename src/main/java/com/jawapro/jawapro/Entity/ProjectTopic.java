package com.jawapro.jawapro.Entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Builder
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(schema="public" ,name="project_topic")
public class ProjectTopic {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name="name")
        private String name;

        @Column(name="description")
        private String description;

        @Column(name="is_Multiple")
        private boolean isMultiple;

        @Column(name="is_Reserved")
        private boolean isReserved;

        @Column(name="multiple_max_count")
        private int multipleMaxCount = 1;

        @Column(name="deadline")
        private Timestamp deadline;

        @OneToMany(mappedBy = "projectTopic", fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
        private java.util.List<TakenProject> takenProjects;

}
