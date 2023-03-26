package ru.msu.cs.www.model.dao;

import lombok.Builder;
import lombok.Getter;
import ru.msu.cs.www.model.entity.Airlines;

import java.util.Collection;

public interface AirlinesDAO extends BaseDAO<Airlines>{
    @Builder
    @Getter
    class Filter {
        private String airlineName;
        private String airlineEmail;
        private String phoneNumber;
    }

    static Filter.FilterBuilder getFilterBuilder() {
        return Filter.builder();
    }

    Collection<Airlines> getAirlinesByFilter(Filter filter);
}
