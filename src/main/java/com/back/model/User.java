package com.back.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Table(name = "users", uniqueConstraints = {
		@UniqueConstraint(columnNames = {
				"username"
		})
})
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

	@JsonManagedReference
	@OneToMany(mappedBy = "whoAddedIt")
	private List<Book> addedBooks;

	@JsonManagedReference
	@OneToMany(mappedBy = "userwhobuys")
	private List<Order> operations;

	@JsonManagedReference
	@OneToMany(mappedBy = "whoBoughtIt")
	private List<Book> boughtBooks;

    public List<Book> getBoughtBooks() {
		return boughtBooks;
	}

	public void setBoughtBooks(List<Book> boughtBooks) {
		this.boughtBooks = boughtBooks;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
		this.password = password;
	}

	public List<Book> getAddedBooks() {
		return addedBooks;
	}

	public void setAddedBooks(List<Book> addedBooks) {
		this.addedBooks = addedBooks;
	}

	public List<Order> getOperations() {
		return operations;
	}

	public void setOperations(List<Order> operations) {
		this.operations = operations;
	}
	public User() {

	}
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
}
