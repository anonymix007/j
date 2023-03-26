package ru.msu.cs.www.model.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import ru.msu.cs.www.model.HibernateConfiguration;
import ru.msu.cs.www.model.dao.PassengersDAO;
import ru.msu.cs.www.model.entity.Passengers;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Transactional
public class PassengersDAOImpl extends BaseDAOImpl<Passengers> implements PassengersDAO {

    public PassengersDAOImpl() {
        super(Passengers.class);
    }

    @Override
    public Collection<Passengers> getUsersByFilter(Filter filter) throws HibernateException {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Passengers> criteriaQuery = builder.createQuery(Passengers.class);
        Root<Passengers> root = criteriaQuery.from(Passengers.class);

        List<Predicate> predicates = new ArrayList<>();

        if (filter.getFullName() != null) {
            String pattern = "%" + filter.getFullName() + "%";
            predicates.add(builder.like(root.get("fullName"), pattern));
        }
        if (filter.getAddress() != null) {
            String pattern = "%" + filter.getAddress() + "%";
            predicates.add(builder.like(root.get("address"), pattern));
        }
        if (filter.getEmail() != null) {
            String pattern = "%" + filter.getEmail() + "%";
            predicates.add(builder.like(root.get("email"), pattern));
        }
        if (filter.getPhoneNumber() != null) {
            String pattern = "%" + filter.getPhoneNumber() + "%";
            predicates.add(builder.like(root.get("phoneNumber"), pattern));
        }

        if (predicates.size() != 0)
            criteriaQuery.where(predicates.toArray(new Predicate[0]));

        List<Passengers> result = session.createQuery(criteriaQuery).getResultList();
        session.getTransaction().commit();
        return result;
    }
}
