package project.library.dao;

import project.library.dao.UserDAO;
import project.library.entities.User;
import project.library.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Service
public class UserDAOImpl implements UserDAO {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");

    public UserDAOImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> addUsers(List<User> users) {
        users.forEach(user -> {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            System.out.println("Hashed password for " + user.getUsername() + ": " + user.getPassword());
        });
        return userRepository.saveAll(users);
    }

    @Override
    public User updateUser(User user) {
        Optional<User> existingUserOpt = userRepository.findById(user.getId());
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            
            // Only update non-null fields
            if (user.getUsername() != null) existingUser.setUsername(user.getUsername());
            if (user.getPassword() != null) existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            if (user.getName() != null) existingUser.setName(user.getName());
            if (user.getEmail() != null) existingUser.setEmail(user.getEmail());
            if (user.getPhone() != null) existingUser.setPhone(user.getPhone());
            if (user.getRoles() != null) existingUser.setRoles(user.getRoles());
            
            System.out.println(existingUser.getPassword());
            return userRepository.save(existingUser);
        }
        return null; //  Return null if user does not exist
    }

    @Override
    public boolean deleteUser(int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
