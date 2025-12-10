package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CRUD<T,PK> {
    void create(T obj) throws SQLException;
    void update(T obj) throws SQLException;
    void delete(PK id) throws SQLException;
    List<T> getAll() throws SQLException;
    Optional<T> get(PK id) throws SQLException; //n'est pas demandé mais pour essayer
}


