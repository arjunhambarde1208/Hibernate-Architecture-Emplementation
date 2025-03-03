package com.hibernate;

import com.hibernate.dao.UserDAO;
import com.hibernate.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HibernateApplication {
    private static final Logger logger = LoggerFactory.getLogger(HibernateApplication.class);

    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();

        /* //Insert Users
         userDAO.saveUser(new User("Arjun Hambarde", "arjunhambarde1208@gmail.com"));
         userDAO.saveUser(new User("Akshay Hamde", "akshay1234@gmail.com"));
         */
         /*// Update User
            userDAO.updateUser(1L, "Pranav Patwekar", "pranav1245@gmail.com");
*/

       /* // Delete User
             userDAO.deleteUser(1L);
*/

        // Retrieve Users Using HQL
        List<User> hqlUsers = userDAO.getUsersUsingHQL();

        /*// Retrieve Users Using Criteria API
        String searchName = "Arjun Hambarde";  // Dynamic search parameter
        List<User> criteriaUsers = userDAO.getUsersUsingCriteria(searchName);
        */
    }
}
