package CRUD_app.repository;

import java.util.List;

public interface GeneralRepository<T, ID> {

     T findById(ID id);

     List<T> findAll();

     ID save(T t);

     boolean update(T t);

     void deleteById(ID id);
}
