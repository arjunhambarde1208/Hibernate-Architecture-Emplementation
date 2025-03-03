package com.hibernate.dao;

import com.hibernate.entity.User;
import com.hibernate.util.HibernateUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class UserDAO {
    private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

    public void saveUser(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Query<User> query = session.createQuery("FROM User WHERE email = :email", User.class);
            query.setParameter("email", user.getEmail());
            User existingUser = query.uniqueResult();

            if (existingUser == null) {
                session.persist(user);
                transaction.commit();
                logger.info("User saved: {}", user);
            } else {
                logger.warn("User with email {} already exists!", user.getEmail());
            }

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Error saving user", e);
        }
    }


    public List<User> getUsersUsingHQL() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<User> users = session.createQuery("FROM User", User.class).list();
            if (users.isEmpty()) {
                logger.warn("No users found in the database.");
            } else {
                logger.info("Users : {}", users);
            }
            return users;
        }
    }


    public List<User> getUsersUsingCriteria(String nameFilter) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);

            // Apply filtering condition dynamically
            criteriaQuery.select(root)
                    .where(criteriaBuilder.like(root.get("name"), "%" + nameFilter + "%"));

            List<User> result = session.createQuery(criteriaQuery).getResultList();

            // Log message if no results are found
            if (result.isEmpty()) {
                logger.warn("No users found with name containing: {}", nameFilter);
            } else {
                logger.info("Users: {}", result);
            }

            return result;
        }
    }



    public void updateUser(Long id, String newName, String newEmail) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            User user = session.get(User.class, id);
            if (user != null) {
                // Check if the new email is already used by another user
                Query<User> query = session.createQuery("FROM User WHERE email = :email AND id <> :id", User.class);
                query.setParameter("email", newEmail);
                query.setParameter("id", id);
                User existingUser = query.uniqueResult();

                if (existingUser == null) {
                    user.setName(newName);
                    user.setEmail(newEmail);
                    session.merge(user);
                    transaction.commit();
                    logger.info("User updated successfully: {}", user);
                } else {
                    logger.warn("Email '{}' is already taken by another user!", newEmail);
                    transaction.rollback();  // Fix: Rollback transaction if email exists
                }
            } else {
                logger.warn("User with ID {} not found!", id);
                transaction.rollback();
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Error updating user with ID {}: {}", id, e.getMessage(), e);
        }
    }

    public void deleteUser(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);

            if (user != null) {
                session.remove(user);
                transaction.commit();
                logger.info("User with ID {} removed from the database.", id);
            } else {
                logger.warn("User with ID {} not found. No deletion performed.", id);
                transaction.rollback();  // Fix: Rollback transaction if user not found
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Error deleting user with ID {}: {}", id, e.getMessage(), e);
        }
    }
}

