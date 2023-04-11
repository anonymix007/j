package ru.msu.cs.www.model.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import ru.msu.cs.www.model.dao.PlanesDAO;
import ru.msu.cs.www.model.entity.Planes;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Transactional
public class PlanesDAOImpl extends BaseDAOImpl<Planes> implements PlanesDAO {

    public PlanesDAOImpl() {
        super(Planes.class);
    }

    @Override
    public Collection<Planes> getAircraftByFilter(Filter filter) throws HibernateException {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Planes> criteriaQuery = builder.createQuery(Planes.class);
        Root<Planes> root = criteriaQuery.from(Planes.class);

        List<Predicate> predicates = new ArrayList<>();
        if (filter.getModelName() != null) {
            String pattern = "%" + filter.getModelName() + "%";
            predicates.add(builder.like(root.get("modelName"), pattern));
        }

        if (predicates.size() != 0)
            criteriaQuery.where(predicates.toArray(new Predicate[0]));

        List<Planes> result = session.createQuery(criteriaQuery).getResultList();
        session.getTransaction().commit();
        return result;
    }
}
