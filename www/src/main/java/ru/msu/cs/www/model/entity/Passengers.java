package ru.msu.cs.www.model.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Objects;

@Entity
@Table(name = "passengers")
@Getter
@Setter
@ToString
@Transactional
@AllArgsConstructor
@NoArgsConstructor

public class Passengers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",
            columnDefinition = "serial",
            insertable = false,
            updatable = false)
    private Integer id;

    @Lob
    @Column(name = "full_name", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String fullName;

    @Lob
    @Column(name = "address")
    @Type(type = "org.hibernate.type.TextType")
    private String address;

    @Lob
    @Column(name = "email", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String email;

    @Lob
    @Column(name = "phone_number", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String phoneNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Passengers users)) return false;
        return getId().equals(users.getId()) && getFullName().equals(users.getFullName()) && getAddress().equals(users.getAddress()) && getEmail().equals(users.getEmail()) && getPhoneNumber().equals(users.getPhoneNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFullName(), getAddress(), getEmail(), getPhoneNumber());
    }
}
