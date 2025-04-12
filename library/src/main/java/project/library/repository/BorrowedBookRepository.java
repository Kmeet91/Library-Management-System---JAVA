package project.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.library.entities.Borrowed_books;

import java.util.List;

public interface BorrowedBookRepository extends JpaRepository<Borrowed_books, Integer> {
    List<Borrowed_books> findByUser_Id(int userId);
}