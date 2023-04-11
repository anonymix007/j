package ru.msu.cs.www.model.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import ru.msu.cs.www.model.dao.AirportsDAO;
import ru.msu.cs.www.model.entity.Airports;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Transactional
public class AirportsDAOImpl extends BaseDAOImpl<Airports> implements AirportsDAO {

    public AirportsDAOImpl() {
        super(Airports.class);
    }

    @Override
    public Collection<Airports> getAirportsByFilter(Filter filter) throws HibernateException {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Airports> criteriaQuery = builder.createQuery(Airports.class);
        Root<Airports> root = criteriaQuery.from(Airports.class);

        List<Predicate> predicates = new ArrayList<>();
        if (filter.getAirportCountry() != null) {
            String pattern = "%" + filter.getAirportCountry() + "%";
            predicates.add(builder.like(root.get("country"), pattern));
        }
        if (filter.getAirportCity() != null) {
            String pattern = "%" + filter.getAirportCity() + "%";
            predicates.add(builder.like(root.get("city"), pattern));
        }

        if (predicates.size() != 0)
            criteriaQuery.where(predicates.toArray(new Predicate[0]));

        List<Airports> result = session.createQuery(criteriaQuery).getResultList();
        session.getTransaction().commit();
        return result;
    }
}
