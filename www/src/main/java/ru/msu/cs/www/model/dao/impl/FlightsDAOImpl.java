package ru.msu.cs.www.model.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import ru.msu.cs.www.model.dao.FlightsDAO;
import ru.msu.cs.www.model.entity.Flights;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Transactional
public class FlightsDAOImpl extends BaseDAOImpl<Flights> implements FlightsDAO {

    public FlightsDAOImpl() {
        super(Flights.class);
    }

    @Override
    public Collection<Flights> getFlightsByFilter(Filter filter) throws HibernateException {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Flights> criteriaQuery = builder.createQuery(Flights.class);
        Root<Flights> root = criteriaQuery.from(Flights.class);

        List<Predicate> predicates = new ArrayList<>();

        if (filter.getTimeDepMin() != null) {
            predicates.add(builder.greaterThan(root.get("timeDep"), filter.getTimeDepMin()));
        }
        if (filter.getTimeDepMax() != null) {
            predicates.add(builder.lessThan(root.get("timeDep"), filter.getTimeDepMax()));
        }
        if (filter.getTimeArrMin() != null) {
            predicates.add(builder.greaterThan(root.get("timeArr"), filter.getTimeArrMin()));
        }
        if (filter.getTimeArrMax() != null) {
            predicates.add(builder.lessThan(root.get("timeArr"), filter.getTimeArrMax()));
        }
        if (filter.getCntSeatsMin() != null) {
            Integer cntSeatsMin = filter.getCntSeatsMin();
            predicates.add(builder.le(builder.literal(cntSeatsMin), root.get("cntSeats")));
        }
        if (filter.getCntSeatsMax() != null) {
            Integer cntSeatsMax = filter.getCntSeatsMax();
            predicates.add(builder.ge(builder.literal(cntSeatsMax), root.get("cntSeats")));
        }
        if (filter.getCntAvailableSeatsMin() != null) {
            Integer cntAvailableSeatsMin = filter.getCntAvailableSeatsMin();
            predicates.add(builder.le(builder.literal(cntAvailableSeatsMin), root.get("cntAvailableSeats")));
        }
        if (filter.getCntAvailableSeatsMax() != null) {
            Integer cntAvailableSeatsMax = filter.getCntAvailableSeatsMax();
            predicates.add(builder.ge(builder.literal(cntAvailableSeatsMax), root.get("cntAvailableSeats")));
        }

        if (predicates.size() != 0)
            criteriaQuery.where(predicates.toArray(new Predicate[0]));

        List<Flights> result = session.createQuery(criteriaQuery).getResultList();
        session.getTransaction().commit();
        return result;
    }
}
