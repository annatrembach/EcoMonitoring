package EcoMonitoring.repository;

import java.util.List;

public interface IRepository<T> {

    <T> void create(T entity);

    <T> T findById(Class<T> tempClass, Long id);

    <T> List<T> findAll(Class<T> tempClass);

    <T> void update(T entity);

    <T> void delete(T entity);

    <T> List<T> findWithSorting(Class<T> entityClass, String nameOfField, boolean isAsc);
}
