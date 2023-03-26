package ru.msu.cs.www.model.dao;

import lombok.Builder;
import lombok.Getter;
import ru.msu.cs.www.model.entity.Tickets;

import java.util.Collection;

public interface TicketsDAO extends BaseDAO<Tickets> {
    @Builder
    @Getter
    class Filter {
        private String status;
        private Integer minPrice;
        private Integer maxPrice;
    }

    static Filter.FilterBuilder getFilterBuilder() {
        return Filter.builder();
    }

    Collection<Tickets> getTicketsByFilter(Filter filter);
}
