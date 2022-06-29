package com.jawapro.jawapro.Entity;

import javax.persistence.*;
import lombok.*;

@Builder
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(schema="public" ,name="pliki")
public class File{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @OneToOne
    @JoinColumn(name="taken_project_id")
    private TakenProject takenProject;

    @Lob
    private byte[] data;

    public File(String name, byte[] data) {
        this.name = name;
        this.data = data;
    }
}
