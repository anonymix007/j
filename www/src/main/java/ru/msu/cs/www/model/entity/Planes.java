package ru.msu.cs.www.model.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Objects;

@Entity
@Table(name = "planes")
@Getter
@Setter
@ToString
@Transactional
@AllArgsConstructor
@NoArgsConstructor

public class Planes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",
            columnDefinition = "serial",
            insertable = false,
            updatable = false)
    private Integer id;

    @Lob
    @Column(name = "model_name", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String modelName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Planes planes)) return false;
        return getId().equals(planes.getId()) && getModelName().equals(planes.getModelName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getModelName());
    }
}
