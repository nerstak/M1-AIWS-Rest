package rest.todo.dao;

/**
 * Function to implement for any Dao
 * @param <T> Type of model to interact with
 */
public interface Dao<T> {
    public void insert(T t);

    public void delete(T t);

    public void update(T t);
}
