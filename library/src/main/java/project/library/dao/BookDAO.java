package project.library.dao;

import project.library.entities.Book;
import project.library.entities.Borrowed_books;
import project.library.entities.Returned_books;

import java.util.List;

public interface BookDAO {
    List<Book> getAllBooks();
    Book getBookById(int id);
    List<Book> addBooks(List<Book> books);
    Book updateBook(int id,Book book);
    boolean deleteBook(int id);
    Borrowed_books borrowBook(int bookId, int userId);
    Borrowed_books renewBook(int borrowedBookId, int userId);
    Returned_books returnBook(int borrowedBookId);
    List<Borrowed_books> getAllBorrowedBooks();
    List<Borrowed_books> getBorrowedBooksByUser(int userId);
    List<Returned_books> getAllReturnedBooks();
    List<Returned_books> getReturnedBooksByUser(int userId);
}