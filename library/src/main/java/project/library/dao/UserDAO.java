package project.library.dao;

import project.library.entities.User;
import java.util.List;

public interface UserDAO {
    List<User> getAllUsers();
    User getUserById(int id);
    List<User> addUsers(List<User> users);
    User updateUser(User user);
    boolean deleteUser(int id);
}