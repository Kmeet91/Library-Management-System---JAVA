package project.library.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String author;
    private String genre;
    @Column(name="publicationYear")
    private int publicationYear;
    private double price;
    private String language;
    private int quantity;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Borrowed_books> borrowedBooks;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getpublicationYear() {
        return publicationYear;
    }

    public void setpublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<Borrowed_books> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<Borrowed_books> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", author=" + author + ", genre=" + genre + ", publicationYear="
				+ publicationYear + ", price=" + price + ", language=" + language + ", quantity=" + quantity
				+ ", borrowedBooks=" + borrowedBooks + "]";
	}
    
    
}