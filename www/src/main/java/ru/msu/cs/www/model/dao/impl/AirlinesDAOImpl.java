package ru.msu.cs.www.model.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import ru.msu.cs.www.model.dao.AirlinesDAO;
import ru.msu.cs.www.model.entity.Airlines;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Transactional
public class AirlinesDAOImpl extends BaseDAOImpl<Airlines> implements AirlinesDAO {

    public AirlinesDAOImpl() {
        super(Airlines.class);
    }

    @Override
    public Collection<Airlines> getAirlinesByFilter(Filter filter) throws HibernateException {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Airlines> criteriaQuery = builder.createQuery(Airlines.class);
        Root<Airlines> root = criteriaQuery.from(Airlines.class);

        List<Predicate> predicates = new ArrayList<>();
        if (filter.getAirlineName() != null) {
            String pattern = "%" + filter.getAirlineName() + "%";
            predicates.add(builder.like(root.get("airlineName"), pattern));
        }
        if (filter.getAirlineEmail() != null) {
            String pattern = "%" + filter.getAirlineEmail() + "%";
            predicates.add(builder.like(root.get("airlineEmail"), pattern));
        }
        if (filter.getPhoneNumber() != null) {
            String pattern = "%" + filter.getPhoneNumber() + "%";
            predicates.add(builder.like(root.get("phoneNumber"), pattern));
        }

        if (predicates.size() != 0)
            criteriaQuery.where(predicates.toArray(new Predicate[0]));

        List<Airlines> result = session.createQuery(criteriaQuery).getResultList();
        session.getTransaction().commit();
        return result;
    }
}
