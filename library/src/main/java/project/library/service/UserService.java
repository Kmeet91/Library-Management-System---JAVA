package project.library.service;

import project.library.dao.UserDAO;
import project.library.entities.User;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }

    public List<User> addUsers(List<User> users) {
        return userDAO.addUsers(users);
    }

    public User updateUser(User user) {
        return userDAO.updateUser(user);
    }

    public boolean deleteUser(int id) {
        return userDAO.deleteUser(id);
    }
}
