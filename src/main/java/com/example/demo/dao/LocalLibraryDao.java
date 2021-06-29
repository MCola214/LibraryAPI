package com.example.demo.dao;

import com.example.demo.model.Book;
import com.example.demo.model.Library;
import com.example.demo.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("LocalDao")
public class LocalLibraryDao implements LibraryDao {

	private static final Library DB = new Library(UUID.randomUUID());

	@Override
	public List<Book> selectAllBooks() {
		return DB.getBooks();
	}

	@Override
	public int insertBook(UUID id, Book book) {
		DB.addBook(new Book(id, book.getTitle(), book.getAuthor(), book.isCheckedOut()));
		return 1;
	}

	@Override
	public List<User> selectAllUsers() {
		return DB.getUsers();
	}

	@Override
	public Optional<User> selectUserById(UUID id) {
		return DB.getUsers().stream().filter(user -> user.getId().equals(id)).findFirst();
	}

	@Override
	public int insertUser(UUID id, User user) {
		DB.addUser(new User(id, user.getUsername(), user.getBooksCheckedOut()));
		return 1;
	}

	@Override
	public Optional<Book> selectBookById(UUID id) {
		return DB.getBooks().stream().filter(book -> book.getId().equals(id)).findFirst();

	}

	@Override
	public int checkoutBookById(UUID bookId, UUID userId) {
		Optional<Book> bookOptional = this.selectBookById(bookId);
		Optional<User> userOptional = this.selectUserById(userId);

		if (bookOptional.isPresent() && userOptional.isPresent()) {
			Book book = bookOptional.get();
			User user = userOptional.get();

			if (!book.isCheckedOut()) {
				book.checkOut();
				user.addBook(book);
				return 1;
			}
		}
		return 0;
	}

	@Override
	public int returnBookById(UUID bookId, UUID userId) {
		Optional<Book> bookOptional = this.selectBookById(bookId);
		Optional<User> userOptional = this.selectUserById(userId);

		if (bookOptional.isPresent() && userOptional.isPresent()) {
			Book book = bookOptional.get();
			User user = userOptional.get();

			if (book.isCheckedOut() && user.hasCheckedOut(bookId)) {
				book.returnBook();
				user.removeBook(book);
				return 1;
			}
		}
		return 0;
	}

	@Override
	public int deleteBookById(UUID bookId) {
		Optional<Book> bookOptional = this.selectBookById(bookId);
		if (bookOptional.isPresent()) {
			Book book = bookOptional.get();
			DB.deleteBook(book);
			return 1;
		}
		return 0;
	}

	@Override
	public int deleteUserById(UUID userId) {
		Optional<User> userOptional = this.selectUserById(userId);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			DB.deleteUser(user);
			return 1;
		}
		return 0;
	}
}
