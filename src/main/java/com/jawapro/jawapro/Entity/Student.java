package com.jawapro.jawapro.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(schema="public" ,name="student")
public class Student {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name="name")
        private String firstName;

        @Column(name="surname")
        private String lastName;

        @Column(name="index_number")
        private String indexNumber;

        @JsonBackReference
        @OneToOne(fetch = FetchType.LAZY)
        private User user;

        @OneToMany(cascade = CascadeType.REMOVE)
        private java.util.List<TakenProject> takenProjects;

}
