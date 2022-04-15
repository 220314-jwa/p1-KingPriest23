package DAOs;


import java.util.List;

public interface GenericDAO <T> {
    // CRUD methods: create, read, update, delete
    public int create(T engineer); // returns the generated ID
    public T getById(); // read one
    public List<T> getAll(); // read all
    public void update(T updatedObj);
    public void delete(T objToDelete);
}
