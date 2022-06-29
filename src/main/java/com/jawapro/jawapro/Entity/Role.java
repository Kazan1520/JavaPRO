package com.jawapro.jawapro.Entity;

import com.jawapro.jawapro.RoleEnum;
import lombok.*;

import javax.persistence.*;


@Builder
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(schema="public" ,name="roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name="name")
    private RoleEnum name;

    @Override
    public String toString(){
        return name.toString();
    }

}
