package ru.msu.cs.www.model.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import ru.msu.cs.www.model.HibernateConfiguration;
import ru.msu.cs.www.model.dao.TicketsDAO;
import ru.msu.cs.www.model.entity.Tickets;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Transactional
public class TicketsDAOImpl extends BaseDAOImpl<Tickets> implements TicketsDAO {

    public TicketsDAOImpl() {
        super(Tickets.class);
    }

    @Override
    public Collection<Tickets> getTicketsByFilter(Filter filter) throws HibernateException {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Tickets> criteriaQuery = builder.createQuery(Tickets.class);
        Root<Tickets> root = criteriaQuery.from(Tickets.class);

        List<Predicate> predicates = new ArrayList<>();
        if (filter.getStatus() != null) {
            String pattern = "%" + filter.getStatus() + "%";
            predicates.add(builder.like(root.get("status"), pattern));
        }
        if (filter.getMinPrice() != null) {
            Integer minPrice = filter.getMinPrice();
            predicates.add(builder.le(builder.literal(minPrice), root.get("price")));
        }
        if (filter.getMaxPrice() != null) {
            Integer maxPrice = filter.getMaxPrice();
            predicates.add(builder.ge(builder.literal(maxPrice), root.get("price")));
        }

        if (predicates.size() != 0)
            criteriaQuery.where(predicates.toArray(new Predicate[0]));

        List<Tickets> result = session.createQuery(criteriaQuery).getResultList();
        session.getTransaction().commit();
        return result;
    }
}
