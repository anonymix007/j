package ru.msu.cs.www.model.dao;

import lombok.Builder;
import lombok.Getter;
import ru.msu.cs.www.model.entity.Passengers;

import java.util.Collection;

public interface PassengersDAO extends BaseDAO<Passengers> {
    @Builder
    @Getter
    class Filter {
        private String fullName;
        private String address;
        private String email;
        private String phoneNumber;
    }

    static Filter.FilterBuilder getFilterBuilder() {
        return Filter.builder();
    }

    Collection<Passengers> getUsersByFilter(Filter filter);
}
