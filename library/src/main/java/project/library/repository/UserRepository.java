package project.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.library.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}