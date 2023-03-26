package ru.msu.cs.www.model.dao;

import lombok.Builder;
import lombok.Getter;
import ru.msu.cs.www.model.entity.Planes;

import java.util.Collection;

public interface PlanesDAO extends BaseDAO<Planes> {
    @Builder
    @Getter
    class Filter {
        private String modelName;
    }

    static Filter.FilterBuilder getFilterBuilder() {
        return Filter.builder();
    }

    Collection<Planes> getAircraftByFilter(Filter filter);
}
