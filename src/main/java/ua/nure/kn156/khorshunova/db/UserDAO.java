package ua.nure.kn156.khorshunova.db;

import java.util.Collection;

import ua.nure.kn156.khorshunova.User;
/**
 * Interface for User class which implement DAO pattern with all CRUD opps.
 * 
 * @author Karina
 *
 */
public interface UserDAO {
	/**
	 * Add user into DB users table and get new user's id from db
	 * @param user all fields of user must be non-null except of id field
	 * @return copy of user from db with id auto-created
	 * @throws DataBaseException in case of any error with db
	 */
 User create(User user) throws DataBaseException;
    /**
    * Find user into db from users table at the specified id.
    * @param id of the user to find in db
    * @return user from db at the specified id. If user doesn't find return null
    * @throws DataBaseException in case of any error with db
    */
 User find(Long id) throws DataBaseException;
    /**
     * Update user into db at the specified id
     * @param user the user who needs to be updated
     * @throws DataBaseException in case of any error with db
     */
 void update(User user) throws DataBaseException;
    /**
     * Delete user from db at the specified id
     * @param user the user who needs to be delete
     * @throws DataBaseException in case of any error with db
     */
 void delete(User user) throws DataBaseException;
    /**
     * Find all users into db from users table
     * @return list of all users into db. If users don't find return empty list
     * @throws DataBaseException in case of any error with db
     */
 Collection<User> findAll() throws DataBaseException;
    /**
     * 
     * @return
     * @throws DataBaseException
     */
 Collection<User> find(String firstName, String lastName) throws DataBaseException;
    /**
     * Set the connection factory
     * @param connectionFactory the connection factory to use
     */
 void setConnectionFactory(ConnectionFactory connectionFactory);
}
