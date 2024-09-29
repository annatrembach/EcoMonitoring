package EcoMonitoring.repository;

public interface IRepository<T> {

    <T> void create(T entity);

    <T> T findById(Class<T> tempClass, Long id);

    <T> void update(T entity);

    <T> void delete(T entity);
}
