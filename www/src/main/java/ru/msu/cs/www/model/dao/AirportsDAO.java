package ru.msu.cs.www.model.dao;

import lombok.Builder;
import lombok.Getter;
import ru.msu.cs.www.model.entity.Airports;

import java.util.Collection;

public interface AirportsDAO extends BaseDAO<Airports> {
    @Builder
    @Getter
    class Filter {
        private String airportCountry;
        private String airportCity;
    }

    static Filter.FilterBuilder getFilterBuilder() {
        return Filter.builder();
    }

    Collection<Airports> getAirportsByFilter(Filter filter);
}
