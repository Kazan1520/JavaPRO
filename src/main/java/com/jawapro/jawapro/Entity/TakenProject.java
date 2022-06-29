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
@Table(schema="public" ,name="taken_project")
public class TakenProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="deadline")
    private Timestamp deadline;

    @ManyToOne
    @JoinColumn(name="project_topic_id")
    private ProjectTopic projectTopic;

    @ManyToOne
    @JoinColumn(name="student_id")
    private Student student;

    @OneToOne
    @JoinColumn(name="file_id")
    private File file;

}
