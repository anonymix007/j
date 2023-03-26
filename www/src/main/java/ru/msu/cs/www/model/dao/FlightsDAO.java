package ru.msu.cs.www.model.dao;

import lombok.Builder;
import lombok.Getter;
import ru.msu.cs.www.model.entity.Flights;

import java.time.LocalDateTime;
import java.util.Collection;

public interface FlightsDAO extends BaseDAO<Flights> {
    @Builder
    @Getter
    class Filter {
        private LocalDateTime timeDepMin;
        private LocalDateTime timeDepMax;
        private LocalDateTime timeArrMin;
        private LocalDateTime timeArrMax;
        private Integer cntSeatsMin;
        private Integer cntSeatsMax;
        private Integer cntAvailableSeatsMin;
        private Integer cntAvailableSeatsMax;
    }

    static Filter.FilterBuilder getFilterBuilder() {
        return Filter.builder();
    }

    Collection<Flights> getFlightsByFilter(Filter filter);
}
