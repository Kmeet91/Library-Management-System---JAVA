package project.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import project.library.entities.Borrowed_books;
import project.library.entities.Returned_books;

public interface ReturnedBookRepository extends JpaRepository<Returned_books, Integer> {
	List<Returned_books> findByUser_Id(int userId);
}