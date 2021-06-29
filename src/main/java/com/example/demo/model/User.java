package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class User {

	@Id
	@JsonProperty("id")
	private final UUID id;

	@JsonProperty("username")
	private final String username;

	@JsonProperty("booksCheckedOut")
	private final List<Book> booksCheckedOut;

	public User(UUID id, String username, List<Book> booksCheckedOut) {
		this.id = id;
		this.username = username;
		this.booksCheckedOut = booksCheckedOut != null ? booksCheckedOut : new ArrayList<>();
	}

	public UUID getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public List<Book> getBooksCheckedOut() {
		return this.booksCheckedOut;
	}

	public void addBook(Book book) {
		this.booksCheckedOut.add(book);
	}

	public void removeBook(Book book) {
		this.booksCheckedOut.removeIf(b -> b.getId().equals(book.getId()));
	}

	public boolean hasCheckedOut(UUID bookId) {
		Optional<Book> checkedOutBook = this.booksCheckedOut.stream().filter(book -> book.getId().equals(bookId))
				.findFirst();
		return checkedOutBook.isPresent();
	}
}
