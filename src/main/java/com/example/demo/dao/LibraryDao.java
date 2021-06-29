package com.example.demo.dao;

import com.example.demo.model.Book;
import com.example.demo.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LibraryDao {

	List<Book> selectAllBooks();

	Optional<Book> selectBookById(UUID id);

	int insertBook(UUID id, Book book);

	default int insertBook(Book book) {
		UUID id = UUID.randomUUID();
		return insertBook(id, book);
	}

	List<User> selectAllUsers();

	Optional<User> selectUserById(UUID id);

	int insertUser(UUID id, User user);

	default int insertUser(User user) {
		UUID id = UUID.randomUUID();
		return insertUser(id, user);
	}

	int checkoutBookById(UUID bookId, UUID userId);

	int returnBookById(UUID bookId, UUID userId);

	int deleteBookById(UUID bookId);

	int deleteUserById(UUID userId);
}
