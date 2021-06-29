package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Library {

	@Id
	@JsonProperty("id")
	private final UUID id;
	private final List<Book> books = new ArrayList<>();
	private final List<User> users = new ArrayList<>();

	public Library(UUID id) {
		this.id = id;
	}

	public UUID getId() {
		return this.id;
	}

	public List<Book> getBooks() {
		return this.books;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void addBook(Book book) {
		this.books.add(book);
	}

	public void addUser(User user) {
		this.users.add(user);
	}

	public void deleteBook(Book book) {
		if (!this.books.contains(book)) {
			return;
		}

		if (book.isCheckedOut()) {
			for (User user : this.users) {
				if (user.hasCheckedOut(book.getId())) {
					user.removeBook(book);
					break;
				}
			}
		}
		this.books.remove(book);
	}

	public void deleteUser(User user) {
		if (!this.users.contains(user)) {
			return;
		}

		for (Book book : user.getBooksCheckedOut()) {
			book.returnBook();
		}
		this.users.remove(user);
	}
}
