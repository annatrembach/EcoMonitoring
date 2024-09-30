package EcoMonitoring.repository;

import EcoMonitoring.util.Util;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.data.domain.Sort;

import java.util.List;

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
    public <T> List<T> findAll(Class<T> tempClass) {
        Session session = Util.getSessionFactory().openSession();
        List<T> entities = null;
        try {
            Query<T> query = session.createQuery("from " + tempClass.getSimpleName(), tempClass);
            entities = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entities;
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

    public <T> List<T> findWithSorting(Class<T> entityClass, String nameOfField, boolean isAsc) {
        Session session = Util.getSessionFactory().openSession();
        List<T> entities = null;
        try {
            session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(entityClass);
            Root<T> root = cq.from(entityClass);

            if(isAsc)
            {
                cq.orderBy(cb.asc(root.get(nameOfField)));
            } else {
                cq.orderBy(cb.desc(root.get(nameOfField)));
            }

            entities = session.createQuery(cq).getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entities;
    }
}
