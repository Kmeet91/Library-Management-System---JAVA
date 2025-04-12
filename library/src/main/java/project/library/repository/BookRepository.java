package project.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.library.entities.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {
}