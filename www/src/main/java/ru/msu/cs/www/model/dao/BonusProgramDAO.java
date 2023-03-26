package ru.msu.cs.www.model.dao;

import lombok.Builder;
import lombok.Getter;
import ru.msu.cs.www.model.entity.BonusProgram;

import java.util.Collection;

public interface BonusProgramDAO extends BaseDAO<BonusProgram> {
    @Builder
    @Getter
    class Filter {
        private String bonusCard;
        private Integer cntKmMin;
        private Integer cntKmMax;
        private Integer cntUsedMin;
        private Integer cntUsedMax;
    }

    static Filter.FilterBuilder getFilterBuilder() {
        return Filter.builder();
    }

    Collection<BonusProgram> getBonusProgramByFilter(Filter filter);
}
