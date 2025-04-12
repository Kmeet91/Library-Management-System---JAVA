package project.library.entities;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String name;
    private String email;
    private String phone;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    @JsonIgnore
    private List<Borrowed_books> borrowedBooks;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    @JsonManagedReference
    private List<Returned_books> returnedBooks;

    public enum Role {
        ROLE_USER,
        ROLE_MANAGER,
        ROLE_ADMIN
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Transient // This field is not persisted in the database
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public User(int id, String username, String password, String name, String email, String phone, List<Borrowed_books> borrowedBooks, List<Returned_books> returnedBooks,
			Set<Role> roles) {
		super();
		this.id = id;
		this.username = username;
		this.password = passwordEncoder.encode(password);
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.borrowedBooks = borrowedBooks;
		this.returnedBooks = returnedBooks;
		this.roles = roles;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = passwordEncoder.encode(password);
		System.out.println(this.password);	
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<Borrowed_books> getBorrowedBooks() {
		return borrowedBooks;
	}

	public void setBorrowedBooks(List<Borrowed_books> borrowedBooks) {
		this.borrowedBooks = borrowedBooks;
	}

	public List<Returned_books> getReturnedBooks() {
		return returnedBooks;
	}

	public void setReturnedBooks(List<Returned_books> returnedBooks) {
		this.returnedBooks = returnedBooks;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", name=" + name + ", email="
				+ email + ", phone=" + phone + ", borrowedBooks=" + borrowedBooks + ", returnedBooks=" + returnedBooks + ", roles=" + roles + "]";
	}

    
}
