package ru.msu.cs.www.model.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import ru.msu.cs.www.model.HibernateConfiguration;
import ru.msu.cs.www.model.dao.BaseDAO;

import javax.transaction.Transactional;

@Transactional
public class BaseDAOImpl<SomeEntity> implements BaseDAO<SomeEntity> {
    Class<SomeEntity> entityClass;

    protected SessionFactory sessionFactory;



    public BaseDAOImpl(Class<SomeEntity> entityClass) {
        this.entityClass = entityClass;
        this.sessionFactory = HibernateConfiguration.getSessionFactory();
        if (this.sessionFactory == null) throw new Error("WTF");
    }

    @Override
    public void add(SomeEntity entity) throws HibernateException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(SomeEntity entity) throws HibernateException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(SomeEntity entity) throws HibernateException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(entity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public SomeEntity getById(Integer id) throws HibernateException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        SomeEntity result = session.load(this.entityClass, id);
        session.getTransaction().commit();
        session.close();
        return result;
    }
}
