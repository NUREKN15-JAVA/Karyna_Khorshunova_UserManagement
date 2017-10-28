package ua.nure.kn156.khorshunova.db;

import java.sql.Connection;

public interface ConnectionFactory {
   Connection createConnection() throws DataBaseException;
}
