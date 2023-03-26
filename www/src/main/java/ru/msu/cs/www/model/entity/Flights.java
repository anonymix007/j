package ru.msu.cs.www.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import javax.transaction.Transactional;
import java.util.Objects;

@Entity
@Table(name = "flights")
@Getter
@Setter
@ToString
@Transactional
@AllArgsConstructor
@NoArgsConstructor

public class Flights {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",
            columnDefinition = "serial",
            insertable = false,
            updatable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "airline_id", nullable = false)
    @ToString.Exclude
    private Airlines airlineId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "airport_id_dep", nullable = false)
    @ToString.Exclude
    private Airports airportIdDep;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "airport_id_arr", nullable = false)
    @ToString.Exclude
    private Airports airportIdArr;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "aircraft_id", nullable = false)
    @ToString.Exclude
    private Planes aircraftId;

    @Column(name = "time_dep", nullable = false)
    private LocalDateTime timeDep;

    @Column(name = "time_arr", nullable = false)
    private LocalDateTime timeArr;

    @Column(name = "cnt_seats", nullable = false)
    private Integer cntSeats;

    @Column(name = "cnt_available_seats", nullable = false)
    private Integer cntAvailableSeats;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flights flights)) return false;
        return getId().equals(flights.getId()) && getAirlineId().equals(flights.getAirlineId()) && getAirportIdDep().equals(flights.getAirportIdDep()) && getAirportIdArr().equals(flights.getAirportIdArr()) && getAircraftId().equals(flights.getAircraftId()) && getTimeDep().equals(flights.getTimeDep()) && getTimeArr().equals(flights.getTimeArr()) && getCntSeats().equals(flights.getCntSeats()) && getCntAvailableSeats().equals(flights.getCntAvailableSeats());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAirlineId(), getAirportIdDep(), getAirportIdArr(), getAircraftId(), getTimeDep(), getTimeArr(), getCntSeats(), getCntAvailableSeats());
    }
}
