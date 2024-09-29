package EcoMonitoring.repository;

import EcoMonitoring.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

@org.springframework.stereotype.Repository
public class Repository<T> implements IRepository<T> {

    @Override
    public <T> void create(T entity) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public <T> T findById(Class<T> tempClass, Long id) {
        Session session = Util.getSessionFactory().openSession();
        T entity = null;
        try {
            entity = session.get(tempClass, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return entity;
    }

    @Override
    public <T> void update(T entity) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public <T> void delete(T entity) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

}
