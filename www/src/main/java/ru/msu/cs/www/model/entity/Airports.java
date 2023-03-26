package ru.msu.cs.www.model.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Objects;

@Entity
@Table(name = "airports")
@Getter
@Setter
@ToString
@Transactional
@AllArgsConstructor
@NoArgsConstructor

public class Airports {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",
            columnDefinition = "serial",
            insertable = false,
            updatable = false)
    private Integer id;

    @Lob
    @Column(name = "country", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String country;

    @Lob
    @Column(name = "city", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String city;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Airports airports)) return false;
        return getId().equals(airports.getId()) && getCity().equals(airports.getCity()) && getCountry().equals(airports.getCountry());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCountry(), getCity());
    }
}
