package rest.dao;

import java.util.List;

/**
 * Function to implement for any Dao
 * @param <T> Type of model to interact with
 */
public interface Dao<T> {
    /**
     * Insert a new element
     * @param t New element
     * @return Assertion
     */
    public boolean insert(T t);

    /**
     * Delete an element
     * @param t Element to delete
     * @return Assertion
     */
    public boolean delete(T t);

    /**
     * Update an element
     * @param t Element to update
     * @return Assertion
     */
    public boolean update(T t);

    /**
     * Select all elements
     * @return List of elements
     */
    public List<T> selectAll();

    /**
     * Select a single element
     * @param id Id of element
     * @return Element (may be null)
     */
    public T selectID(int id);
}
