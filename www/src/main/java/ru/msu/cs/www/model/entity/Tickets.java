package ru.msu.cs.www.model.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Objects;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@ToString
@Transactional
@AllArgsConstructor
@NoArgsConstructor

public class Tickets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",
            columnDefinition = "serial",
            insertable = false,
            updatable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "flight_id", nullable = false)
    @ToString.Exclude
    private Flights flightId;

    @Lob
    @Column(name = "status", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String status;

    @Lob
    @Column(name = "price", nullable = false)
    private Integer price;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private Passengers userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tickets tickets)) return false;
        return getId().equals(tickets.getId()) && getPrice().equals(tickets.getPrice()) && getFlightId().equals(tickets.getFlightId()) && getStatus().equals(tickets.getStatus()) && getUserId().equals(tickets.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFlightId(), getStatus(), getUserId(), getPrice());
    }
}
