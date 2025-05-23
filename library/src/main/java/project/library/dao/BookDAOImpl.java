package project.library.dao;

import org.apache.el.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import project.library.entities.Book;
import project.library.entities.Borrowed_books;
import project.library.entities.Returned_books;
import project.library.entities.User;
import project.library.repository.BookRepository;
import project.library.repository.BorrowedBookRepository;
import project.library.repository.ReturnedBookRepository;
import project.library.repository.UserRepository;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Repository
public class BookDAOImpl implements BookDAO {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private BorrowedBookRepository borrowedBooksRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ReturnedBookRepository returnedBooksRepository;

	private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	public Book getBookById(int id) {
		return bookRepository.findById(id).orElse(null);
	}

	public List<Book> addBooks(List<Book> books) {
		return bookRepository.saveAll(books);
	}

	public Book updateBook(int id, Book bookDetails) {
		Optional<Book> optionalBook = bookRepository.findById(id);
		if (optionalBook.isPresent()) {
			Book book = optionalBook.get();
			book.setTitle(bookDetails.getTitle());
			book.setAuthor(bookDetails.getAuthor());
			book.setGenre(bookDetails.getGenre());
			book.setpublicationYear(bookDetails.getpublicationYear());
			book.setPrice(bookDetails.getPrice());
			book.setLanguage(bookDetails.getLanguage());
			book.setQuantity(bookDetails.getQuantity());
			return bookRepository.save(book);
		}
		return null;
	}

	public boolean deleteBook(int id) {
		if (bookRepository.existsById(id)) {
			bookRepository.deleteById(id);
			return true;
		}
		return false;
	}

	public Borrowed_books borrowBook(int bookId, int userId) {
		Optional<Book> optionalBook = bookRepository.findById(bookId);
		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalBook.isPresent() && optionalUser.isPresent()) {
			Book book = optionalBook.get();
			User user = optionalUser.get();
			if (book.getQuantity() > 0) {
				Borrowed_books borrowedBook = new Borrowed_books();
				borrowedBook.setBook(book);
				borrowedBook.setUser(user);
				borrowedBook.setTitle(book.getTitle());
				borrowedBook.setAuthor(book.getAuthor());

				// Using LocalDate with consistent formatting
				borrowedBook.setborrowedDate(LocalDate.now().format(dateFormatter));
				borrowedBook.setreturnDate(LocalDate.now().plusDays(20).format(dateFormatter));

				book.setQuantity(book.getQuantity() - 1);
				bookRepository.save(book);
				return borrowedBooksRepository.save(borrowedBook);
			}
		}
		return null;
	}

	public Borrowed_books renewBook(int borrowedBookId, int userId) {
		Optional<Borrowed_books> optionalBorrowedBook = borrowedBooksRepository.findById(borrowedBookId);
		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalBorrowedBook.isPresent() && optionalUser.isPresent()) {
			Borrowed_books borrowedBook = optionalBorrowedBook.get();

			// Using LocalDate with consistent formatting
			borrowedBook.setreturnDate(LocalDate.now().plusDays(20).format(dateFormatter));

			return borrowedBooksRepository.save(borrowedBook);
		}
		return null;
	}

	public Returned_books returnBook(int borrowedBookId) {
		Optional<Borrowed_books> optionalBorrowedBook = borrowedBooksRepository.findById(borrowedBookId);
		if (optionalBorrowedBook.isPresent()) {
			Borrowed_books borrowedBook = optionalBorrowedBook.get();
			Book book = borrowedBook.getBook();

			Returned_books returnedBook = new Returned_books();
			returnedBook.setBook(book);
			returnedBook.setUser(borrowedBook.getUser());
			returnedBook.setTitle(borrowedBook.getTitle());
			returnedBook.setAuthor(borrowedBook.getAuthor());

			// Use the same date format as Borrowed_books
			returnedBook.setborrowedDate(borrowedBook.getborrowedDate());
			returnedBook.setreturnDate(LocalDate.now().format(dateFormatter));

			book.setQuantity(book.getQuantity() + 1);
			bookRepository.save(book);
			borrowedBooksRepository.delete(borrowedBook);
			return returnedBooksRepository.save(returnedBook);
		}
		return null;
	}

	public List<Borrowed_books> getAllBorrowedBooks() {
		return borrowedBooksRepository.findAll();
	}

	public List<Borrowed_books> getBorrowedBooksByUser(int userId) {
		return borrowedBooksRepository.findByUser_Id(userId);
	}

	public List<Returned_books> getAllReturnedBooks() {
		return returnedBooksRepository.findAll();
	}

	public List<Returned_books> getReturnedBooksByUser(int userId) {
		return returnedBooksRepository.findByUser_Id(userId);
	}
}